/**
 * 
 */
package net.sunrise.manager.auth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.sunrise.cdx.domain.entity.Configuration;
import net.sunrise.cdx.manager.ConfigurationManager;
import net.sunrise.common.CommonUtility;
import net.sunrise.domain.AuthorityGroup;
import net.sunrise.domain.DefaultAccessRightAction;
import net.sunrise.domain.entity.admin.Authority;
import net.sunrise.domain.entity.admin.UserAccount;
import net.sunrise.domain.entity.security.AccessRight;
import net.sunrise.domain.entity.security.Module;
import net.sunrise.domain.entity.security.Permission;
import net.sunrise.enums.DefaultConfigurations;
import net.sunrise.exceptions.AuthenticationException;
import net.sunrise.framework.logging.LogService;
import net.sunrise.service.api.admin.UserAuthenticationService;

/**
 * @author bqduc
 *
 */

@Service
public class AuthenticationServiceManager implements Serializable{
	private static final long serialVersionUID = 6272702669192923840L;
	@Inject 
	private LogService log;

	@Inject
	private AuthorityManager authorityManager;

	@Inject
	private AccessRightManager accessRightService;

	@Inject
	private UserAuthenticationService userAuthenticationService;

	@Inject
	private ModuleManager moduleManager;

	@Inject
	private AccessRightManager accessRightManager;

	@Inject
	private PermissionManager permissionManager;

	@Inject
	private ConfigurationManager configurationManager;

	public UserAccount authenticate(String principal, String credential) throws AuthenticationException{
		return userAuthenticationService.authenticate(principal, credential);
	}

	public UserAccount getUser(String userToken) throws AuthenticationException{
		return userAuthenticationService.getUser(userToken);
	}

	public UserAccount save(UserAccount user)  throws AuthenticationException{
		return userAuthenticationService.save(user);
	}

	public Map<String, Authority> getAuthorityMap(){
		Map<String, Authority> authoritiesMap = new HashMap<>();
		List<Authority> authorities = authorityManager.getAll();
		for (Authority authority :authorities){
			authoritiesMap.put(authority.getName(), authority);
		}
		return authoritiesMap;
	}

	public void initializeMasterData() throws AuthenticationException {
		log.info("Setup default master data for user sub-module. ");
		userAuthenticationService.initializeMasterData();

		log.info("Setup default master data for access right sub-module. ");
		accessRightService.initializeMasterData();

		log.info("Setup default master data for function/module sub-module. ");
		moduleManager.initializeMasterData();

		setupPermissionsByExample();
		log.info("Setup default permission data. ");
	}


	protected void setupPermissionsByExample(){
		Optional<Configuration> optSetupPermission = configurationManager.getByName(DefaultConfigurations.setupPerms.getConfigurationName());
		if (optSetupPermission.isPresent() && 
				!CommonUtility.BOOLEAN_STRING_FALSE.equalsIgnoreCase(optSetupPermission.get().getValue())){
			log.info("Default permissions is setup already. ");
			return;
		}

		AccessRight viewAccessRight = accessRightManager.getByName(DefaultAccessRightAction.View.getAction());
		AccessRight createAccessRight = accessRightManager.getByName(DefaultAccessRightAction.Create.getAction());
		AccessRight modifyAccessRight = accessRightManager.getByName(DefaultAccessRightAction.Modify.getAction());
		AccessRight deleteAccessRight = accessRightManager.getByName(DefaultAccessRightAction.Delete.getAction());

		List<Module> modules = moduleManager.getAll();

		Authority adminRole = authorityManager.getByName(AuthorityGroup.RoleAdmin.getCode());
		Authority clientRole = authorityManager.getByName(AuthorityGroup.RoleClient.getCode());
		for (Module module :modules){
			this.permissionManager.save(Permission.getInstance(adminRole, module, viewAccessRight));
			this.permissionManager.save(Permission.getInstance(adminRole, module, createAccessRight));
			this.permissionManager.save(Permission.getInstance(adminRole, module, modifyAccessRight));
			this.permissionManager.save(Permission.getInstance(adminRole, module, deleteAccessRight));
		}

		for (Module module :modules){
			this.permissionManager.save(Permission.getInstance(clientRole, module, viewAccessRight));
			this.permissionManager.save(Permission.getInstance(clientRole, module, createAccessRight));
			this.permissionManager.save(Permission.getInstance(clientRole, module, modifyAccessRight));
			this.permissionManager.save(Permission.getInstance(clientRole, module, deleteAccessRight));
		}

		Configuration setupPermission = Configuration.builder().name(DefaultConfigurations.setupPerms.getConfigurationName()).value(CommonUtility.BOOLEAN_STRING_TRUE).build();
		configurationManager.save(setupPermission);
		/**
		 * 1. Load all modules
		 * 2. For administrator, allow all objects from all modules
		 * 3. For corporate client, allow full permission on defined from UI
		 */
	}
}
