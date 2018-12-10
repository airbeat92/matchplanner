package listeners;

import javax.swing.JLabel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class TeamModulListener implements ListDataListener {
	
	private JLabel infoLabel;
	private boolean mpIsOpen;

	public TeamModulListener(JLabel infoLabel, boolean mpIsOpen) {
		this.infoLabel = infoLabel;
		this.mpIsOpen = mpIsOpen;
	}

	@Override
	public void intervalAdded(ListDataEvent e) {
		
		infoLabel.setText("Sie müssen den Spielplan neu erstellen");
		
	}

	@Override
	public void intervalRemoved(ListDataEvent e) {
		
		infoLabel.setText("Sie müssen den Spielplan neu erstellen");
		
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		
		infoLabel.setText("Sie müssen den Spielplan neu erstellen");
		
	}

}
