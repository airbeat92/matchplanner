package matchplanner;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

public class Main {
	

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception ignored) {
				}

				try {
					new MatchplanerGUI();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

//		 Hier wird getestet
		Matchplan testPlan = new Matchplan();
		testPlan.createLeague(18);

		List<Team> test1 = new ArrayList();
		test1.add(new Team("Vfb Stuttgart", "VFB",1));
		test1.add(new Team("BVB Dortmund", "BVB",2));
		test1.add(new Team("FCB München", "FCB",3));
		test1.add(new Team("RB Leipzig", "RBL",4));

		Matchplan mtest = new Matchplan(test1);
		
		CSVWriter writer = new CSVWriter();
		try {
			writer.writeCsv("/Users/marvin/Downloads", mtest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CSVReader reader = new CSVReader("/Users/marvin/Downloads/matchplan.csv");
		try {
			Matchplan importM = reader.importCSV();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
