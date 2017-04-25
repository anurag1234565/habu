/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;


/**
 * @author ays
 *
 */
public class UI {
	public HabuWindow frame;


	public JPanel topPanel, northPanel, southPanel, centralPanel;
	public JPanel southBottomPanel;

	public BorderLayout topBorderLayout, northBorderLayout;

	public JTextArea textArea;
	public JScrollPane scrollBar;


	public JLabel northInsertLabel;


	public JTextField fileNameField;
	public JButton findFileButton;

	public JButton clearTextButton, saveTextButton, aboutButton;

	public JLabel statusLabel;

	public HabuMenuBar habuMenuBar;

	public HabuTextArea habuTextArea;

	public JLabel bufferStatusLabel;


	public UI(HabuWindow frame){
		this.frame = frame;
	}

	public void setupUI(){
		topPanel = new JPanel();
		
		
		topBorderLayout = new BorderLayout();
		northBorderLayout = new BorderLayout();

		topPanel.setLayout(topBorderLayout);

		// create north panel
		northPanel = new JPanel();
		northPanel.setBorder(new EmptyBorder(0, 5, 0, 5));
		northPanel.setLayout(northBorderLayout);


		// create south panel
		southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 1));

		// create south - top and bottom panels
		southBottomPanel = new JPanel();


		//create the centre panel
		centralPanel = new JPanel();
		centralPanel.setBorder(new EmptyBorder(5, 5, 0, 5));
		centralPanel.setLayout(new GridLayout(1, 1));

		// fill the whole center of topPanel with text area
		// setup HabuTextArea
		habuTextArea = new HabuTextArea(this);
		habuTextArea.setup();

		centralPanel.add(habuTextArea.scrollBar);

		topPanel.add(centralPanel, BorderLayout.CENTER);


		// northPanel elements
		//northInsertLabel = new JLabel("Filename ");
		//northPanel.add(northInsertLabel, BorderLayout.WEST);

		fileNameField = new JTextField();

		// set a little gap after the starting of the textfield
		// for readability
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		fileNameField.setBorder(BorderFactory.createCompoundBorder(border, 
				BorderFactory.createEmptyBorder(0, 5, 0, 0)));
		fileNameField.setToolTipText("Enter absolute path to a file");
		fileNameField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent key) {
				if (key.getKeyCode() == KeyEvent.VK_ENTER){
					File f = new File(fileNameField.getText());
					HabuWindow.showFileInTextArea(f, habuTextArea.textArea, frame);
				}
			}
		});
		
		bufferStatusLabel = new JLabel(habuTextArea.bufferSaved?"":"*");
		northPanel.add(bufferStatusLabel);
		northPanel.add(fileNameField, BorderLayout.CENTER);

		findFileButton = new JButton("Open");
		findFileButton.setToolTipText("Open a text file");
		findFileButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				/*
				 * open the JFileChooser on click, and select a file.
				 * Put the absolute path of the file in the textfield
				 * */
				final JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					fileNameField.setText(file.getAbsolutePath());
					HabuWindow.showFileInTextArea(file, habuTextArea.textArea, frame);
				}
			}
		});

		northPanel.add(findFileButton, BorderLayout.EAST);

		// add the northPanel to topPanel
		topPanel.add(northPanel, BorderLayout.NORTH);

		// southPanel components
		statusLabel = new JLabel("Empty Buffer");

		southBottomPanel = new JPanel();
		southBottomPanel.add(new JLabel("Status - "));
		southBottomPanel.add(statusLabel);

		southPanel.add(southBottomPanel);

		// add southPanel to topPanel
		topPanel.add(southPanel, BorderLayout.SOUTH);

		setupMenu();
		// add the topPanel to frame
		this.frame.add(topPanel);
	}

	private void setupMenu(){
		habuMenuBar = new HabuMenuBar(this);
		if (habuTextArea.textArea == null)
			System.out.println("TextArea is NULL");
		habuMenuBar.addActionListenerToOpenMenuItem();
		habuMenuBar.addActionListenerToSaveAsMenuItem();
		habuMenuBar.addActionListenerToNewMenuItem();
		habuMenuBar.addActionListenerToExitMenuItem();
		habuMenuBar.addActionListenerToAboutHabuMenuItem();
		habuMenuBar.addActionListenerToClearMenuItem();
	}

	public File fileFromChooser(){
		File outFile;
		final JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			outFile = fileChooser.getSelectedFile();
			try {
				if (outFile.exists() == false)
					outFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return outFile;
		}
		return null;
	}
}
