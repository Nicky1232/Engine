package com.mygdx.zombie.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.mygdx.zombie.ZombieEngine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.plaf.BorderUIResource;

public class DesktopLauncher extends JFrame {

	private static final int WIDTH = 1280, HEIGHT = 720;
	private static final int PANEL_WIDTH = 240;

	private static LwjglAWTCanvas lwjglAWTCanvas;
	private static JMenuBar menuBar;
	private static JToolBar toolBar;
	private static JMenu menu;
	private static JPanel panel;		//<< The panel on the left side

	private static JMenuItem iExport, iImport, iExit;

	public DesktopLauncher() throws HeadlessException {

		setTitle("ZombieEngine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setLayout(new BorderLayout());
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (lwjglAWTCanvas != null) {
					lwjglAWTCanvas.stop();
				}
			}
		});

//		createToolBar();
		createMenu();
		createPanel();

		lwjglAWTCanvas = new LwjglAWTCanvas(new ZombieEngine());
		getContentPane().add(lwjglAWTCanvas.getCanvas(), BorderLayout.CENTER);

	}

	public static void main (String[] arg) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new DesktopLauncher();
			}
		});
	}

	private void createPanel() {

		Container container = getContentPane();

		panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.BLUE);

		ControlPanel panel = new ControlPanel(PANEL_WIDTH);
		container.add(panel, BorderLayout.WEST);

	}

//	private void createToolBar() {
//
//		toolBar = new JToolBar();
//		toolBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
//		toolBar.setPreferredSize(new Dimension(Integer.MAX_VALUE, 20));
//		add(toolBar, BorderLayout.PAGE_START);
//
//		JToggleButton pipeButton = new JToggleButton();
//		pipeButton.setIcon(new ImageIcon("Engine/icons/water-pipe.png"));
//		pipeButton.setMaximumSize(new Dimension(15, 15));
//		toolBar.add(pipeButton);
//
//	}

	private void createMenu() {
		menuBar = new JMenuBar();
		menu    = new JMenu("File");

		iExport = new JMenuItem("Export");
		iImport = new JMenuItem("Import");
		iExit   = new JMenuItem("Exit");

		//Add actions on click
		MenuActions actions = new MenuActions();
		iExport.addActionListener(actions);
		iImport.addActionListener(actions);
		iExit.addActionListener(actions);

		menu.add(iExport);
		menu.add(iImport);
		menu.addSeparator();
		menu.add(iExit);

		menuBar.add(menu);
		setJMenuBar(menuBar);
	}

	private static class MenuActions implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			//if(actionEvent.getSource() == iExport)
			//if(actionEvent.getSource() == iImport)
			if(actionEvent.getSource() == iExit)
				System.exit(0);
		}
	}
}