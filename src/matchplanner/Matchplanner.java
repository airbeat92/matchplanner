/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matchplanner;

import java.awt.BorderLayout;
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

/**
 *
 * @author Marcel, Marvin
 */
public class Matchplanner extends javax.swing.JFrame {

	private JFrame frame;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Matchplanner window = new Matchplanner();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public Matchplanner() {
		/*
		 * frame
		 */
		frame = new JFrame();
		frame.setTitle("Matchplanner");
		frame.setBounds(100, 100, 800, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		panel.setLayout(gbl_panel);

		gbl_panel.columnWidths = new int[] { 12, 516, 232, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };

		JLabel lbloutput = new JLabel("");
		GridBagConstraints gbc_lbloutput = new GridBagConstraints();
		gbc_lbloutput.gridheight = 5;
		gbc_lbloutput.insets = new Insets(0, 0, 0, 5);
		gbc_lbloutput.gridx = 1;
		gbc_lbloutput.gridy = 1;
		panel.add(lbloutput, gbc_lbloutput);

		JLabel lblspiel1 = new JLabel("Spieltag 1");
		lblspiel1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lbloutput.setText("Spieltag 1");
			}
		});
		GridBagConstraints gbc_lblspiel1 = new GridBagConstraints();
		gbc_lblspiel1.insets = new Insets(0, 0, 5, 5);
		gbc_lblspiel1.gridx = 2;
		gbc_lblspiel1.gridy = 0;
		panel.add(lblspiel1, gbc_lblspiel1);

		JLabel lblspiel2 = new JLabel("Spieltag 2");
		lblspiel2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lbloutput.setText("Spieltag 2");
			}
		});
		GridBagConstraints gbc_lblspiel2 = new GridBagConstraints();
		gbc_lblspiel2.insets = new Insets(0, 0, 5, 5);
		gbc_lblspiel2.gridx = 2;
		gbc_lblspiel2.gridy = 1;
		panel.add(lblspiel2, gbc_lblspiel2);
		
		

		/*
		 * menubar with actionlisteners
		 */
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);

		JMenuItem mntmNeu = new JMenuItem("Neu");
		mntmNeu.addActionListener((e)-> {
				String message = "=> neuen Spielplan anlegen";
				JOptionPane.showMessageDialog(null, message);
			}
		);
		mnDatei.add(mntmNeu);

		JMenuItem mntmoffnen = new JMenuItem("Öffnen");
		mntmoffnen.addActionListener((e)-> {
				String message = "=> vorhandenen Spielplan öffnen";
				JOptionPane.showMessageDialog(null, message);
			}
		);
		mnDatei.add(mntmoffnen);

		JMenuItem mntmSpeichern = new JMenuItem("Speichern");
		mntmSpeichern.addActionListener(e -> {
				String message = "=> Aenderungen am Spielplan speichern";
				JOptionPane.showMessageDialog(null, message);
			}
		);
		
		mnDatei.addSeparator();
		mnDatei.add(mntmSpeichern);

		JMenuItem mntmSpeichernUnter = new JMenuItem("Speichern unter");
		mntmSpeichernUnter.addActionListener((e)-> {
				String message = "=> geöffneten Spielplan als neue Datei speichern";
				JOptionPane.showMessageDialog(null, message);
			}
		);
		mnDatei.add(mntmSpeichernUnter);
		
		mnDatei.addSeparator();
		
		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener((e) ->{
				System.exit(0);
			}
		);
		mnDatei.add(mntmBeenden);

		JMenu mnExtras = new JMenu("Extras");
		menuBar.add(mnExtras);

		JMenuItem mntmManschaften = new JMenuItem("Manschaften bearbeiten");
		mntmManschaften.addActionListener((e)-> {
				String message = "=> Mannschaften verändern";
				JOptionPane.showMessageDialog(null, message);
			}
		);
		mnExtras.add(mntmManschaften);

		JMenuItem mntmSpieltage = new JMenuItem("Spieltage bearbeiten");
		mntmSpieltage.addActionListener((e)-> {
				String message = "=> Spieltage festlegen/verändern";
				JOptionPane.showMessageDialog(null, message);
			}
		);
		mnExtras.add(mntmSpieltage);

	}

}
