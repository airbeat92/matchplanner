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

		frame = new JFrame();
		frame.setTitle("Matchplanner");
		frame.setBounds(100, 100, 800, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		panel.setLayout(gbl_panel);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);

		JMenuItem mntmNeu = new JMenuItem("Neu");
		mntmNeu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = "=> neuen Spielplan anlegen";
				JOptionPane.showMessageDialog(null, message);
			}
		});
		mnDatei.add(mntmNeu);

		JMenuItem mntmffnen = new JMenuItem("Öffnen");
		mntmffnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = "=> vorhandenen Spielplan öffnen";
				JOptionPane.showMessageDialog(null, message);
			}
		});
		mnDatei.add(mntmffnen);

		JMenuItem mntmSpeichern = new JMenuItem("Speichern");
		mntmSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = "=> Aenderungen am Spielplan öffnen";
				JOptionPane.showMessageDialog(null, message);
			}
		});
		mnDatei.add(mntmSpeichern);

		JMenuItem mntmSpeichernUnter = new JMenuItem("Speichern unter");
		mnDatei.add(mntmSpeichernUnter);

		JMenuItem mntmBeenden = new JMenuItem("Beenden");
		mntmBeenden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnDatei.add(mntmBeenden);

		JMenu mnExtras = new JMenu("Extras");
		menuBar.add(mnExtras);

		JMenuItem mntmManschaften = new JMenuItem("Manschaften bearbeiten");
		mnExtras.add(mntmManschaften);

		JMenuItem mntmSpieltage = new JMenuItem("Spieltage bearbeiten");
		mnExtras.add(mntmSpieltage);

	}

}
