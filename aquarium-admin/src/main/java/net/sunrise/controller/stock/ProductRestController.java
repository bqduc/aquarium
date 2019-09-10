package net.sunrise.controller.stock;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.sunrise.controller.ControllerConstants;
import net.sunrise.domain.entity.stock.Product;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.service.api.inventory.ProductService;

@RestController
@RequestMapping(ControllerConstants.REQUEST_URI_PRODUCT)
public class ProductRestController {
	@Inject
	private ProductService serviceProvider;

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Product> list(HttpServletRequest request, HttpServletResponse response, Model model) {
		PageRequest pageRequest = PageRequest.of(0, 500, Sort.Direction.ASC, "id");
		SearchParameter searchParameter = SearchParameter.getInstance()
				.setPageable(pageRequest);
		Page<Product> objects = serviceProvider.getObjects(searchParameter);
		System.out.println("COME PRODUCT LOADING >>>>>>");
		return objects.getContent();
	}
}
