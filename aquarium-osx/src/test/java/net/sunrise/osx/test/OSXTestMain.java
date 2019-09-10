/**
 * 
 */
package net.sunrise.osx.test;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import net.sunrise.common.ListUtility;
import net.sunrise.osx.OfficeSuiteServiceProvider;
import net.sunrise.osx.model.DataBucket;

/**
 * @author bqduc
 *
 */
public class OSXTestMain {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		doTestReadXlsx();
	}

	protected static void doTestReadXlsx() {
		Map<Object, Object> params = ListUtility.createMap();
		String[] sheetIds = new String[]{/*"languages", "items", "localized-items"*/"online-books", "Forthcoming"}; 
		DataBucket dataBucket = null;
		String dataSheet = "D:\\workspace\\aquariums.git\\aquarium\\aquarium-admin\\src\\main\\resources\\config\\data\\data-catalog.xlsx";
		try {
			params.put(DataBucket.PARAM_INPUT_STREAM, new FileInputStream(dataSheet));
			params.put(DataBucket.PARAM_DATA_SHEETS, sheetIds);
			params.put(DataBucket.PARAM_STARTED_ROW_INDEX, new Integer[] {1, 1, 1});
			dataBucket = OfficeSuiteServiceProvider.builder()
			.build()
			.readXlsxData(params);
			List<?> details = null;
			List<?> forthcomingBooks = (List<?>)dataBucket.getBucketData().get("Forthcoming");
			List<?> onlineBooks = (List<?>)dataBucket.getBucketData().get("online-books");
			for (Object currentItem :forthcomingBooks) {
				System.out.println(currentItem);
			}

			for (Object currentItem :onlineBooks) {
				details = (List<?>)currentItem;
				System.out.println(details.size());
			}
			//System.out.println(dataBucket.getBucketData().get("Forthcoming"));
			//System.out.println(dataBucket.getBucketData().get("online-books"));
		} catch (Exception e) {
			e.printStackTrace();;
		}

	}
}
