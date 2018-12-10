package listeners;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import matchplanner.MatchplanerGUI;

public class TeamCountFieldDocumentListener implements DocumentListener {
	JTextField textField;
	boolean validData = true;
	JLabel infoLabel;
	JList jList;
	DefaultListModel listModel;



	public TeamCountFieldDocumentListener(JTextField textField, JLabel infoLabel, JList jList,
			DefaultListModel listModel) {
		super();
		this.textField = textField;
		this.infoLabel = infoLabel;
		this.jList = jList;
		this.listModel = listModel;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		boolean validData = true;

		if (textField.getText().equals(""))

			validData = false;
		if (!textField.getText().equals("")) {
			if (textField.getText().replaceAll("[0-9]", "").length() > 0) {
				infoLabel.setText("Sie müssen eine Zahl eingeben!");
				validData = false;

			} else {
				if (Integer.parseInt(textField.getText()) % 2 != 0) {
					infoLabel.setText("Sie müssen eine gerade Anzahl an Teams eingeben!");
					validData = false;

				}

				if (Integer.parseInt(textField.getText()) < 4) {
					infoLabel.setText("Sie müssen mindestens vier Teams erzeugen!");
					validData = false;

				}
//
			}
		}
		matchplanner.MatchplanerGUI.enableButtons(validData, validData);
		if (validData) {
			if (Integer.parseInt(textField.getText()) == 4) {
				matchplanner.MatchplanerGUI.enableButtons(validData, false);
			}

			int size = listModel.getSize();
			int newSize = Integer.parseInt(textField.getText());
			int sizediff = newSize - size;
			infoLabel.setText("");
			if (sizediff > 0) {
				while (size < newSize) {
					listModel.addElement(size + " <Name> <Kürzel>");
					size++;
				}
			} else {
				while (size > newSize) {
					jList.clearSelection();
					listModel.removeElementAt(size - 1);
					size--;
				}

			}
		}

	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changedUpdate(DocumentEvent e) {

	}

}
