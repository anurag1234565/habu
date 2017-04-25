package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.text.Highlighter.HighlightPainter;

public class HabuMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;


	private HabuWindow frame;


	private JMenuBar menuBar;
	private UI ui;
	private JMenu fileMenu, editMenu, aboutMenu;
	private JMenuItem newMenuItem, openMenuItem, saveAsMenuItem, exitMenuItem;
	private JMenuItem clearMenuItem;
	private JMenuItem aboutHabuMenuItem;


	public HabuMenuBar(UI ui){
		super();
		//set the frame
		this.frame = ui.frame;
		this.ui = ui;
		this.setupUI();

	}

	public void setupUI(){
		menuBar = new JMenuBar();

		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		aboutMenu = new JMenu("About");

		// create menu items

		setupFileMenu();
		setupEditMenu();
		setupAboutMenu();

		frame.setJMenuBar(menuBar);
	}

	private void setupAboutMenu() {
		aboutHabuMenuItem = new JMenuItem("About Habu");
		aboutHabuMenuItem.setActionCommand("About Habu");
		aboutMenu.add(aboutHabuMenuItem);

		menuBar.add(aboutMenu);
	}

	private void setupEditMenu() {
		clearMenuItem = new JMenuItem("Clear Buffer");
		clearMenuItem.setActionCommand("Clear Buffer");
		editMenu.add(clearMenuItem);

		menuBar.add(editMenu);
	}

	public void setupFileMenu(){
		// file menu items
		newMenuItem = new JMenuItem("New");
		newMenuItem.setMnemonic(KeyEvent.VK_N);
		newMenuItem.setActionCommand("New");
		fileMenu.add(newMenuItem);

		openMenuItem = new JMenuItem("Open");
		openMenuItem.setActionCommand("Open");
		fileMenu.add(openMenuItem);


		saveAsMenuItem = new JMenuItem("Save As");
		saveAsMenuItem.setActionCommand("Save As");
		fileMenu.add(saveAsMenuItem);

		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setActionCommand("Exit");
		fileMenu.add(exitMenuItem);
		//
		// add to menu bar
		menuBar.add(fileMenu);
	}

	public void addActionListenerToOpenMenuItem(){
		openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * open the JFileChooser on click, and select a file.
				 * Put the absolute path of the file in the text field
				 * */
					File file = ui.fileFromChooser();
					ui.habuTextArea.textArea.setText(file.getAbsolutePath());
					HabuWindow.showFileInTextArea(file, ui.habuTextArea.textArea, frame);
					frame.buffer_name = file.getAbsolutePath();
					ui.fileNameField.setText(file.getAbsolutePath());
					ui.statusLabel.setText("file opened : " + file.getName());
			}
		});
	}

	public void addActionListenerToExitMenuItem(){
		exitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * close the program
				 * */
				System.exit(0);
			}
		});
	}



	public void addActionListenerToNewMenuItem(){

		newMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				/*
				 * saves the file as a document
				 * */
				ui.habuTextArea.textArea.setText("");
				ui.fileNameField.setText("");
				frame.buffer_name = "";

				ui.statusLabel.setText("Buffer Cleared");
			}
		});
	}


	public void addActionListenerToAboutHabuMenuItem(){

		aboutHabuMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				/*
				 * saves the file as a document
				 * */
				JOptionPane.showMessageDialog(frame,
						"Habu is a simple text editor to edit your ASCII files.\n"+
								"Written By Ayush Jha\n" +
								"Licensed Under GNU GPL 3.0",
								"Welcome to the Habu Text Editor",
								JOptionPane.PLAIN_MESSAGE);
			}
		});
	}

	public void addActionListenerToSaveAsMenuItem(){

		saveAsMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				/*
				 * saves the file as a document
				 * */

				File file = ui.fileFromChooser();
				String buffer = ui.habuTextArea.textArea.getText();
				try {
					HabuWindow.writeTextAreaToFile(file, buffer, frame);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ui.fileNameField.setText(file.getAbsolutePath());
				frame.buffer_name = file.getAbsolutePath();
				ui.statusLabel.setText("File saved as " + file.getAbsoluteFile());
			}
		});
	}
	
	
	public void addActionListenerToClearMenuItem(){

		clearMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				/*
				 * clears the buffer
				 * */

				ui.habuTextArea.textArea.setText("");
			}
		});
	}
}