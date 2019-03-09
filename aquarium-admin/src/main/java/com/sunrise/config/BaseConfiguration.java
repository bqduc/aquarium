/**
 * 
 */
package com.sunrise.config;

import java.util.Locale;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.sunrise.start.WebApplicationStarter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ducbq
 *
 */
@Slf4j
@EnableCaching
@Configuration
@EnableJpaRepositories(basePackages = {"com.sunrise", "net.sunrise", "net.brilliance"})
@ComponentScan(basePackages = {"com.sunrise", "net.sunrise", "net.brilliance"})
@EntityScan(basePackages={ "com.sunrise", "net.sunrise", "net.brilliance"})
@EnableTransactionManagement
public class BaseConfiguration {
	/**
	 * {@link PasswordEncoder} bean.
	 * 
	 * @return <b>{@code BCryptPasswordEncoder}</b> with strength (passed as
	 *         argument) the log rounds to use, between 4 and 31
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	  public MessageSource messageSource() {
	  	String[] resourceBundles = new String[]{
	  			"classpath:/i18n/messages-menu", 
	    		"classpath:/i18n/messages-stock", 
	    		"classpath:/i18n/messages-catalog", 
	    		"classpath:/i18n/messages-general",
	    		"classpath:/i18n/messages-hrcx",
	    		"classpath:/i18n/messages-master",
	    		"classpath:/i18n/messages-contact",
	    		"classpath:/i18n/messages",
	    		"classpath:/i18n/messages-crx",
	    		"classpath:/i18n/messages-admin"
	    };
	  	
	  	log.info("Initialize the message source......");
	  	
	  	ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	      messageSource.setBasenames(resourceBundles);
	      messageSource.setDefaultEncoding("UTF-8");
	      return messageSource;
	  }

	  /**
		 * i18n support bean. The locale resolver being used is Cookie.<br />
		 * When locale is changed and intercepted by the
		 * {@link WebApplicationStarter#localeChangeInterceptor localeChangeInterceptor}.
		 * <br />
		 * The new locale is stored in a Cookie and remains active even after
		 * session timeout<br />
		 * or session being invalidated
		 * <p>
		 * Set a fixed Locale to <em>US</em> that this resolver will return if no
		 * cookie found.
		 * </p>
		 * 
		 * @return {@code LocaleResolver}
		 * @see WebApplicationStarter#localeChangeInterceptor
		 */
		@Bean
		public LocaleResolver localeResolver() {
			CookieLocaleResolver clr = new CookieLocaleResolver();
			//clr.setDefaultLocale(Locale.US);
			clr.setDefaultLocale(getDefaultLocale());
			return clr;
		}

		private Locale getDefaultLocale(){
			return new Locale("vi", "VN");
		}
}
