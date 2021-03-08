package es.basa.examples.neo4j.configuration.neo4j;

import org.neo4j.driver.AuthToken;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Config;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ebasanez
 * @since 2021-03-06
 */
@Configuration
public class Neo4jConfiguration {

	@Autowired
	private Neo4jProperties properties;

	@Bean
	public Driver neo4jDriver() {
		AuthToken authToken = AuthTokens.basic(properties.getUsername(),properties.getPassword());
		return GraphDatabase.driver(properties.getHost(), authToken);
	}

}
