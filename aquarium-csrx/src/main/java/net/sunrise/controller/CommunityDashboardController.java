/**
 * 
 */
package net.sunrise.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sunrise.controller.base.BaseController;

/**
 * @author ducbq
 *
 */
@Controller
@RequestMapping("/hrcx")
public class CommunityDashboardController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8100966561440298304L;

	@RequestMapping(path={"/", ""}, method=RequestMethod.GET)
	public String viewDashboard(){
		System.out.println("Enter view HRC dashboard");
		return "pages/hrcx/dashboard";
	}

}
