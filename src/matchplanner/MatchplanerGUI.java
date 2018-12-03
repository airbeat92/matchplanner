/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchplanner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.TreeSet;

import javax.swing.Box;
import javax.swing.DefaultListModel;
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
import javax.swing.SwingConstants;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Marcel, Marvin, Samet
 */
public class MatchplanerGUI extends javax.swing.JFrame {

	private Matchplan mp;
	private boolean save = true;
	public static final DateTimeFormatter DF = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
	JTabbedPane outerPane = new JTabbedPane();

// Menu hinzufügen
	//Datei
	JMenu mnDatei = new JMenu("Datei");
	
	JMenuItem mntmNeu = new JMenuItem("Neu");
	JMenuItem mntmOffnen = new JMenuItem("Öffnen");
	JMenuItem mntmClose = new JMenuItem("Schließen");
	JMenuItem mntmSpeichern = new JMenuItem("Speichern");
	JMenuItem mntmSpeichernUnter = new JMenuItem("Speichern unter");
	//Extras
	JMenu mnExtras = new JMenu("Extras");
	
	JMenuItem mntmManschaften = new JMenuItem("Mannschaften bearbeiten");
	JMenuItem mntmSpieltage = new JMenuItem("Spieltage bearbeiten");
	
	//Flag Label
	JLabel saveFlag = new JLabel();

	public MatchplanerGUI() {
		/*
		 * frame
		 */
		super();
		this.setTitle("Matchplanner");
		this.setSize(500, 400);
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
		menuBar.add(mnDatei);
		menuBar.add(mnExtras);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(saveFlag);
		setDataSave(true);
		changeMenu(false);
		


		// Liste für Mannschaftsanzeige
		DefaultListModel teamModel = new DefaultListModel();
		JList teamList = new JList(teamModel);

		// JTabbedPane hinzufügen
		outerPane.addTab("Spiele", tabbedPane);
		outerPane.addTab("Manschaften", teamList);
		getContentPane().add(outerPane, BorderLayout.CENTER);

		
		
//Menu Datei
		//Neues Team �ber Men�option "Neu" hinzuf�gen
		//todo:
		// Setze boolean save auf false wenn das neue Team erstellt wurde
		mntmNeu.addActionListener((e) -> {
			Object[] options = { "Abbrechen", "Erzeugen" };
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
			inputLabel.setText("Bitte Anzahl an Teams eingeben:");
			boolean validData = true;

			do {
				pane.createDialog("Teams hinzufügen").setVisible(true);
				Object selectedValue = pane.getValue();

				validData = true;
				// Abbrechen gedrückt bei Zahleneingabe
				if (selectedValue.equals(options[0])) {
					break;
				}
				if (inputField.getText().replaceAll("[0-9]", "").length()>0 || inputField.getText().equals("")) {
					failLabel.setText("Sie müssen eine Zahl eingeben!");

					validData = false;
				} else {
					if (Integer.parseInt(inputField.getText()) % 2 != 0) {
						failLabel.setText("Sie müssen eine gerade Anzahl an Teams eingeben!");

						validData = false;
					}

					if (Integer.parseInt(inputField.getText()) < 4) {
						failLabel.setText("Sie müssen mindestens vier Teams erzeugen!");

						validData = false;
					}

				}
				if (inputField.getText().equals("")) {
					failLabel.setText("Das Eingabefeld darf nicht leer sein");
				}

				// Teams mit default Werten erzeugen
				if (selectedValue.equals(options[1]) && defaultValues.isSelected() && validData) {
					for (int i = 0; i < Integer.parseInt(inputField.getText()); i++) {
						
						mp.addNewTeam(new Team("< " + i + " Bitte ändern>", "", i));
						teamModel.addElement("< " + i + " Bitte ändern>");

					}
					setDataSave(false);
					changeMenu(true);
					refreshTabbedPane();
				}

				// Teams von Hand erzeugen
				if (selectedValue.equals(options[1]) && !defaultValues.isSelected() && validData) {
					options[1] = "Hinzufügen";
					defaultValues.setVisible(false);
					int count = Integer.parseInt(inputField.getText());
					for (int i = 0; i < count; i++) {

						inputField.setText("");
						inputLabel.setText("Bitte das Team mit der ID " + i + " eingeben");
						pane.createDialog("Teams hinzufügen").setVisible(true);
						selectedValue = pane.getValue();
						if (selectedValue.equals(options[1])) {
							infoLabel.setText(inputField.getText() + " wurde hinzugefügt");
							mp.addNewTeam(new Team(inputField.getText(), "", i));
							teamModel.addElement(inputField.getText());
						}

						if (selectedValue.equals(options[0])) {
							mp.teams.clear();
							teamModel.removeAllElements();
							// hier brauchen wir noch eine methode um teams wieder zurückzusetzen, wenn
							// abbrechen gedrückt wurde

							break;
						}
						if(i==count-1) {
							refreshTabbedPane();
							changeMenu(true);
							setDataSave(false);
						}
					}
					

				}

				inputField.setText("");
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

		});

		mnDatei.add(mntmNeu);

		// MenuItem Öffnen
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

		// MenuItem Speichern
		mntmSpeichern.addActionListener(e -> {
			String message = "=> Aenderungen am Spielplan speichern";
			JOptionPane.showMessageDialog(null, message);

		});

		// MenuItem Schließen
		mnDatei.add(mntmClose);
		mntmClose.addActionListener((e) -> {

			closeDialog();

		});

		mnDatei.addSeparator();

		// MenuItem Speichern
		mnDatei.add(mntmSpeichern);

		// MenuItem Speichern unter
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
			if (!save) {
				closeDialog();
			}
			if (save) {
				System.exit(0);
			}

		});
		mnDatei.add(mntmBeenden);

//		menuitem Extras

