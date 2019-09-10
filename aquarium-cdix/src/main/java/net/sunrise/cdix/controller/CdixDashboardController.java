/**
 * 
 */
package net.sunrise.cdix.controller;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sunrise.cdix.entity.ReserveResource;
import net.sunrise.cdix.service.ReserveResourceService;
import net.sunrise.cdx.domain.entity.Configuration;
import net.sunrise.cdx.service.ConfigurationService;
import net.sunrise.controller.base.BaseController;

/**
 * @author bqduc
 *
 */
@Controller
@RequestMapping("/cdix")
public class CdixDashboardController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -942920666885947692L;

	@Inject
	private ReserveResourceService reserveResourceService;

	@Inject
	private ConfigurationService configurationService;

	@RequestMapping(path = { "/", "" }, method = RequestMethod.GET)
	public String viewDashboard() {
		try {
			Optional<ReserveResource> reserveResource = reserveResourceService.getBusinessObject("Beginning Spring 5.pdf");
			System.out.println("Reserved resource: " + reserveResource.get());

			Optional<Configuration> dropboxConfig = configurationService.getBusinessObject("DRBX_token");
			System.out.println("Dropbox configuration: " + dropboxConfig.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Enter view CDIX dashboard");
		return "pages/cdix/dashboard";
	}
}
