package listeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import matchplanner.Matchplan;
import matchplanner.MatchplanerGUI;
import matchplanner.Team;

public class MenuNeuActionListener implements ActionListener{
	
	private Matchplan mp;
	
	public MenuNeuActionListener(Matchplan mp) {
		this.mp=mp;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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

					mp.addNewTeam(new Team("< Bitte ändern>", "", i));

				}
				setDataSave(false);
				MatchplanerGUI.setMpIsOpen(true);
				changeMenu(true);
				refreshTabbedPane(true);
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

		
	}

}
