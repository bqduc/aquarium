/**
 * 
 */
package net.sunrise.helper;

import org.springframework.stereotype.Component;

import net.sunrise.framework.component.BaseComponent;

/**
 * @author bqduc
 *
 */
@Component
public class GlobalRepositoryHelper extends BaseComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6567346477426198878L;

	private final String defaultDataDirectory = "/config/liquibase/data/";

	public String getDataDirectory(){
		return defaultDataDirectory;
	}
}
