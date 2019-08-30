/**
 * 
 */
package net.brilliance.framework.deployment;

import net.brilliance.exceptions.ExecutionContextException;
import net.brilliance.framework.model.ExecutionContext;

/**
 * @author bqduc
 *
 */
public interface Deployment {
	void deploy(ExecutionContext executionContext) throws ExecutionContextException;
}
