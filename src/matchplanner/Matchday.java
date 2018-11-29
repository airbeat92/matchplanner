package matchplanner;

import java.util.ArrayList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Matchday {
	
	private List <Match> md = new ArrayList();

	public Matchday(List<Match> md) {
		this.md = md;
	}

	public List<Match> getMd() {
		return md;
	}

	public void setMd(List<Match> md) {
		this.md = md;
	}

	@Override
	public String toString() {
		throw new NotImplementedException();
	}
	
	
	
}
