package matchplanner;

public class Team {

	private String name;
	private String shortname;
	private int id;

	public Team() {
		this.name = "";
		this.shortname = "";
		this.id = 0;
	}

	public Team(String name, String shortname, int id) {
		this.name = name;
		this.shortname = shortname;
		this.id = id;
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
		return "Team [name=" + name + ", shortname=" + shortname +  ", id=" + id +"]";
	}

}
