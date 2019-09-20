/**
 * 
 */
package net.sunrise.osx.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sunrise.common.ListUtility;

/**
 * @author bqduc
 *
 */
public class DataBucket {
	public final static String PARAM_INPUT_RESOURCE_NAME = "inputResourceName";
	public final static String PARAM_WORK_DATA_SHEET = "workDataSheet";
	public final static String PARAM_INPUT_STREAM = "sourceInputStream";
	public final static String PARAM_DATA_SHEETS = "dataSheets";
	public final static String PARAM_DATA_INDEXES = "dataIndexes";
	public final static String PARAM_STARTED_ROW_INDEX = "startedRowIndex";
	public final static String PARAM_ENCRYPTION_KEY = "encryptionKey";
	public final static String PARAM_LIMITED_COLUMNS = "limitedColumns";
	public final static String PARAM_LIMITED_ROWS = "limitedRows";

	private OfficeSuiteTarget suiteTargeted;
	private Map<Object, Object> bucketData = null;
	private List<Object> container;

	private DataBucket() {
		this.bucketData = new HashMap<>();
	}

	public DataBucket(Object[] values) {
		this.container = ListUtility.createArrayList();
		for (int idx = 0; idx < values.length; idx++) {
			this.container.add(values[idx]);
		}
	}

	public List<Object> getContainer() {
		return container;
	}

	public void setContainer(List<Object> container) {
		this.container = container;
	}

	public static DataBucket getInstance() {
		DataBucket dataBucket = new DataBucket();
		return dataBucket;
	}

	public static DataBucket getInstance(OfficeSuiteTarget suiteTargeted) {
		DataBucket dataBucket = new DataBucket();
		dataBucket.setSuiteTargeted(suiteTargeted);
		return dataBucket;
	}

	public Map<Object, Object> getBucketData() {
		return bucketData;
	}

	public Object getBucketedData(Object key){
		return bucketData.get(key);
	}

	public void setBucketData(Map<Object, Object> bucketData) {
		this.bucketData = bucketData;
	}

	public DataBucket putAll(Map<Object, Object> values) {
		this.bucketData.putAll(values);
		return this;
	}

	public DataBucket put(Object key, Object value) {
		this.bucketData.put(key, value);
		return this;
	}

	public Object get(Object key) {
		if (this.bucketData.containsKey(key))
			return this.bucketData.get(key);

		return null;
	}

	public Object pull(Object key) {
		if (this.bucketData.containsKey(key))
			return this.bucketData.remove(key);

		return null;
	}

	public OfficeSuiteTarget getSuiteTargeted() {
		return suiteTargeted;
	}

	public void setSuiteTargeted(OfficeSuiteTarget suiteTargeted) {
		this.suiteTargeted = suiteTargeted;
	}
}
