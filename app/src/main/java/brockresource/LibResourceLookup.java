package brockresource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class LibResourceLookup {

    public static void main(String[] args) {
        SpringApplication.run(LibResourceLookup.class, args);
    }
}
