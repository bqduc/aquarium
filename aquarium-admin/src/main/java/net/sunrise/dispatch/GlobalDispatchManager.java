/**
 * 
 */
package net.sunrise.dispatch;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import net.sunrise.cdx.domain.entity.Configuration;
import net.sunrise.cdx.service.ConfigurationService;
import net.sunrise.engine.CommonCdixConstants;
import net.sunrise.engine.dropbox.DropboxUser;
import net.sunrise.engine.dropbox.factory.DropboxClientServiceFactory;
import net.sunrise.framework.component.RootComponent;

/**
 * @author bqduc_2
 *
 */
@Component
public class GlobalDispatchManager extends RootComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1032979812928994399L;

	@Inject
	private ConfigurationService configurationService;
	
	@Inject
	private DropboxClientServiceFactory dropboxClientServiceFactory;

	@EventListener(ApplicationReadyEvent.class)
	public void onAfterStartup() {
		initializeDropboxService();
		log.info("Dropbox service initialize is done. ");
	}

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
}
