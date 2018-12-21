package matchplanner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	private final String XLSX_FILE_PATH;
	private Workbook workbook;
	private Sheet sheet;
	private DataFormatter dataFormatter;
	private String planName;
	private int numberOfTeams;
	private List<String> dates = new ArrayList<String>();
	private List<Team> teams=new ArrayList<>();

	public ExcelReader(String Path) throws IOException, InvalidFormatException {
		this.XLSX_FILE_PATH=Path;
		// Creating a Workbook from an Excel file (.xls or .xlsx)
		this.workbook = WorkbookFactory.create(new File(XLSX_FILE_PATH));

		// Getting the Sheet at index zero
		this.sheet = workbook.getSheetAt(0);

		// Create a DataFormatter to format and get each cell's value as String
		this.dataFormatter = new DataFormatter();

		
		setNumberOfTeams();
		setTeams();
		setDates();

		workbook.close();
	}
	/*
	 * Liest den Namen aus der ersten Zelle der ersten Reihe des Sheets aus und speichert ihn in das Feld planName
	 * 
	 */
	private void setPlanName() {
		Row row = sheet.getRow(0);
		Cell cell = row.getCell(0);
		planName = dataFormatter.formatCellValue(cell);
		MatchplanerGUI.setMpName(planName);

	}
	/*
	 * Liest die Anzahl der Teams aus der ersten Zelle der zweiten Reihe aus und speichert sie in das Feld numberOfTeams
	 */
	private void setNumberOfTeams() {
		Row row = sheet.getRow(1);
		Cell cell = row.getCell(0);
		numberOfTeams = Integer.valueOf(dataFormatter.formatCellValue(cell).replaceAll("@teamCount=", ""));
	}
	/*
	 * Füllt die Liste teams mit den teams aus der Excel Datei.
	 * Der erste name beginnt in Reihe 6.
	 * Die Liste wird mit Objekten des Typs Team gefüllt.
	 */
	private void setTeams() {
		int rowIndex = 5;
		for (int i = 0; i < numberOfTeams; i++) {
			Row row = sheet.getRow(rowIndex+i);
			Cell nameCell = row.getCell(2);
			Cell shortNameCell = row.getCell(1);
			Cell idCell = row.getCell(0);
			teams.add(new Team(dataFormatter.formatCellValue(nameCell),dataFormatter.formatCellValue(shortNameCell),Integer.valueOf(dataFormatter.formatCellValue(idCell))));

		}
	}
	/*
	 * Liest die Matchdates aus der Excel Datei
	 * Das erste Datum steht in der 3. Reihe nach dem letzem Team
	 */
	private void setDates() {
		int rowIndex = (5+numberOfTeams+2);
		for (int i = rowIndex; i < sheet.getLastRowNum()+1; i++) {
			Row row = sheet.getRow(i);
			Cell cell = row.getCell(0);
			dates.add(dataFormatter.formatCellValue(cell));
		}
	}
	
	/*
	 * Hier erfolgt der eigentliche Import.
	 * Beim Import wird die Liste der Teams in die GUI geschrieben.
	 * Ebenfalls wird der Matchplan direkt erzeugt.
	 */
	public Matchplan importExcel() {
		Matchplan mp = new Matchplan();
		mp.createLeague(numberOfTeams);
		mp.createPlan();
		mp.teams=this.teams;
		mp.addimportDateList(dates, "EXCEL");
		
		
		return mp;
		
	}
	
	
}
