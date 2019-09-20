/**
 * 
 */
package net.sunrise.edix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sunrise.controller.base.BaseController;

/**
 * @author bqduc
 *
 */
@Controller
@RequestMapping("/cdix")
public class EdixDashboardController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1404535580419372050L;

	@RequestMapping(path = { "/", "" }, method = RequestMethod.GET)
	public String viewDashboard() {
		System.out.println("Enter view EDIX dashboard");
		return "pages/cdix/dashboard";
	}
}
