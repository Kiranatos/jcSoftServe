package net.kiranatos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Optional;
@SpringBootApplication
public class SpringDataJpaExamplesApplication {
	private static final Logger log = LoggerFactory.getLogger(SpringDataJpaExamplesApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaExamplesApplication.class, args);
	}
	@Bean
	public CommandLineRunner demo(AuthorRepository authorRepo, BookRepository bookRepo) {
		return (args) -> {
			Author author = new Author("Sam Erickson");
			authorRepo.save(author); //saves author to database
			Optional<Author> result = authorRepo.findById(author.getId()); //find one from db
			if(result.isPresent()) {
				System.out.println(result.get().getName());
			}
		};
	}
}