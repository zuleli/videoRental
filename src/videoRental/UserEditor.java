package videoRental;

/* Author: Zule Li
 * Email:zule.li@hotmail.com
 * Last Modified Date:Mar.7,2015
 * */

import javax.swing.*;
import java.awt.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.*;

public class UserEditor extends JDialog implements ActionListener {
	JTable users = new JTable();
	JButton add = new JButton();
	JButton delete = new JButton();
	JButton close = new JButton();
	JComboBox type = new JComboBox();
	BorrowingRecordModel userModel;
	GridBagLayout gridBagLayout1 = new GridBagLayout();

	public UserEditor() {

		try {
			String[] columnNames = new String[] { "First Name", "Last Name",
					"User Type", "Password" };
			boolean[] edit = new boolean[] { true, true, false, true };
			userModel = new BorrowingRecordModel(columnNames, edit);
			userModel.setID("userModel");

			users = new JTable(userModel);
			type.addItem("GENERAL");
			type.addItem("ITEM");
			type.addItem("SETTING");
			jbInit();
			pack();
			setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == close) {
			dispose();
			return;
		}

	}

	private void jbInit() throws Exception {
		JScrollPane scr = new JScrollPane();
		scr.getViewport().add(users);
		this.setTitle("Users Editor");
		users.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		add.setText("Add");
		add.addActionListener(this);
		delete.setText("Delete");
		delete.addActionListener(this);
		close.setText("Close");
		close.addActionListener(this);
		JPanel p = new JPanel(new GridBagLayout());
		p.setPreferredSize(new Dimension(450, 300));
		p.add(type, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(30, 5, 35, 0), -25, 4));
		p.add(close, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						30, 50, 35, 7), 13, -2));
		p.add(delete, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						30, 50, 35, 0), 19, -2));
		p.add(add, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						30, 40, 35, 0), 25, -2));
		p.add(scr, new GridBagConstraints(0, 0, 4, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						5, 5, 0, 7), 490, 240));
		this.getContentPane().add(p);
	}
}
