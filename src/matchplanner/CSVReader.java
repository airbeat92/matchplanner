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

			// set MatchplanName Wiest
			if (line.contains("@type")) {
				MatchplanerGUI.setMpName(line.substring(9, line.length()));
			}

			// set MatchplanName MAMA
			if (line.contains("#matchplan")) {
				MatchplanerGUI.setMpName(line.substring(9, line.length()));
			}

			// set and create League Wiest
			if (line.contains("@teamCount")) {
				int numberOfTeams = Integer.parseInt(line.replaceAll("@teamCount = ", ""));
				mp.createLeague(numberOfTeams);
				mp.createPlan();
			}

			// set and create League
			if (line.contains("#teamsize")) {
				int numberOfTeams = Integer.parseInt(line.replaceAll("#teamsize ", ""));
				mp.createLeague(numberOfTeams);
				mp.createPlan();
			}

			// Set LineSeperator
			if (line.contains("@splitter")) {
				csvSplitBy = line.substring(line.length() - 1, line.length());
			}

			// set Teams Wiest
			if (line.contains("# Teams")) {
				br.readLine();
				while ((line = br.readLine()).contains(csvSplitBy)) {
					String[] team = line.split(csvSplitBy);
					team[1] = team[1].replaceAll(" ", "");
					team[0] = team[0].replaceAll(" ", "");
					mp.addNewTeam(new Team(team[2], team[1], Integer.parseInt(team[0])));

				}

			}

			// set Teams MAMA
			if (line.contains("#teamlist")) {
				br.readLine();
				while ((line = br.readLine()).contains(csvSplitBy)) {
//					line = line.replaceAll(" ", "");
					String[] team = line.split(csvSplitBy);
					team[0] = team[0].replaceAll(" ", "");
					mp.addNewTeam(new Team(team[1], team[2], Integer.parseInt(team[0])));

				}

			}

			// set Dates MAMA
			if (line.contains("#seasonmatchdates")) {
				while ((!((line = br.readLine()) == null)) && line.replaceAll(" ", "").length() > 0) {

					dates.add(line);

					// set Dates Wiest
					if (line.contains("# MatchDates")) {
						while ((!((line = br.readLine()) == null)) && line.replaceAll(" ", "").length() > 0) {

							dates.add(line);
						}
						mp.addCSVDateList(dates);
					}

				}
				mp.addCSVDateList(dates);
			}

		}
		return mp;
	}
}
