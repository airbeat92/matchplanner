package matchplanner;

import java.util.ArrayList;
import java.util.List;

public class Matchplanner {

	List<List<Match>> games = new ArrayList();
	List<Team> teams = new ArrayList();
	League mLeague;

	public Matchplanner(List<Team> teams) {
		this.mLeague = new League(teams.size());
		createPlan();
	}

	public int addNewTeam(Team t) {
		teams.add(t);
		return teams.size();
	}

	public void createLeague(int numberOfTeams) {
		mLeague = new League(numberOfTeams);
	}

	public void createPlan() {

		for (int i = 0; i < mLeague.getTeams() - 1; i++) {
			List <Match> tempM = new ArrayList();
			for (int j = 0; j < mLeague.getTeams() / 2; j++) {
				Match m = mLeague.getMatch(j);
				tempM.add(m);
			}
			games.add(tempM);
			mLeague.shift();
		}

		mLeague = new League(mLeague.getTeams());

		for (int l = mLeague.getTeams(); l < 2 * mLeague.getTeams() -1; l++) {
			List <Match> tempM = new ArrayList();
			for (int k = 0; k < mLeague.getTeams() / 2; k++) {
				Match m = mLeague.getMatch(k);
				tempM.add(m);
			}
			games.add(tempM);
			mLeague.shift();

		}

	}

}