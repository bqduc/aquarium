/**
 * 
 */
package net.sunrise.config;

import java.util.Locale;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import lombok.extern.slf4j.Slf4j;
import net.sunrise.start.WebApplicationStarter;

/**
 * @author bqduc
 *
 */
@Slf4j
@EnableCaching
@Configuration
@EnableJpaRepositories(basePackages = {"net.sunrise"})
@ComponentScan(basePackages = {"net.sunrise"})
@EntityScan(basePackages={"net.sunrise"})
@EnableTransactionManagement
public class BaseConfiguration {
	@Inject
	private ApplicationContext applicationContext;
	
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
	 * i18n bean support for switching locale through a request param. <br />
	 * Users who are authenticated can change their default locale to another
	 * when they pass in a<br />
	 * url (http://example.com/&lt;contextpath&gt;/<em>lang=&lt;locale&gt;</em>)
	 * 
	 * @return
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
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

		@Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
		

		@Bean
		public ThreadPoolTaskExecutor taskExecutor() {
			ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
			taskExecutor.setCorePoolSize(5);
			taskExecutor.setMaxPoolSize(25);
			taskExecutor.setQueueCapacity(100);
			taskExecutor.initialize();
			return taskExecutor;
		}

		/*
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(true);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.addDialect(new SpringSecurityDialect()); 
        return templateEngine;
    }

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }
    */
		/*@Bean
		public SpringTemplateEngine templateEngine() {
		    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		    templateEngine.setTemplateResolver(thymeleafTemplateResolver());
		    templateEngine.setEnableSpringELCompiler(true);
		    templateEngine.addDialect(springSecurityDialect());
		    return templateEngine;
		}

		@Bean
		public SpringResourceTemplateResolver thymeleafTemplateResolver() {
		    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		    templateResolver.setPrefix("classpath:templates/");
		    templateResolver.setSuffix(".html");
		    templateResolver.setCacheable(false);
		    templateResolver.setTemplateMode(TemplateMode.HTML);
		    return templateResolver;
		}

		@Bean
		public ThymeleafViewResolver thymeleafViewResolver() {
		    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		    viewResolver.setTemplateEngine(templateEngine());
		    viewResolver.setCharacterEncoding("UTF-8");
		    return viewResolver;
		}*/
}
