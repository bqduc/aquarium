/**
 * 
 */
package net.sunrise.osx;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.monitorjbl.xlsx.StreamingReader;

import lombok.Builder;
import net.sunrise.common.ListUtility;
import net.sunrise.exceptions.EcosysException;
import net.sunrise.osx.model.BucketContainer;
import net.sunrise.osx.model.WorkbookContainer;
import net.sunrise.osx.model.WorksheetContainer;

/**
 * @author ducbq
 *
 */
@Builder
public class OfficeStreamingReaderHealper {

	public WorkbookContainer readXlsx(Map<?, ?> parameters) throws EcosysException {
		InputStream inputStream = null;
		Workbook workbook = null;

		List<String> sheetIds = null;
		WorksheetContainer worksheet = null;
		WorkbookContainer workbookContainer = WorkbookContainer.builder().build();
		List<Object> dataRow = null;
		try {
			inputStream = (InputStream)parameters.get(BucketContainer.PARAM_INPUT_STREAM);
			sheetIds = (List<String>)parameters.get(BucketContainer.PARAM_DATA_SHEET_IDS);
			if (parameters.containsKey(BucketContainer.PARAM_ENCRYPTION_KEY)) {
				workbook = StreamingReader.builder()
						.rowCacheSize(100)
						.bufferSize(4096)
						.password((String)parameters.get(BucketContainer.PARAM_ENCRYPTION_KEY))
						.open(inputStream);
			} else {
				workbook = StreamingReader.builder()
						.rowCacheSize(100)
						.bufferSize(4096)
						.open(inputStream);
			}

			for (Sheet sheet : workbook) {
				if (!sheetIds.contains(sheet.getSheetName()))
					continue;

				worksheet = WorksheetContainer.builder()
						.id(sheet.getSheetName())
						.build();
				for (Row currentRow : sheet) {
					dataRow = ListUtility.createArrayList();
					for (Cell currentCell : currentRow) {
						if (null==currentCell || CellType._NONE.equals(currentCell.getCellType()) || CellType.BLANK.equals(currentCell.getCellType())) {
							dataRow.add("");
						} else if (CellType.BOOLEAN.equals(currentCell.getCellType())) {
							dataRow.add(currentCell.getBooleanCellValue());
						} else if (CellType.FORMULA.equals(currentCell.getCellType())) {
							
						} else if (CellType.NUMERIC.equals(currentCell.getCellType())) {
							dataRow.add(currentCell.getNumericCellValue());
						} else if (CellType.STRING.equals(currentCell.getCellType())) {
							dataRow.add(currentCell.getStringCellValue());
						}
					}
					worksheet.addDataRows(Integer.valueOf(currentRow.getRowNum()), dataRow);
				}
				workbookContainer.put(worksheet.getId(), worksheet);
			}
		} catch (Exception e) {
			throw new EcosysException(e);
		}
		return workbookContainer;
	}
}