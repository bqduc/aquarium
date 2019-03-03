package com.sunrise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication/*(scanBasePackages = "com.sunrise.*")*/
/*@Import(value = { 
		BaseConfiguration.class
})*/
/*@EnableJpaRepositories({"com.sunrise"})
@ComponentScan({"com.sunrise.*", "net.sunrise.*"})
@EntityScan(basePackages={ "com.sunrise" })
*/
public class AquariumApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AquariumApplication.class, args);
	}
}
