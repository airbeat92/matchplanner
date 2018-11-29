/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchplanner;

import java.awt.BorderLayout;

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

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

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
			Object[] options1 = { "Fertig", "Hinzufügen", "Abbrechen" };

			JPanel panel = new JPanel();
			panel.add(new JLabel("Bitte ein Team eingeben"));
			JTextField textField = new JTextField(10);
			panel.add(textField);

			int result = JOptionPane.showOptionDialog(null, panel, "Enter a Number", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options1, null);
			if (result == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(null, textField.getText());
			}

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
								//Speichern aufrufen
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
			//Aufruf Close
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
