package matchplanner;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Arrays;
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
					 //new MatchplanerGUI();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

//		 Hier wird getestet

		List<Team> test1 = new ArrayList();
		test1.add(new Team("Vfb S", "VFB"));
		test1.add(new Team("BVB d", "BVB"));
		test1.add(new Team("FCB m", "fcb"));
		test1.add(new Team("fck k", "fck"));

		Matchplan mtest = new Matchplan(test1);

//		League instance = new League(18);
//		String teams[] = { "Arminia Bielefeld", "1. FSV Mainz 05", "VfB Stuttgart", "1. FC Kaiserslautern",
//				"Borussia Dortmund", "Borussia Mönchengladbach", "Bayer 04 Leverkusen", "Hertha BSC", "1. FC Nürnberg",
//				"Hannover 96", "Eintracht Frankfurt", "FC Bayern München", "VfL Wolfsburg", "FC Schalke 04",
//				"MSV Duisburg", "1. FC Köln", "Werder Bremen", "Hamburger SV" };
//
//		for (int i = 0; i < instance.getTeams() - 1; i++) {
//			System.out.printf("%d. Spieltag %b%n", i + 1, i < instance.getTeams() - 1);
//			for (int j = 0; j < instance.getTeams() / 2; j++) {
//				Match m = instance.getMatch(j);
//				//if (m.getHome() == 0 || m.getGuest() == 0 || m.getHome() == 17 || m.getGuest() == 17) {
//					System.out.printf("%25s <-> %25s%n", teams[m.getHome()], teams[m.getGuest()]);
//				}
//			
//			instance.shift();
//		}
//		
//		instance = new League (18);
//		
//		for (int i = 0; i < instance.getTeams() - 1; i++) {
//			System.out.printf("%d. Spieltag %b%n", i + 18, i < instance.getTeams() - 1);
//			for (int j = 0; j < instance.getTeams() / 2; j++) {
//				Match m = instance.getMatch(j);
//				//if (m.getHome() == 0 || m.getGuest() == 0 || m.getHome() == 17 || m.getGuest() == 17) {
//					System.out.printf("%25s <-> %25s%n", teams[m.getGuest()], teams[m.getHome()]);
//				}
//			
//			instance.shift();
//		}
//		
//		}
//	

	}
}
