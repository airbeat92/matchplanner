package matchplanner;

import java.util.ArrayList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Matchday {

	private List<Match> md = new ArrayList();

	public Matchday(List<Match> md) {
		this.md = md;
	}

	public List<Match> getMd() {
		return md;
	}

	public void setMd(List<Match> md) {
		this.md = md;
	}

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
