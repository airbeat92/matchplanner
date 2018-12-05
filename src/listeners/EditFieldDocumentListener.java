package listeners;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EditFieldDocumentListener implements DocumentListener {

	JTextField textField;
	boolean mpIsOpen;
	JList jList;
	DefaultListModel listModel;
	int type;
	public EditFieldDocumentListener(JTextField textField, boolean mpIsOpen, JList jList, DefaultListModel listModel, int type) {
		this.textField = textField;
		this.type=type;
		this.mpIsOpen = mpIsOpen;
		this.jList = jList;
		this.listModel = listModel;
	}

	@Override
	public void removeUpdate(DocumentEvent e) {

	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		if (textField.hasFocus()) {
			String[] listElement = listModel.getElementAt(jList.getSelectedIndex()).toString().split(" ");
			if (type==1) {
				if (!textField.getText().equals("Teamname")) {
					listModel.setElementAt(listElement[0] + " " + textField.getText().replaceAll(" ", "") + " " + listElement[2],
							jList.getSelectedIndex());
				}
			}
			if (type==2) {
				if (!textField.getText().equals("Kürzel")) {
					listModel.setElementAt(listElement[0] + " " + listElement[1] + " " + textField.getText().replaceAll(" ", ""),
							jList.getSelectedIndex());
				}
			}
			if (type==3) {
				if (!textField.getText().equals("ID")) {
					listModel.setElementAt(textField.getText().replaceAll(" ", "")+ " " + listElement[1] + " " +listElement[2]  ,
							jList.getSelectedIndex());
				}
			}
			
		}

	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub

	}

}
