/*package com.sunrise.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.sunrise.config.BaseConfiguration;

@Configuration
@SpringBootApplication(scanBasePackages = "com.sunrise.*")
@Import(value = { 
		BaseConfiguration.class
})
@EnableJpaRepositories({"com.sunrise"})
@ComponentScan({"com.sunrise.*", "net.sunrise.*"})
@EntityScan(basePackages={ "com.sunrise" })

public class ApplicationStarter extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStarter.class, args);
	}
}
*/