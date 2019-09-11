/**
 * 
 */
package net.sunrise.engine.dropbox.factory;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.http.OkHttp3Requestor;
import com.dropbox.core.v2.DbxClientV2;

import net.sunrise.cdx.domain.entity.Configuration;
import net.sunrise.cdx.domain.entity.ConfigurationDetail;
import net.sunrise.cdx.service.ConfigurationService;
import net.sunrise.engine.CommonCdixConstants;
import net.sunrise.engine.dropbox.DropboxUser;
import net.sunrise.exceptions.DropboxException;
import net.sunrise.exceptions.MspDataException;
import net.sunrise.framework.component.RootComponent;

/**
 * @author bqduc_2
 *
 */
@Component
public class DropboxClientServiceFactory extends RootComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2862270388738645420L;

	private static DbxClientV2 sDbxClient;

	@Inject
	private ConfigurationService configurationService;

	public void init(DropboxUser dropboxUser) {
		DbxRequestConfig requestConfig = null;
		if (sDbxClient == null) {
			requestConfig = DbxRequestConfig.newBuilder(dropboxUser.getClientIdentifier())
					.withUserLocale(dropboxUser.getClientLocale())
					 .withHttpRequestor(new OkHttp3Requestor(OkHttp3Requestor.defaultOkHttpClient()))
					.build();

			sDbxClient = new DbxClientV2(requestConfig, dropboxUser.getAccessToken());
		}
	}

	public DbxClientV2 getDropboxClient() {
		if (sDbxClient == null) {
			throw new IllegalStateException("Client not initialized.");
		}
		return sDbxClient;
	}

	public Configuration initDropboxConfigurations() {
		Configuration drpbxConfig = null;
		Optional<Configuration> dbxConfiguration = Optional.empty();
		try {
			dbxConfiguration = configurationService.getBusinessObject(CommonCdixConstants.DROPBOX_CONFIG_NAME);
		} catch (MspDataException e) {
			log.error(e);
		}

		if (dbxConfiguration.isPresent()) {
			drpbxConfig = dbxConfiguration.get();
		} else {
			drpbxConfig = Configuration.builder()
					.group(CommonCdixConstants.DROPBOX_CONFIG_GROUP)
					.name(CommonCdixConstants.DROPBOX_CONFIG_NAME)
					.value("VE1CL2kpG8AAAAAAAAAADA47480I5HDoN84fxkzaKgQN9-X_CZxPOFGzJEivq9Xw") // Initial Access token
					.build();
			drpbxConfig.setActivated(Boolean.TRUE);
			drpbxConfig.setVisible(Boolean.TRUE);
		}

		if (drpbxConfig.getConfigurationDetails().isEmpty()) {
			ConfigurationDetail configDetail = ConfigurationDetail.builder()
			.name("clientIdentifier")
			.value(CommonCdixConstants.DROPBOX_CONFIG_CLIENT_ID)
			.valueExtended(CommonCdixConstants.DROPBOX_CONFIG_DIRECTORY_ID)
			.build();
			configDetail.setActivated(Boolean.TRUE);
			configDetail.setVisible(Boolean.TRUE);
			
			drpbxConfig.addConfigurationDetail(configDetail);
		}
		configurationService.saveOrUpdate(drpbxConfig);
		return drpbxConfig;
	}

	public DropboxUser getDropboxUser() throws DropboxException {
		String clientId = "";
		String workingDir = "/";
		Optional<Configuration> optDbxConfig = Optional.empty();
		DropboxUser dropboxUser = null;
		try {
			optDbxConfig = configurationService.getBusinessObject(CommonCdixConstants.DROPBOX_CONFIG_NAME);
			if (!optDbxConfig.isPresent()) {
				throw new DropboxException("There is no configuration for dropbox in repository. Please check and setup the configuration accordingly.");
			} 

			clientId = optDbxConfig.get().getConfigurationDetails().iterator().next().getValue();
			workingDir = optDbxConfig.get().getConfigurationDetails().iterator().next().getValueExtended();
			dropboxUser = DropboxUser.builder()
					.clientIdentifier(clientId)
					.clientLocale(optDbxConfig.get().getValueExtended())
					.accessToken(optDbxConfig.get().getValue())
					.workingDirectory(workingDir)
					.build();
		} catch (Exception e) {
			throw new DropboxException(e);
		}
		
		return dropboxUser;
	}
}
/*

	protected void initializeDropboxService() {
		String clientId = "";
		String workingDir = "/";
		Configuration dbxConfig = null;
		try {
			Optional<Configuration> optDbxConfig = configurationService.getBusinessObject(CommonCdixConstants.DROPBOX_CONFIG_NAME);
			if (!optDbxConfig.isPresent()) {
				log.error("There is no configuration for dropbox in repository. Please check and setup the configuration accordingly.");
				dbxConfig = dropboxClientServiceFactory.initDropboxConfigurations();
			} else {
				dbxConfig = optDbxConfig.get();
				if (dbxConfig.getConfigurationDetails().isEmpty()) {
					dbxConfig = dropboxClientServiceFactory.initDropboxConfigurations();
				}
			}
			clientId = dbxConfig.getConfigurationDetails().iterator().next().getValue();
			workingDir = dbxConfig.getConfigurationDetails().iterator().next().getValueExtended();
			DropboxUser dropboxUser = DropboxUser.builder()
					.clientIdentifier(clientId)
					.clientLocale(dbxConfig.getValueExtended())
					.accessToken(dbxConfig.getValue())
					.workingDirectory(workingDir)
					.build();
			dropboxClientServiceFactory.init(dropboxUser);
		} catch (Exception e) {
			log.error(e);
		}
	}
*/