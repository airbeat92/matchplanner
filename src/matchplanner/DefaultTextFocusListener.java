package matchplanner;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class DefaultTextFocusListener implements FocusListener{
	JTextField textField;
	String name;
	
	public DefaultTextFocusListener(JTextField textField, String name) {
		this.textField=textField;
		this.name=name;
		this.textField.setText(name);
		this.textField.setForeground(Color.LIGHT_GRAY);
	}
	@Override
	public void focusLost(FocusEvent e) {
		if(!textField.getText().equals("")){
		} else {
		textField.setText(name);
		textField.setForeground(Color.LIGHT_GRAY);
		}
	}
	
	@Override
	public void focusGained(FocusEvent e) {
		if(textField.getText().equals(name)) {
		textField.setText("");
		textField.setForeground(Color.BLACK);
		}
		
	}

}
