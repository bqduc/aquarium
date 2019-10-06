/**
 * 
 */
package net.sunrise.deployment;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import net.sunrise.cdx.domain.entity.Configuration;
import net.sunrise.cdx.domain.entity.ConfigurationDetail;
import net.sunrise.cdx.service.ConfigurationService;
import net.sunrise.exceptions.DataLoadingException;
import net.sunrise.osx.model.OfficeDataPackage;

/**
 * @author ducbui
 * 
 *
 */
@Component
public class InventoryProfileDataDeploymentHelper {
	private final static String DATA_LOADING_GROUP = "DATA_LOADING";
	private final static String DATA_LOADING_INVENTORY_PROFILE = "inventoryProfileLoadingConfig";

	@Inject
	private ConfigurationService configurationService;

	public Configuration initConfigurations() throws DataLoadingException {
		Optional<Configuration> optConfig = configurationService.getOne(DATA_LOADING_INVENTORY_PROFILE);
		Configuration contactConfig = null;
		if (!optConfig.isPresent()) {
			contactConfig = buildContactConfig();
			configurationService.saveOrUpdate(contactConfig);
		} else {
			contactConfig = optConfig.get();
		}
		return contactConfig;
	}

	private Configuration buildContactConfig() {
		Configuration contactConfig = Configuration.builder()
				.group(DATA_LOADING_GROUP)
				.name(DATA_LOADING_INVENTORY_PROFILE)
				.value("default")
				.build();
		buildContactConfigDetails(contactConfig);
		return contactConfig;
	}

	private void buildContactConfigDetails(Configuration contactConfig){
		contactConfig.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("code").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("lastName").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("unit").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("departmentCode").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("departmentName").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("dateOfBirth").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("gender").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("status").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("designationCode").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("designationName").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("address").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("priorityPhone").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("workPhone").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("homePhone").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("mobilePhone").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("fax").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("otherPhone").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("priorityEmail").value("0").build())
		.addConfigurationDetail(ConfigurationDetail.builder().configuration(contactConfig).name("personalEmail").value("0").build())
		;
	}

	public void loadDataFrom(OfficeDataPackage officeDataPackage) throws DataLoadingException {
		
	}
}
