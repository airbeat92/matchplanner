/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchplanner;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
	private boolean save = true;
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

		/*
		 * menubar with actionlisteners
		 */

		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);

		// Menuitem File
		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);

		// JTabbedPane hinzufügen
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// Setze boolean save auf false wenn das neue Team erstellt wurde

		JMenuItem mntmNeu = new JMenuItem("Neu");
		mntmNeu.setEnabled(mp == null);
		mntmNeu.addActionListener((e) -> {
			Object[] options = { "Abbrechen", "Erzeugen" };
			int inputCount = 0;
			JPanel panel = new JPanel(new BorderLayout());
			JPanel inputPanel = new JPanel();
			JPanel failPanel = new JPanel();
			JTextField inputField = new JTextField(10);

			JPanel info = new JPanel();
			JLabel inputLabel = new JLabel();
			JLabel infoLabel = new JLabel();
			JLabel failLabel = new JLabel();
			JCheckBox defaultValues = new JCheckBox("Möchten Sie die Teams mit Standardwerten erzeugen?");

			failPanel.add(failLabel);
			inputPanel.add(inputLabel);
			inputPanel.add(inputField);
			info.add(infoLabel);
			info.add(defaultValues);
			panel.add(failPanel, BorderLayout.NORTH);
			panel.add(inputPanel, BorderLayout.CENTER);
			panel.add(info, BorderLayout.SOUTH);
			
			JOptionPane pane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, 0, null, options, options[1]);
			mp = new Matchplan();
			int input;
			inputLabel.setText("Bitte Anzahl an Teams eingeben:");
			boolean validData = true;	

			do {
				pane.createDialog("Teams hinzufügen").setVisible(true);
				Object selectedValue = pane.getValue();
				System.out.println(selectedValue);
				validData = true;
				if (!inputField.getText().matches("[0-9]")) {
					failLabel.setText("Sie müssen eine Zahl eingeben!");
					inputField.setText("");
					validData = false;
				} else {
					if (Integer.parseInt(inputField.getText()) < 4) {
						failLabel.setText("Sie müssen mindestens vier Teams erzeugen!");
						inputField.setText("");
						validData = false;
					}

					if (Integer.parseInt(inputField.getText()) % 2 != 0) {
						failLabel.setText("Sie müssen eine gerade Anzahl an Teams eingeben!");
						inputField.setText("");
						validData = false;
					}

				}

				// Teams mit default Werten erzeugen
				if (selectedValue.equals(options[1])&& defaultValues.isSelected() && validData) {
					for (int i = 0; i < Integer.parseInt(inputField.getText()); i++) {
						System.out.println("default");
						mp.addNewTeam(new Team("<Bitte ändern>", "", i));

					}
					save = false;
					mp.refreshPlan();
					tabbedPane.removeAll();
					TreeSet<LocalDate> keyTree = new TreeSet(mp.season.keySet());
					for (LocalDate key : keyTree) {
						JList displayMatches = new JList(mp.season.get(key).toObjectArray(mp));
						tabbedPane.addTab(key.format(DF), new JScrollPane(displayMatches));
					}
				}

				// Teams von Hand erzeugen
				if (selectedValue.equals(options[1]) && !defaultValues.isSelected() && validData) {
					options[1] = "Hinzufügen";
					defaultValues.setVisible(false);
					System.out.println("handerzeugen");
					int count = Integer.parseInt(inputField.getText());
					System.out.println(count);
					for (int i = 0; i < count; i++) {
						
						inputField.setText("");
						inputLabel.setText("Bitte das Team mit der ID " + i + " eingeben");
						pane.createDialog("Teams hinzufügen").setVisible(true);
						selectedValue= pane.getValue();
						if (selectedValue.equals(options[1])) {
							infoLabel.setText(inputField.getText() + " wurde hinzugefügt");
							mp.addNewTeam(new Team(inputField.getText(), "", i));
						}
						if (selectedValue.equals(options[0])) {

							// hier brauchen wir noch eine methode um teams wieder zurückzusetzen, wenn
							// abbrechen gedrückt wurde
							
							break;
						}
					}
					
					save = false;
					mp.refreshPlan();
					tabbedPane.removeAll();
					TreeSet<LocalDate> keyTree = new TreeSet(mp.season.keySet());
					for (LocalDate key : keyTree) {
						JList displayMatches = new JList(mp.season.get(key).toObjectArray(mp));
						tabbedPane.addTab(key.format(DF), new JScrollPane(displayMatches));
					}

				}

			} while (!validData);

			/*
			 * Alte Eingabe do {
			 * 
			 * 
			 * 
			 * 
			 * inputLabel.setText("Bitte das Team mit der ID " + inputCount + " eingeben");
			 * input = JOptionPane.showOptionDialog(null, panel, "Teams hinzufügen",
			 * JOptionPane.PLAIN_MESSAGE, 0, null, options, options[2]);
			 * defaultValues.hide(); defaultValues.addItemListener( l ->{
			 * if(l.getStateChange() == ItemEvent.SELECTED) {
			 * inputLabel.setText("Anzahl zu erzeugender Teams eingeben:"); }
			 * 
			 * 
			 * }); // Checkbox selected if (defaultValues.isSelected() && input == 2) { for
			 * (int i = 0; i < 4; i++) {
			 * 
			 * mp.addNewTeam(new Team("<Bitte ändern>", "", i)); inputCount = 4; }
			 * 
			 * } // Fertig gedrückt // Team Anzahl kleiner Vier if (input == 2 && inputCount
			 * < 4) { team.setText("");
			 * 
			 * infoLabel.setText("Sie müssen mindestens vier Teams hinzufügen!"); input =
			 * JOptionPane.showOptionDialog(null, panel, "Neuen Matchplan erstellen",
			 * JOptionPane.PLAIN_MESSAGE, 0, null, options, options[1]);
			 * 
			 * }
			 * 
			 * // Ungerade Anzahl an Teams if (input == 2 && inputCount % 2 != 0) {
			 * team.setText("");
			 * infoLabel.setText("Sie müssen eine gerade Anzahl an Teams hinzufügen!");
			 * input = JOptionPane.showOptionDialog(null, panel, "Confirmation",
			 * JOptionPane.PLAIN_MESSAGE, 0, null, options, options[2]);
			 * 
			 * }
			 * 
			 * // Fertig gedrückt & alle Eingaben korrekt if (input == 2 && inputCount % 2
			 * == 0 && inputCount > 3) { save = false; mp.refreshPlan();
			 * tabbedPane.removeAll(); TreeSet<LocalDate> keyTree= new
			 * TreeSet(mp.season.keySet()); for (LocalDate key : keyTree) { JList
			 * displayMatches = new JList(mp.season.get(key).toObjectArray(mp));
			 * tabbedPane.addTab(key.format(DF), new JScrollPane(displayMatches)); }
			 * 
			 * System.out.println("es wird refresht"); }
			 * 
			 * // Hinzufügen gedrückt if (input == 1) { if (team.getText().equals("")) {
			 * infoLabel.setText("Eingabe darf nicht leer sein"); } else {
			 * infoLabel.setText(team.getText() + " wurde hinzugefügt");
			 * 
			 * mp.addNewTeam(new Team(team.getText(), "", inputCount)); inputCount++; } }
			 * 
			 * // Abbrechen gedrückt if (input == 0) {
			 * 
			 * } team.setText(""); } while (input == 1 || (input == 2 && inputCount % 2 !=
			 * 0) || (input == 2 && inputCount < 4));
			 */
			// hier ein ausgabe Test von Teams

			for (int i = 0; i < mp.teams.size(); i++) {
				System.out.println(mp.teams.get(i).toString());
			}

		});

		mnDatei.add(mntmNeu);

		JMenuItem mntmOffnen = new JMenuItem("Öffnen");
		mntmOffnen.setEnabled(mp == null);
		mntmOffnen.addActionListener((e) -> {
			String message = "=> vorhandenen Spielplan öffnen";

			// Bedingung später hinfällig, da auf das erfolgreiche Laden geprüft werden muss
			// Erstellt Spieltag Tabs mit den Begegnungen als Liste
			if (mp != null) {

				for (LocalDate key : mp.season.keySet()) {
					JList displayMatches = new JList(mp.season.get(key).toObjectArray(mp));
					tabbedPane.addTab(key.format(DF), new JScrollPane(displayMatches));
				}
			}

			JOptionPane.showMessageDialog(null, message);
		});
		mnDatei.add(mntmOffnen);

		JMenuItem mntmSpeichern = new JMenuItem("Speichern");
		mntmSpeichern.addActionListener(e -> {
			String message = "=> Aenderungen am Spielplan speichern";
			JOptionPane.showMessageDialog(null, message);

			save = true;
		});

		JMenuItem mntmClose = new JMenuItem("Schließen");
		mnDatei.add(mntmClose);
		mntmClose.setEnabled(mp != null);
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
		mntmManschaften.setEnabled(mp != null);
		mntmManschaften.addActionListener((e) -> {
			String message = "=> Mannschaften verändern";
			JOptionPane.showMessageDialog(null, message);
		});
		mnExtras.add(mntmManschaften);

		JMenuItem mntmSpieltage = new JMenuItem("Spieltage bearbeiten");
		mntmSpieltage.setEnabled(mp != null);
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
