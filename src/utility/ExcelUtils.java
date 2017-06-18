package utility;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utility.City;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

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
	public static void writeData(String[] value, String originCity) throws Exception {
		try {
			
			File file = new File(EXCEL_PATH);
			
			City city = new City(originCity);
			
			FileInputStream inputStream = new FileInputStream(file);
			
			XSSFWorkbook excelBook = new XSSFWorkbook(inputStream);
			
			Sheet excelSheet = excelBook.getSheet(city.getCountry());
						
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
	
	/**
	 * Method reading in excel file and adding airline depending on origin city and destination
	 * in the ArrayList<String>
	 * 
	 * @param originCity
	 * @return airlines
	 * @throws IOException
	 */
	public static ArrayList<String> readAirlines(String originCity, String destination) throws IOException{
		ArrayList<String> airlines = new ArrayList<>();
		
		File file = new File(EXCEL_PATH);
		
		City city = new City(originCity);
		
		FileInputStream inputStream = new FileInputStream(file);
		
		XSSFWorkbook excelBook = new XSSFWorkbook(inputStream);
		
		Sheet excelSheet = excelBook.getSheet(city.getCountry());
		
		int rowCount = excelSheet.getLastRowNum() - excelSheet.getFirstRowNum();
		
	    for (int i = 0; i < rowCount+1; i++) {
	        Row row = excelSheet.getRow(i);
	        String excelFrom = row.getCell(0).getStringCellValue().toLowerCase();
	        String excelTo = row.getCell(2).getStringCellValue().toLowerCase();
	                	        
        	if(excelFrom.contains(originCity.toLowerCase()) && excelTo.contains(destination.toLowerCase())){       		
        		airlines.add(row.getCell(3).getStringCellValue());
        	}
	    }
	    
	    return airlines;
	}
}
