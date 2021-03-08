package es.basa.examples.neo4j.repository.neo4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;
import org.neo4j.driver.Values;
import org.springframework.stereotype.Component;

import es.basa.examples.neo4j.configuration.neo4j.Neo4jProperties;
import es.basa.examples.neo4j.configuration.postgre.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ebasanez
 * @since 2021-03-06
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MigrationRepository {

	private static final Function<String, String> QUERY_PARAM_FORMATTER = s -> String.format("n.%1$s=$%1$s", s);

	private final Driver neo4jDriver;
	private final ConnectionFactory postgreConnectionFactory;

	public void migrate(String database, String table, String nodeLabel) throws SQLException {

		// Retrieve data from RDBMS
		try (Connection connection = postgreConnectionFactory.createConnection()) {
			ResultSet resultSet = connection.prepareStatement("SELECT * FROM " + table).executeQuery();

			// Create nodes for each record retrieved:
			ResultSetMetaData meta = resultSet.getMetaData();
			int columnCount = meta.getColumnCount();
			List<String> columnNames = new ArrayList<>();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = meta.getColumnName(i);
				columnNames.add(columnName);
			}
			String queryParams = columnNames.stream().map(QUERY_PARAM_FORMATTER).collect(Collectors.joining(","));

			int numRecords = 0;
			SessionConfig sessionConfig = SessionConfig.builder().withDatabase(database).build();
			try (Session session = neo4jDriver.session(sessionConfig)) {
				while (resultSet.next()) {
					/** Lambda function equivalent to functional interface {@link TransactionWork<Integer>}  **/
					int result = session.writeTransaction(tx -> {
						Map<String, Object> params = new HashMap<>();
						try {
							for (int i = 0; i < columnCount; i++) {
								params.put(columnNames.get(i), resultSet.getObject(i + 1));
							}
						} catch (SQLException e) {
							log.error("Error reading object from resultSet", e);
							return 0;
						}
						tx.run("CREATE (n:" + nodeLabel + ") SET " + queryParams, Values.value(params));
						return 1;
					});

					numRecords += result;

				}
				log.info("End neo4j operations. Created {} {} nodes", numRecords, nodeLabel);
			}
			log.info("neo4j session closed");
		}
		log.info("sql session closed");
	}

}
