package es.basa.examples.neo4j.configuration.postgre;

import java.sql.Connection;

/**
 * @author ebasanez
 * @since 2021-03-06
 */
public interface ConnectionFactory {
	Connection createConnection();
}
