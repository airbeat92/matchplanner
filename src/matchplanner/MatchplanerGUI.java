/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchplanner;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Marcel, Marvin, Samet
 */
public class MatchplanerGUI extends javax.swing.JFrame {

	private Matchplan mp;
	private boolean save = true;		//Merkt sich ungespeicherte Änderungen
	private boolean planOpen = false;	//Merkt sich ob ein Plan geöffnet ist oder nicht
	public static final DateTimeFormatter DF = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

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
		

		// JTabbedPane hinzufügen
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		/*
		 * menubar with actionlisteners
		 */

		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);

		// Menuitem File
		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);

		// Setze boolean save auf false wenn das neue Team erstellt wurde

		JMenuItem mntmNeu = new JMenuItem("Neu");
		mntmNeu.setEnabled(!planOpen);
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
			mp = new Matchplan();

			int input;

			do {

				inputLabel.setText("Bitte das Team mit der ID " + inputCount + "eingeben");
				input = JOptionPane.showOptionDialog(null, panel, "Teams hinzufügen", JOptionPane.WARNING_MESSAGE, 0,
						null, options, options[2]);
				defaultValues.setVisible(false);

				// Checkbox selected
				if (defaultValues.isSelected() && input == 2) {
					for (int i = 0; i < 4; i++) {

						mp.addNewTeam(new Team("<Bitte ändern>", "", i));
						inputCount = 4;
					}
					mp.refreshPlan();
					planOpen = true;
					this.revalidate();
					
					//TabbedPane mit dem neuen Plan befüllen
					tabbedPane.removeAll();
					SortedSet<LocalDate> keyTree = new TreeSet(mp.season.keySet());
					for (LocalDate key : keyTree) {

						JList displayMatches = new JList(mp.season.get(key).toObjectArray(mp));
						tabbedPane.addTab(key.format(DF), new JScrollPane(displayMatches));
					}

				}
				// Fertig gedrückt
				// Team Anzahl kleiner Vier
				if (input == 2 && inputCount < 4) {
					team.setText("");

					infoLabel.setText("Sie müssen mindestens vier Teams hinzufügen!");
					input = JOptionPane.showOptionDialog(null, panel, "Confirmation", JOptionPane.WARNING_MESSAGE, 0,
							null, options, options[2]);

				}

				// Ungerade Anzahl an Teams
				if (input == 2 && inputCount % 2 != 0) {
					team.setText("");
					infoLabel.setText("Sie müssen eine gerade Anzahl an Teams hinzufügen!");
					input = JOptionPane.showOptionDialog(null, panel, "Confirmation", JOptionPane.WARNING_MESSAGE, 0,
							null, options, options[2]);

				}

				// Hinzufügen gedrückt
				if (input == 1) {
					if (team.getText().equals("")) {
						infoLabel.setText("Eingabe darf nicht leer sein");
					} else {
						infoLabel.setText(team.getText() + " wurde hinzugefügt");

						mp.addNewTeam(new Team(team.getText(),"",inputCount));
						inputCount++;
					}
				}
				// Abbrechen gedrückt
				if (input == 0) {

				}
				team.setText("");
			} while (input == 1 || (input == 2 && inputCount % 2 != 0) || (input == 2 && inputCount < 4));

			for (int i = 0; i < mp.teams.size(); i++) {
				System.out.println(mp.teams.get(i).getName() + mp.teams.get(i).getId());
			}

		});

		mnDatei.add(mntmNeu);

		JMenuItem mntmOffnen = new JMenuItem("Öffnen");
		mntmOffnen.setEnabled(!planOpen);
		mntmOffnen.addActionListener((e) -> {
			String message = "=> vorhandenen Spielplan öffnen";

			// Bedingung später hinfällig, da auf das erfolgreiche Laden geprüft werden muss
			// Erstellt Spieltag Tabs mit den Begegnungen als Liste
			if (mp != null) {
				tabbedPane.removeAll();

				SortedSet<LocalDate> keyTree = new TreeSet(mp.season.keySet());
				for (LocalDate key : keyTree) {
					JList displayMatches = new JList(mp.season.get(key).toObjectArray(mp));
					tabbedPane.addTab(key.format(DF), new JScrollPane(displayMatches));
				}
			}

			JOptionPane.showMessageDialog(null, message);
		});
		mnDatei.add(mntmOffnen);

		JMenuItem mntmSpeichern = new JMenuItem("Speichern");
		mntmSpeichern.setEnabled(planOpen);
		mntmSpeichern.addActionListener(e -> {
			String message = "=> Aenderungen am Spielplan speichern";
			JOptionPane.showMessageDialog(null, message);

			save = true;
		});

		JMenuItem mntmClose = new JMenuItem("Schließen");
		mnDatei.add(mntmClose);
		mntmClose.setEnabled(planOpen);
		mntmClose.addActionListener((e) -> {
			if (!save) {
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
		mntmSpeichernUnter.setEnabled(planOpen);
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
		mntmManschaften.setEnabled(planOpen);
		mntmManschaften.addActionListener((e) -> {
			String message = "=> Mannschaften verändern";
			JOptionPane.showMessageDialog(null, message);
		});
		mnExtras.add(mntmManschaften);

		JMenuItem mntmSpieltage = new JMenuItem("Spieltage bearbeiten");
		mntmSpieltage.setEnabled(planOpen);
		mntmSpieltage.addActionListener((e) -> {
			String message = "=> Spieltage festlegen/verändern";
			JOptionPane.showMessageDialog(null, message);
		});
		mnExtras.add(mntmSpieltage);

		// Dummy Füllung
		Object[] dummyMatchdays = new Object[1];
		dummyMatchdays[0] = "1. HF Toll : FC Code";
		LocalDate date = LocalDate.now();
		for (int i = 0; i < 4; i++) {
			if (i != 0) {
				date = date.plusDays(1);
			}
			//TabbedPane mit Dummy gefüllt
			JList displayGames = new JList(dummyMatchdays);
			tabbedPane.addTab(date.format(DF), new JScrollPane(displayGames));
		}

	}

	// Frames

//	private void closeDialog() {
//		if (!save) {
//			JFrame closeConfirmFrame = new JFrame();
//			closeConfirmFrame.setVisible(true);
//			int result = JOptionPane.showConfirmDialog(closeConfirmFrame,
//					"Ungespeicherte Änderungen, dennoch beenden?");
//			if (JOptionPane.YES_OPTION == result) {
//				mp = null;
//			}
//			if (JOptionPane.NO_OPTION == result) {
//				// Speichern aufrufen
//			}
//		}
//	}

	// Methoden

	private void saveData() {
		throw new NotImplementedException();
	}

}
