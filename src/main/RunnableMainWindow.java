package main;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JTextArea;

import gui.HabuWindow;
import gui.UI;

public class RunnableMainWindow {
	
	private static HabuWindow mainWin;
	private static UI ui;

	private static void init(){
		mainWin = new HabuWindow("Habu Text Editor");
		mainWin.setSize(800, 600);
		
		ui = new UI(mainWin);
		ui.setupUI();
	}
	
	private static void init(String filename){
		mainWin = new HabuWindow("Habu Text Editor");
		mainWin.setSize(800, 600);
		ui = new UI(mainWin);
		ui.setupUI();
		File file = new File(filename);
		HabuWindow.showFileInTextArea(file, ui.habuTextArea.textArea, ui.frame);
	}
	
	/**
	 * @param args file name to open the editor with 
	 */
	public static void main(String[] args) {
		Runnable r = new Runnable() {
			public void run() {
				if(args.length == 0) {
					init();
				}
				else{
					init(args[0]);
				}
				addKeyListeners();
				mainWin.setVisible(true);
			}
		};
		EventQueue.invokeLater(r);
	}
	
	private static void addKeyListeners(){
		mainWin.addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_S))
					System.out.println("Save file!!!!!!");
			}
		});
	}
	
}
