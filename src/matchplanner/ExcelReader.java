package matchplanner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
	private List<Team> teams=new ArrayList();

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
		
//		while (rowIterator.hasNext()) {
//			Row row = rowIterator.next();
//
//			// Now let's iterate over the columns of the current row
//			Iterator<Cell> cellIterator = row.cellIterator();
//
//			while (cellIterator.hasNext()) {
//				Cell cell = cellIterator.next();
//				String cellValue = dataFormatter.formatCellValue(cell);
//				System.out.print(cellValue + "\t");
//			}
//			System.out.println();
//		}

//        // 2. Or you can use a for-each loop to iterate over the rows and columns
//        System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
//        for (Row row: sheet) {
//            for(Cell cell: row) {
//                String cellValue = dataFormatter.formatCellValue(cell);
//                System.out.print(cellValue + "\t");
//            }
//            System.out.println();
//        }
//
//        // 3. Or you can use Java 8 forEach loop with lambda
//        System.out.println("\n\nIterating over Rows and Columns using Java 8 forEach with lambda\n");
//        sheet.forEach(row -> {
//            row.forEach(cell -> {
//                String cellValue = dataFormatter.formatCellValue(cell);
//                System.out.print(cellValue + "\t");
//            });
//            System.out.println();
//        });

		// Closing the workbook
		workbook.close();
	}
	/*
	 * Liest den Namen aus der ersten Zelle der ersten Reihe des Sheets aus und speichert ihn in das Feld planName
	 * 
	 */
	public void setPlanName() {
		Row row = sheet.getRow(0);
		Cell cell = row.getCell(0);
		planName = dataFormatter.formatCellValue(cell);

	}
	/*
	 * Liest die Anzahl der Teams aus der ersten Zelle der zweiten Reihe aus und speichert sie in das Feld numberOfTeams
	 */
	public void setNumberOfTeams() {
		Row row = sheet.getRow(1);
		Cell cell = row.getCell(0);
		numberOfTeams = Integer.valueOf(dataFormatter.formatCellValue(cell).replaceAll("@teamCount=", ""));
	}
	/*
	 * Füllt die Liste teams mit den teams aus der Excel Datei.
	 * Der erste name beginnt in Reihe 6.
	 * Die Liste wird mit Objekten des Typs Team gefüllt.
	 */
	public void setTeams() {
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
	public void setDates() {
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