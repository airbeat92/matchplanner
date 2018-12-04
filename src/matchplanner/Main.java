package matchplanner;

import java.awt.EventQueue;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
	
	

	}
}
