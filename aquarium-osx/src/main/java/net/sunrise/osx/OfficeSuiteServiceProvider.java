/**
 * 
 */
package net.sunrise.osx;

import java.io.InputStream;
import java.util.Map;

import lombok.Builder;
import net.brilliance.common.CommonUtility;
import net.brilliance.exceptions.EcosysException;
import net.sunrise.osx.engine.SpreadsheetXSSFEventBasedExtractor;
import net.sunrise.osx.model.DataBucket;

/**
 * @author ducbq
 *
 */
@Builder
public class OfficeSuiteServiceProvider {
	public DataBucket readXlsxData(Map<Object, Object> params) throws EcosysException {
		DataBucket bucket = null;
		String[] sheetNames = null;
		InputStream inputStream = null;
		if (CommonUtility.isEmpty(params))
			throw new EcosysException("The map of parameters is empty. ");

		try {
			inputStream = (InputStream)params.get(DataBucket.PARAM_INPUT_STREAM);
			sheetNames = (String[])params.get(DataBucket.PARAM_DATA_SHEETS);
			bucket = SpreadsheetXSSFEventBasedExtractor.getInstance(inputStream).extractData(sheetNames, params);
		} catch (Exception e) {
			throw new EcosysException("Extract spreadsheet data error. ", e);
		}
		return bucket;
	}
}
