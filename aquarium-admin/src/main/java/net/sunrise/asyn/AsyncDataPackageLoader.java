/**
 * 
 */
package net.sunrise.asyn;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.sunrise.common.DateTimeUtility;
import net.sunrise.framework.asyn.Asynchronous;
import net.sunrise.framework.model.ExecutionContext;

/**
 * @author bqduc_2
 *
 */
@Component
@Scope("prototype")
public class AsyncDataPackageLoader extends Asynchronous {
	public AsyncDataPackageLoader(ExecutionContext executionContext) {
		super();
	}

	protected void executeAsync(ExecutionContext executionContext) {
		log.info("Enter AsyncExtendedDataLoader::execute " + DateTimeUtility.getSystemDate());
		try {
			for (int idx=0; idx < 10000000 && super.isRunning(); ++idx) {
				System.out.println("Data package loader Idx: " + idx);
			}
		} catch (Exception e) {
			log.error(e);;
		}
		log.info("Leave AsyncExtendedDataLoader::execute " + DateTimeUtility.getSystemDate());
	}
}
