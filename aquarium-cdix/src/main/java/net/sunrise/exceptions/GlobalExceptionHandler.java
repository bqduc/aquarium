/**
 * 
 */
package net.sunrise.exceptions;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.sunrise.common.CommonUtility;
import net.sunrise.framework.logging.LogService;

/**
 * @author bqduc
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	@Inject
	protected LogService log;

	@ExceptionHandler(MultipartException.class)
	public String handleError1(MultipartException multipartException, RedirectAttributes redirectAttributes) {
		log.debug("=================Multipart Exception=================");
		log.debug(CommonUtility.buildStackTrace(multipartException));
		log.debug("=============End of multipart exception=============");
		redirectAttributes.addFlashAttribute("message", multipartException.getCause().getMessage());
		return "redirect:/uploadStatus";
	}
}
