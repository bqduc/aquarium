package net.sunrise.cdix.service.impl;

import java.io.InputStream;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.brilliance.common.CommonUtility;
import net.brilliance.deplyment.DeploymentSpecification;
import net.brilliance.domain.model.DataInterfaceModel;
import net.brilliance.domain.model.DataSourceType;
import net.brilliance.exceptions.ExecutionContextException;
import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.framework.model.ExecutionContext;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.brilliance.helper.GlobalDataServicesRepository;
import net.brilliance.model.base.IDataContainer;
import net.sunrise.cdix.entity.PersistenceResource;
import net.sunrise.cdix.repository.PersistenceResourceRepository;
import net.sunrise.cdix.repository.specification.PersistenceResourceRepoSpecification;
import net.sunrise.cdix.service.PersistenceResourceService;


@Service
public class PersistenceResourceServiceImpl extends GenericServiceImpl<PersistenceResource, Long> implements PersistenceResourceService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7761477574156308888L;

	@Inject 
	private PersistenceResourceRepository repository;
	
	protected BaseRepository<PersistenceResource, Long> getRepository() {
		return this.repository;
	}

	@Override
	public PersistenceResource getByName(String name) throws ObjectNotFoundException {
		return (PersistenceResource)super.getOptionalObject(repository.findByName(name));
	}

	@Override
	protected Page<PersistenceResource> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public Page<PersistenceResource> getObjects(SearchParameter searchParameter) {
		Page<PersistenceResource> pagedProducts = this.repository.findAll(PersistenceResourceRepoSpecification.buildSpecification(searchParameter), searchParameter.getPageable());
		//Perform additional operations here
		return pagedProducts;
	}

	@Override
	public ExecutionContext load(ExecutionContext executionContext) throws ExecutionContextException {
		GlobalDataServicesRepository globalDataServicesRepository = null;
		Object projectContextData = null;
		DataInterfaceModel dataInterfaceModel = null;
		IDataContainer<String> dataContainer = null;
		try {
			if (!(executionContext.containKey(DeploymentSpecification.DEPLOYMENT_DATA_KEY) || 
					executionContext.containKey(DeploymentSpecification.DEPLOYMENT_DATA_MODEL_KEY))){
				executionContext.setExecutionStage("There is not enough deployment specification for enterprise. ");
				log.info(executionContext.getExecutionStage());
				return executionContext;
			}

			globalDataServicesRepository = GlobalDataServicesRepository.builder().build();
			projectContextData = executionContext.getContextData(DeploymentSpecification.DEPLOYMENT_DATA_KEY);
			dataInterfaceModel = (DataInterfaceModel)executionContext.getContextData(DeploymentSpecification.DEPLOYMENT_DATA_MODEL_KEY);
			if (DataSourceType.CSV.equals(dataInterfaceModel.getDataSourceType())){
				dataContainer = globalDataServicesRepository.readCsvFile(
						(InputStream)projectContextData, 
						dataInterfaceModel.isProcessColumnHeaders(), 
						dataInterfaceModel.getComponentSeparator());
			} else if (DataSourceType.EXCEL.equals(dataInterfaceModel.getDataSourceType())) {
			}

			if (CommonUtility.isEmpty(dataContainer)){
				executionContext.setExecutionStage("The data container is empty. Please recheck data source. ");
				log.info(executionContext.getExecutionStage());
				return executionContext;
			}
			log.info("Start to deploy enterprise data ......");
			
		} catch (Exception e) {
			throw new ExecutionContextException(e);
		}

		executionContext.setExecutionStage("The data deployment for project is done. ");
		log.info(executionContext.getExecutionStage());
		return executionContext;
	}
}
