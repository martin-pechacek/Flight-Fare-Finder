package utility;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelUtils {
	private static final String EXCEL_PATH = System.getProperty("user.dir")+"//src//data//flights.xlsx";
	
	/**
	 * Method for writing data in the excel file
	 * 
	 * @param value - Value for writing in the excel file
	 * @throws Exception
	 */
	public static void writeData(String[] value, String sheetname) throws Exception {
		try {
			
			File file = new File(EXCEL_PATH);
			
			FileInputStream inputStream = new FileInputStream(file);
			
			XSSFWorkbook excelBook = new XSSFWorkbook(inputStream);
			
			Sheet excelSheet = excelBook.getSheet(sheetname);
						
			int rowCount = excelSheet.getLastRowNum() - excelSheet.getFirstRowNum();
			
			Row row = excelSheet.getRow(0);
			
			Row newRow = excelSheet.createRow(rowCount + 1);
			
			int test = row.getLastCellNum();
			
			for(int i=0; i < row.getLastCellNum(); i++) {
				Cell excelCell = newRow.createCell(i);
				excelCell.setCellValue(value[i]);
			}
			
			inputStream.close();
			
			FileOutputStream outputStream = new FileOutputStream(file);
			
			excelBook.write(outputStream);
			excelBook.close();

			outputStream.close();
		} catch (Exception ex) {
			throw(ex);
		}
	}
}
