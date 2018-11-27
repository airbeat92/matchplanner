/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchplanner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Marcel, Marvin, Samet
 */
public class MatchplanerGUI extends javax.swing.JFrame {
	JMenuBar menuBar = new JMenuBar();
	JMenu mnDatei = new JMenu();
	JMenuItem mntmNeu = new JMenuItem();
	JMenuItem mntmoffnen = new JMenuItem();
	JMenuItem mntmSpeichern = new JMenuItem();
	JMenuItem mntmSpeichernUnter = new JMenuItem();
	JMenuItem mntmBeenden = new JMenuItem();
	
	JMenu mnExtras = new JMenu();
	JMenuItem mntmManschaften = new JMenuItem();
	JMenuItem mntmSpieltage = new JMenuItem();
	
	public void loadLanguage(String lang) {
		Properties p = new Properties();
		try {
			p.load(getClass().getClassLoader().getResourceAsStream(lang+".properties"));
			String datei=p.getProperty("datei");
			String neu=p.getProperty("neu");
			String oeffnen=p.getProperty("oeffnen");
			String speichern=p.getProperty("speichern");
			String speichernUnter=p.getProperty("speichernUnter");
			String beenden=p.getProperty("beenden");
			String extras=p.getProperty("extras");
			String manschaft=p.getProperty("manschaftBearbeiten");
			String spieltage=p.getProperty("spieltageBearbeiten");
			
			
			
			mnDatei.setText(datei);
			mntmNeu.setText(neu);
			mntmoffnen.setText(oeffnen);
			mntmSpeichern.setText(speichern);
			mntmSpeichernUnter.setText(speichernUnter);
			mntmBeenden.setText(beenden);
			
			mnExtras.setText(extras);
			mntmManschaften.setText(manschaft);
			mntmSpieltage.setText(spieltage);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public MatchplanerGUI() {
		/*
		 * frame
		 */
		super();
		this.setTitle("Matchplanner");
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout(0, 0));
		this.setVisible(true);
		loadLanguage("german");

		/*
		 * menubar with actionlisteners
		 */

		
		this.add(menuBar, BorderLayout.NORTH);

		//Menuitem File
		
		menuBar.add(mnDatei);

		
		mntmNeu.addActionListener((e) -> {
			String message = "=> neuen Spielplan anlegen";
			JOptionPane.showMessageDialog(null, message);
		});
		mnDatei.add(mntmNeu);

		
		mntmoffnen.addActionListener((e) -> {
			String message = "=> vorhandenen Spielplan öffnen";
			JOptionPane.showMessageDialog(null, message);
		});
		mnDatei.add(mntmoffnen);

		
		mntmSpeichern.addActionListener(e -> {
			String message = "=> Aenderungen am Spielplan speichern";
			JOptionPane.showMessageDialog(null, message);
		});

		mnDatei.addSeparator();
		mnDatei.add(mntmSpeichern);

		
		mntmSpeichernUnter.addActionListener((e) -> {
			String message = "=> geöffneten Spielplan als neue Datei speichern";
			JOptionPane.showMessageDialog(null, message);
		});
		mnDatei.add(mntmSpeichernUnter);

		mnDatei.addSeparator();

		
		mntmBeenden.addActionListener((e) -> {
			System.exit(0);
		});
		mnDatei.add(mntmBeenden);

//		menuitem Extras

		
		menuBar.add(mnExtras);

		
		mntmManschaften.addActionListener((e) -> {
			String message = "=> Mannschaften verändern";
			JOptionPane.showMessageDialog(null, message);
		});
		mnExtras.add(mntmManschaften);

		
		mntmSpieltage.addActionListener((e) -> {
			String message = "=> Spieltage festlegen/verändern";
			JOptionPane.showMessageDialog(null, message);
		});
		mnExtras.add(mntmSpieltage);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
		this.add(tabbedPane, BorderLayout.CENTER);

		JLabel test = new JLabel("test");
		JLabel test2 = new JLabel("test");
		test.setHorizontalTextPosition(SwingConstants.RIGHT);
		tabbedPane.addTab("testtitle", test);
		tabbedPane.addTab("testtitle2", test2);
		//testcomment

	}

}
