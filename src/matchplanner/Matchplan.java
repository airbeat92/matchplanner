package matchplanner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeSet;

public class Matchplan {

//	HashMap<LocalDate, Matchday> season = new HashMap();
	ArrayList<Matchday> season = new ArrayList();
	List<Team> teams = new ArrayList();
	private League mLeague;
	private String matchplanName;

	/*
	 * StandardKonsrtuktor
	 */
	public Matchplan() {

	}

	/*
	 * Für import aus CSV datei
	 */
	public Matchplan(List<Team> teams) {
		this.mLeague = new League(teams.size());
		this.teams = teams;
		createPlan();

	}

	/*
	 * Fügt einzelne Teams zu einem neuen oder bestehenden Matchplan hinzu
	 */
	public int addNewTeam(Team t) {
		teams.add(t);
		return teams.size();
	}

	/*
	 * Wenn neue Teams hizugefüt wurden muss zum Abschluss der Plan aktualisiert
	 * werden!
	 */
	public void refreshPlan() {
		this.createLeague(this.teams.size());
		this.createPlan();
	}

	/*
	 * Erstellt eine Liga für die angegebene Anzahl von Teams
	 */
	public void createLeague(int numberOfTeams) {
		mLeague = new League(numberOfTeams);
	}

	/*
	 * Erstellt den eigentlichen Matchplan
	 */
	public void createPlan() {
		LocalDate defaultDate = LocalDate.now();

		for (int i = 0; i < mLeague.getTeams() - 1; i++) {
			List<Match> tempM = new ArrayList();
			for (int j = 0; j < mLeague.getTeams() / 2; j++) {
				Match m = mLeague.getMatch(j);
				tempM.add(m);
			}
			
			season.add(new Matchday(tempM, defaultDate));
			defaultDate = defaultDate.plusDays(1);
			mLeague.shift();
		}

		mLeague = new League(mLeague.getTeams());

		for (int l = mLeague.getTeams(); l < 2 * mLeague.getTeams() - 1; l++) {
			List<Match> tempM = new ArrayList();
			for (int k = 0; k < mLeague.getTeams() / 2; k++) {
				Match m = mLeague.getMatch(k);
				m.switchHomeGuest();
				tempM.add(m);
			}
			season.add(new Matchday(tempM, defaultDate));
			defaultDate = defaultDate.plusDays(1);
			mLeague.shift();

		}

	}

	/*
	 * 
	 */
	public void addCSVDateList(List<String> sa) {
		
		for (int i = 0; i < season.size(); i++) {
			String[] temp = sa.get(i).split("-");
			LocalDate tempD = LocalDate.of(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]),
					Integer.parseInt(temp[2]));
			season.get(i).setMatchDate(tempD);
			
		}
	}

	public ArrayList<LocalDate> createDates(int spieltage) {
		// Variablen
		List matchday = new ArrayList();
		// Day Today
		Calendar calendar = new GregorianCalendar(TimeZone.getDefault());
		Date date = calendar.getTime();
		// return: 1-7 (Saturday = 7)
		int day = calendar.get(Calendar.DAY_OF_WEEK);

		// berechnung Samstage
		for (int i = 0; i <= spieltage; i++) {
			if (day == Calendar.SATURDAY) {
				matchday.add(day);
				i++;
			} else {
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		return (ArrayList<LocalDate>) matchday;
	}

	// Getter und Setter

	public League getmLeague() {
		return mLeague;
	}

	public void setmLeague(League mLeague) {
		this.mLeague = mLeague;
	}

	public String getMatchplanName() {
		return matchplanName;
	}

	public void setMatchplanName(String matchplanName) {
		this.matchplanName = matchplanName;
	}

}