/**
 * 
 */
package net.sunrise.framework.deployment;

import net.sunrise.exceptions.ExecutionContextException;
import net.sunrise.framework.model.ExecutionContext;

/**
 * @author bqduc
 *
 */
public interface Deployment {
	void deploy(ExecutionContext executionContext) throws ExecutionContextException;
}
