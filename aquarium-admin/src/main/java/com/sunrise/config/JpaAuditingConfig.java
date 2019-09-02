/**
 * 
 */
package com.sunrise.config;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import net.brilliance.common.CommonConstants;
import net.brilliance.common.CommonUtility;
import net.brilliance.framework.logging.LogService;

/**
 * @author bqduc
 *
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {
	@Inject
	private LogService log;

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new AuditorAware<String>() {
			@Override
			public Optional<String> getCurrentAuditor() {
				String fetchedResult = CommonConstants.STRING_BLANK;
				try {
					if (SecurityContextHolder.getContext().getAuthentication() != null) {
						Authentication auth = SecurityContextHolder.getContext().getAuthentication();
						Object principal = auth.getPrincipal();
						org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User)principal;
						System.out.println(authUser);
						fetchedResult = authUser.getUsername();
					} 
				} catch (Exception e) {
					log.error(CommonUtility.getStackTrace(e));
				}
				log.debug("Current auditor: " + fetchedResult);
				return Optional.of(fetchedResult);
			}
		};
	}
}
