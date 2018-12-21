package matchplanner;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
	
	Matchplan mp;
	private List<String> dates = new ArrayList<String>();
	private List<Team> teams=new ArrayList<>();
	private int numberOfTeams;
	
	 // Blank workbook 
    XSSFWorkbook workbook = new XSSFWorkbook(); 

    // Create a blank sheet 
    XSSFSheet sheet = workbook.createSheet("matchplan"); 
    
    private void setMp(Matchplan mp) {
    	this.mp=mp;
    }
    
    private void writePlanname(String name) {
    	Row row = sheet.createRow(0); 
    	Cell cell = row.createCell(0); 
    	cell.setCellValue("@type="+name);
    }
    
    private void writeTeamCount() {
    	Row row = sheet.createRow(1);
    	Cell cell = row.createCell(0);
    	this.numberOfTeams=mp.teams.size();
    	cell.setCellValue("@teamcount="+numberOfTeams);
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
    		cell=row.createCell(3);
    		cell.setCellValue(mp.teams.get(i).getName());
			
		}
    	
    	
    	
    }
    
    
    public void writeExcel(Matchplan mp, String planname) {
    	setMp(mp);
    	writePlanname(planname);
    	writeTeamCount();
    	writeTeams();
    }

}
