package matchplanner;

public class Team {
	
	private String name;
	private String shortname;
	private int id;
	
	
	public Team(String name, String shortname) {
		this.name = name;
		this.shortname = shortname;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getShortname() {
		return shortname;
	}


	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "Team [name=" + name + ", shortname=" + shortname + "]";
	}
	
	
	
	
	

}
