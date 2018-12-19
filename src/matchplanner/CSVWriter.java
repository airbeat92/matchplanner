package matchplanner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * @author marvin
 */
public class CSVWriter {

	/*
	 * Schreibt in eine CSV Dateoi.
	 */
	public static void writeCsv(String filePath, String name, Matchplan mp) throws IOException {
		List<Team> teamList = new ArrayList(mp.teams);
		List<LocalDate> dateList = new ArrayList();
		for (Matchday s : mp.season) {
			dateList.add(s.getMatchDate());
		}

		
		File f = new File(filePath + name + ".csv");
		f.createNewFile();

		FileWriter fileWriter = new FileWriter(filePath + "/" +  name + ".csv");

		fileWriter.append("#matchplan " + name);
		fileWriter.append("\n");
		fileWriter.append("#teamsize " + mp.teams.size());
		fileWriter.append("\n");
		fileWriter.append("#teamlist");
		fileWriter.append("\n");
		fileWriter.append("Id, Name, Shortname");
		fileWriter.append("\n");

		for (Team t : teamList) {
			fileWriter.append(String.valueOf(t.getId()));
			fileWriter.append(";");
			fileWriter.append(String.valueOf(t.getName()));
			fileWriter.append(";");
			fileWriter.append(String.valueOf(t.getShortname()));
			fileWriter.append("\n");

		}

		fileWriter.append("#matchdate");
		fileWriter.append("\n");
		for (LocalDate ld : dateList) {
			fileWriter.append(ld.toString()); // Datum entsprechend ausgeben
			fileWriter.append("\n");
		}
		fileWriter.flush();
		fileWriter.close();

	}

}