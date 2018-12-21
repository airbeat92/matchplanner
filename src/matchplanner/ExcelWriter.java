package matchplanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
	
	Matchplan mp;
	private List<String> dates = new ArrayList<String>();
	private List<Team> teams=new ArrayList<>();
	private int numberOfTeams;
	public static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	 // Blank workbook 
    XSSFWorkbook workbook = new XSSFWorkbook(); 

    // Create a blank sheet 
    XSSFSheet sheet = workbook.createSheet(); 
    
    private void setMp(Matchplan mp) {
    	this.mp=mp;
    }
    
    private void writePlanname(String name) {
    	Row row = sheet.createRow(0); 
    	Cell cell = row.createCell(0); 
    	cell.setCellValue("@type= "+name);
    }
    
    private void writeTeamCount() {
    	Row row = sheet.createRow(1);
    	Cell cell = row.createCell(0);
    	this.numberOfTeams=mp.teams.size();
    	cell.setCellValue("@teamCount="+numberOfTeams);
    }
    
    private void writeTeams() {
    	Row row = sheet.createRow(3);
    	Cell cell = row.createCell(0);
    	cell.setCellValue("# Teams");
    	row = sheet.createRow(4);
    	cell = row.createCell(0);
    	cell.setCellValue("# ID");
    	cell = row.createCell(1);
    	cell.setCellValue("Kürzel");
    	cell = row.createCell(2);
    	cell.setCellValue("Voller Name");
    	for (int i = 0; i < numberOfTeams; i++) {
    		row=sheet.createRow(5+i);
    		cell=row.createCell(0);
    		cell.setCellValue(mp.teams.get(i).getId());
    		cell=row.createCell(1);
    		cell.setCellValue(mp.teams.get(i).getShortname());
    		cell=row.createCell(2);
    		cell.setCellValue(mp.teams.get(i).getName());
			
		}
    	
    	
    	
    }
    
    private void writeDates() {
    	Row row = sheet.createRow(5+numberOfTeams+1);
    	Cell cell = row.createCell(0);
    	cell.setCellValue("#MatchDates");
    	int i = 0;
    	for (Matchday m : mp.season) {
			row = sheet.createRow(7 + numberOfTeams + i);
			cell = row.createCell(0);
			cell.setCellValue(m.getMatchDate().format(DF));
			i++;
		}
    		
    	
    }
    
    
    public void writeExcel(Matchplan mp, String planname, String path) throws IOException, InvalidFormatException {
    	setMp(mp);
    	writePlanname(planname);
    	writeTeamCount();
    	writeTeams();
    	writeDates();
    	
    	FileOutputStream out = 
				new FileOutputStream(new File(path + "/" + planname + ".xlsx" ));
		workbook.write(out);
		out.close();
		System.out.println("Excel written successfully..");
    	
    }

}
