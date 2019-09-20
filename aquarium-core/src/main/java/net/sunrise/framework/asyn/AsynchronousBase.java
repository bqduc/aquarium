/**
 * 
 */
package net.sunrise.framework.asyn;

import java.util.GregorianCalendar;

import javax.inject.Inject;

import net.sunrise.framework.logging.LogService;
import net.sunrise.framework.model.ExecutionContext;

/**
 * @author bqduc_2
 *
 */
public abstract class AsynchronousBase implements Runnable {
	@Inject 
	protected LogService log;

	private ExecutionContext executionContext;

	public AsynchronousBase() {
		this.executionContext = ExecutionContext.builder().build();
	}

	@Override
	public void run() {
		log.info("Execute started at: " + GregorianCalendar.getInstance().getTime());
		executeAsync(ExecutionContext.builder().build());
		log.info("Execute finishaed at: " + GregorianCalendar.getInstance().getTime());
	}

	protected abstract void executeAsync(ExecutionContext executionContext);

	public ExecutionContext getExecutionContext() {
		return executionContext;
	}

	public void setExecutionContext(ExecutionContext executionContext) {
		this.executionContext = executionContext;
	}
}
