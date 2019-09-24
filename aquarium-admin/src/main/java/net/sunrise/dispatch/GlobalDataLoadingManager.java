/**
 * 
 */
package net.sunrise.dispatch;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import net.sunrise.common.DateTimeUtility;
import net.sunrise.exceptions.ExecutionContextException;
import net.sunrise.framework.component.RootComponent;
import net.sunrise.framework.model.ExecutionContext;

/**
 * @author ducbq
 *
 */
@Component
public class GlobalDataLoadingManager extends RootComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2918455795495882165L;

	@Async
	public void loadExtendedData(ExecutionContext executionContext) throws ExecutionContextException {
		log.info("Enter GlobalDataLoadingManager::loadExtendedData: " + DateTimeUtility.getSystemDate());
		try {
			Thread.sleep(90000);
		} catch (InterruptedException e) {
			throw new ExecutionContextException(e);
		}
		log.info("Leave GlobalDataLoadingManager::loadExtendedData: " + DateTimeUtility.getSystemDate());
	}
}
