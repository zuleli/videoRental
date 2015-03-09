package videoRental;

/* Author: Zule Li
 * Email:zule.li@hotmail.com
 * Last Modified Date:Mar.7,2015
 * */

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.*;
//import javax.swing.
//import oracle.jdeveloper.layout.XYConstraints;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.SwingConstants;
import javax.swing.JButton;
///import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.*;
import javax.swing.event.*;

public class Main extends JFrame implements ActionListener,
		ListSelectionListener, FocusListener {
	private String password = "xxxx";

	JLabel jLabel10 = new JLabel();
	int step = 0;
	JTextField enterfield = new JTextField(12);
	JRadioButton onOff = new JRadioButton();
	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();
	JTextField firstName, lastName, address, apt, phone;
	JTable borrowingRecordTable, customerTable, itemTable, catagoryTable;
	BorrowingRecordModel borrowingRecordModel, customerModel, itemModel,
			catagoryModel;
	JButton delete = new JButton();
	JPanel jPanel3 = new JPanel();
	GridBagLayout gridBagLayout1 = new GridBagLayout();

	JTabbedPane tabbed = new JTabbedPane();
	Vector returns = new Vector();

	JTextField firstName0, lastName0, street, apt0, phone0, city, zip, title,
			location, itemID, summary, publisher, version;
	JRadioButton searchOnOff, detail_summary, search;
	JButton update, check, newAccount, deleteAccount, itemUpdate, itemAdd,
			itemDelete, print, preview, itemConfirm;
	JComboBox state, catagory;

	JLabel barcodePreview;
	JTextField prefix, itemPrefix, welcome;

	JComboBox copy, row, column, noOfItem, digit;
	ListSelectionModel rowSM, rowSMItem;

	JList stateEditor;
	DefaultListModel cataModel, stateModel;
	JTextField cataField, stateField, itemField;
	JButton cataAdd, cataRemove, stateAdd, stateRemove;
	String operator = "NA";
	String ACCOUNT = "";
	private JMenuItem help, about, logOff, logOn;

	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			dispose();
			System.exit(0);
		}
		super.processWindowEvent(e);
	}

	public Main() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			initial();
			jbInit();

			enableEvents(AWTEvent.WINDOW_EVENT_MASK);

			this.setTitle("Vedio Rental");
			pack();
			setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initial() {
		search = new JRadioButton();
		search.addActionListener(this);
		itemPrefix = new JTextField();
		apt = new JTextField();
		firstName = new JTextField();
		lastName = new JTextField();
		address = new JTextField();
		phone = new JTextField();
		newAccount = new JButton();
		newAccount.addActionListener(this);
		deleteAccount = new JButton();
		deleteAccount.addActionListener(this);
		delete.setText("Delete");
		delete.addActionListener(this);
		state = new JComboBox();
		state.addActionListener(this);
		enterfield.addActionListener(this);
		onOff.addActionListener(this);

		title = new JTextField();
		location = new JTextField();
		itemID = new JTextField();
		catagory = new JComboBox();
		catagory.addActionListener(this);
		summary = new JTextField();
		publisher = new JTextField();
		version = new JTextField();
		itemUpdate = new JButton("Update");
		itemUpdate.addActionListener(this);
		itemAdd = new JButton("Add");
		itemAdd.addActionListener(this);
		itemDelete = new JButton("Delete");
		itemDelete.addActionListener(this);

		prefix = new JTextField("A");
		copy = new JComboBox();
		print = new JButton("Print");
		print.addActionListener(this);
		preview = new JButton("Preview");
		preview.addActionListener(this);
		String[] values = new String[] { "1", "2", "3", "4", "5", "6", "7", "8" };
		row = new JComboBox(values);
		column = new JComboBox(values);
		noOfItem = new JComboBox(values);
		values = new String[] { "1", "2", "3", "4", "5", "6" };
		digit = new JComboBox(values);
		digit.setSelectedIndex(3);
		digit.addActionListener(this);

		searchOnOff = new JRadioButton("Active Only");
		searchOnOff.addActionListener(this);
		detail_summary = new JRadioButton("Detail");
		detail_summary.addActionListener(this);
		update = new JButton("Update");
		update.addActionListener(this);
		check = new JButton("Check");
		check.addActionListener(this);
		newAccount.setText("New Account");
		deleteAccount.setText("Delete");

		cataField = new JTextField();
		cataAdd = new JButton("Add");
		cataAdd.addActionListener(this);
		cataRemove = new JButton("Remove");
		cataRemove.addActionListener(this);

		stateModel = new DefaultListModel();
		stateEditor = new JList(stateModel);
		stateEditor.setBorder(BorderFactory.createLineBorder(Color.black, 1));

		stateField = new JTextField();
		stateAdd = new JButton("Add");
		stateAdd.addActionListener(this);
		stateRemove = new JButton("Remove");
		stateRemove.addActionListener(this);

		Vector ss = Database.getSetting("State");
		for (int i = 0; i < ss.size(); i++) {
			stateModel.addElement(ss.get(i));
			state.addItem(ss.get(i));
		}

		ss = Database.getSetting("Catagory");
		for (int i = 0; i < ss.size(); i++) {
			catagory.addItem(((String[]) (ss.get(i)))[0]);
		}

		String[] columnNames = new String[] { "Catagory", "#Of Days Allowed" };
		boolean[] edit = new boolean[] { true, true };
		catagoryModel = new BorrowingRecordModel(columnNames, edit);
		catagoryModel.setID("catagoryModel");

		TableSorter sorter4 = new TableSorter(catagoryModel); // ADDED THIS
		catagoryModel.setData(ss);
		catagoryTable = new JTable(sorter4);
		sorter4.setTableHeader(catagoryTable.getTableHeader()); // ADDED THIS

		itemField = new JTextField(12);
		itemField.setPreferredSize(new Dimension(12, 25));
		enterfield.setPreferredSize(new Dimension(12, 25));
		itemField.addActionListener(this);
		itemConfirm = new JButton("Confirm");
		itemConfirm.addActionListener(this);
		itemConfirm.setEnabled(false);
		enterfield.addFocusListener(this);
		disableIt(delete);
		disableIt(firstName);
		disableIt(lastName);
		disableIt(address);
		disableIt(apt);
		disableIt(phone);
		disableIt(prefix);

		welcome = new JTextField(Database.getNextValue("Greeting"));
		welcome.addActionListener(this);

	}

	public static void main(String[] args) {
		Main main = new Main();
	}

	private void jbInit() throws Exception {

		JPanel panel0 = new JPanel(new GridLayout(2, 1));
		jLabel10.setText("Account No.");
		jLabel10.setHorizontalAlignment(SwingConstants.TRAILING);
		onOff.setText("Return Off");
		jPanel3.setLayout(gridBagLayout1);
		// *****************************************
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		JMenu usermenu = new JMenu("Users");
		JMenu helpmenu = new JMenu("Help");
		menubar.add(usermenu);
		menubar.add(helpmenu);
		help = helpmenu.add("User Manual");
		help.addActionListener(this);
		about = helpmenu.add("About");
		about.addActionListener(this);
		logOff = usermenu.add("Log OFF");
		logOff.addActionListener(this);
		logOn = usermenu.add("Log ON");
		logOn.addActionListener(this);

		GridBagLayout gridBagLayout1 = new GridBagLayout();
		jPanel1.setLayout(gridBagLayout1);
		JLabel jLabel1 = new JLabel();
		jLabel1.setText("First Name");
		jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
		JLabel jLabel2 = new JLabel();
		jLabel2.setText("Last Name");
		jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);

		JLabel jLabel3 = new JLabel();
		jLabel3.setText("Address:");
		jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
		JLabel jLabel4 = new JLabel();
		jLabel4.setText("Apt#:");
		JLabel jLabel5 = new JLabel();
		jLabel4.setHorizontalAlignment(SwingConstants.TRAILING);
		jLabel5.setText("Phone#:");
		jLabel5.setHorizontalAlignment(SwingConstants.TRAILING);
		search.setText("Search OFF");
		jPanel1.add(search, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						10, 0, 0, 30), 27, 3));
		jPanel1.add(phone, new GridBagConstraints(2, 4, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(20, 0, 5, 0), 147, 14));
		jPanel1.add(jLabel5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						25, 35, 5, 0), 4, 13));
		jPanel1.add(apt, new GridBagConstraints(6, 2, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(20, 0, 0, 30), 92, 14));
		jPanel1.add(jLabel4, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						25, 25, 0, 0), 8, 13));
		jPanel1.add(address, new GridBagConstraints(2, 2, 3, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(20, 0, 0, 0), 332, 14));
		jPanel1.add(jLabel3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						25, 30, 0, 0), 6, 8));
		jPanel1.add(lastName, new GridBagConstraints(4, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(10, 10, 0, 0), 92, 9));
		jPanel1.add(jLabel2, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						10, 0, 0, 0), 14, 8));
		jPanel1.add(firstName, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(10, 0, 0, 0), 147, 9));
		jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						10, 15, 0, 0), 14, 8));
		TitledBorder titledBorder = new TitledBorder(new EtchedBorder(),
				"Customer Information");
		jPanel1.setBorder(titledBorder);
		TitledBorder titledBorder2 = new TitledBorder(new EtchedBorder(),
				"Borrowing Recording");

		String[] columnNames = new String[] { "ItemID", "Title", "Date Out",
				"Date Due" };
		boolean[] edit = new boolean[] { true, false, false, false };
		borrowingRecordModel = new BorrowingRecordModel(columnNames, edit);
		borrowingRecordModel.setID("borrowingRecordModel");
		borrowingRecordTable = new JTable(borrowingRecordModel);
		JScrollPane scr = new JScrollPane();
		scr.getViewport().add(borrowingRecordTable);

		columnNames = new String[] { "Account#", "First Name", "Last Name",
				"Phone", "Create Date", "Status" };
		edit = new boolean[] { false, false, false, false, false, false, false };
		customerModel = new BorrowingRecordModel(columnNames, edit);
		TableSorter sorter = new TableSorter(customerModel); // ADDED THIS
		customerModel.setData(Database.getCustomers("Active"));
		customerModel.setID("CustomerModel");
		customerTable = new JTable(sorter);
		sorter.setTableHeader(customerTable.getTableHeader()); // ADDED THIS
		JScrollPane scr2 = new JScrollPane();
		scr2.getViewport().add(customerTable);
		// customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rowSM = customerTable.getSelectionModel();
		rowSM.addListSelectionListener(this);
		// customerTable.setsesetRowSelectionInterval(1,5);

		JLabel jLabel12 = new JLabel("First Name:");
		firstName0 = new JTextField();
		JLabel jLabel22 = new JLabel("Last Name:");
		lastName0 = new JTextField();
		JLabel jLabel32 = new JLabel("Street:");
		street = new JTextField();
		JLabel jLabel42 = new JLabel("APT#:");
		apt0 = new JTextField();
		JLabel jLabel52 = new JLabel("Phone#:");
		phone0 = new JTextField();
		JLabel jLabel62 = new JLabel("City:");
		city = new JTextField();
		JLabel zi = new JLabel("Zip:");
		zip = new JTextField();

		JPanel panelc1 = new JPanel(new GridBagLayout());
		JLabel jLabel72 = new JLabel();
		jLabel72.setText("State");
		jLabel72.setHorizontalAlignment(SwingConstants.TRAILING);
		panelc1.add(state, new GridBagConstraints(4, 5, 2, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 10, 0, 0), -20, 9));
		panelc1.add(jLabel72, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
						0, 0, 0), 12, 8));
		panelc1.add(deleteAccount, new GridBagConstraints(10, 5, 1, 2, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(0, 0, 0, 0), 34, 3));
		panelc1.add(newAccount, new GridBagConstraints(10, 8, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						0, 0, 0, 0), 0, 3));
		panelc1.add(check, new GridBagConstraints(8, 8, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						0, 15, 0, 0), 11, 3));
		panelc1.add(update, new GridBagConstraints(4, 8, 3, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						0, 35, 0, 0), 15, 3));
		panelc1.add(searchOnOff, new GridBagConstraints(10, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						10, 0, 0, 0), 23, 0));
		panelc1.add(zip, new GridBagConstraints(8, 5, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 82, 9));
		panelc1.add(zi, new GridBagConstraints(7, 5, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
						0, 0, 0), 5, 13));
		panelc1.add(city, new GridBagConstraints(1, 5, 2, 2, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 92, 14));
		panelc1.add(jLabel62, new GridBagConstraints(0, 5, 1, 3, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						10, 40, 0, 0), 32, 13));
		panelc1.add(phone0, new GridBagConstraints(1, 8, 3, 2, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 35, 0), 132, 14));
		panelc1.add(jLabel52, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
						45, 0, 0), 4, 13));
		panelc1.add(apt0, new GridBagConstraints(10, 2, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 97, 14));
		panelc1.add(jLabel42, new GridBagConstraints(9, 2, 1, 2, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						10, 15, 0, 0), 8, 13));
		panelc1.add(street, new GridBagConstraints(1, 2, 8, 3, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(10, 0, 0, 0), 372, 14));
		panelc1.add(jLabel32, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						10, 35, 0, 0), 20, 8));
		panelc1.add(lastName0, new GridBagConstraints(5, 0, 4, 2, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(10, 0, 0, 0), 157, 9));
		panelc1.add(jLabel22, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						10, 0, 0, 0), 14, 8));
		panelc1.add(firstName0, new GridBagConstraints(1, 0, 3, 2, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(10, 0, 0, 0), 132, 9));
		panelc1.add(jLabel12, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						10, 15, 0, 0), 14, 8));

		// titledBorder= new TitledBorder( new EtchedBorder(),
		// "Customer Information");
		// panelc1.setBorder( titledBorder);
		JScrollPane scr21 = new JScrollPane();
		scr21.getViewport().add(panelc1);
		JSplitPane panelc = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scr21,
				scr2);
		panelc.setOneTouchExpandable(true);
		panelc.setDividerLocation(150);

		JLabel jLabel13 = new JLabel("Title:");
		JLabel jLabel23 = new JLabel("Catagory:");
		JLabel jLabel33 = new JLabel("Location:");
		JLabel jLabel53 = new JLabel("Item ID");
		JLabel jLabel103 = new JLabel("Summary:");
		JLabel jLabel13333 = new JLabel();
		JLabel jLabel23333 = new JLabel();

		JPanel jPanel1Item = new JPanel(new GridBagLayout());
		jLabel103.setHorizontalAlignment(SwingConstants.TRAILING);
		jLabel13.setHorizontalAlignment(SwingConstants.TRAILING);
		jLabel13333.setText("Version");
		jLabel13333.setHorizontalAlignment(SwingConstants.TRAILING);
		jLabel23333.setText("Publisher:");
		jLabel23333.setHorizontalAlignment(SwingConstants.TRAILING);
		jPanel1Item.add(detail_summary, new GridBagConstraints(8, 9, 1, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(20, 25, 30, 0), 48, 0));
		jPanel1Item.add(summary, new GridBagConstraints(1, 7, 9, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(15, 0, 0, 75), 437, 9));
		jPanel1Item.add(publisher, new GridBagConstraints(6, 4, 4, 2, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(15, 0, 0, 75), 202, 14));
		jPanel1Item.add(jLabel23333, new GridBagConstraints(3, 4, 3, 1, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(15, 30, 0, 0), 4, 8));
		jPanel1Item.add(version, new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(20, 0, 0, 0), 137, 9));
		jPanel1Item.add(jLabel13333, new GridBagConstraints(0, 2, 1, 1, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(20, 30, 0, 0), 13, 8));
		jPanel1Item.add(jLabel103, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						15, 25, 0, 0), 4, 8));
		jPanel1Item.add(itemDelete, new GridBagConstraints(5, 9, 3, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(20, 0, 30, 0), 14, -2));
		jPanel1Item.add(itemAdd, new GridBagConstraints(2, 9, 2, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						20, 25, 30, 0), 35, -2));
		jPanel1Item.add(itemUpdate, new GridBagConstraints(1, 9, 1, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(20, 0, 30, 0), 10, -2));
		jPanel1Item.add(catagory, new GridBagConstraints(6, 0, 4, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 75), 85, 9));
		jPanel1Item.add(location, new GridBagConstraints(6, 2, 4, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(20, 0, 0, 75), 202, 9));
		jPanel1Item.add(jLabel33, new GridBagConstraints(3, 2, 3, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						20, 30, 0, 0), 5, 13));
		jPanel1Item.add(jLabel23, new GridBagConstraints(3, 0, 2, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
						30, 0, 0), 1, 8));
		jPanel1Item.add(title, new GridBagConstraints(1, 0, 2, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 137, 9));
		jPanel1Item.add(jLabel13, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
						10, 0, 0), 52, 8));
		jPanel1Item.add(itemID, new GridBagConstraints(1, 4, 2, 2, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(15, 0, 0, 0), 137, 14));
		jPanel1Item.add(jLabel53, new GridBagConstraints(0, 4, 1, 2, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						15, 35, 0, 0), 8, 13));
		columnNames = new String[] { "Item ID", "Title", "Version", "Location",
				"Catagory", "Status", "Account#", "Publisher", "Date Added" };
		edit = new boolean[] { false, false, false, false, false, false, false,
				false, false };
		itemModel = new BorrowingRecordModel(columnNames, edit);
		TableSorter sorter2 = new TableSorter(itemModel); // ADDED THIS
		itemModel.setData(Database.getItems("Summary"));
		itemTable = new JTable(sorter2);
		sorter2.setTableHeader(itemTable.getTableHeader()); // ADDED THIS

		// itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rowSMItem = itemTable.getSelectionModel();
		rowSMItem.addListSelectionListener(this);

		itemModel.setID("itemModel");
		JScrollPane scr33 = new JScrollPane();
		scr33.getViewport().add(itemTable);
		JScrollPane scr332 = new JScrollPane();
		scr332.getViewport().add(jPanel1Item);

		JSplitPane pItem = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scr332,
				scr33);
		pItem.setOneTouchExpandable(true);
		pItem.setDividerLocation(250);

		JLabel jLabel14 = new JLabel("Prefix");
		JLabel jLabel24 = new JLabel("#Of Copy");
		JLabel jLabel34 = new JLabel("#Of Item");
		JLabel jLabel54 = new JLabel("Row");
		JLabel jLabel144 = new JLabel("Column");
		JLabel jLabel1444 = new JLabel("Digit");
		JLabel jLabel142 = new JLabel("ItemPrefix");
		jLabel142.setHorizontalAlignment(SwingConstants.TRAILING);

		JScrollPane scr44 = new JScrollPane();
		scr44.getViewport().add(catagoryTable);
		catagoryTable.setPreferredSize(new Dimension(120, 120));
		JScrollPane scr45 = new JScrollPane();
		scr45.getViewport().add(stateEditor);
		barcodePreview = new JLabel();
		JPanel barcode = new JPanel(new GridBagLayout());
		barcode.add(barcodePreview, new GridBagConstraints(4, 0, 1, 8, 0.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(10, 25, 0, 10), 390, 350));
		barcode.add(itemPrefix, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(10, 0, 0, 0), 87, 9));
		barcode.add(jLabel142, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						10, 10, 0, 0), 8, 13));
		barcode.add(noOfItem, new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(10, 0, 0, 0), -30, 9));
		barcode.add(jLabel144, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						10, 20, 0, 0), 11, 13));
		barcode.add(copy, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(15, 0, 0, 0), -30, 9));
		barcode.add(jLabel24, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						15, 25, 0, 0), 2, 13));
		barcode.add(row, new GridBagConstraints(3, 3, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(10, 0, 0, 0), -30, 9));
		barcode.add(jLabel34, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
						35, 0, 0), 12, 13));
		barcode.add(column, new GridBagConstraints(3, 5, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(20, 0, 0, 0), -30, 9));
		barcode.add(jLabel54, new GridBagConstraints(0, 5, 3, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(
						20, 30, 0, 0), 8, 13));
		barcode.add(print, new GridBagConstraints(2, 7, 2, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						15, 0, 0, 0), 41, 8));
		barcode.add(preview, new GridBagConstraints(2, 6, 2, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						65, 0, 0, 0), 23, 8));

		JPanel setting = new JPanel(new GridBagLayout());
		JPanel jPanel14321 = new JPanel();
		jPanel14321.setLayout(new GridBagLayout());
		jPanel14321.setBorder(BorderFactory
				.createTitledBorder("State(Province) Editor"));
		JPanel jPanel14322 = new JPanel();
		jPanel14322.setLayout(new GridBagLayout());
		jPanel14322.setBorder(BorderFactory
				.createTitledBorder("Catagory Editor"));
		JLabel jLabel44323 = new JLabel();
		jLabel44323.setText("Message:");
		jLabel44323.setHorizontalAlignment(SwingConstants.TRAILING);
		jPanel14321.add(stateField, new GridBagConstraints(0, 0, 1, 1, 1.0,
				0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(25, 10, 0, 0), 117, 9));
		jPanel14321.add(stateAdd, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						10, 10, 0, 0), 65, 3));
		jPanel14321.add(stateRemove, new GridBagConstraints(0, 2, 1, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(10, 10, 8, 0), 39, 3));
		jPanel14321.add(scr45, new GridBagConstraints(2, 0, 1, 3, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						25, 10, 8, 8), 120, 110));

		jPanel14322.add(scr44, new GridBagConstraints(0, 1, 3, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						10, 5, 3, 3), 310, 80));
		jPanel14322.add(cataAdd, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						25, 45, 0, 0), 40, -2));
		jPanel14322.add(cataRemove, new GridBagConstraints(1, 0, 1, 1, 0.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(25, 40, 0, 0), 9, -2));
		setting.add(welcome, new GridBagConstraints(5, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 10, 0, 5), 312, 9));
		setting.add(jLabel44323, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
						10, 0, 0), 31, 13));
		setting.add(jPanel14322, new GridBagConstraints(5, 0, 1, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						5, 10, 0, 5), -10, 4));
		setting.add(jPanel14321, new GridBagConstraints(0, 0, 5, 1, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						5, 5, 0, 0), -15, 4));
		setting.add(digit, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 10, 0, 0), 27, 9));
		setting.add(jLabel1444, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
						10, 0, 0), 10, 8));
		setting.add(prefix, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 27, 9));
		setting.add(jLabel14, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0,
						15, 0, 0), 5, 8));

		jPanel14321.setPreferredSize(new Dimension(150, 130));
		jPanel14322.setPreferredSize(new Dimension(150, 130));
		JScrollPane scr42 = new JScrollPane();
		scr42.getViewport().add(barcode);
		JScrollPane scr43 = new JScrollPane();
		scr43.getViewport().add(setting);
		JSplitPane splitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				scr43, scr42);
		splitPane3.setOneTouchExpandable(true);
		splitPane3.setDividerLocation(250);

		// ******************************/
		jPanel3.add(jLabel10);
		jPanel3.add(enterfield);
		jPanel3.add(new JLabel("Item ID:"));
		jPanel3.add(itemField);
		jPanel3.add(itemConfirm);
		jPanel3.add(delete);
		jPanel3.add(onOff);

		JPanel panelU = new JPanel(new BorderLayout());
		panelU.add(jPanel3, BorderLayout.NORTH);
		panelU.add(scr);
		panel0.add(panelU);
		panel0.add(jPanel1);

		tabbed.add("Main", panel0);
		tabbed.add("Customer", panelc);
		tabbed.add("Items", pItem);
		tabbed.add("Settings", splitPane3);
		tabbed.setPreferredSize(new Dimension(700, 550));
		this.getContentPane().add(tabbed);
	}

	public void valueChanged(ListSelectionEvent e) {

		if (e.getValueIsAdjusting())
			return;

		ListSelectionModel lsm = (ListSelectionModel) e.getSource();
		if (lsm.isSelectionEmpty()) {
			;// no rows are selected
		} else {
			int selectedRow = lsm.getMinSelectionIndex();
			if (lsm == rowSM) {
				String accountno = (String) (customerTable.getValueAt(
						selectedRow, 0));
				String[] ss = Database.getCustomerInfo(accountno);

				// 0-first,1=last,2=street,3=apt,4=city,5=state,6=zip,7=phone
				firstName0.setText(ss[1]);
				lastName0.setText(ss[2]);
				street.setText(ss[3]);
				apt0.setText(ss[4]);
				city.setText(ss[5]);
				state.setSelectedItem(ss[6]);
				zip.setText(ss[7]);
				phone0.setText(ss[8]);
			}

			if (lsm == rowSMItem) {

				String accountno = (String) (itemTable.getValueAt(selectedRow,
						0));
				String[] ss = Database.getItem(accountno);
				itemID.setText(ss[0]);
				title.setText(ss[1]);
				version.setText(ss[2]);
				location.setText(ss[3]);
				catagory.setSelectedItem(ss[4]);
				publisher.setText(ss[5]);
				summary.setText(ss[6]);
				itemID.setEnabled(false);
			}
		}
	}

	public void focusLost(FocusEvent e) {
		enterfield.setText(ACCOUNT);
	}

	public void focusGained(FocusEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == welcome) {
			welcomeOp();// vv
			return;
		}
		if (source == itemField) {
			itemFieldOp();// vv
			return;
		}

		if (source == stateAdd) {
			stateAddOp();// vv
			return;
		}
		if (source == stateRemove) {
			stateRemoveOp();// vv
			return;
		}
		if (source == cataAdd) {
			cataAddOp();// vv
			return;
		}
		if (source == cataRemove) {
			cataRemoveOp();// vv
			return;
		}
		if (source == itemUpdate) {
			itemUpdateOp();// vv
			return;
		}
		if (source == itemDelete) {
			itemDeleteOp();// vv
			return;
		}
		if (source == detail_summary) {
			detail_summaryOp();// vv
			return;
		}
		if (source == onOff) {
			onOffOp();// vv
			return;
		}
		if (source == enterfield) {
			enterfieldOp();// vv
			return;
		}
		if (source == delete) {
			deleteOp();// vv
			return;
		}
		if (source == deleteAccount) {
			deleteAccountOp();// vv
			return;
		}
		if (source == newAccount) {
			newAccountOp();// vv
			return;
		}
		if (source == update) {
			updateOp();// vv
			return;
		}
		if (source == searchOnOff) {
			searchOnOffOp();// VV
			return;
		}
		if (source == itemAdd) {
			itemAddOp();// VV
			return;
		}
		if (source == itemConfirm) {
			itemConfirmOp();// VV
			return;
		}
		if (source == check) {
			checkOp();// VV
			return;
		}
		if (source == digit) {
			digitOp();// VV
			return;
		}
		if (source == search) {
			searchOp();// VV
			return;
		}
		if (source == about) {
			aboutOp();// VV
			return;
		}
		if (source == logOn) {
			logOnOp();// VV
			return;
		}

	}

	private void logOnOp() {
		Password display = new Password(this);
	}

	private void aboutOp() {
		Display display = new Display(Database.getNextValue("About"));
	}

	private void searchOp() {
		if (search.isSelected()) {
			search.setText("Search ON");
			disableIt(onOff);
			disableIt(delete);
			disableIt(itemConfirm);
			able(enterfield);
			able(itemField);
			setTitle("Search");
		} else {
			search.setText("Search OFF");
			able(onOff);
			String[] names = new String[] { "ItemID", "Title", "Date Out",
					"Date Due" };
			borrowingRecordModel.setNames(names);
			borrowingRecordModel.setData(new Vector());
			clear();
			enterfield.setText("");
			itemField.setText("");
		}
	}

	private void digitOp() {
		String s = JOptionPane
				.showInputDialog("Please Enter Your Password to Enable this change");
		if (s == null || !s.equals(password)) {
			JOptionPane.showMessageDialog(null, "Password is not correct");
			return;
		}
		able(prefix);
		Database.updateDigit((String) digit.getSelectedItem());
	}

	private void welcomeOp() {
		String message = welcome.getText().trim();
		Database.setGreetingMessage(message);
	}

	private void checkOp() {
		int index = customerTable.getSelectedRow();
		if (index < 0) {
			JOptionPane.showMessageDialog(null,
					"Please Select An Account in the Table");
			return;
		}

		String value = (customerModel.getValueAt(index, 0)).toString();
		account(value);
	}

	private void test() {

	
		String[] acc = new String[7];
		for (int i = 0; i < 3000; i++) {
			// 0-id,1=title,2=version,3=location,4=catagory,5-publisher,6=summary
			acc[0] = "B" + (1000 + i);
			acc[1] = "Title" + i;
			acc[2] = "Version" + i;
			acc[3] = "KD" + i;
			acc[4] = "Video CD";
			acc[5] = "Publisher" + i;
			acc[6] = "This is a story about somebody No." + i;
			Database.addItem(acc);
		}

	}

	private void itemConfirmOp() {

		String message = Database.getNextValue("Greeting");

		boolean[] result = null;
		String[] itemIDs = null;

		if (onOff.isSelected()) {
			message = message
					+ "\nThe following items have been returned.\nKeep this receipt for your record";
			message = message + "\nItem,   ItemID,   Title\n";
			itemIDs = new String[returns.size()];
			String[] tem = null;
			for (int i = 0; i < returns.size(); i++) {
				tem = (String[]) (returns.get(i));
				itemIDs[i] = tem[0];
				message = message + "\n" + (i + 1) + ",  " + tem[0] + ",  "
						+ tem[1];
			}
			message = message + "\nAccount Number:" + ACCOUNT;
			message = message + "\nName:" + tem[3] + " " + tem[4];
			result = Database.itemReturned(itemIDs, operator, this);
			Database.toHistory(returns);
			returns.removeAllElements();
			setTitle("Return Operation");
			disableIt(itemConfirm);
			disableIt(delete);

		} else {
			message = message
					+ "\nYou have borrowed the following items.\nKeep this receipt for your record";
			message = message + "\nAccount Number:" + ACCOUNT;
			message = message + "\nName:" + firstName.getText().trim() + " "
					+ lastName.getText().trim();
			message = message
					+ "\nItem,   ItemID,   Title,          Due Date;\n";
			enterfield.setEnabled(true);
			onOff.setEnabled(true);
			itemIDs = borrowingRecordModel.getUnConfirmedIDs();
			result = Database.itemsOut(itemIDs, operator, enterfield.getText()
					.trim(), this);
			setTitle("Check_Out Operation");
			for (int i = 0; i < itemIDs.length; i++) {
				message = message + "\n" + (i + 1) + ",  " + itemIDs[i] + ", "
						+ Database.getItemInfo(itemIDs[i], "Title") + ",  "
						+ getDueDate(itemIDs[i]);
			}
			able(search);
		}
		message = message + "\nOperator is " + operator + ". Thank you" + "!\n"
				+ (new java.util.Date(System.currentTimeMillis())).toString();
		boolean ok = true;
		String s = "The following Item(s) failed to updated\n";
		for (int i = 0; i < result.length; i++) {
			if (!result[i]) {
				s = s + itemIDs[i] + "\n";
				ok = false;
			}
		}
		if (!ok)
			JOptionPane.showMessageDialog(null, s);
		else if (!onOff.isSelected()) {
			itemConfirm.setEnabled(false);
			disableIt(delete);
		}

		if (onOff.isSelected()) {
			Vector br = Database.getBorrowingRecord(ACCOUNT);
			borrowingRecordModel.setData(br);
		} else
			borrowingRecordModel.setConfirmedIDs(itemIDs, result);
		System.out.println(message);

	}

	private void stateAddOp() {
		String value = stateField.getText();
		if (value == null || value.length() == 0) {
			JOptionPane.showMessageDialog(null, "Please Enter Value");
			return;
		}
		value = value.trim().toUpperCase();
		String[] values = new String[2];
		values[0] = value;
		if (Database.addSetting(values, "State")) {
			stateModel.addElement(value);
			state.addItem(value);
		} else
			JOptionPane.showMessageDialog(null, "Adding Failed");
		stateField.setText("");
		stateField.requestFocus();

	}

	private void cataAddOp() {
		if (!catagoryModel.check("New Catagory", 0)) {
			String[] ss = new String[2];
			ss[0] = "New Catagory";
			ss[1] = "14";
			if (Database.addSetting(ss, "Catagory"))
				;
			catagoryModel.addData(ss);
		}

	}

	private void cataRemoveOp() {
		// *
		int index = catagoryTable.getSelectedRow();
		if (index < 0) {
			JOptionPane.showMessageDialog(null,
					"Please Choose an Item to Remove");
			return;
		}
		String value = (String) (catagoryTable.getValueAt(index, 0));

		if (Database.removeSetting(value, "Catagory")) {
			catagory.removeItem(value);
			catagoryModel.delete(value);
		} else
			JOptionPane.showMessageDialog(null, "Removeing Failed");
		// */
	}

	private void stateRemoveOp() {
		int index = stateEditor.getSelectedIndex();
		if (index < 0) {
			JOptionPane.showMessageDialog(null,
					"Please Choose an Item to Remove");
			return;
		}
		String value = (String) (stateEditor.getSelectedValue());

		if (Database.removeSetting(value, "State")) {
			stateModel.removeElement(value);
			state.removeItem(value);
		} else
			JOptionPane.showMessageDialog(null, "Removeing Failed");
		stateField.requestFocus();

	}

	private void searchOnOffOp() {
		if (searchOnOff.isSelected())
			searchOnOff.setText("ALL");
		else
			searchOnOff.setText("Active Only");
		if (searchOnOff.isSelected()) {
			String[] columnNames = new String[] { "Account#", "First Name",
					"Last Name", "Phone", "Create Date", "Status" };
			customerModel.setNames(columnNames);
			customerModel.setData(Database.getCustomers("ALL"));
		} else {
			String[] columnNames = new String[] { "Account#", "First Name",
					"Last Name", "Phone", "Create Date" };
			customerModel.setNames(columnNames);
			customerModel.setData(Database.getCustomers("Active"));
		}
	}

	private void deleteOp() {
		if ((delete.getText()).equalsIgnoreCase("Delete")) {
			int index = borrowingRecordTable.getSelectedRow();
			if (index < 0) {
				JOptionPane.showMessageDialog(null,
						"Please Select an Item to Delete");
				return;
			}
			if (borrowingRecordModel.isUnConfirmed(index))
				borrowingRecordModel.delete(index);
			String[] tem = borrowingRecordModel.getUnConfirmedIDs();
			if (tem == null || tem.length == 0) {
				disableIt(delete);
				disableIt(itemConfirm);
				able(enterfield);
				able(onOff);
				able(search);
			}
		} else if ((delete.getText()).equalsIgnoreCase("Cancel")) {
			int index = borrowingRecordTable.getSelectedRow();
			String id = "";
			if (index < 0
					|| (id = borrowingRecordModel.cancelReturnMark(index))
							.equalsIgnoreCase("NA"))
				JOptionPane.showMessageDialog(null, "Please select proper row");
			else if (!removeReturn(id))
				JOptionPane.showMessageDialog(null,
						"Return Cancellation Marking failed");

			if (returns.size() == 0) {
				disableIt(delete);
				disableIt(itemConfirm);
				// able(search);
			}
		}
	}

	private boolean removeReturn(String id) {
		String[] ss = null;
		for (int i = 0; i < returns.size(); i++) {
			ss = (String[]) (returns.get(i));
			if (ss[0].equalsIgnoreCase(id)) {
				returns.removeElementAt(i);
				return true;
			}
		}
		return false;
	}

	private void detail_summaryOp() {
		if (detail_summary.isSelected())
			detail_summary.setText("Summary");
		else
			detail_summary.setText("Detail");
		if (detail_summary.isSelected()) {
			String[] columnNames = new String[] { "Item ID", "Title",
					"Version", "Location", "Catagory", "Status", "Account#",
					"DateOut", "DateDue", "DateReturned", "InOperator",
					"OutOperator", "Create Date", "Point" };
			itemModel.setNames(columnNames);
			itemModel.setData(Database.getItems("Detail"));
			disableIt(itemAdd);
		} else {
			String[] columnNames = new String[] { "Item ID", "Title",
					"Version", "Location", "Catagory", "Status", "Account#" };
			itemModel.setNames(columnNames);
			itemModel.setData(Database.getItems("Summary"));
			able(itemAdd);
		}
	}

	private void itemDeleteOp() {

		int[] index = itemTable.getSelectedRows();
		if (index.length <= 0) {
			JOptionPane.showMessageDialog(null,
					"Please Select An Account in the Table");
			return;
		}
		String[] itemids = new String[index.length];
		for (int i = 0; i < index.length; i++) {
			itemids[i] = (String) (itemTable.getValueAt(index[i], 0));
		}

		if (JOptionPane.showConfirmDialog(null,
				"Delete " + this.getStatement(itemids) + " ?", "Confirm",
				JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
			return;
		String result = null;
		if ((result = Database.deleteItem(itemids)) != null) {
			JOptionPane.showMessageDialog(null, "Delete Item " + result
					+ " failed");
			return;
		}
		itemModel.delete(itemids);

	}

	private void deleteAccountOp() {
		int[] index = customerTable.getSelectedRows();
		if (index.length <= 0) {
			JOptionPane.showMessageDialog(null,
					"Please Select An Account in the Table");
			return;
		}
		String[] accountnos = new String[index.length];
		for (int i = 0; i < index.length; i++) {
			accountnos[i] = (String) (customerTable.getValueAt(index[i], 0));
		}
		// System.out.println(customerTable.getValueAt(index,0));
		if (JOptionPane.showConfirmDialog(null, "Cancel "
				+ getStatement(accountnos) + " ?", "Confirm",
				JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
			return;
		String result = null;
		if ((result = Database.setInActive(accountnos)) != null) {
			JOptionPane.showMessageDialog(null, "Cancel " + result + " failed");
			return;
		}
		customerModel.delete(accountnos);
	}

	private String getStatement(String[] ss) {
		String s = "";
		for (int i = 0; i < ss.length; i++) {
			if (i == 0)
				s = s + ss[i];
			else
				s = s + "\n" + ss[i];
		}
		return s;
	}

	private void onOffOp() {
		if (onOff.isSelected()) {
			onOff.setText("Return ON");
			clear();
			enterfield.setText("");
			enterfield.setEnabled(false);
			setTitle("Return Operation");
			able(itemConfirm);
			able(delete);
			delete.setText("Cancel");
			disableIt(search);
		} else {
			onOff.setText("Return Off");
			clear();
			enterfield.setText("");
			itemField.setText("");
			enterfield.setEnabled(true);
			setTitle("Check_Out Operation");
			disableIt(itemConfirm);
			delete.setText("Delete");
			disableIt(delete);
			returns.removeAllElements();
			able(search);
		}
	}

	private void itemAddOp() {
		if (!itemID.isEnabled()) {
			itemID.setEnabled(true);
			itemID.setText("");
			return;
		}
		String[] ss = new String[7];
		// 0-id,1=title,2=version,3=location,4=catagory,5-publisher,6=summary,7=createDay
		ss[0] = itemID.getText().trim();
		ss[1] = title.getText().trim();
		ss[2] = version.getText().trim();
		ss[3] = location.getText().trim();
		ss[4] = catagory.getSelectedItem().toString().trim();
		ss[5] = publisher.getText().trim();
		ss[6] = summary.getText().trim();
		if (Database.checkForValid(ss[0], "ItemID")) {
			JOptionPane.showMessageDialog(null, ss[0]
					+ " already exists in database.Please choose another one");
			return;
		}
		if (!Database.addItem(ss))
			JOptionPane.showMessageDialog(null, "Item Addition failed");
		String[] ssTable;
		if (detail_summary.isSelected())
			ssTable = new String[13];
		else
			ssTable = new String[7];
		ssTable[0] = ss[0];
		ssTable[1] = ss[1];
		ssTable[2] = ss[2];
		ssTable[3] = ss[3];
		ssTable[4] = ss[4];
		ssTable[5] = "In";
		ssTable[6] = "NA";
		if (detail_summary.isSelected()) {
			ssTable[7] = "";
			ssTable[8] = "";
			ssTable[9] = "";
			ssTable[10] = "NA";
			ssTable[11] = "NA";
			ssTable[12] = (new java.sql.Date(System.currentTimeMillis()))
					.toString();
		}

		itemModel.addData(ssTable);
		itemID.setText("");

	}

	private void newAccountOp() {
		if (JOptionPane.showConfirmDialog(null, "New Account?", "Confirm",
				JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
			return;
		// 0-first,1=last,2=street,3=apt,4=city,5=state,6=zip,7=phone
		String[] ss = new String[9];
		long tem = Long.parseLong(Database.getNextValue("Account"));
		while (true) {
			ss[0] = prefix.getText().trim().toUpperCase() + tem;
			if (Database.checkForValid(ss[0], "AccountNo"))
				tem++;
			else
				break;
		}
		ss[1] = firstName0.getText().trim();
		ss[2] = lastName0.getText().trim();
		ss[3] = street.getText().trim();
		ss[4] = apt0.getText().trim();
		ss[5] = city.getText().trim();
		ss[6] = state.getSelectedItem().toString().trim();
		ss[7] = zip.getText().trim();
		ss[8] = phone0.getText().trim();
		if (!Database.addAccount(ss, tem))
			JOptionPane.showMessageDialog(null, "Account Creation failed");
		String[] ssTable = new String[5];
		if (searchOnOff.isSelected())
			ssTable = new String[6];
		ssTable[0] = ss[0];
		ssTable[1] = ss[1];
		ssTable[2] = ss[2];
		ssTable[3] = ss[8];
		ssTable[4] = (new java.sql.Date(System.currentTimeMillis())).toString();
		if (searchOnOff.isSelected())
			ssTable[5] = "Active";

		customerModel.addData(ssTable);
	}

	private void itemUpdateOp() {
		if (JOptionPane.showConfirmDialog(null, "Update?", "Confirm",
				JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
			return;

		int index = itemTable.getSelectedRow();
		if (index < 0) {
			JOptionPane.showMessageDialog(null,
					"Please Select An Account in the Table");
			return;
		}
		String[] ss = new String[7];
		ss[0] = itemID.getText().trim();
		ss[1] = title.getText().trim();
		ss[2] = version.getText().trim();
		ss[3] = location.getText().trim();
		ss[4] = catagory.getSelectedItem().toString().trim();
		ss[5] = publisher.getText().trim();
		ss[6] = summary.getText().trim();
		if (!Database.updateItem(ss))
			JOptionPane.showMessageDialog(null, "Item update failed");
		String[] ssTable = new String[6];
		ssTable[0] = ss[0];
		ssTable[1] = ss[1];
		ssTable[2] = ss[2];
		ssTable[3] = ss[3];
		ssTable[4] = ss[4];
		// ssTable[5]="In";
		// ssTable[6]="NA";
		ssTable[5] = ss[5];

		itemModel.update(ssTable);
	}

	private void updateOp() {
		if (JOptionPane.showConfirmDialog(null, "Update?", "Confirm",
				JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
			return;
		// 0=accountNO,0-first,1=last,2=street,3=apt,4=city,5=state,6=zip,7=phone
		int index = customerTable.getSelectedRow();
		if (index < 0) {
			JOptionPane.showMessageDialog(null,
					"Please Select An Account in the Table");
			return;
		}
		String[] ss = new String[9];
		ss[0] = (customerModel.getValueAt(index, 0)).toString();
		ss[1] = firstName0.getText().trim();
		ss[2] = lastName0.getText().trim();
		ss[3] = street.getText().trim();
		ss[4] = apt0.getText().trim();
		ss[5] = city.getText().trim();
		ss[6] = state.getSelectedItem().toString().trim();
		ss[7] = zip.getText().trim();
		ss[8] = phone0.getText().trim();
		if (!Database.updateAccount(ss))
			JOptionPane.showMessageDialog(null, "Account Update failed");

		String[] ssTable = new String[4];
		ssTable[0] = ss[0];
		ssTable[1] = ss[1];
		ssTable[2] = ss[2];
		ssTable[3] = ss[8];
		
		customerModel.update(ssTable);
	}

	private void clear() {
		firstName.setText("");
		lastName.setText("");
		address.setText("");
		apt.setText("");
		phone.setText("");
		borrowingRecordModel.setData(new Vector());
	}

	public void open() {
	}

	private void returnOperation(String value, String[] customers) {
		if (returns.size() > 0 && !ACCOUNT.equalsIgnoreCase(customers[0])) {
			JOptionPane.showMessageDialog(null,
					"This item belongs to another account.Operation Cancelled");
			return;
		}
		String[] tem = new String[8];
		tem[0] = value;// itemID
		tem[1] = Database.getItemInfo(value, "Title");// title
		tem[2] = customers[0];// account#
		tem[3] = customers[1];// first name
		tem[4] = customers[2];// lastname
		tem[5] = Database.getItemInfo(value, "DateOut");
		tem[6] = Database.getItemInfo(value, "OutOperator");
		tem[7] = operator;// inoperator

		if (returns.size() == 0) {
			Vector br = Database.getBorrowingRecord(customers[0]);
			borrowingRecordModel.setData(br);
			ACCOUNT = customers[0];
			setTitle(customers[2] + "," + customers[1]
					+ "......Return Operation");
		}
		returns.add(tem);
		if (returns.size() == 1) {
			able(delete);
			able(itemConfirm);
			disableIt(search);
		}
		borrowingRecordModel.markReturn(value);
	

		enterfield.setText(customers[0]);
		firstName.setText(customers[1]);
		lastName.setText(customers[2]);
		apt.setText(customers[4]);
		address.setText(customers[3] + "," + customers[5] + "," + customers[6]
				+ customers[7]);
		phone.setText(customers[8]);

	}

	private void returnCommitted(Vector sss, String operator, String value) {
		String message = Database.getNextValue("Greeting");
		message = message
				+ "\n The following items have been returned.\nkeep this receipt for your record";
		String[] ids = new String[sss.size()];
		String[] tem = null;
		for (int i = 0; i < sss.size(); i++) {
			tem = (String[]) (sss.get(i));
			ids[i] = tem[0];
			message = message + "\n No." + (i + 1) + ",  " + tem[1] + ",  "
					+ tem[2] + ",  " + tem[3] + ",  " + tem[4];
		}
		message = message + "\nOperator is " + operator + ". Thank you" + "\n"
				+ (new java.util.Date(System.currentTimeMillis())).toString();
		boolean[] result = Database.itemReturned(ids, operator, this);

		String[] customers = Database.getCustomerInfoByItemID(value);
		Vector br = Database.getBorrowingRecord(customers[0]);
		borrowingRecordModel.setData(br);
		enterfield.setText(customers[0]);
		ACCOUNT = customers[0];
		firstName.setText(customers[1]);
		lastName.setText(customers[2]);
		apt.setText(customers[4]);
		address.setText(customers[3] + "," + customers[5] + "," + customers[6]
				+ customers[7]);
		phone.setText(customers[8]);
		System.out.println(message);
	}

	private void itemFieldOp() {
		String value = (itemField.getText().trim()).toUpperCase();

		if (value == null || value.length() == 0) {
			JOptionPane.showMessageDialog(null, "Please Enter Item ID");
			return;
		}

		if (!Database.checkForValid(value, "ItemID")) {
			JOptionPane.showMessageDialog(null,
					"This item is not in database.Please Check it again");
			itemField.setText("");
			return;
		}

		if (search.isSelected()) {
			String[] names = new String[] { "Account", "Firs Name",
					"Last Name", "Date Out", "Date In" };
			borrowingRecordModel.setNames(names);
			borrowingRecordModel.setData(Database.search(value, "ItemID"));
			return;
		}

		if (onOff.isSelected()) {
			if ((Database.getItemStatus(value)).equalsIgnoreCase("IN")) {
				JOptionPane.showMessageDialog(null,
						"This item has already been  returned");
				itemField.setText("");
				return;
			}
			String[] customers = Database.getCustomerInfoByItemID(value);
			// 0-first,1=last,2=street,3=apt,4=city,5=state,6=zip,7=phone
			if (customers == null || customers[0] == null
					|| customers[0].length() == 0) {
				JOptionPane
						.showMessageDialog(null,
								"No Account#. Found. This Item May have already been Returned");
				String message = "Item ID " + value
						+ " can't find corrensponding account No..";
				message = message
						+ (new java.util.Date(System.currentTimeMillis()))
								.toString();
				Database.addErrorMessage(message);
				itemField.setText("");
				return;
			}
			returnOperation(value, customers);

		} else {

			if ((Database.getItemStatus(value)).equalsIgnoreCase("Out")) {
				if ((JOptionPane.showConfirmDialog(null,
						"Do you want to return item?", "Confirm",
						JOptionPane.YES_NO_OPTION)) == JOptionPane.NO_OPTION)
					return;
				onOff.setSelected(true);
				onOffOp();
				String[] customers = Database.getCustomerInfoByItemID(value);
				// 0-first,1=last,2=street,3=apt,4=city,5=state,6=zip,7=phone
				if (customers == null || customers[0] == null
						|| customers[0].length() == 0) {
					JOptionPane
							.showMessageDialog(null,
									"No Account#. Found. This Item May have already been Returned");
					String message = "Item ID " + value
							+ " can't find corrensponding account No..";
					message = message
							+ (new java.util.Date(System.currentTimeMillis()))
									.toString();
					Database.addErrorMessage(message);
					itemField.setText("");
					return;
				}
				returnOperation(value, customers);
				itemField.setText("");
				
				return;
			}

			String valueAcc = enterfield.getText().trim();
			if (valueAcc == null || valueAcc.length() == 0) {
				JOptionPane.showMessageDialog(null,
						"Please Enter Account Number First");
				itemField.setText("");
				return;
			}
			if (borrowingRecordModel.check(value, 0)) {
				JOptionPane.showMessageDialog(null,
						"This Item has already been selected or borrowed");
				itemField.setText("");
				return;
			}

			String[] ss = new String[5];
			ss[0] = value;
			ss[1] = Database.getItemInfo(value, "Title");
			ss[2] = "Ready to go";
			ss[3] = (getDueDate(value)).toString();
			ss[4] = "UC";
			borrowingRecordModel.addData(ss);
			borrowingRecordTable.setSelectionForeground(Color.BLUE);
			enterfield.setEnabled(false);
			onOff.setEnabled(false);
			itemConfirm.setEnabled(true);
			able(delete);
			disableIt(search);
			setTitle("Check_Out Operation");

		}
		itemField.setText("");

	}

	private void disableIt(Component c) {
		c.setEnabled(false);
	}

	private void able(Component c) {
		c.setEnabled(true);
	}

	public java.sql.Date getDueDate(String itemId) {

		return Database.getDueDate(itemId);
	}

	private void enterfieldOp() {
		String value = (enterfield.getText().trim()).toUpperCase();
		if (value == null || value.length() == 0) {
			JOptionPane.showMessageDialog(null, "Please Enter Account Number");
			return;
		}
		boolean valid = false;

		if (!Database.checkForValid(value, "AccountNO")) {
			JOptionPane.showMessageDialog(null, value
					+ " is not a valid Account Number");
			enterfield.setText("");
			clear();
			return;
		}
		if (search.isSelected()) {
			String[] names = new String[] { "ItemID", "Title", "Date Out",
					"Date In" };
			borrowingRecordModel.setNames(names);
			borrowingRecordModel.setData(Database.search(value, "Account"));
		} else
			account(value);

	}

	private void account(String value) {
		// 0-first,1=last,2=street,3=apt,4=city,5=state,6=zip,7=phone
		String[] customers = Database.getCustomerInfo(value);
		Vector br = Database.getBorrowingRecord(value);
		firstName.setText(customers[1]);
		lastName.setText(customers[2]);
		apt.setText(customers[4]);
		address.setText(customers[3] + "," + customers[5] + "," + customers[6]
				+ customers[7]);
		phone.setText(customers[8]);

		borrowingRecordModel.setData(br);
		ACCOUNT = value;
		setTitle(customers[2] + "," + customers[1]
				+ "......Check_Out Operation");
	}

}