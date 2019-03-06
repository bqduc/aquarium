/**
 * 
 */
package net.brilliance.framework.runnable;

import net.brilliance.framework.component.RootComponent;
import net.brilliance.framework.model.ExecutionContext;

/**
 * @author ducbq
 *
 */
public abstract class RunnableBase extends RootComponent implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7087174224187449778L;
	
	private ExecutionContext context;

	@Override
	public void run() {
		execute();
	}

	protected void execute(){		
	}

	public ExecutionContext getContext() {
		return context;
	}

	public void setContext(ExecutionContext context) {
		if (null==this.context){
			this.context = ExecutionContext
					.builder()
					.build();
		}
		this.context.buildFromOther(context);
	}
}
