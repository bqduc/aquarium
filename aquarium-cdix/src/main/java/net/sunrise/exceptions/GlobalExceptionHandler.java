/**
 * 
 */
package net.sunrise.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author bqduc
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MultipartException.class)
	public String handleError1(MultipartException multipartException, RedirectAttributes redirectAttributes) {

		redirectAttributes.addFlashAttribute("message", multipartException.getCause().getMessage());
		return "redirect:/uploadStatus";
	}

}
