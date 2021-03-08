package es.basa.examples.neo4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.basa.examples.neo4j.repository.neo4j.MigrationRepository;

/**
 * @author ebasanez
 * @since 2021-03-06
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private MigrationRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args).close();
	}


	@Override
	public void run(String... args) throws Exception {
		System.out.println("Application started");
		repository.migrate("products", "products", "Products");
		System.out.println("Application finished");
	}
}
