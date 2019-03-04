/**
 * 
 */
package com.sunrise.controller.cux;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunrise.controller.base.BaseController;

import net.sunrise.service.BookService;

/**
 * @author ducbq
 *
 */
@RequestMapping("/book")
@Controller
public class BookController extends BaseController {
	@Inject 
	private BookService bookService;

	@Override
	protected String loadDefault(Model model, HttpServletRequest request) {
		System.out.println("Book");
		System.out.println("Number of books in repository: " + bookService.count());
		return "pages/cspx/bookBrowse";
	}

}
