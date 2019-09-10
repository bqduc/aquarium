/**
 * 
 */
package net.sunrise.deployment;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import net.sunrise.constants.CommonManagerConstants;
import net.sunrise.exceptions.ExecutionContextException;
import net.sunrise.framework.deployment.Deployment;
import net.sunrise.framework.deployment.DeploymentBase;
import net.sunrise.framework.model.ExecutionContext;
import net.sunrise.service.api.dmx.ProjectService;

/**
 * @author bqduc
 *
 */
@Component
public class ProjectDeployment extends DeploymentBase implements Deployment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6388000854548763374L;

	@Inject
	private ProjectService projectService;

	@Override
	public void deploy(ExecutionContext executionContext) throws ExecutionContextException {
		super.log.info("Enter ProjectDeployment::deploy ");
		if (executionContext.containKey(CommonManagerConstants.CONTEXT_DATA)){
			projectService.count();
		}
		super.log.info("Leave ProjectDeployment::deploy ");
	}

}
