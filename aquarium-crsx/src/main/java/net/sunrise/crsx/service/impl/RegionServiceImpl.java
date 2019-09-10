package net.sunrise.crsx.service.impl;

import java.io.InputStream;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.sunrise.common.CommonUtility;
import net.sunrise.crsx.domain.entity.Region;
import net.sunrise.crsx.repository.RegionRepository;
import net.sunrise.crsx.service.RegionService;
import net.sunrise.deplyment.DeploymentSpecification;
import net.sunrise.domain.DataInterfaceModel;
import net.sunrise.domain.DataSourceType;
import net.sunrise.exceptions.ExecutionContextException;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.ExecutionContext;
import net.sunrise.framework.repository.BaseRepository;
import net.sunrise.framework.service.GenericServiceImpl;
import net.sunrise.helper.GlobalDataServicesRepository;
import net.sunrise.model.base.IDataContainer;


@Service
public class RegionServiceImpl extends GenericServiceImpl<Region, Long> implements RegionService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7761477574156308888L;

	@Inject 
	private RegionRepository repository;
	
	protected BaseRepository<Region, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Region getOne(String name) throws ObjectNotFoundException {
		return (Region)super.getOptionalObject(repository.findByName(name));
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
				executionContext.setExecutionStage("There is not enough deployment specification for Region. ");
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
			log.info("Start to deploy Region data ......");
			
		} catch (Exception e) {
			throw new ExecutionContextException(e);
		}

		executionContext.setExecutionStage("The data deployment for project is done. ");
		log.info(executionContext.getExecutionStage());
		return executionContext;
	}
}
