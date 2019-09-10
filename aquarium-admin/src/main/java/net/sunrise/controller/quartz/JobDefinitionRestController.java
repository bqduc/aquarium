package net.sunrise.controller.quartz;

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
import net.sunrise.controller.base.BaseRestController;
import net.sunrise.domain.entity.schedule.JobDefinition;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.service.api.admin.quartz.JobDefinitionService;

@RestController
@RequestMapping("/" + ControllerConstants.REQUEST_URI_BACKGROUND_JOB)
public class JobDefinitionRestController extends BaseRestController<JobDefinition>{
	@Inject 
	private JobDefinitionService businessService;

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<JobDefinition> onListCatalogueSubtypes(HttpServletRequest request, HttpServletResponse response, Model model) {
		log.info("Rest::Come to job definition data listing ...>>>>>>");
		PageRequest pageRequest = PageRequest.of(0, 500, Sort.Direction.ASC, "id");
		SearchParameter searchParameter = SearchParameter.getInstance()
				.setPageable(pageRequest);
		Page<JobDefinition> objects = businessService.getObjects(searchParameter);
		log.info("Rest::Leave from job definition data loading. >>>>>>");
		return objects.getContent();
	}
}
