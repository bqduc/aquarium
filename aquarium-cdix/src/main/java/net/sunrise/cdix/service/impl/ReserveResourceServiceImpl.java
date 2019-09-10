package net.sunrise.cdix.service.impl;

import java.io.InputStream;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.sunrise.cdix.entity.ReserveResource;
import net.sunrise.cdix.repository.ReserveResourceRepository;
import net.sunrise.cdix.repository.specification.PersistenceResourceRepoSpecification;
import net.sunrise.cdix.service.ReserveResourceService;
import net.sunrise.common.CommonUtility;
import net.sunrise.deplyment.DeploymentSpecification;
import net.sunrise.domain.DataInterfaceModel;
import net.sunrise.domain.DataSourceType;
import net.sunrise.exceptions.ExecutionContextException;
import net.sunrise.exceptions.MspDataException;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.ExecutionContext;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.repository.BaseRepository;
import net.sunrise.framework.service.GenericServiceImpl;
import net.sunrise.helper.GlobalDataServicesRepository;
import net.sunrise.model.base.IDataContainer;


@Service
public class ReserveResourceServiceImpl extends GenericServiceImpl<ReserveResource, Long> implements ReserveResourceService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7761477574156308888L;

	@Inject 
	private ReserveResourceRepository repository;
	
	protected BaseRepository<ReserveResource, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Optional<ReserveResource> getByName(String name) throws ObjectNotFoundException {
		return repository.findByName(name);
	}

	@Override
	protected Page<ReserveResource> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public Page<ReserveResource> getObjects(SearchParameter searchParameter) {
		Page<ReserveResource> pagedProducts = this.repository.findAll(PersistenceResourceRepoSpecification.buildSpecification(searchParameter), searchParameter.getPageable());
		//Perform additional operations here
		return pagedProducts;
	}

	@Override
	protected Optional<ReserveResource> fetchBusinessObject(Object key) throws MspDataException {
		return super.getBizObject("findByName", key);
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
