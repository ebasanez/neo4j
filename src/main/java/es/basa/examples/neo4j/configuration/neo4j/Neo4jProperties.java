package es.basa.examples.neo4j.configuration.neo4j;

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
@ConfigurationProperties("neo4j")
public class Neo4jProperties {

	private String host;
	private String username;
	private String password;

}
