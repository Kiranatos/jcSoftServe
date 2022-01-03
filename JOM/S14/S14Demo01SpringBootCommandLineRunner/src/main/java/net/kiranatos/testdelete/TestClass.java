package net.kiranatos.testdelete;

//import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@AllArgsConstructor
public class TestClass implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TestClass.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n\nRunning Spring Boot Application\n\n");
    }
}
