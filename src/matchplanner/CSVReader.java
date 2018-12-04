package matchplanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sun.font.CreatedFontTracker;

/*
 * @author marvin
 */
public class CSVReader {

	String csvFile;
	BufferedReader br = null;
	String line = "";
	String csvSplitBy = ";";

	public CSVReader(String csvFile) {
		this.csvFile = csvFile;

	}

	public Matchplan importCSV() throws NumberFormatException, IOException {
		Matchplan mp = new Matchplan();
		List<String> dates = new ArrayList<String>();

		br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {

			// set MatchplanName
			if (line.contains("@type")) {
				mp.setMatchplanName(line.substring(9, line.length()));
			}

			// set and create League
			if (line.contains("@teamCount")) {
				int numberOfTeams = Integer.parseInt(line.replaceAll("@teamCount = ", ""));
				mp.createLeague(numberOfTeams);
				mp.createPlan();
			}

			// Set LineSeperator
			if (line.contains("@splitter")) {
				csvSplitBy = line.substring(line.length() - 1, line.length());
			}

			if (line.contains("# Teams")) {
				br.readLine();
				while ((line = br.readLine()).contains(csvSplitBy) ) {
					line = line.replaceAll(" ", "");
					String[] team = line.split(csvSplitBy);
					mp.addNewTeam(new Team(team[2], team[1], Integer.parseInt(team[0])));
				}
			}

			if (line.contains("# MatchDates")) {
				while ((!((line = br.readLine()) == null)) && line.replaceAll(" ", "").length() > 0) {
					
					dates.add(line);
				}
				mp.addCSVDateList(dates);
			}

		}
		return mp;
	}

}
