/**
 * 
 */
package com.sunrise.controller.base;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.context.MessageSource;

import net.brilliance.framework.logging.LogService;

/**
 * @author ducbq
 *
 */
public abstract class RootController {
  protected static final String  DEFAULT_PAGE_SIZE = "100";
  protected static final String DEFAULT_PAGE_NUM = "0";

  @Inject
	protected LogService log;

	@Inject
	protected MessageSource messageSource;

	@Inject
	protected HttpSession httpSession;

	protected void cachePut(String key, Object data){
		this.httpSession.setAttribute(key, data);
	}

	protected Object cacheGet(String key){
		return this.httpSession.getAttribute(key);
	}

}
