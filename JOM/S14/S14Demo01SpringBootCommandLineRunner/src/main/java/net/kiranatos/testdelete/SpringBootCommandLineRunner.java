package net.kiranatos.testdelete;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class SpringBootCommandLineRunner implements CommandLineRunner {

    private static Logger LOG = LoggerFactory
            .getLogger(SpringBootCommandLineRunner.class);

    public static void main(String[] args) {
        LOG.info("STARTING : Spring boot application starting");
        SpringApplication.run(SpringBootCommandLineRunner.class, new String[]{"ichi","ni","san"});
        LOG.info("STOPPED  : Spring boot application stopped");
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("EXECUTING : command line runner");

        LOG.info("ARGS : - {}", Arrays.toString(args));

        for(int i=0;i<=10;i++){
            LOG.info("Count ="+i);
        }
    }

}