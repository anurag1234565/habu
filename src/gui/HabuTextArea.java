package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class HabuTextArea {

	private UI ui;
	public JTextArea textArea;
	public JScrollPane scrollBar;
	public Font textAreaFont;
	
	public boolean bufferSaved;

	public HabuTextArea(UI ui){
		this.ui = ui;
		this.bufferSaved = true;
	}


	public void setup() {
		// create a text area
		textArea = new JTextArea();
		textArea.setName("textArea");
		// set the border gap inside the textarea on all sides, for readability purposes
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		//textArea.setBorder(BorderFactory.createCompoundBorder(border, 
		//            BorderFactory.createEmptyBorder(10, 10, 0, 10)));
		textAreaFont = new Font("ubuntu mono", Font.PLAIN, 15);
		textArea.setFont(textAreaFont);

		textArea.setBorder(new EmptyBorder(5, 5, 0, 0));
		// add a scrollbar to the testarea


		scrollBar = new JScrollPane(textArea);
		scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JViewport rowHeaderViewPort = new JViewport(); 
		rowHeaderViewPort.setPreferredSize(new Dimension(40, 0));
		final LineNumberComponent lineNumberComponent = new LineNumberComponent(textAreaFont);

		//add new line everytime enter is pressed
		textArea.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e){
				lineNumberComponent.setLines(getNumberOfLines());
			}
		});
		rowHeaderViewPort.setView(lineNumberComponent);	
		scrollBar.setRowHeader(rowHeaderViewPort);

		setupKeyBindings();
	}

	public int getNumberOfLines(){
		int lines = textArea.getLineCount();
		return (lines > 0)?lines:0;
	}

	private void setupKeyBindings(){
		InputMap textAreaInputMap = textArea.getInputMap();
		ActionMap textAreaActionMap = textArea.getActionMap();
		
		// save file
		textAreaInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "saveText");
		textAreaActionMap.put("saveText", new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				File file = new File(ui.fileNameField.getText());
				if (!file.exists())
					file = ui.fileFromChooser();
				try {
					HabuWindow.writeTextAreaToFile(
							file,
							textArea.getText(), ui.frame);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ui.statusLabel.setText("File saved");
				ui.fileNameField.setText(file.getAbsolutePath());
			}
		});
		
		textAreaInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK), "openFile");
		textAreaActionMap.put("openFile", new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				File file = ui.fileFromChooser();
				HabuWindow.showFileInTextArea(file, textArea, ui.frame);
				ui.fileNameField.setText(file.getAbsolutePath());
				ui.statusLabel.setText("File opened");
				bufferSaved = true;
			}
		});
		
		
		textAreaInputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), "newFile");
		textAreaActionMap.put("newFile", new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				ui.fileNameField.setText("");
				ui.statusLabel.setText("Buffer Cleared");
				bufferSaved = true;
			}
		});
	}
}