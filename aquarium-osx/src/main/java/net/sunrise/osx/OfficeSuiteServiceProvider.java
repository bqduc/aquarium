/**
 * 
 */
package net.sunrise.osx;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.poifs.filesystem.FileMagic;

import lombok.Builder;
import net.sunrise.common.CommonUtility;
import net.sunrise.exceptions.EcosysException;
import net.sunrise.osx.engine.SpreadsheetXSSFEventBasedExtractor;
import net.sunrise.osx.engine.XSSFEventDataHelper;
import net.sunrise.osx.model.DataBucket;

/**
 * @author bqduc
 *
 */
@Builder
public class OfficeSuiteServiceProvider {
	protected static void detectType() {
		try {
			InputStream checkInputStream = null;
			InputStream inputStream = null;
			String dir = "C:\\Users\\ducbq\\Downloads\\data_sheets";
			File directory = new File(dir);
			for (File file :directory.listFiles()) {
				inputStream = new FileInputStream(file);
				checkInputStream = FileMagic.prepareToCheckMagic(inputStream);
				if (FileMagic.OOXML.equals(FileMagic.valueOf(checkInputStream))){
					System.out.println("Xlsx =>\t" + file.getName());
				} else {
					System.out.println("Xls =>\t" + file.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
  }

	public DataBucket readXlsxData(Map<Object, Object> params) throws EcosysException {
		DataBucket bucket = null;
		String[] sheetNames = null;
		InputStream inputStream = null;
		if (CommonUtility.isEmpty(params))
			throw new EcosysException("The map of parameters is empty. ");

		try {
			inputStream = (InputStream) params.get(DataBucket.PARAM_INPUT_STREAM);
			sheetNames = (String[]) params.get(DataBucket.PARAM_DATA_SHEETS);
			bucket = SpreadsheetXSSFEventBasedExtractor.getInstance(inputStream).extractData(sheetNames, params);
		} catch (Exception e) {
			throw new EcosysException("Extract spreadsheet data error. ", e);
		}
		return bucket;
	}

	public DataBucket readXlsx(Map<?, ?> params) throws EcosysException {
		DataBucket bucket = null;
		String[] sheetNames = null;
		InputStream inputStream = null;
		try {
			bucket = XSSFEventDataHelper.instance(params).parseXlsxData();
		} catch (Exception e) {
			throw new EcosysException("Extract spreadsheet data error. ", e);
		}
		return bucket;
	}

	public static void main(String[] args) throws Exception {
		String encryptKey = "thanhcongx";
		String fileName = "C:\\Users\\ducbq\\Downloads\\data_sheets/Danh bạ toàn hệ thống Ngân hàng LIENVIET POST BANK_Khoảng 3.000.xlsx";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(DataBucket.PARAM_ENCRYPTION_KEY, encryptKey);
		params.put(DataBucket.PARAM_INPUT_STREAM, new FileInputStream(fileName));
		OfficeSuiteServiceProvider.builder()
		.build()
		.readXlsx(params);
		/*
		String file = "C:\\Users\\ducbq\\Downloads\\data_sheets.zip";
		List<InputStream> inputStreams = CommonUtility.getZipFileInputStreams(new File(file));
		DataBucket dataBucket = null;
		Map<Object, Object> params = ListUtility.createMap();
		for (InputStream inputStream :inputStreams) {
			params.put(DataBucket.PARAM_INPUT_STREAM, inputStream);
			//params.put(DataBucket.PARAM_DATA_SHEETS, sheetIds);
			params.put(DataBucket.PARAM_STARTED_ROW_INDEX, new Integer[] {1, 1, 1});
			dataBucket = OfficeSuiteServiceProvider.builder()
			.build()
			.readXlsxData(params);
			System.out.println(dataBucket);
		}
		*/
	}
}
