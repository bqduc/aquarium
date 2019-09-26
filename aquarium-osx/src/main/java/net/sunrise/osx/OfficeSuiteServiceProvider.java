/**
 * 
 */
package net.sunrise.osx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.poifs.filesystem.FileMagic;

import lombok.Builder;
import net.sunrise.common.CommonUtility;
import net.sunrise.common.ListUtility;
import net.sunrise.exceptions.EcosysException;
import net.sunrise.osx.engine.SpreadsheetXSSFEventBasedExtractor;
import net.sunrise.osx.engine.XSSFEventDataHelper;
import net.sunrise.osx.model.BucketContainer;
import net.sunrise.osx.model.OfficeDocumentType;
import net.sunrise.osx.model.OfficeMarshalType;
import net.sunrise.osx.model.WorkbookContainer;
import net.sunrise.osx.model.WorksheetContainer;

/**
 * @author bqduc
 *
 */
@Builder
public class OfficeSuiteServiceProvider {
	protected OfficeDocumentType detectOfficeDocumentType(InputStream inputStream) throws EcosysException {
		OfficeDocumentType excelSheetType = OfficeDocumentType.INVALID;
		InputStream checkInputStream = null;
		checkInputStream = FileMagic.prepareToCheckMagic(inputStream);
		try {
			if (FileMagic.OOXML.equals(FileMagic.valueOf(checkInputStream))){
				excelSheetType = OfficeDocumentType.XSSF_WORKBOOK;
			} else {
				excelSheetType = OfficeDocumentType.HSSF_WORKBOOK;
			}
		} catch (IOException e) {
			throw new EcosysException(e);
		}
		return excelSheetType;
  }

	protected BucketContainer readXlsxData(Map<Object, Object> params) throws EcosysException {
		BucketContainer bucket = null;
		String[] sheetNames = null;
		InputStream inputStream = null;
		if (CommonUtility.isEmpty(params))
			throw new EcosysException("The map of parameters is empty. ");

		try {
			inputStream = (InputStream) params.get(BucketContainer.PARAM_INPUT_STREAM);
			sheetNames = (String[]) params.get(BucketContainer.PARAM_DATA_SHEET_IDS);
			bucket = SpreadsheetXSSFEventBasedExtractor.getInstance(inputStream).extractData(sheetNames, params);
		} catch (Exception e) {
			throw new EcosysException("Extract spreadsheet data error. ", e);
		}
		return bucket;
	}

	protected WorkbookContainer readXlsxByEventHandler(final Map<?, ?> params) throws EcosysException {
		return XSSFEventDataHelper
				.instance(params)
				.readXlsx();
	}
	
	protected WorkbookContainer readXlsxByStreaming(final Map<?, ?> params) throws EcosysException {
		return OfficeStreamingReaderHealper
				.builder().build()
				.readXlsx(params);
	}

	public WorkbookContainer readExcelFile(final Map<?, ?> parameters) throws EcosysException {
		WorkbookContainer workbookContainer = null;
		OfficeMarshalType officeMarshalType = OfficeMarshalType.STREAMING;
		if (parameters.containsKey(BucketContainer.PARAM_EXCEL_MARSHALLING_TYPE)) {
			officeMarshalType = (OfficeMarshalType)parameters.get(BucketContainer.PARAM_EXCEL_MARSHALLING_TYPE);
		}

		if (OfficeMarshalType.EVENT_HANDLER.equals(officeMarshalType)) {
			workbookContainer = this.readXlsxByEventHandler(parameters);
		} else if (OfficeMarshalType.STREAMING.equals(officeMarshalType)) {
			workbookContainer = this.readXlsxByStreaming(parameters);
		} else {
			//Not implemented yet
		}
		return workbookContainer;
	}

	public BucketContainer readOfficeDataInZip(final Map<String, Object> parameters) throws EcosysException {
		BucketContainer bucketContainer = BucketContainer.instance();
		File zipFile = null;
		Map<String, InputStream> zipInputStreams = null;
		Map<String, Object> processingParameters = ListUtility.createMap();
		OfficeDocumentType officeDocumentType = OfficeDocumentType.INVALID;
		WorkbookContainer workbookContainer = null;
		InputStream zipInputStream = null;
		Map<String, List<String>> sheetIdsMap = null;
		List<String> worksheetIds = null;
		Map<String, String> passwordMap = null;
		try {
			zipFile = (File)parameters.get(BucketContainer.PARAM_COMPRESSED_FILE);
			zipInputStreams = CommonUtility.extractZipInputStreams(zipFile, (List<String>)parameters.get(BucketContainer.PARAM_ZIP_ENTRY));
			if (zipInputStreams.isEmpty()) {
				return bucketContainer;
			}

			passwordMap = (Map)parameters.get(BucketContainer.PARAM_ENCRYPTION_KEY);
			sheetIdsMap = (Map)parameters.get(BucketContainer.PARAM_DATA_SHEET_IDS);
			for (String zipEntry :zipInputStreams.keySet()) {
				zipInputStream  = zipInputStreams.get(zipEntry);
				officeDocumentType = detectOfficeDocumentType(zipInputStream);
				if (!OfficeDocumentType.isExcelDocument(officeDocumentType)) {
					continue;
				}

				worksheetIds = (List<String>)sheetIdsMap.get(zipEntry);
				processingParameters.putAll(parameters);
				processingParameters.remove(BucketContainer.PARAM_COMPRESSED_FILE);
				processingParameters.put(BucketContainer.PARAM_INPUT_STREAM, zipInputStream);
				processingParameters.put(BucketContainer.PARAM_DATA_SHEET_IDS, worksheetIds);
				processingParameters.put(BucketContainer.PARAM_ENCRYPTION_KEY, (String)passwordMap.get(zipEntry));
				workbookContainer = readExcelFile(processingParameters);
				if (null != workbookContainer) {
					bucketContainer.put(zipEntry, workbookContainer);
				}
			}
		} catch (Exception e) {
			throw new EcosysException(e);
		}
		return bucketContainer;
	}

