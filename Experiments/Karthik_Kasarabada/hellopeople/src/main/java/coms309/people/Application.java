package coms309.people;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Sample Spring Boot Application.
 * 
 * @author Vivek Bengre
 */
@EnableJpaRepositories(basePackages = {"coms309.people"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application {
	
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
