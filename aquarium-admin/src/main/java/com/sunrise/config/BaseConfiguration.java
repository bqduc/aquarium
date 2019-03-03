/**
 * 
 */
package com.sunrise.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ducbq
 *
 */
@EnableCaching
@Configuration
@EnableJpaRepositories({"com.sunrise"})
@ComponentScan({"com.sunrise", "net.sunrise"})
@EntityScan(basePackages={ "com.sunrise" })
@EnableTransactionManagement
public class BaseConfiguration {
}
