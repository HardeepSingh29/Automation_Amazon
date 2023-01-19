package com.vs.libraries;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

public class Xls_Reader {

	public String path;
	public FileInputStream fInputStream = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook xssfWorkbook = null;
	private XSSFSheet xssfSheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	public Xls_Reader(String path) {

		this.path = path;
		try {
			fInputStream = new FileInputStream(path);
			xssfWorkbook = new XSSFWorkbook(fInputStream);
			xssfSheet = xssfWorkbook.getSheetAt(0);
			fInputStream.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void getWorkSheetNames(String path, String sheetName) throws Exception {

		fInputStream = new FileInputStream(path);
		xssfWorkbook = new XSSFWorkbook(fInputStream);
		xssfSheet = xssfWorkbook.getSheet(sheetName);
		// xssfSheet = xssfWorkbook.getSheetAt(0);
		// xssfSheet oWorkSheet = oWorkbook.getSheet(sheetName);
	}

	public Xls_Reader() {

		super();

	}

	// create excel file at specified path
	public void createExcelFile(String path, String sheetName, String headerArgs) {

		try {
			this.path = path;
			Path filePath = Paths.get(path);
			if (Files.exists(filePath)) {
				System.out.println("File already exists!");
				fInputStream = new FileInputStream(path);
				xssfWorkbook = new XSSFWorkbook(fInputStream);
			} else {
				System.out.println("Creating File...");
				fileOut = new FileOutputStream(path);
				xssfWorkbook = new XSSFWorkbook();
				xssfSheet = xssfWorkbook.createSheet(sheetName);
				// xssfSheet = xssfWorkbook.getSheet(sheetName);
				// fileOut = new FileOutputStream(path);
				Row row = xssfSheet.createRow(0);
				// Create a new font and alter it.
				CellStyle style = xssfWorkbook.createCellStyle();
				XSSFFont font = xssfWorkbook.createFont();
				font.setFontHeightInPoints((short) 11);
				font.setFontName("Calibri");
				font.setItalic(true);
				((XSSFFont) font).setBold(true);
				style.setFont(font);
				style.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());
				// style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
				style.setFillPattern(CellStyle.SOLID_FOREGROUND);
				// Create a cell and put a value in it.
				String arr[] = headerArgs.split(",");
				for (int i = 0; i < arr.length; i++) {
					Cell cell = row.createCell(i);
					cell.setCellValue(arr[i]);
					cell.setCellStyle(style);
				}

				xssfWorkbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// return xssfWorkbook;

	}

	// returns the row count in a xssfSheet
	public int getRowCount(String sheetName) {
		int index = xssfWorkbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			xssfSheet = xssfWorkbook.getSheetAt(index);
			int number = xssfSheet.getLastRowNum() + 1;
			return number;
		}

	}

	// returns the data from a cell
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = xssfWorkbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			xssfSheet = xssfWorkbook.getSheetAt(index);
			row = xssfSheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";

			xssfSheet = xssfWorkbook.getSheetAt(index);
			row = xssfSheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {

					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

				}

				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	// returns the data from a cell
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = xssfWorkbook.getSheetIndex(sheetName);

			if (index == -1)
				return "";

			xssfSheet = xssfWorkbook.getSheetAt(index);
			row = xssfSheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

				}

				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
			fInputStream = new FileInputStream(path);
			xssfWorkbook = new XSSFWorkbook(fInputStream);

			if (rowNum <= 0)
				return false;

			int index = xssfWorkbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			xssfSheet = xssfWorkbook.getSheetAt(index);

			row = xssfSheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				// System.out.println(row.getCell(i).getStringCellValue().trim());
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;

			xssfSheet.autoSizeColumn(colNum);
			row = xssfSheet.getRow(rowNum - 1);
			if (row == null)
				row = xssfSheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			cell.setCellValue(data);

			fileOut = new FileOutputStream(path);

			xssfWorkbook.write(fileOut);

			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// returns true if data is set successfully else false
	public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {

		try {
			fInputStream = new FileInputStream(path);
			xssfWorkbook = new XSSFWorkbook(fInputStream);

			if (rowNum <= 0)
				return false;

			int index = xssfWorkbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;

			xssfSheet = xssfWorkbook.getSheetAt(index);

			row = xssfSheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {

				if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
					colNum = i;
			}

			if (colNum == -1)
				return false;
			xssfSheet.autoSizeColumn(colNum);
			row = xssfSheet.getRow(rowNum - 1);
			if (row == null)
				row = xssfSheet.createRow(rowNum - 1);

			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);

			cell.setCellValue(data);
			XSSFCreationHelper createHelper = xssfWorkbook.getCreationHelper();

			// cell style for hyperlinks

			CellStyle hlink_style = xssfWorkbook.createCellStyle();
			XSSFFont hlink_font = xssfWorkbook.createFont();
			hlink_font.setUnderline(XSSFFont.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);
			// hlink_style.setWrapText(true);

			XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
			link.setAddress(url);
			cell.setHyperlink(link);
			cell.setCellStyle(hlink_style);

			fileOut = new FileOutputStream(path);
			xssfWorkbook.write(fileOut);

			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// returns true if xssfSheet is created successfully else false
	public boolean addSheet(String sheetname) {

		FileOutputStream fileOut;
		try {
			xssfWorkbook.createSheet(sheetname);
			fileOut = new FileOutputStream(path);
			xssfWorkbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// returns true if xssfSheet is removed successfully else false if xssfSheet
	// does not exist
	public boolean removeSheet(String sheetName) {
		int index = xssfWorkbook.getSheetIndex(sheetName);
		if (index == -1)
			return false;

		FileOutputStream fileOut;
		try {
			xssfWorkbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			xssfWorkbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// returns true if column is created successfully
	public boolean addColumn(String sheetName, String colName) {

		try {
			fInputStream = new FileInputStream(path);
			xssfWorkbook = new XSSFWorkbook(fInputStream);
			int index = xssfWorkbook.getSheetIndex(sheetName);
			if (index == -1)
				return false;

			XSSFCellStyle style = xssfWorkbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

			xssfSheet = xssfWorkbook.getSheetAt(index);

			row = xssfSheet.getRow(0);
			if (row == null)
				row = xssfSheet.createRow(0);

			if (row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());

			cell.setCellValue(colName);
			cell.setCellStyle(style);

			fileOut = new FileOutputStream(path);
			xssfWorkbook.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	// removes a column and all the contents
	public boolean removeColumn(String sheetName, int colNum) {
		try {
			if (!isSheetExist(sheetName))
				return false;
			fInputStream = new FileInputStream(path);
			xssfWorkbook = new XSSFWorkbook(fInputStream);
			xssfSheet = xssfWorkbook.getSheet(sheetName);
			XSSFCellStyle style = xssfWorkbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			XSSFCreationHelper createHelper = xssfWorkbook.getCreationHelper();
			style.setFillPattern(HSSFCellStyle.NO_FILL);

			for (int i = 0; i < getRowCount(sheetName); i++) {
				row = xssfSheet.getRow(i);
				if (row != null) {
					cell = row.getCell(colNum);
					if (cell != null) {
						cell.setCellStyle(style);
						row.removeCell(cell);
					}
				}
			}
			fileOut = new FileOutputStream(path);
			xssfWorkbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	// find whether sheets exists
	public boolean isSheetExist(String sheetName) {
		int index = xssfWorkbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = xssfWorkbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}

	// returns number of columns in a xssfSheet
	public int getColumnCount(String sheetName) {
		// check if xssfSheet exists
		if (!isSheetExist(sheetName))
			return -1;

		xssfSheet = xssfWorkbook.getSheet(sheetName);
		row = xssfSheet.getRow(0);

		if (row == null)
			return -1;

		return row.getLastCellNum();

	}

	// String sheetName, String testCaseName,String keyword ,String URL,String
	// message
	public boolean addHyperLink(String sheetName, String screenShotColName, String testCaseName, int index, String url,
			String message) {

		url = url.replace('\\', '/');
		if (!isSheetExist(sheetName))
			return false;

		xssfSheet = xssfWorkbook.getSheet(sheetName);

		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, 0, i).equalsIgnoreCase(testCaseName)) {

				setCellData(sheetName, screenShotColName, i + index, message, url);
				break;
			}
		}

		return true;
	}

	public int getCellRowNum(String sheetName, String colName, String cellValue) {

		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;

	}

	// to run this on stand alone
	public static void main(String arg[]) throws IOException {

		Xls_Reader datatable = null;

		datatable = new Xls_Reader(
				"C:\\CM3.0\\app\\test\\Framework\\AutomationBvt\\src\\config\\xlfiles\\Controller.xlsx");
		for (int col = 0; col < datatable.getColumnCount("TC5"); col++) {
			System.out.println(datatable.getCellData("TC5", col, 1));
		}
	}

}
