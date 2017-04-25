package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;


public class HabuWindow extends JFrame {

	String buffer_name;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private void init(){
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(300, 200));
		
	}

	public HabuWindow(String title, int w, int h){
		setTitle(title);
		setSize(w, h);
		this.init();
	}

	public HabuWindow(String title){
		setTitle(title);
		setSize(800, 600);
		this.init();
	}

	public List<Component> getAllComponents(final Container cont){
		/* this function gets a list of components for the frame,
		 * then recursively gets all the components of all the children
		 */ 
		List<Component> allComponents = new ArrayList<Component>();

		for (Component c : cont.getComponents()){
			allComponents.add(c);
			if (c instanceof Container){
				// if c is a container, then add all it's children too
				allComponents.addAll(getAllComponents((Container) c));
			}
		}
		return allComponents;
	}


	public Component getComponentByName(String name){
		List<Component> allComponents = getAllComponents(this);
		for (Component c : allComponents){
			if (c.getName() == name)
				return c;
		}
		return null;
	}


	public static void writeTextAreaToFile(File file, String buffer, HabuWindow frame) throws FileNotFoundException{
		FileWriter fileWriter = null;
		// open the file
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame,
					"The file you are trying to open was not found!",
					"File Not Found Error",
					JOptionPane.ERROR_MESSAGE);
		}
		// overwrite the data from the textarea into the file.
		try {
			fileWriter.write(buffer.toCharArray());
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void showFileInTextArea(File file, JTextArea textArea, HabuWindow frame){ 
		// create a FileReader
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			// file not found
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame,
					"The file you are trying to open was not found!",
					"File Not Found Error",
					JOptionPane.ERROR_MESSAGE);
		}

		int ch;
		String data = "";
		// read data from file and store it in string called 'data'
		try {
			while ((ch = fileReader.read()) != -1)
				data += String.valueOf((char)ch);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// set the text in textarea to the buffer from the file
		textArea.setText(data);
	}
}
