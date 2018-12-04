/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchplanner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.TreeSet;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
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

/**
 *
 * @author Marcel, Marvin, Samet
 */
public class MatchplanerGUI extends javax.swing.JFrame {

	private Matchplan mp;
	private boolean save = true;
	private boolean mpIsOpen = false;
	public static final DateTimeFormatter DF = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);

	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
	JTabbedPane outerPane = new JTabbedPane();
	
	
	JLabel headlineLabelnew = new JLabel();
	JTextField teamNameEditField = new JTextField();
	JTextField teamShortnameEditField = new JTextField();
	JTextField teamIDEditField = new JTextField();
	JLabel headlineLabeledit = new JLabel("Mannschaft bearbeiten");

// Menu hinzufügen
	// Datei
	JMenu mnDatei = new JMenu("Datei");

	JMenuItem mntmNeu = new JMenuItem("Neu");
	JMenuItem mntmOffnen = new JMenuItem("Öffnen");
	JMenuItem mntmClose = new JMenuItem("Schließen");
	JMenuItem mntmSpeichern = new JMenuItem("Speichern");
	JMenuItem mntmSpeichernUnter = new JMenuItem("Speichern unter");
	// Extras
	JMenu mnExtras = new JMenu("Extras");

	JMenuItem mntmMannschaften = new JMenuItem("Mannschaften bearbeiten");
	JMenuItem mntmSpieltage = new JMenuItem("Spieltage bearbeiten");

	// Flag Label
	JLabel saveFlag = new JLabel();

	public MatchplanerGUI() {
		/*
		 * frame
		 */
		super();
		this.setTitle("Matchplanner");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.setVisible(true);

		/*
		 * Menubar with actionlisteners
		 */
		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);

		// Menuitem File
		menuBar.add(mnDatei);
		menuBar.add(mnExtras);
		// Setzt das nächste MenuItem nach Rechts
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(saveFlag);

		// Menu und Flag auf korrekten Wert setzen
		setDataSave(true);
		changeMenu(false);
		setTeamEditVisible(false);

		// befüllt Dummy Werte
