/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchplanner;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.toedter.calendar.JDateChooser;

import design.DarkMenuBar;
import design.DarkModeTabbedPane;
import gameOfLife.ConwaysGameOfLife;
import listeners.DefaultTextFocusListener;
import listeners.EditFieldDocumentListener;
import listeners.TeamCountFieldDocumentListener;
import listeners.TeamModulListener;

/**
 *
 * @author Marcel, Marvin, Samet
 */
public class MatchplanerGUI extends javax.swing.JFrame implements AWTEventListener {

	private Matchplan mp;
	private boolean save = true;
	private boolean mpIsOpen = false;
	private boolean dModeOn = false;
	private boolean csv;
	public static final DateTimeFormatter DF = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
	private String savePath = "";
	private String mpName = "";

	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT); // für Spieltage
	JTabbedPane outerPane = new JTabbedPane(); // für Mannschaften und Spiele

	// Liste für Mannschaftsanzeige
	DefaultListModel teamModel = new DefaultListModel();
	JList teamList = new JList(teamModel);

	// Components für MatchplanPanel
	JPanel teamPanel = new JPanel(); // Gesamtes Panel

	JPanel addMatchplanPanel = new JPanel(new GridBagLayout());
	// new/add Panel
	JLabel headlineLabelnew = new JLabel();
	JLabel infoLabelNew = new JLabel();
	JLabel teamCountLabel = new JLabel("Anzahl Teams");
	JTextField teamCountField = new JTextField();
	JLabel matchplanNameField = new JLabel("Matchplanname");
	static JButton deleteTeamButton = new JButton("-");
	static JButton addTeamButton = new JButton("+");
	static JButton createMatchplanButton = new JButton();

	// edit Panel
	JTextField teamNameEditField = new JTextField();
	JTextField teamShortnameEditField = new JTextField();
	JTextField teamIDEditField = new JTextField();
	JLabel headlineLabeledit = new JLabel("Mannschaft bearbeiten");

	// Menu hinzufügen
	DarkMenuBar menuBar = new DarkMenuBar();
	// Datei
	JMenu mnDatei = new JMenu("Datei");
	JMenuItem mntmNeu = new JMenuItem("Neu");
	JMenuItem mntmOffnen = new JMenuItem("Öffnen");
	JMenuItem mntmClose = new JMenuItem("Schließen");
	JMenuItem mntmSpeichern = new JMenuItem("Speichern");
	JMenuItem mntmSpeichernUnter = new JMenuItem("Speichern unter");
	JMenuItem mntmExportieren = new JMenuItem("Als PDF exportieren");

	// Extras
	JMenu mnExtras = new JMenu("Extras");
	JMenuItem mntmMannschaften = new JMenuItem("Mannschaften bearbeiten");
	JMenuItem mntmSpieltage = new JMenuItem("Spieltag bearbeiten");
	JMenuItem mntmDarkMode = new JMenuItem("DarkMode");
	// Flag Label
	JLabel saveFlag = new JLabel();

	// Components für SpieleTab
	JButton gameDateEditButton;
	JPanel gameDatePanel;
	JPanel gameDateNorthPanel;
	JLabel gameDateLabel;

	// GameOfLife initialisieren
	JFrame game = new ConwaysGameOfLife();

	public MatchplanerGUI() {

		// GameOfLife verstecken
		Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);

		game.setVisible(false);
		/*
		 * frame
		 */

		this.setTitle("Matchplanner");
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout(0, 0));
		this.setVisible(true);
		// darkModeOn(true);

		/*
		 * Menubar with actionlisteners
		 */
		this.getContentPane().add(menuBar, BorderLayout.NORTH);

		// Menuitem File
		menuBar.add(mnDatei);
		menuBar.add(mnExtras);
		// Setzt das nächste MenuItem nach Rechts
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(saveFlag);

		// Menu und Flag auf korrekten Wert setzen
		setDataSave(true);
		saveFlag.setVisible(false);
		changeMenu(false);
		setTeamEditVisible(false);

		// MannschaftenTab

		// ManschaftenEdit anzeigen, wenn Mannschaft ausgewählt
		teamList.addListSelectionListener(l -> {
			setTeamEditVisible(true);
			teamNameEditField.setText("Teamname");
			teamNameEditField.setForeground(Color.LIGHT_GRAY);
			teamShortnameEditField.setText("Kürzel");
			teamShortnameEditField.setForeground(Color.LIGHT_GRAY);
			teamIDEditField.setText("ID");
			teamIDEditField.setForeground(Color.LIGHT_GRAY);
		});
		// Panel für bearbeiten und hinzufügen

		GridBagConstraints c = new GridBagConstraints();
		addMatchplanPanel.setPreferredSize(new Dimension((int) (this.getWidth() * 0.3), this.getHeight()));

		// addNew

		headlineLabelnew.setFont(new Font("Helvetica", Font.BOLD, 14));

		teamCountLabel.setFont(new Font("Helvetica", Font.PLAIN, 12));

		deleteTeamButton.addActionListener(l -> {
			teamCountField.setText(String.valueOf((Integer.parseInt(teamCountField.getText()) - 2)));
		});

		addTeamButton.addActionListener(l -> {
			teamCountField.setText(String.valueOf((Integer.parseInt(teamCountField.getText()) + 2)));

		});
		enableButtons(false, false);
		teamCountField.getDocument().addDocumentListener(
				new TeamCountFieldDocumentListener(teamCountField, infoLabelNew, teamList, teamModel));

		if (mpIsOpen)
			headlineLabelnew.setText("Spielplan bearbeiten");
		else
			headlineLabelnew.setText("Spielplan erstellen");
		createMatchplanButton.setText("Spielplan erstellen");
		createMatchplanButton.addActionListener(l -> {

			mp = new Matchplan();
			infoLabelNew.setText("");
			refreshMpTeams();
			setDataSave(false);
			mpIsOpen = true;
			changeMenu(true);
			refreshTabbedPane(true);

		});

		// teamEdit

		headlineLabeledit.setFont(new Font("Helvetica", Font.BOLD, 14));

		teamIDEditField.setEnabled(false);

		c.insets = new Insets(20, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;

		c.weightx = 0;
		c.weighty = 0;

		addMatchplanPanel.add(headlineLabelnew, c);
		c.gridx = 0;
		c.gridy = c.gridy + 1;
		;
		c.weighty = 0.05;
		addMatchplanPanel.add(teamCountLabel, c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(0, 10, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		addMatchplanPanel.add(teamCountField, c);
		c.gridx = 1;

		c.ipadx = 0;
		c.weightx = 0;
		c.insets = new Insets(0, 0, 0, 0);
		addMatchplanPanel.add(deleteTeamButton, c);
		c.gridx = 2;

		c.insets = new Insets(0, 0, 0, 10);
		addMatchplanPanel.add(addTeamButton, c);
		c.gridx = 0;
		c.gridy = c.gridy + 1;
		c.gridwidth = 3;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		addMatchplanPanel.add(infoLabelNew, c);
		c.gridx = 0;
		c.gridy = c.gridy + 1;
		c.weighty = 0.5;
		c.insets = new Insets(50, 0, 0, 0);
		addMatchplanPanel.add(createMatchplanButton, c);
		c.gridx = 0;
		c.gridy = c.gridy + 1;
		c.weighty = 0;
		c.insets = new Insets(0, 0, 0, 0);
		addMatchplanPanel.add(headlineLabeledit, c);
		c.gridx = 0;
		c.gridy = c.gridy + 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 20, 0, 20);

		addMatchplanPanel.add(teamNameEditField, c);
		c.gridx = 0;
		c.gridy = c.gridy + 1;

		addMatchplanPanel.add(teamShortnameEditField, c);
		c.gridx = 0;
		c.gridy = c.gridy + 1;
		addMatchplanPanel.add(teamIDEditField, c);
		c.gridx = 0;
		c.gridy = c.gridy + 1;
		c.weighty = 1.0;

		JPanel fill = new JPanel();
		fill.setBackground(new Color(0, 0, 0, 0));
		JLabel dark = new JLabel();
		dark.setText("DarkMode");

		addMatchplanPanel.add(fill, c);

		// Listener
		// Manschaften
		teamNameEditField.addFocusListener(new DefaultTextFocusListener(teamNameEditField, "Teamname"));
		teamShortnameEditField.addFocusListener(new DefaultTextFocusListener(teamShortnameEditField, "Kürzel"));
		teamIDEditField.addFocusListener(new DefaultTextFocusListener(teamIDEditField, "ID"));
		teamNameEditField.getDocument().addDocumentListener(
				new EditFieldDocumentListener(teamNameEditField, mpIsOpen, teamList, teamModel, 1));
		teamShortnameEditField.getDocument().addDocumentListener(
				new EditFieldDocumentListener(teamShortnameEditField, mpIsOpen, teamList, teamModel, 2));
		teamIDEditField.getDocument()
				.addDocumentListener(new EditFieldDocumentListener(teamIDEditField, mpIsOpen, teamList, teamModel, 3));
		teamModel.addListDataListener(new TeamModulListener(infoLabelNew, mpIsOpen));
		teamPanel.setLayout(new BorderLayout(0, 0));
		teamPanel.add(new JScrollPane(teamList), BorderLayout.CENTER);
		teamPanel.add(addMatchplanPanel, BorderLayout.EAST);

		// Spiele Tab
		JPanel gamePanel = new JPanel(new BorderLayout());
		JLabel gameDateLabel = new JLabel();
		gamePanel.add(gameDateLabel, BorderLayout.NORTH);
		gamePanel.add(tabbedPane, BorderLayout.CENTER);

		// JTabbedPane
		outerPane.addTab("Mannschaften", teamPanel);
		outerPane.addTab("Spiele", gamePanel);
		this.getContentPane().add(outerPane, BorderLayout.CENTER);

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

						mp.addNewTeam(new Team("< Bitte_ändern>", "", i));

					}
					setDataSave(false);
					mpIsOpen = true;
					changeMenu(true);
					refreshTabbedPane(true);
					refreshJList();
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

							break;
						}
						if (i == count - 1) {
							refreshTabbedPane(true);
							changeMenu(true);
							setDataSave(false);
							mpIsOpen = true;
						}
					}

				}

				inputField.setText("");
			} while (!validData);

		});

		mnDatei.add(mntmNeu);

		// MenuItem Öffnen
		mntmOffnen.addActionListener((e) -> {

			// JFileChooser-Objekt erstellen
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filterCsv = new FileNameExtensionFilter("CSV", "csv");
			FileNameExtensionFilter filterExcel = new FileNameExtensionFilter("Excel", "xls", "xlsx");
			FileNameExtensionFilter filterDefault = new FileNameExtensionFilter("Excel und Csv", "xls", "xlsx", "csv");
			chooser.addChoosableFileFilter(filterCsv);
			chooser.addChoosableFileFilter(filterExcel);
			chooser.setFileFilter(filterDefault);
			// Dialog zum Oeffnen von Dateien anzeigen
			int select = chooser.showDialog(null, "Öffnen");
			if (select == JFileChooser.APPROVE_OPTION) {
				savePath = chooser.getCurrentDirectory().getAbsolutePath();
				int lastIndexOf = chooser.getSelectedFile().getName().lastIndexOf(".");
				String extension = chooser.getSelectedFile().getName().substring(lastIndexOf);
				openFile(chooser.getSelectedFile().getAbsolutePath(), extension);
				changeMenu(true);

				System.out.println();
			}

		});
		mnDatei.add(mntmOffnen);

		// MenuItem Schließen
		mntmClose.addActionListener((e) -> {

			closeDialog(false);

		});
		mnDatei.add(mntmClose);

		mnDatei.addSeparator();

		// MenuItem Speichern
		mntmSpeichern.addActionListener(e -> {
			try {
				try {
					saveData();
				} catch (InvalidFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (IOException e1) {

				e1.printStackTrace();
			}

		});
		mnDatei.add(mntmSpeichern);

		// MenuItem Speichern unter
		mntmSpeichernUnter.addActionListener((e) -> {

			saveAs();

		});

		mnDatei.add(mntmSpeichernUnter);

		mnDatei.addSeparator();
		// MenuItem Export
		mnDatei.add(mntmExportieren);
		mntmExportieren.addActionListener(e -> {

			new PDFPrint(mp);

		});

		mnDatei.addSeparator();

		/*
		 * Enthält Abfrage über ungespeicherte Änderungen!
		 */
		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener((a) -> {
			if (!save) {
				closeDialog(true);
			}
			if (save) {
				System.exit(0);
			}

		});
		mnDatei.add(mntmBeenden);

//		menuitem Extras

		// MenuItem Mannschaften bearbeiten
		mntmMannschaften.addActionListener((e) -> {
			String message = "Klicke doch mal auf eine Mannschaft in der Liste.\n"
					+ "Aber nicht vergessen den Spielplan zu erneuern!";
			JOptionPane.showMessageDialog(null, message);
		});
		mnExtras.add(mntmMannschaften);

		// MenuItem Spieltag bearbeiten
		mntmSpieltage.addActionListener((e) -> {
			String message = "Schau doch mal unter Spiele vorbei.\n"
					+ "Ein Klick rechts vom Datum und schon kannst du es ändern.";
			JOptionPane.showMessageDialog(null, message);
		});
		mnExtras.add(mntmSpieltage);

		mnExtras.addSeparator();

		// MenuItem DarkMode
		mntmDarkMode.addActionListener((l) -> {
			darkModeOn(!dModeOn);
			dModeOn = !dModeOn;
			this.repaint();
		});

		mnExtras.add(mntmDarkMode);

	}

	// Frames
	/*
	 * Schließt den Matchplan, speichert bei Bedarf. Wenn exit true wird das
	 * Programm geschlossen.
	 * 
	 * @param exit
	 */
	private void closeDialog(boolean exit) {
		if (!save) {
			JFrame closeConfirmFrame = new JFrame();
			closeConfirmFrame.setLocationRelativeTo(null);
			int result = JOptionPane.showConfirmDialog(closeConfirmFrame,
					"Ungespeicherte Änderungen! Möchten Sie jetzt speichern?");
			if (JOptionPane.YES_OPTION == result) {
				try {
					saveData();
					if (save) {
						closeMP();
					}

				} catch (IOException | InvalidFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (JOptionPane.NO_OPTION == result) {
				closeMP();
			}
		} else {
			closeMP();
		}

	}

	// Methoden

	/*
	 * Enabled/Disabled die buttons zum hinzufügen zum und erstellen des Matchplans
	 */
	public static void enableButtons(boolean enable, boolean enableDelete) {
		createMatchplanButton.setEnabled(enable);
		addTeamButton.setEnabled(enable);
		deleteTeamButton.setEnabled(enableDelete);
	}

	/*
	 * Schließt den Matchplan
	 */
	public void closeMP() {
		mp = null;
		savePath = "";
		mpName = "";
		mpIsOpen = false;
		saveFlag.setText("");
		tabbedPane.removeAll();
		refreshJList();
		changeMenu(false);
		setTeamEditVisible(false);
		headlineLabelnew.setText("Spielplan erstellen");
		createMatchplanButton.setText("Spielplan erstellen");
		enableButtons(false, false);
	}

	/*
	 * Setzt save auf false und zeigt ein Flag in der GUI.
	 */
	private void setDataSave(boolean save) {
		if (!save) {
			saveFlag.setVisible(true);
			saveFlag.setForeground(Color.RED);
			saveFlag.setText("Ungespeicherte Änderungen!");
		} else {
			saveFlag.setForeground(new Color(0, 153, 51));
			saveFlag.setText("Erfolgreich gespeichert!");
		}
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
		mntmExportieren.setEnabled(fileOpen);
		headlineLabelnew.setText("Spielplan bearbeiten");
		createMatchplanButton.setText("Spielplan erneuern");

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
	 * Parameter gibt an ob der Plan refreshed werden soll. Nur bei Änderungen am
	 * Plan.
	 */
	private void refreshTabbedPane(boolean refresh) {
		if (refresh) {
			mp.refreshPlan();

		}
		tabbedPane.removeAll();
		mp.season.forEach(a -> {

			gameDatePanel = new JPanel(new BorderLayout());
			gameDateNorthPanel = new JPanel(new BorderLayout());
			gameDateLabel = new JLabel("", SwingConstants.CENTER);

			JDateChooser dateChooser = new JDateChooser();
			dateChooser.setBounds(20, 20, 200, 20);
			gameDateNorthPanel.add(dateChooser, BorderLayout.CENTER);
			JList displayMatches = new JList(a.toObjectArray(mp));
			DefaultListCellRenderer renderer = (DefaultListCellRenderer) displayMatches.getCellRenderer();
			renderer.setHorizontalAlignment(SwingConstants.CENTER);
			String gameDate = a.getMatchDate().format(DF);
			dateChooser.setDate(Date.from(a.getMatchDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
			tabbedPane.setSelectedIndex(-1);
			dateChooser.getDateEditor().addPropertyChangeListener(listener -> {

				if (tabbedPane.getSelectedIndex() != -1) {
					mp.season.get(tabbedPane.getSelectedIndex()).setMatchDate(
							dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
					tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(),
							mp.season.get(tabbedPane.getSelectedIndex()).getMatchDate().format(DF));
				}
			});
			gameDateLabel.setText(gameDate);
			gameDatePanel.add(gameDateNorthPanel, BorderLayout.NORTH);
			gameDatePanel.add(new JScrollPane(displayMatches), BorderLayout.CENTER);
			tabbedPane.addTab(gameDate, gameDatePanel);
		});

	}

	/*
	 * Füllt die Matchplan teams neu mit den Werten aus der JList Teams.
	 */
	private void refreshMpTeams() {
		mp.teams.clear();
		for (int i = 0; i < teamModel.size(); i++) {
			String[] teamSplit = teamModel.getElementAt(i).toString().split(" ");
			mp.addNewTeam(new Team(teamSplit[1], teamSplit[2], Integer.parseInt(teamSplit[0])));

		}
	}

	/*
	 * Aktualisiert die JList mit den Elementen aus Matchplan Teams.
	 */
	private void refreshJList() {
		if (mp == null) {
			teamModel.removeAllElements();
			teamCountField.setText("");
		} else {
			teamModel.removeAllElements();
			mp.teams.forEach(o -> {
				teamModel.addElement(o.getId() + " " + o.getName() + " " + o.getShortname());

			});
			teamCountField.setText(String.valueOf(teamModel.getSize()));
		}

	}

	/*
	 * Speichert am letzten Speicherort. Wenn keiner vorhanden ist wird ein Dialog
	 * zum wählen des Pfades aufgerufen.
	 */
	private void saveData() throws IOException, InvalidFormatException {
		if (savePath.length() > 0) {
			if (csv) {
				CSVWriter.writeCsv(savePath, mpName, mp);
				setDataSave(true);
			} else {
				ExcelWriter ex = new ExcelWriter();
				ex.writeExcel(mp, mpName, savePath);
			}
		} else {
			saveAs();
		}
	}

	public void saveAs() {
		// JFileChooser-Objekt erstellen
		JFileChooser chooser = new JFileChooser();
		// Dialog zum Oeffnen von Dateien anzeigen
		int select = chooser.showDialog(null, "Speichern unter");
		if (select == JFileChooser.APPROVE_OPTION) {
			savePath = chooser.getCurrentDirectory().getAbsolutePath();
			mpName = chooser.getSelectedFile().getName();
			Object[] options = { "CSV", "XLSX" };

			int n = JOptionPane.showOptionDialog(null, "CSV oder Excel, das ist hier die Frage?", "Die Frage",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
			
			System.out.println(n);

			if (n == 1) {
				ExcelWriter ex = new ExcelWriter();
				try {
					ex.writeExcel(mp, mpName, savePath);
					setDataSave(true);
					csv = false;
				} catch (InvalidFormatException | IOException e) {
					System.out.println("Speichern nicht erfolgreich: " + LocalTime.now() + " " + LocalDate.now());
					e.printStackTrace();
				}
			}
			if (n == 0) {
				try {
					CSVWriter.writeCsv(savePath, mpName, mp);
					setDataSave(true);
					csv = true;
				} catch (IOException e) {
					System.out.println("Speichern nicht erfolgreich: " + LocalTime.now() + " " + LocalDate.now());
					e.printStackTrace();
				}

			}
		}
		if (select == JFileChooser.CANCEL_OPTION) {

		}
	}

	private void setTeamEditVisible(boolean bool) {
		headlineLabeledit.setVisible(bool);
		teamNameEditField.setVisible(bool);
		teamShortnameEditField.setVisible(bool);
		teamIDEditField.setVisible(bool);
	}

	private void openFile(String path, String extension) {
//		CSVReader reader = new CSVReader(path);
		try {
			if (extension.equals(".csv")) {
				CSVReader reader = new CSVReader(path);
				mp = reader.importCSV();
			}
			if (extension.equals(".xlsx") || extension.equals(".xls")) {
				ExcelReader reader = new ExcelReader(path);
				mp = reader.importExcel();
			}
			refreshJList();

		} catch (NumberFormatException e) {
			System.out.println("Öffnen nicht erfolgreich: " + LocalTime.now() + " " + LocalDate.now());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Öffnen nicht erfolgreich: " + LocalTime.now() + " " + LocalDate.now());
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		refreshTabbedPane(false);
	}

	public void darkModeOn(boolean enable) {
		if (enable) {
			outerPane.setUI(new DarkModeTabbedPane(outerPane));
			tabbedPane.setUI(new DarkModeTabbedPane(outerPane));
			teamPanel.setBackground(Color.GRAY);
			teamList.setBackground(Color.GRAY);
			teamList.setForeground(Color.WHITE);
			addMatchplanPanel.setBackground(Color.DARK_GRAY);
			addMatchplanPanel.setForeground(Color.WHITE);
			headlineLabelnew.setForeground(Color.WHITE);
			infoLabelNew.setForeground(Color.WHITE);
			teamCountLabel.setForeground(Color.WHITE);

			// edit Panel
			headlineLabeledit.setForeground(Color.WHITE);

			// edit Menubar
			menuBar.setColor(Color.DARK_GRAY);
			mnDatei.setForeground(Color.WHITE);
			mnExtras.setForeground(Color.WHITE);

		} else {
			outerPane.setUI(new BasicTabbedPaneUI());
			tabbedPane.setUI(new DarkModeTabbedPane(outerPane));

		}

	}

	/*
	 * Setzt den Namen des Matchplans.
	 */
	public static void setMpName(String mpName) {
		mpName = mpName;
	}

	public void eventDispatched(AWTEvent event) {
		if (event instanceof KeyEvent) {
			KeyEvent ke = (KeyEvent) event;
			if (ke.getKeyCode() == KeyEvent.VK_G && ke.isControlDown()) {
				game.setVisible(true);

			}
			if (ke.getKeyCode() == KeyEvent.VK_H && ke.isControlDown()) {
				game.setVisible(false);

			}

		}
	}

}
