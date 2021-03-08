package es.basa.examples.neo4j.configuration.postgre;

import java.sql.Connection;
import java.sql.DriverManager;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

/**
 * @author ebasanez
 * @since 2021-03-06
 */
@RequiredArgsConstructor
public class PostgreSessionFactory implements ConnectionFactory {
	private final String url;
	private final String username;
	private final String password;

	@Override
	@SneakyThrows
	public Connection createConnection() {
		return DriverManager.getConnection(url, username, password);
	}

}