//		dummyFill();


		// MannschaftenTab

		// Liste für Mannschaftsanzeige
		DefaultListModel teamModel = new DefaultListModel();
		JList teamList = new JList(teamModel);
		teamList.addListSelectionListener(l ->{
			setTeamEditVisible(true);
			teamNameEditField.setText("Teamname");
			teamNameEditField.setForeground(Color.LIGHT_GRAY);
			teamShortnameEditField.setText("Kürzel");
			teamShortnameEditField.setForeground(Color.LIGHT_GRAY);
			teamIDEditField.setText("ID");
			teamIDEditField.setForeground(Color.LIGHT_GRAY);
		});
		
		
		
		JPanel teamPanel = new JPanel();
		
		
		
		JPanel addMatchplanPanel = new JPanel();
		addMatchplanPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		addMatchplanPanel.setPreferredSize(new Dimension((int) (this.getWidth() * 0.3), this.getHeight()));

		// addNew

		
		headlineLabelnew.setFont(new Font("Helvetica", Font.BOLD, 14));
		JLabel teamCountLabel = new JLabel("Anzahl Teams");
		teamCountLabel.setFont(new Font("Helvetica", Font.PLAIN, 12));

		JTextField teamCountField = new JTextField();
		JButton deleteTeamButton = new JButton("-");
		JButton addTeamButton = new JButton("+");
		JLabel failLabelNew = new JLabel("Fail");
		
		teamCountField.addKeyListener(new KeyListener() {
			boolean validData =true;
			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				boolean validData =true;
				if(teamCountField.getText().equals(""))
					validData=false;
				if(!teamCountField.getText().equals("")) {
				if (teamCountField.getText().replaceAll("[0-9]", "").length() > 0) {
					failLabelNew.setText("Sie müssen eine Zahl eingeben!");
					validData=false;

					
				} else {
					if (Integer.parseInt(teamCountField.getText()) % 2 != 0) {
						failLabelNew.setText("Sie müssen eine gerade Anzahl an Teams eingeben!");
						validData=false;
						
					}

					if (Integer.parseInt(teamCountField.getText()) < 4) {
						failLabelNew.setText("Sie müssen mindestens vier Teams erzeugen!");
						validData=false;
						
					}
//
				}
				}
				if(validData) {
					teamModel.removeAllElements();
					failLabelNew.setText("");
					for (int i = 0; i < Integer.parseInt(teamCountField.getText()); i++) {
						
//						mp.addNewTeam(new Team("< " + i + " Bitte ändern>", "", i));
						teamModel.addElement("< " + i + " Bitte ändern>");
					}
				}

				
			}
			
		});
		
		if (mpIsOpen)
			headlineLabelnew.setText("Spielplan bearbeiten");
		else
			headlineLabelnew.setText("Spielplan erstellen");
		JButton createMatchplanButton = new JButton("Spielplan erstellen");
		
		//teamEdit
		
		headlineLabeledit.setFont(new Font("Helvetica", Font.BOLD, 14));
		teamNameEditField.addFocusListener(new DefaultTextFocusListener(teamNameEditField,"Teamname"));
		teamShortnameEditField.addFocusListener(new DefaultTextFocusListener(teamShortnameEditField,"Kürzel"));
		teamIDEditField.addFocusListener(new DefaultTextFocusListener(teamIDEditField,"ID"));
		

		
		c.insets = new Insets(20,0,0,0);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;

	    c.weightx = 0;
	    c.weighty = 0;		
		
		addMatchplanPanel.add(headlineLabelnew,c);
		c.gridx = 0;
		c.gridy=c.gridy+1;;
		c.weighty = 0.05;	
		addMatchplanPanel.add(teamCountLabel, c);
		c.gridx = 0;
		c.gridy=2;
		c.gridwidth = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(0,10,0,0);
		c.fill = GridBagConstraints.HORIZONTAL;
		addMatchplanPanel.add(teamCountField,c);
		c.gridx = 1;
		
		c.ipadx = 0;
		c.weightx = 0;
		c.insets = new Insets(0,0,0,0);
		addMatchplanPanel.add(deleteTeamButton, c);
		c.gridx = 2;
		
		c.insets = new Insets(0,0,0,10);
		addMatchplanPanel.add(addTeamButton,c);
		c.gridx = 0;
		c.gridy=c.gridy+1;
		c.gridwidth = 3;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		addMatchplanPanel.add(failLabelNew,c);
		c.gridx = 0;
		c.gridy = c.gridy+1;
		c.weighty = 0.5;
		c.insets = new Insets(50,0,0,0);
	    addMatchplanPanel.add(createMatchplanButton, c);
	    c.gridx = 0;
		c.gridy = c.gridy+1;
		c.weighty = 0;
		c.insets = new Insets(0,0,0,0);
		addMatchplanPanel.add(headlineLabeledit,c);
		c.gridx = 0;
		c.gridy = c.gridy+1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,20,0,20);
		
		addMatchplanPanel.add(teamNameEditField,c);
		c.gridx = 0;
		c.gridy = c.gridy+1;
		
		
		addMatchplanPanel.add(teamShortnameEditField,c);
		c.gridx = 0;
		c.gridy = c.gridy+1;
		addMatchplanPanel.add(teamIDEditField,c);
		c.gridx = 0;
		c.gridy = c.gridy+1;
		c.weighty = 1.0;
		addMatchplanPanel.add(new JPanel(),c);

		teamPanel.setLayout(new BorderLayout(0, 0));
		teamPanel.add(teamList, BorderLayout.CENTER);
		teamPanel.add(addMatchplanPanel, BorderLayout.EAST);

		// JTabbedPane
		outerPane.addTab("Mannschaften", teamPanel);
		outerPane.addTab("Spiele", tabbedPane);
		getContentPane().add(outerPane, BorderLayout.CENTER);

		// Menu Datei
		// Neues Team über Menuoption "Neu" hinzufügen

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
				if (inputField.getText().replaceAll("[0-9]", "").length() > 0 || inputField.getText().equals("")) {
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
					mpIsOpen=true;
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
						if (i == count - 1) {
							refreshTabbedPane();
							changeMenu(true);
							setDataSave(false);
							mpIsOpen=true;
						}
					}

				}

				inputField.setText("");
			} while (!validData);

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

		// MenuItem Schließen
		mntmClose.addActionListener((e) -> {

			closeDialog();
			

		});
		mnDatei.add(mntmClose);

		mnDatei.addSeparator();

		// MenuItem Speichern
		mntmSpeichern.addActionListener(e -> {
			String message = "=> Aenderungen am Spielplan speichern";
			JOptionPane.showMessageDialog(null, message);

			saveData();

		});
		mnDatei.add(mntmSpeichern);

		// MenuItem Speichern unter
		mntmSpeichernUnter.addActionListener((e) -> {
			String message = "=> geöffneten Spielplan als neue Datei speichern";
			JOptionPane.showMessageDialog(null, message);

			saveData();
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
		mntmMannschaften.addActionListener((e) -> {
			String message = "=> Mannschaften verändern";
			JOptionPane.showMessageDialog(null, message);
		});
		mnExtras.add(mntmMannschaften);

		// MenuItem Spieltag bearbeiten
		mntmSpieltage.addActionListener((e) -> {
			String message = "=> Spieltage festlegen/verändern";
			JOptionPane.showMessageDialog(null, message);
		});
		mnExtras.add(mntmSpieltage);

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
				headlineLabelnew.setText("Spielplan erstellen");
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
		mntmMannschaften.setEnabled(fileOpen);
		mntmSpieltage.setEnabled(fileOpen);
		headlineLabelnew.setText("Spielplan bearbeiten");
		

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

	/*
	 * Aktualisiert die TabbedPane
	 */
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
		mp = null;
		tabbedPane.removeAll();
		dummyFill();
		setDataSave(true);
		changeMenu(false);

	}
	
	private void setTeamEditVisible(boolean bool) {
		headlineLabeledit.setVisible(bool);
		teamNameEditField.setVisible(bool);
		teamShortnameEditField.setVisible(bool);
		teamIDEditField.setVisible(bool);
	}

}
