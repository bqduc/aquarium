package net.sunrise.controller.catalogue;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.sunrise.controller.ControllerConstants;
import net.sunrise.controller.base.BaseRestController;
import net.sunrise.domain.entity.general.Catalogue;
import net.sunrise.manager.catalog.CatalogManager;

@RestController
@RequestMapping("/" + ControllerConstants.REQUEST_MAPPING_CATALOG)
public class CatalogueRestController extends BaseRestController <Catalogue>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7509368451679468647L;
	@Inject 
	private CatalogManager businessManager;

	@RequestMapping(path = "/listCatalogues", method = RequestMethod.GET)
	public List<Catalogue> onListCatalogues() {
		return businessManager.getAll();
	}
}
