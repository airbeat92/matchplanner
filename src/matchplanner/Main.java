package matchplanner;

import java.awt.EventQueue;
import java.time.LocalDate;
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

		List<Team> test1 = new ArrayList();
		test1.add(new Team("Vfb Stuttgart", "VFB",1));
		test1.add(new Team("BVB Dortmund", "BVB",2));
		test1.add(new Team("FCB München", "FCB",3));
		test1.add(new Team("RB Leipzig", "RBL",4));

		Matchplan mtest = new Matchplan(test1);
		
		mtest.teams.add(new Team("Lang", "Kurz", 5 ));
		mtest.teams.add(new Team("Lang1", "Kurz1", 6 ));
		
		mtest.createLeague(mtest.teams.size());
		mtest.createPlan();
		
		
		LocalDate test = LocalDate.of(2000, 1, 1);
		LocalDate test2 = LocalDate.of(2000, 1, 2);
		LocalDate test3 = LocalDate.of(2000, 1, 3);
		
		System.out.println("---Spieltag 1---");
		Object [] array = mtest.season.get(test).toObjectArray(mtest);
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
		System.out.println();
		
		System.out.println("---Spieltag 2---");
		Object [] array1 = mtest.season.get(test2).toObjectArray(mtest);
		for (int i = 0; i < array.length; i++) {
			System.out.println(array1[i]);
		}
		System.out.println();
		
		System.out.println("---Spieltag 3---");
		Object [] array2 = mtest.season.get(test3).toObjectArray(mtest);
		for (int i = 0; i < array.length; i++) {
			System.out.println(array2[i]);
		}
		
		


	}
}
