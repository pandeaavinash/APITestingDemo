package com.avinash.report;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.IndexedColors;

import com.avinash.bean.User;


public class GenerateReport 
{
	
	public static String reportFileName = "";
	
	//public static void createReport(String siteToTest, String reportFileName, ArrayList<com.avinash.bean.SiteMapURLBean> finalLinksBean)
	public static String createReport(String siteToTest, String reportFileName, TreeMap<Integer, User> users)
	{

		// TODO Auto-generated method stub
		
		//For windows".\\test-output\\"
		reportFileName = ".\\test-output\\"+"_"+reportFileName+".xls";
		//String outputFile = "Sears"+DupplicateMainTest2.reportFileName+".xls";
		//String homeDir = System.getProperty("home.dir");
		//File file = new File(homeDir, outputFile);
		
		try {
			FileOutputStream fileSiteMapOut = new FileOutputStream(reportFileName);
			HSSFWorkbook workbookSiteMap = new HSSFWorkbook();			
			HSSFSheet worksheetSiteMap = workbookSiteMap.createSheet("UserVerification");
			worksheetSiteMap.setColumnWidth(0,2788);
			worksheetSiteMap.setColumnWidth(1,5000);
			worksheetSiteMap.setColumnWidth(2,17588);
			worksheetSiteMap.setColumnWidth(3,20588);
			worksheetSiteMap.setColumnWidth(4,8000);			
			/*worksheetSiteMap.setColumnWidth(5,4988);		
			worksheetSiteMap.setColumnWidth(6,3288);
			worksheetSiteMap.setColumnWidth(7,2588);*/		
			HSSFRow rowSiteMap = worksheetSiteMap.createRow(0);
			
			HSSFCellStyle titleCellStyle = workbookSiteMap.createCellStyle();
			titleCellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
			titleCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			HSSFCellStyle cellStyle = workbookSiteMap.createCellStyle();
			
			HSSFFont cellFont = workbookSiteMap.createFont();
			cellFont.setFontHeightInPoints((short) 9);
			cellStyle.setFont(cellFont);
			
			HSSFCellStyle hlinkStyle = workbookSiteMap.createCellStyle();
			HSSFFont hlinkFont = workbookSiteMap.createFont();
			hlinkFont.setFontHeightInPoints((short) 9);
			hlinkFont.setColor(IndexedColors.BLUE.getIndex());
			hlinkFont.setUnderline(HSSFFont.U_SINGLE);
			hlinkStyle.setFont(hlinkFont);
			int colPosSiteMap = 0;
			
			HSSFCell cellSiteMap = rowSiteMap.createCell(colPosSiteMap++);			
			cellSiteMap.setCellStyle(titleCellStyle);			
			cellSiteMap.setCellValue("ID");
			
			
			cellSiteMap = rowSiteMap.createCell(colPosSiteMap++);			
			cellSiteMap.setCellStyle(titleCellStyle);			
			cellSiteMap.setCellValue("Name");
			
			cellSiteMap = rowSiteMap.createCell(colPosSiteMap++);
			cellSiteMap.setCellStyle(titleCellStyle);			
			cellSiteMap.setCellValue("Email");
			
			cellSiteMap = rowSiteMap.createCell(colPosSiteMap++);
			cellSiteMap.setCellStyle(titleCellStyle);			
			cellSiteMap.setCellValue("Gender");		
			
			cellSiteMap = rowSiteMap.createCell(colPosSiteMap++);
			cellSiteMap.setCellStyle(titleCellStyle);			
			cellSiteMap.setCellValue("Status");
			
			cellSiteMap = rowSiteMap.createCell(colPosSiteMap++);
			cellSiteMap.setCellStyle(titleCellStyle);
			cellSiteMap.setCellValue("created_at");		
			
			cellSiteMap = rowSiteMap.createCell(colPosSiteMap++);
			cellSiteMap.setCellStyle(titleCellStyle);
			cellSiteMap.setCellValue("updated_at");	

			int rowCntSiteMap = 0;
			Set<Entry<Integer, User>> entrySet = users.entrySet();
			for (Entry<Integer, User> entry : entrySet) {
				
			
			//for(User bean : finalLinksBean){
				
				//if(linkWithFilterCheckMap.get(link).containsValue(false)){
					rowSiteMap = worksheetSiteMap.createRow(++rowCntSiteMap); 
					colPosSiteMap = 0;
					
					cellSiteMap = rowSiteMap.createCell(colPosSiteMap++);
					cellSiteMap.setCellStyle(cellStyle);
					cellSiteMap.setCellValue(entry.getValue().getId());
					
					
					cellSiteMap = rowSiteMap.createCell(colPosSiteMap++);
					cellSiteMap.setCellStyle(cellStyle);
					cellSiteMap.setCellValue(entry.getValue().getName());
					
					
					cellSiteMap = rowSiteMap.createCell(colPosSiteMap++);
					cellSiteMap.setCellStyle(cellStyle);
					cellSiteMap.setCellValue(entry.getValue().getEmail());
					
					cellSiteMap = rowSiteMap.createCell(colPosSiteMap++);
					cellSiteMap.setCellStyle(cellStyle);
					cellSiteMap.setCellValue(entry.getValue().getGender());
					
					cellSiteMap = rowSiteMap.createCell(colPosSiteMap++);
					cellSiteMap.setCellStyle(cellStyle);
					cellSiteMap.setCellValue(entry.getValue().getStatus());
					
					cellSiteMap = rowSiteMap.createCell(colPosSiteMap++);
					cellSiteMap.setCellStyle(cellStyle);
					cellSiteMap.setCellValue(entry.getValue().getCreated_at());
					
					cellSiteMap = rowSiteMap.createCell(colPosSiteMap++);
					cellSiteMap.setCellStyle(cellStyle);
					cellSiteMap.setCellValue(entry.getValue().getUpdated_at());
							
			}
			workbookSiteMap.write(fileSiteMapOut);
			fileSiteMapOut.flush();
			fileSiteMapOut.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return reportFileName;
	
	}
	
	
	
	
	/***
	 * Delete the report file once attached to email and sent.
	 */
	public static void deleteReportFile(String siteToTest , String fileName)
	{
		File file = null;
		reportFileName = "./"+siteToTest+"_"+fileName+".xls";
		try
		{
			if(reportFileName != null)
				file = new File(reportFileName);
			
			boolean isDeleted = file.delete();
					if(isDeleted)
						System.out.println("Report File Deleted Successfully after report mail sent!!!");
		}
		catch(Exception e)
		{
			System.out.println("Exception occurred while deleting report file !! Message:"+e.getMessage());
		}
		
	}	

}

