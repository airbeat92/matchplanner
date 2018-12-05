package matchplanner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Matchday {

	private List<Match> md = new ArrayList();
	private LocalDate matchDate;

	public Matchday(List<Match> md) {
		this.md = md;
	}
	
	public Matchday(List<Match> md, LocalDate date) {
		this.md = md;
		this.matchDate = date;
	}

	public List<Match> getMd() {
		return md;
	}

	public LocalDate getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(LocalDate matchDate) {
		this.matchDate = matchDate;
	}

	public void setMd(List<Match> md) {
		this.md = md;
	}
	
	/*
	 * Gibt alle Partien eines Spieltages als Object Array zurück.
	 */
	public Object[] toObjectArray(Matchplan mp) {
		Object[] result = new Object[mp.teams.size() / 2];
		for (int i = 0; i < result.length; i++) {
			result[i] = md.get(i).matchAsString(mp);
		}
		
		return result;

	}

	@Override
	public String toString() {
		throw new NotImplementedException();
	}

}
