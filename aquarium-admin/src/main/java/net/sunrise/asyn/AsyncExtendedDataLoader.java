/**
 * 
 */
package net.sunrise.asyn;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.sunrise.common.DateTimeUtility;
import net.sunrise.framework.asyn.AsynchronousBase;
import net.sunrise.framework.model.ExecutionContext;

/**
 * @author bqduc_2
 *
 */
@Component
@Scope("prototype")
public class AsyncExtendedDataLoader extends AsynchronousBase {
	public AsyncExtendedDataLoader(ExecutionContext executionContext) {
		super();
	}

	protected void executeAsync(ExecutionContext executionContext) {
		log.info("Enter AsyncExtendedDataLoader::execute " + DateTimeUtility.getSystemDate());
		try {
			for (int idx=0; idx < 10000000; ++idx) {
				System.out.println("Index at: " + idx);
			}
		} catch (Exception e) {
			log.error(e);;
		}
		log.info("Leave AsyncExtendedDataLoader::execute " + DateTimeUtility.getSystemDate());
	}
}
