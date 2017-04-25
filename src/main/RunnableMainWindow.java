package main;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runnable r = new Runnable() {
			public void run() {
				init();
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