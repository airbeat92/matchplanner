/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchplanner;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
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
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setVisible(true);

		/*
		 * menubar with actionlisteners
		 */

		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);

		// Menuitem File
		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);

		JMenuItem mntmNeu = new JMenuItem("Neu");
		mntmNeu.addActionListener((e) -> {
			Object[] options = { "Abbrechen", "Hinzufügen", "Fertig" };
			int inputCount = 0;
			JPanel panel = new JPanel(new BorderLayout());
			JPanel inputpanel = new JPanel();
			JLabel inputLabel = new JLabel();
			inputpanel.add(inputLabel);
			JTextField team = new JTextField(10);
			inputpanel.add(team);
			JPanel info = new JPanel();
			JLabel infoLabel = new JLabel();
			JCheckBox defaultValues = new JCheckBox("Möchten sie vier Teams mit Standardwerten erzeugen?");
			info.add(infoLabel);
			info.add(defaultValues);
			panel.add(inputpanel, BorderLayout.CENTER);
			panel.add(info, BorderLayout.PAGE_END);

			int input;

			do {

				inputLabel.setText("Bitte das Team mit der ID " + inputCount + "eingeben");
				input = JOptionPane.showOptionDialog(null, panel, "Teams hinzufügen", JOptionPane.WARNING_MESSAGE, 0,
						null, options, options[2]);
				defaultValues.hide();
				
				//Checkbox selected
				if(defaultValues.isSelected()) {
					
				}
				// Fertig gedrückt
				//Team Anzahl kleiner Vier
				if (input == 2 && inputCount < 4) {
					team.setText("");

					infoLabel.setText("Sie müssen mindestens vier Teams hinzufügen!");
					input = JOptionPane.showOptionDialog(null, panel, "Confirmation", JOptionPane.WARNING_MESSAGE, 0,
							null, options, options[2]);

				}
				
				//Ungerade Anzahl an Teams
				if (input == 2 && inputCount % 2 != 0) {
					team.setText("");
					infoLabel.setText("Sie müssen eine gerade Anzahl an Teams hinzufügen!");
					input = JOptionPane.showOptionDialog(null, panel, "Confirmation", JOptionPane.WARNING_MESSAGE, 0,
							null, options, options[2]);

				}

				// Hinzufügen gedrückt
				if (input == 1) {
					infoLabel.setText(team.getText() + " wurde hinzugefügt");
					inputCount++;

				}
				// Abbrechen gedrückt
				if (input == 0) {

				}
				team.setText("");
			} while (input == 1 || (input == 2 && inputCount % 2 != 0) || (input == 2 && inputCount < 4));

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

		JMenuItem mntmClose = new JMenuItem("Schließen");
		mnDatei.add(mntmClose);
		mntmClose.setEnabled(mp != null);
		mntmClose.addActionListener((e) -> {
			// Bedingung für nicht gespeicherte Änderungen
			if (true) {
				JFrame closeConfirmFrame = new JFrame();
				int result = JOptionPane.showConfirmDialog(closeConfirmFrame,
						"Ungespeicherte Änderungen, dennoch beenden?");
				if (JOptionPane.YES_OPTION == result) {
					mp = null;
				}
				if (JOptionPane.NO_OPTION == result) {
					// Speichern aufrufen
				}
			}
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
			// Aufruf Close
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
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// hier sollen die Einträge für die Spieltage rein
		JLabel test = new JLabel("test");
		JLabel test2 = new JLabel("test");
		test.setHorizontalTextPosition(SwingConstants.RIGHT);
		tabbedPane.addTab("testtitle", test);
		tabbedPane.addTab("testtitle2", test2);

	}

	// Methoden

}
