/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchplanner;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Marcel, Marvin, Samet
 */
public class MatchplanerGUI extends javax.swing.JFrame {

	Matchplan mp;

	public MatchplanerGUI() {
		/*
		 * frame
		 */
		super();
		this.setTitle("Matchplanner");
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(0, 0));
		this.setVisible(true);

		/*
		 * menubar with actionlisteners
		 */

		JMenuBar menuBar = new JMenuBar();
		this.add(menuBar, BorderLayout.NORTH);

		// Menuitem File
		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);

		JMenuItem mntmNeu = new JMenuItem("Neu");
		mntmNeu.addActionListener((e) -> {
			Object[] options = { "Abbrechen", "Hinzufügen", "Fertig" };
			
			JPanel panel = new JPanel(new BorderLayout());
			JPanel inputpanel = new JPanel();
			inputpanel.add(new JLabel("Bitte ein Team eingeben"));
			JTextField textField = new JTextField(10);
			inputpanel.add(textField);
			JPanel warning = new JPanel();
			JLabel warningLabel = new JLabel();
			warning.add(warningLabel);
			panel.add(inputpanel,BorderLayout.CENTER);
			panel.add(warning, BorderLayout.PAGE_END);
			
			
			
			int input = JOptionPane.showOptionDialog(null, panel, "Confirmation",
			        JOptionPane.WARNING_MESSAGE, 0, null, options, options[2]);

			
			do {
				
				
				input = JOptionPane.showOptionDialog(null, panel, "Confirmation",
				        JOptionPane.WARNING_MESSAGE, 0, null, options, options[2]);
			//Fertig gedrückt
			if(input==2) {
				warningLabel.setText("Sie müssen eine gerade Anzahl an Teams hinzufügen");
				input = JOptionPane.showOptionDialog(null, panel, "Confirmation",
				        JOptionPane.WARNING_MESSAGE, 0, null, options, options[2]);
				
				
			}
			
			//Hinzufügen gedrückt
			if(input==1) {
				
			}
			//Abbrechen gedrückt
			if(input==0) {
				
			}
			}while(input==1);

		});
		
		mnDatei.add(mntmNeu);

		JMenuItem mntmoffnen = new JMenuItem("Öffnen");
		mntmoffnen.addActionListener((e) -> {
			String message = "=> vorhandenen Spielplan öffnen";
			JOptionPane.showMessageDialog(null, message);
		});
		mnDatei.add(mntmoffnen);

		JMenuItem mntmSpeichern = new JMenuItem("Speichern");
		mntmSpeichern.addActionListener(e -> {
			String message = "=> Aenderungen am Spielplan speichern";
			JOptionPane.showMessageDialog(null, message);
		});

		mnDatei.addSeparator();
		mnDatei.add(mntmSpeichern);

		JMenuItem mntmSpeichernUnter = new JMenuItem("Speichern unter");
		mntmSpeichernUnter.addActionListener((e) -> {
			String message = "=> geöffneten Spielplan als neue Datei speichern";
			JOptionPane.showMessageDialog(null, message);
		});
		mnDatei.add(mntmSpeichernUnter);

		mnDatei.addSeparator();

		/*
		 * Enthält Abfrage über ungespeicherte Änderungen!
		 */
		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener((a) -> {
			// Bedingung für nicht gespeicherte Änderungen
//			if (true) {
//				JFrame closeFrame = new JFrame();
//				closeFrame.setTitle("Close");
//				closeFrame.setSize(300, 200);
//				closeFrame.setLocationRelativeTo(null);
//				closeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				closeFrame.setLayout(new BorderLayout(0, 0));
//				closeFrame.setVisible(true);
//				
//				
//			}
			System.exit(0);
		});
		mnDatei.add(mntmBeenden);

//		menuitem Extras

		JMenu mnExtras = new JMenu("Extras");
		menuBar.add(mnExtras);

		JMenuItem mntmManschaften = new JMenuItem("Manschaften bearbeiten");
		mntmManschaften.addActionListener((e) -> {
			String message = "=> Mannschaften verändern";
			JOptionPane.showMessageDialog(null, message);
		});
		mnExtras.add(mntmManschaften);

		JMenuItem mntmSpieltage = new JMenuItem("Spieltage bearbeiten");
		mntmSpieltage.addActionListener((e) -> {
			String message = "=> Spieltage festlegen/verändern";
			JOptionPane.showMessageDialog(null, message);
		});
		mnExtras.add(mntmSpieltage);

		// JTabbedPane hinzufügen
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
		this.add(tabbedPane, BorderLayout.CENTER);

		// hier sollen die Einträge für die Spieltage rein
		JLabel test = new JLabel("test");
		JLabel test2 = new JLabel("test");
		test.setHorizontalTextPosition(SwingConstants.RIGHT);
		tabbedPane.addTab("testtitle", test);
		tabbedPane.addTab("testtitle2", test2);

	}

	// Methoden

}