		// MenuItem Mannschaften bearbeiten
		mntmManschaften.addActionListener((e) -> {
			String message = "=> Mannschaften verändern";
			JOptionPane.showMessageDialog(null, message);
		});
		mnExtras.add(mntmManschaften);

		// MenuItem Spieltag bearbeiten
		mntmSpieltage.addActionListener((e) -> {
			String message = "=> Spieltage festlegen/verändern";
			JOptionPane.showMessageDialog(null, message);
		});
		mnExtras.add(mntmSpieltage);

		dummyFill();

	}

	// Frames

	private void closeDialog() {
		if (!save) {
			JFrame closeConfirmFrame = new JFrame();
			closeConfirmFrame.setLocationRelativeTo(null);
			int result = JOptionPane.showConfirmDialog(closeConfirmFrame,
					"Ungespeicherte Änderungen, dennoch beenden?");
			if (JOptionPane.YES_OPTION == result) {
				mp = null;
				tabbedPane.removeAll();
				dummyFill();
				setDataSave(true);
				changeMenu(false);
			}
			if (JOptionPane.NO_OPTION == result) {
				// Speichern aufrufen
			}
		}
	}

	// Methoden
	
	/*
	 * Setzt save auf false und zeigt ein Flag in der GUI.
	 */
	private void setDataSave(boolean save) {
		saveFlag.setVisible(!save);
		saveFlag.setForeground(Color.RED);
		saveFlag.setText("Ungespeicherte Änderungen!");
		this.save = (save);
	}
	
	

	/*
	 * Stell das Menü um. Parameter ob Spieltag geöffnet ist übergeben.
	 */
	private void changeMenu(boolean fileOpen) {
		mntmNeu.setEnabled(!fileOpen);
		mntmOffnen.setEnabled(!fileOpen);
		mntmClose.setEnabled(fileOpen);
		mntmSpeichern.setEnabled(fileOpen);
		mntmSpeichernUnter.setEnabled(fileOpen);
		mntmManschaften.setEnabled(fileOpen);
		mntmSpieltage.setEnabled(fileOpen);

	}

	/*
	 * Befüllt Pane mit Dummys
	 */
	private void dummyFill() {
		Object[] dummyMatchdays = new Object[2];
		dummyMatchdays[0] = "---Dummy---";
		dummyMatchdays[1] = "1. HF Toll : FC Code";
		LocalDate date = LocalDate.now();
		for (int i = 0; i < 4; i++) {
			if (i != 0) {
				date = date.plusDays(1);
			}
			JList displayGames = new JList(dummyMatchdays);
			tabbedPane.addTab(date.format(DF), new JScrollPane(displayGames));
		}

	}

	private void refreshTabbedPane() {
		mp.refreshPlan();
		tabbedPane.removeAll();
		TreeSet<LocalDate> keyTree = new TreeSet(mp.season.keySet());
		for (LocalDate key : keyTree) {
			JList displayMatches = new JList(mp.season.get(key).toObjectArray(mp));
			tabbedPane.addTab(key.format(DF), new JScrollPane(displayMatches));
		}
	}

	private void saveData() {
		throw new NotImplementedException();
	}

}
