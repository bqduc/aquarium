/**
 * 
 */
package com.sunrise.controller.cux;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunrise.controller.base.BaseController;

/**
 * @author ducbq
 *
 */
@RequestMapping("/book")
@Controller
public class BookController extends BaseController {
	
	@Override
	protected String loadDefault(Model model, HttpServletRequest request) {
		return "pages/cspx/bookBrowse";
	}

}
