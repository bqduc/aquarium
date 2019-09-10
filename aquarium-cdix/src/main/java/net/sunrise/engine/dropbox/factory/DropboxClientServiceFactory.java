/**
 * 
 */
package net.sunrise.engine.dropbox.factory;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import net.sunrise.cdx.domain.entity.Configuration;
import net.sunrise.cdx.domain.entity.ConfigurationDetail;
import net.sunrise.cdx.service.ConfigurationService;
import net.sunrise.engine.CommonCdixConstants;
import net.sunrise.engine.dropbox.DropboxUser;
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
					// .withHttpRequestor(new
					// OkHttp3Requestor(OkHttp3Requestor.defaultOkHttpClient()))
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

	public void initDropboxConfigurations() {
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
		}

		if (drpbxConfig.getConfigurationDetails().isEmpty()) {
			drpbxConfig.addConfigurationDetail(
					ConfigurationDetail.builder()
					.name("clientIdentifier")
					.value(CommonCdixConstants.DROPBOX_CONFIG_CLIENT_ID)
					.valueExtended(CommonCdixConstants.DROPBOX_CONFIG_DIRECTORY_ID)
					.build());
		}
		configurationService.saveOrUpdate(drpbxConfig);
	}
}
