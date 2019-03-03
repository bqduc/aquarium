/**
 * 
 */
package com.sunrise.controller.base;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ducbq
 *
 */
public abstract class BaseController extends RootController{
	protected static final String PAGE_POSTFIX_BROWSE = "Browse";
	protected static final String PAGE_POSTFIX_SHOW = "Show";
	protected static final String PAGE_POSTFIX_EDIT = "Edit";
	protected static final String REDIRECT =	"redirect:/";

	protected String loadDefault(Model model, HttpServletRequest request) {
		return "";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		return loadDefault(model, request);
	}

}
