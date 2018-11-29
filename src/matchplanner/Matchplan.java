package matchplanner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.HashMap;


public class Matchplan {
	
	HashMap <Date, Matchday> season = new HashMap();
	List<Team> teams = new ArrayList();
	League mLeague;

	/*
	 * Für import aus CSV datei
	 */
	public Matchplan(List<Team> teams) {
		this.mLeague = new League(teams.size());
		this.teams=teams;
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
	 * Erstellt eine Liga für die angegebene Anzahl von Teams
	 */
	public void createLeague(int numberOfTeams) {
		mLeague = new League(numberOfTeams);
	}

	/*
	 * Erstellt dein eigentlichen Matchplan
	 */
	public void createPlan() {
		Date defaultDate = new Date(0000, 00 ,00);

		for (int i = 0; i < mLeague.getTeams() - 1; i++) {
			List <Match> tempM = new ArrayList();
			for (int j = 0; j < mLeague.getTeams() / 2; j++) {
				Match m = mLeague.getMatch(j);
				tempM.add(m);
			}
			season.put(defaultDate, new Matchday(tempM));
			defaultDate.setDate(defaultDate.getDate()+1);
			mLeague.shift();
		}

		mLeague = new League(mLeague.getTeams());

		for (int l = mLeague.getTeams(); l < 2 * mLeague.getTeams() -1; l++) {
			List <Match> tempM = new ArrayList();
			for (int k = 0; k < mLeague.getTeams() / 2; k++) {
				Match m = mLeague.getMatch(k);
				tempM.add(m);
			}
			season.put(defaultDate, new Matchday(tempM));
			defaultDate.setDate(defaultDate.getDate()+1);
			mLeague.shift();

		}

	}
	
	
	//Jan
	//Bitte Liste erzeugen, die die Datums nächsten Samstage ab dem aktuellen datum speichert
	//anzahl der Samstag ist anzahl spieltage
	public ArrayList<Date> createDates(int spieltage){
		throw new NotImplementedException();
	}
	
	


}