	protected void buildStockData() {
		
	}

	public static void main(String[] args) throws Exception {
		String encryptKey = "thanhcong";
		Map<String, Object> params = ListUtility.createMap();
		
		Map<String, String> secretKeyMap = ListUtility.createMap("Mobifone_36890.xlsx", "thanhcongbqduc", "Vietbank_14.000.xlsx", "thanhcong");
		Map<String, List<String>> sheetIdMap = ListUtility.createMap();
		sheetIdMap.put("Bieu thue XNK 2019.07.11.xlsx", ListUtility.arraysAsList(new String[] {"BIEU THUE 2019"}));
		sheetIdMap.put("Mobifone_36890.xlsx", ListUtility.arraysAsList(new String[] {"Mobifone", "Viettel"}));
		sheetIdMap.put("Vietbank_14.000.xlsx", ListUtility.arraysAsList(new String[] {"File Tổng hợp", "Các trưởng phó phòng", "9"}));
		sheetIdMap.put("Danh sách các tài liệu.xlsx", ListUtility.arraysAsList(new String[] {"Ebook (Sách điện tử)"}));
		
		String zipFileName = "D:/development_data/data_sheets.zip";
		params.put(BucketContainer.PARAM_COMPRESSED_FILE, new File(zipFileName));
		params.put(BucketContainer.PARAM_ENCRYPTION_KEY, secretKeyMap);
		params.put(BucketContainer.PARAM_ZIP_ENTRY, ListUtility.arraysAsList(new String[] {
				"Bieu thue XNK 2019.07.11.xlsx", 
				"Danh sách các tài liệu.xlsx", 
				"Mobifone_36890.xlsx", 
				"Vietbank_14.000.xlsx"}));
		params.put(BucketContainer.PARAM_EXCEL_MARSHALLING_TYPE, OfficeMarshalType.STREAMING);
		params.put(BucketContainer.PARAM_DATA_SHEET_IDS, sheetIdMap);
		BucketContainer bucketContainer = OfficeSuiteServiceProvider
				.builder()
				.build()
				.readOfficeDataInZip(params);
		WorkbookContainer workbookContainer = null;
		Set<Object> keys = bucketContainer.getKeys();
		for (Object key :keys) {
			workbookContainer = (WorkbookContainer)bucketContainer.get(key);
			//System.out.println(workbookContainer.getValues().size());
			displayWorkbookContainer(workbookContainer);
		}

		if (null != keys)
			return;

		long started = System.currentTimeMillis();
		String fileName = "C:/Users/ducbq/Downloads/data_sheets/[FILE GỐC] 100.000 Khách hàng PG Bank.xlsx";//"C:/Users/ducbq/Downloads/data_sheets/Danh bạ toàn hệ thống Ngân hàng Vietinbank_Khoảng 14.000.xlsx";//"C:\\Users\\ducbq\\Downloads\\data_sheets\\Bieu thue XNK 2019.07.11 (SongAnhLogs).xlsx";//"C:\\Users\\ducbq\\Downloads\\data_sheets/Danh bạ toàn hệ thống Ngân hàng LIENVIET POST BANK_Khoảng 3.000.xlsx";
		//params.put(BucketContainer.PARAM_ENCRYPTION_KEY, encryptKey);
		//params.put("BIEU THUE 2019" + BucketContainer.PARAM_STARTED_ROW_INDEX, 0);
		params.put(BucketContainer.PARAM_INPUT_STREAM, new FileInputStream(zipFileName));
		workbookContainer = OfficeSuiteServiceProvider.builder()
		.build()
		.readXlsxByStreaming(params);
		long duration = System.currentTimeMillis()-started;

		keys = bucketContainer.getKeys();
		for (Object key :keys) {
			workbookContainer = (WorkbookContainer)bucketContainer.get(key);
			System.out.println(workbookContainer.getValues().size());
			//displayWorkbookContainer(workbookContainer);
		}
		System.out.println("Event: " + duration);

		params.put(BucketContainer.PARAM_INPUT_STREAM, new FileInputStream(fileName));
		started = System.currentTimeMillis();
		workbookContainer = OfficeSuiteServiceProvider.builder()
				.build()
				.readXlsxByEventHandler(params);
		duration = System.currentTimeMillis()-started;
		System.out.println("---------------------------------------------------------------------");
		System.out.println("Streaming: " + duration);
		workbookContainer = null;
		for (Object key :bucketContainer.getKeys()) {
			workbookContainer = (WorkbookContainer)bucketContainer.get(key);
			System.out.println(workbookContainer.getValues().size());
			displayWorkbookContainer(workbookContainer);
		}

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
	
	protected static void displayWorkbookContainer(WorkbookContainer workbookContainer) {
		for (WorksheetContainer worksheetContainer :workbookContainer.getValues()) {
			System.out.println("Sheet: " + worksheetContainer.getId());
			for (List<?> dataRow :worksheetContainer.getValues()) {
				System.out.println(dataRow);
			}
			System.out.println("============================DONE==============================");
		}
	}
}
