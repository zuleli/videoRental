/* Author: Zule Li
 * Email:zule.li@hotmail.com
 * Last Modified Date:Mar.7,2015
 * */
package videoRental;

//Copyright (c) 2000 HOME
import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;

/**
 * 
 * @author ZULE LI
 */
public class Display extends JDialog {

	/**
	 * Constructs a new instance.
	 */
	public Display(String s) {
		super();
		JTextArea area = new JTextArea(s);
		area.setEditable(false);
		JScrollPane scroll = new JScrollPane(area);
		scroll.setPreferredSize(new Dimension(400, 300));
		Container content = getContentPane();
		content.add(scroll);
		pack();
		setVisible(true);
	}

	/**
	 * Initializes the state of this instance.
	 */
	private void jbInit() throws Exception {
	}
}
