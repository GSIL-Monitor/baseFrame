package excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class SXSSFTest {

	public static void main(String[] args) throws IOException {
//		SXSSFWorkbook wb = new SXSSFWorkbook(1000);
		SXSSFWorkbook wb = new SXSSFWorkbook();//默认内存保留100行，超出部分写入硬盘
		wb.setCompressTempFiles(true);
		Font titleFont = wb.createFont();
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		CellStyle titleCellStyle=wb.createCellStyle();
		titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleCellStyle.setFont(titleFont);
		String title="";
		for(int i=0;i<15;i++){
			String sheetName = "第"+i+"文档簿";
			Sheet sh = wb.createSheet(sheetName);
			
			Row head = sh.createRow(0);
			int width = 22;//英文字符宽度，中文*2
			for(int cellNum=0;cellNum<10;cellNum++){
				sh.setColumnWidth(cellNum,  256*width+184);//设置列宽
//				sh.setColumnWidth(cellNum, title.getBytes().length*2*172);
				title = "第" + (i+1) + "文档表，第" + (cellNum+1) + "列标题";
				Cell cell = head.createCell(cellNum);
				cell.setCellValue(title);
				cell.setCellStyle(titleCellStyle);
			}
			
			for(int rowNum=1;rowNum<1000;rowNum++){
				Row row = sh.createRow(rowNum);
				for(int cellNum=0;cellNum<10;cellNum++){
					Cell cell = row.createCell(cellNum);
					String address = new CellReference(cell).formatAsString();
					cell.setCellValue(i + " , " + address);
				}
			}
		}
		
		/*for(int rowNum=0;rowNum<900;rowNum++){
			System.out.println(rowNum + " : " + sh.getRow(rowNum));
		}
		for(int rowNum=900;rowNum<1000;rowNum++){
			System.out.println(rowNum + " : " + sh.getRow(rowNum));
		}*/
		FileOutputStream out = new FileOutputStream("C:/sxssf.xlsx");
		wb.write(out);
		out.close();
		System.out.println("finished...");
	}
	
	private void loadExcelInfo(String xlsPath) throws IOException{
		FileInputStream fis = new FileInputStream(xlsPath);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheetAt(0);
		for(Row row : sheet){
			for(int i=0;i<10;i++){
				Cell cell = row.getCell(i);
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_BLANK:
					System.out.print("null");
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					System.out.print(" boolean "+cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_ERROR:
					System.out.print(cell.getErrorCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					System.out.print(" formula "+cell.getCellFormula());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					System.out.print(" number "+cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					System.out.print(" string "+cell.getStringCellValue());
					break;				

				default:
					break;
				}
			}
			System.out.println();
			
		}
		fis.close();
	}
	
	@Test
	public void testLoadExcelInfo() throws IOException{
		loadExcelInfo("c:/sxssf.xlsx");
	}

}
