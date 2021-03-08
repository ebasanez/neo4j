package es.basa.examples.neo4j.configuration.postgre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ebasanez
 * @since 2021-03-06
 */
@Configuration
public class PostgreSQLConfiguration {

	@Autowired
	private PostgreSQLProperties properties;

	@Bean
	public ConnectionFactory postgreDriver(){
		String url = properties.getHost()+"/"+properties.getDatabase();
		return new PostgreSessionFactory(url, properties.getUsername(),properties.getPassword());
	}

}
