/**
 * 
 */
package net.sunrise.osx.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.Builder;

/**
 * @author bqduc
 *
 */
@Builder
public class WorkbookContainer {
	@Builder.Default
	private Map<Object, WorksheetContainer> worksheets = new HashMap<>();

	public WorksheetContainer getWorksheet(Object key) {
		if (!this.worksheets.containsKey(key))
			return null;

		return this.worksheets.get(key);
	}

	public WorkbookContainer put(Object key, WorksheetContainer worksheet) {
		this.worksheets.put(key, worksheet);
		return this;
	}

	public Set<?> getKeys(){
		return this.worksheets.keySet();
	}

	public Collection<WorksheetContainer> getValues(){
		return this.worksheets.values();
	}
}
