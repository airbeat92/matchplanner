package matchplanner;

import java.awt.EventQueue;

public class Main {
	
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MatchplanerGUI();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
