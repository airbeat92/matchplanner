package matchplanner;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

import gameOfLife.ConwaysGameOfLife;

public class Main {
	

	public static void main(String[] args) throws FileNotFoundException {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (Exception ignored) {
				}

				try {
					new MatchplanerGUI();
			        // Setup the swing specifics

			    

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}
	
}
