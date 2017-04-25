package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class LineNumberComponent extends JTextArea{

	int lineCount = 1;

	public LineNumberComponent(Font font){
		super();
		this.setFont(font);
		this.setEditable(false);
		this.setHighlighter(null);
		this.setForeground(Color.white);
		this.setBackground(Color.black);
		this.setLines(lineCount);
		this.setBorder(new EmptyBorder(5, 3, 0, 0));
	}

	public void insertNewLine(){
		this.setText(this.getText() + "\n" + String.valueOf(lineCount));
		lineCount++;
	}


	void setLines(int noLines){
		String lines = "";
		for (int i = 1; i  <= noLines;  i++)
			lines += String.valueOf(i) + "\n";
		this.setText(lines);
	}

}
