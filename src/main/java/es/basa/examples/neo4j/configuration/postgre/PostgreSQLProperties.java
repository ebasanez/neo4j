package es.basa.examples.neo4j.configuration.postgre;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ebasanez
 * @since 2021-03-06
 */
@Getter
@Setter
@Component
@ConfigurationProperties("postgre")
public class PostgreSQLProperties {

	private String host;
	private String database;
	private String username;
	private String password;

}
