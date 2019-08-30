/**
 * 
 */
package net.sunrise.crsx.controller.master;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sunrise.controller.base.BaseController;

/**
 * @author bqduc
 *
 */
@Controller
@RequestMapping("/crsx")
public class MasterDashboardController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -942920666885947692L;

	@RequestMapping(path={"/", ""}, method=RequestMethod.GET)
	public String viewDashboard(){
		System.out.println("-->Enter view CRSX dashboard");
		return "pages/hrcx/dashboard";
	}

}
