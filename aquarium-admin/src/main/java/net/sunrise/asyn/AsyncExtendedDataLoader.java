/**
 * 
 */
package net.sunrise.asyn;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.sunrise.common.DateTimeUtility;
import net.sunrise.framework.asyn.AsynchronousBase;
import net.sunrise.framework.model.ExecutionContext;
import net.sunrise.osx.OfficeSuiteServiceProvider;
import net.sunrise.utility.ClassPathResourceUtility;

/**
 * @author bqduc_2
 *
 */
@Component
@Scope("prototype")
public class AsyncExtendedDataLoader extends AsynchronousBase {
  @Inject
	private OfficeSuiteServiceProvider officeSuiteServiceProvider;

	public AsyncExtendedDataLoader(ExecutionContext executionContext) {
		super();
	}

	protected void executeAsync(ExecutionContext executionContext) {
		log.info("Enter AsyncExtendedDataLoader::execute " + DateTimeUtility.getSystemDate());
		try {
			System.out.println("Started load default configuration data at: " + DateTimeUtility.getSystemDateTime());
			officeSuiteServiceProvider.loadDefaultConfigureData(ClassPathResourceUtility.builder().build().getFile("config/data/develop_data.zip"));
			System.out.println("Finished load default configuration data at: " + DateTimeUtility.getSystemDateTime());
			/*
			for (int idx=0; idx < 10000000; ++idx) {
				System.out.println("Index at: " + idx);
			}
			*/
		} catch (Exception e) {
			log.error(e);;
		}
		log.info("Leave AsyncExtendedDataLoader::execute " + DateTimeUtility.getSystemDate());
	}
}
