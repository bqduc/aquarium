/*
* Copyright 2017, Bui Quy Duc
* by the @authors tag. See the LICENCE in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package net.sunrise.config;

import javax.inject.Inject;
import javax.inject.Provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Enable spring security for this application that handles Authorization and Authentication
 * https://g00glen00b.be/securing-your-rest-api-with-spring-security/
 * @author bqduc
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Inject
	private Provider<UserDetailsService> userDetailsServiceProvider;

	@Inject
	private CustomLoginSuccessfulHandler loginSuccessfulHandler;

	@Inject
	private CustomLoginFailureHandler loginFailureHandler;

	@Inject
	private PasswordEncoder passwordEncoder;

	/**
	 * Inject a global parent for Spring Authentication Manager.
	 * 
	 * @param auth
	 * @throws Exception
	 * @see AuthenticationManager
	 */
	@Inject
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceProvider.get()).passwordEncoder(this.passwordEncoder);
	}

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
    	.antMatchers(buildUnauthorizedMatchers()).permitAll()
    	//.antMatchers("/*", "/forum/**", "/bootstrap/**", "/dist/**", "/js/**", "/plugins/**", "/bower_components/**", "/pages/**").permitAll()
      .anyRequest().authenticated()
    .and()
    .formLogin()
    .failureUrl("/login?error")
    .loginPage("/login")
    .failureHandler(loginFailureHandler)
    .successHandler(loginSuccessfulHandler)
    
    .defaultSuccessUrl("/")
    .permitAll()
    .and()
.logout()
    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    .logoutSuccessUrl("/login")
    .permitAll()
;

    loginSuccessfulHandler.setUseReferer(true);
    /*http.formLogin()
    .successHandler(loginSuccessfulHandler)
    .failureHandler(loginFailureHandler)
    ;*/
 	
		http
    //.csrf().disable()
    .authorizeRequests().anyRequest().authenticated()
    .antMatchers(ConfigurationConstants.REST_API + "**").hasRole("CLIENT")
    .and()
    	.httpBasic().realmName(ConfigurationConstants.AUTHENTICATION_REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
    	
    	.and().csrf().disable().headers().frameOptions().disable()
    ;

  }
	
  private String[] buildUnauthorizedMatchers(){
  	String[] unauthorizedPatterns = new String[] {
  			"/*",
  			"/image/**",
  			"/bootstrap/**",
  			"/dist/**",
  			"/js/**",
  			"/plugins/**",
  			"/bower_components/**",
  			"/pages/**",
  			"/book/**",
  			"/rapi/book/**",
  			"/forum/**",
  			"/thread/**",
  			"/post/**",
  			"/topic/**",
  			"/auth/register/**"
  	};
  	return unauthorizedPatterns;
  	/*List<String> unauthorizedMatchers = ListUtility.createArrayList();
  	unauthorizedMatchers.add("/*");
  	unauthorizedMatchers.add("/bootstrap/**");
  	unauthorizedMatchers.add("/dist/**");
  	unauthorizedMatchers.add("/js/**");
  	unauthorizedMatchers.add("/plugins/**");
  	unauthorizedMatchers.add("/bower_components/**");
  	unauthorizedMatchers.add("/pages/**");
  	unauthorizedMatchers.add("/book/**");
  	unauthorizedMatchers.add("/forum/**");
  	unauthorizedMatchers.add("/thread/**");
  	unauthorizedMatchers.add("/post/**");
  	unauthorizedMatchers.add("/topic/**");

  	unauthorizedMatchers.add("/auth/register/**");

  	return unauthorizedMatchers.toArray(new String[0]);*/
  }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers(HttpMethod.OPTIONS, "/**")
			// don't secure static resources
			.antMatchers("/static/**")
			.antMatchers("/js/**")
			.antMatchers("/css/**")
			.antMatchers("/plugins/**")
			.antMatchers("/bower_components/**")
			.antMatchers("/bootstrap/**")
			.antMatchers("/dist/**")
			.antMatchers("/templates/**")
			;
	}

  @Bean
  public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
      return new CustomBasicAuthenticationEntryPoint();
  }

  /*
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
      return new ProviderManager(Arrays.asList((AuthenticationProvider) new AuthProvider()));
  }
  */
}
