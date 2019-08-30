/**
 * 
 */
package net.sunrise.cdix.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import net.sunrise.cdix.form.PersistenceResourceUploadForm;
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

	@RequestMapping(path = { "/", "" }, method = RequestMethod.GET)
	public String viewDashboard() {
		System.out.println("Enter view CDIX dashboard");
		return "pages/cdix/dashboard";
	}
}
