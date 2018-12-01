package matchplanner;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

		List<Team> test1 = new ArrayList();
		test1.add(new Team("Vfb S", "VFB",1));
		test1.add(new Team("BVB d", "BVB",2));
		test1.add(new Team("FCB m", "fcb",3));
		test1.add(new Team("fck k", "fck",4));

		Matchplan mtest = new Matchplan(test1);
		
		ArrayList <Date> testList= new ArrayList();
		testList = mtest.createDates(4);
		
		
		
		
		


	}
}
