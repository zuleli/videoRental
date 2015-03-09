/* Author: Zule Li
 * Email:zule.li@hotmail.com
 * Last Modified Date:Mar.7,2015
 * */
package videoRental;

//for videoRental project
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Database extends Object {

	private static String source = "jdbc:odbc:VideoRental";
	private static Connection con;

	/**
	 * Constructor
	 */
	public Database() {

		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static boolean addItem(String[] basic) {
		// 0-id,1=title,2=version,3=location,4=catagory,5-publisher,6=summary,7=createDay,8=status,9=account
		String str = "insert into Items  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		boolean ok = false;
		try {
			con = DriverManager.getConnection(source);
			PreparedStatement pst = con.prepareStatement(str);
			pst.setString(1, basic[0]);
			pst.setString(2, basic[1]);
			pst.setString(3, basic[2]);
			pst.setString(4, basic[3]);
			pst.setString(5, basic[4]);
			pst.setString(6, basic[5]);
			pst.setString(7, basic[6]);
			pst.setDate(8, new java.sql.Date(System.currentTimeMillis()));
			pst.setString(9, "In");
			pst.setString(10, "NA");
			pst.setDate(11, null);
			pst.setDate(12, null);
			pst.setDate(13, null);
			pst.setString(14, "NA");
			pst.setString(15, "NA");
			pst.setInt(16, 0);

			int index = pst.executeUpdate();
			if (index > 0)
				ok = true;

			pst.close();
			con.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return ok;
	}

	public static boolean addSetting(String[] values, String type)//
	{

		String str = "";
		if (type.equalsIgnoreCase("Catagory"))
			str = "insert into Catagory values (?,?)";
		else if (type.equalsIgnoreCase("State"))
			str = "insert into State values (?)";
		boolean ok = false;
		try {
			con = DriverManager.getConnection(source);
			PreparedStatement pst = con.prepareStatement(str);
			pst.setString(1, values[0]);
			if (type.equalsIgnoreCase("Catagory"))
				pst.setInt(2, Integer.parseInt(values[1]));
			int index = pst.executeUpdate();
			if (index > 0)
				ok = true;

			pst.close();
			con.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return ok;
	}

	public static boolean updateSetting(String[] values, String oldValue)//
	{

		String str = "update Catagory set catagory=?,daysAllowed=? where catagory=?";
		
		boolean ok = false;
		try {
			con = DriverManager.getConnection(source);
			PreparedStatement pst = con.prepareStatement(str);
			pst.setString(1, values[0]);
			pst.setInt(2, Integer.parseInt(values[1]));
			pst.setString(3, oldValue);
			int index = pst.executeUpdate();
			if (index > 0)
				ok = true;

			pst.close();
			con.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return ok;
	}

	public static boolean updateDigit(String s)//
	{

		String str = "update NextValue set account=?";
		int no = Integer.parseInt(s);
		boolean ok = false;
		try {
			con = DriverManager.getConnection(source);
			PreparedStatement pst = con.prepareStatement(str);
			pst.setDouble(1, Math.pow(10, no));
			System.out.println("" + Math.pow(10, no));
			int index = pst.executeUpdate();
			if (index > 0)
				ok = true;

			pst.close();
			con.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return ok;
	}

	public static java.sql.Date getDueDate(String id) {
		long days = 0;
		try {
			con = DriverManager.getConnection(source);
			Statement ste = con.createStatement();
			ResultSet rs = null;
			String str = "select * from Catagory where catagory='"
					+ getItemInfo(id, "Catagory") + "'";

			rs = ste.executeQuery(str);
			// 0-first,1=last,2=street,3=apt,4=city,5=state,6=zip,7=phone
			if (rs.next()) {
				days = rs.getInt("daysAllowed");

				rs.close();
				ste.close();
				con.close();
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return (new java.sql.Date(System.currentTimeMillis() + 1000 * 60 * 60
				* 24 * days));
	}

	public static Vector getSetting(String type) {

		String str = "";
		if (type.equalsIgnoreCase("Catagory"))
			str = "select * from Catagory";
		else if (type.equalsIgnoreCase("State"))
			str = "select * from State";
		Vector data = new Vector();
		String[] tem = new String[2];
		try {
			con = DriverManager.getConnection(source);
			Statement ste = con.createStatement();
			ResultSet rs = ste.executeQuery(str);
			while (rs.next()) {
				if (type.equalsIgnoreCase("State"))
					data.add(rs.getString(1));
				else if (type.equalsIgnoreCase("Catagory")) {
					tem = new String[2];
					tem[0] = rs.getString("Catagory");
					tem[1] = "" + rs.getInt("DaysAllowed");
					data.add(tem);
				}
			}
			rs.close();
			ste.close();
			con.close();
			return data;
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return data;
	}

	public static boolean removeSetting(String value, String type) {

		String str = "";
		if (type.equalsIgnoreCase("Catagory"))
			str = "delete from Catagory where catagory=?";
		else if (type.equalsIgnoreCase("State"))
			str = "delete from state where state=?";
		boolean ok = false;
		try {
			con = DriverManager.getConnection(source);
			PreparedStatement pst = con.prepareStatement(str);
			pst.setString(1, value);
			int index = pst.executeUpdate();
			if (index > 0)
				ok = true;

			pst.close();
			con.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return ok;
	}

	public static boolean addAccount(String[] basic, long acct) {

		// 0=accountNO,1-first,2=last,3=street,4=apt,5=city,6=state,7=zip,8=phone,9=createDate,10=EndDate,11=status
		String str = "insert into customers  values (?,?,?,?,?,?,?,?,?,?,?,?)";
		boolean ok = false;
		try {
			con = DriverManager.getConnection(source);
			con.setAutoCommit(false);
			PreparedStatement pst = con.prepareStatement(str);
			pst.setString(1, basic[0]);
			pst.setString(2, basic[1]);
			pst.setString(3, basic[2]);
			pst.setString(4, basic[3]);
			pst.setString(5, basic[4]);
			pst.setString(6, basic[5]);
			pst.setString(7, basic[6]);
			pst.setString(8, basic[7]);
			pst.setString(9, basic[8]);
			pst.setDate(10, new java.sql.Date(System.currentTimeMillis()));
			pst.setDate(11, null);
			pst.setString(12, "Active");

			int index = pst.executeUpdate();
			if (index < 0) {
				pst.close();
				con.close();
				return ok;
			}

			str = "update NextValue set Account=?";
			pst.close();
			pst = con.prepareStatement(str);
			pst.setDouble(1, (acct + 1));
			index = pst.executeUpdate();
			if (index < 0) {
				pst.close();
				con.close();
				return ok;
			}
			con.commit();
			con.setAutoCommit(true);
			pst.close();
			con.close();
			return true;

			// if(!Database.updateNextValue("Account",(acct+1)))
			// JOptionPane.showMessageDialog(null,"NextAccountNo update failed");
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return ok;
	}

	public static Vector getBorrowingRecord(String accountNo)// 1=working,0=close,2=startup,3=open,4=all
	{
		Vector data = new Vector();
		String[] tem = null;
		String str = "select * from Items where AccountNo=?";
		try {
			con = DriverManager.getConnection(source);
			PreparedStatement ste = con.prepareStatement(str);
			ste.setString(1, accountNo);
			ResultSet rs = ste.executeQuery();
			while (rs.next()) {
				tem = new String[4];
				// 0==itemID,1=title,2=dateout,3=datedue
				tem[0] = rs.getString("ItemID");
				tem[1] = rs.getString("Title");
				tem[2] = rs.getDate("DateOut").toString();
				tem[3] = rs.getDate("DateDue").toString();
				data.add(tem);
			}
			rs.close();
			ste.close();
			con.close();
			return data;
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return null;
	}

	public static boolean orderStatusChange(String orderno) {
		boolean status = false;
		String str = "UPDATE planning SET status=1 WHERE (order='" + orderno
				+ "' AND status=2)";
		try {
			con = DriverManager.getConnection(source);
			Statement ste = con.createStatement();
			int in = ste.executeUpdate(str);
			ste.close();
			con.close();
			if (in >= 1)
				status = true;
			return status;
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return false;
	}

	public static Vector getMeasureData() {
		String str = "select * from measurement where status=1";
		String[] ss = null;
		Vector v = new Vector();

		try {
			con = DriverManager.getConnection(source);
			Statement ste = con.createStatement();
			str = "select mc,position,stock,end,beamsize,measurement,"
					+ "((select capacity/(RO*RO-RI*RI)*((RO-measurement)*(RO-measurement)-RI*RI) from emptybeam where size=beamsize)/end*453.6/"
					+ "(select Rawm.size*Rawm.modify from Rawm where Rawm.stock=Measurement.stock)*9000/"
					+ "((select (speed*efficiency/100) from MCS where MCS.MC=measurement.mc)/"
					+ "(SELECT pick from styles where styles.styleno=(select styleno from mcs where MCS.MC=measurement.mc))/36*0.9199*60)),"
					+

					"(select sum([status])from beams where(status=1 and beams.stock=measurement.stock and beams.end=measurement.end)),"
					+

					"(select capacity/(RO*RO-RI*RI)*((RO-measurement)*(RO-measurement)-RI*RI) from emptybeam where size=beamsize)/end*453.6/"
					+ "(select Rawm.size*Rawm.modify from Rawm where Rawm.stock=Measurement.stock)*9000*0.9199*"
					+ "(SELECT (pick/Fpick) from styles where styles.styleno=(select styleno from mcs where MCS.MC=measurement.mc)),"
					+

					"hot,orderno from measurement where status=1";
			ResultSet rs = ste.executeQuery(str);
			long lon = 0;
			while (rs.next()) {
				ss = new String[12];
				// System.out.println("try0="+rs.getDouble(1));
				ss[0] = rs.getString("MC");
				ss[1] = "" + rs.getByte("Position");
				ss[2] = rs.getString("Stock");
				ss[3] = "" + rs.getInt("End");
				ss[4] = "" + rs.getByte("BeamSize");
				ss[5] = "" + rs.getDouble("Measurement");
				lon = (long) (rs.getDouble(7));
				ss[6] = ""
						+ (new java.sql.Date(System.currentTimeMillis() + lon
								* 60 * 60 * 1000)).toString();// dateOUt
				ss[7] = "" + rs.getInt(8);// Invertory
				ss[8] = "" + (int) (rs.getDouble(9));// yard
				// System.out.println(ss[6]);
				ss[10] = rs.getString("hot");
				ss[11] = rs.getString("Orderno");// */
				v.add(ss);
			}
			rs.close();
			ste.close();
			con.close();
			// System.out.println("try2");
			return v;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;

	}

	public static boolean updateItem(String[] ss) {
		boolean ok = false;
		try {
			con = DriverManager.getConnection(source);
			// 0-id,1=title,2=version,3=location,4=catagory,5-publisher,6=summary,7=createDay
			String str = "update items set title=?,version=?,location=?"
					+ ",catagory=?,publisher=?,summary=? where itemid=?";
			;
			System.out.println(str);
			PreparedStatement ste = con.prepareStatement(str);
			ste.setString(1, ss[1]);
			ste.setString(2, ss[2]);
			ste.setString(3, ss[3]);
			ste.setString(4, ss[4]);
			ste.setString(5, ss[5]);
			ste.setString(6, ss[6]);
			ste.setString(7, ss[0]);
			int ind = ste.executeUpdate();
			if (ind > 0) {
				ok = true;
			}
			ste.close();
			con.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return ok;

	}

	public static String deleteItem(String[] ss) {
		String ok = null;
		try {
			con = DriverManager.getConnection(source);
			// 0-id,1=title,2=version,3=location,4=catagory,5-publisher,6=summary,7=createDay
			String str = "delete from items  where itemid=?";
			;
			System.out.println(str);
			PreparedStatement ste = con.prepareStatement(str);
			for (int i = 0; i < ss.length; i++) {
				ste.setString(1, ss[i]);
				int ind = ste.executeUpdate();
				if (ind < 0) {
					if (ok == null)
						ok = "The Following item failed to delete from database:\nItem ID:"
								+ ss[i]
								+ ", Title:"
								+ getItemInfo(ss[i], "Title");
					else
						ok = ok + "\nItem ID:" + ss[i] + ", Title:"
								+ getItemInfo(ss[i], "Title");
				}
			}
			ste.close();
			con.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return ok;
	}

	public static boolean updateAccount(String[] ss) {
		// 0=accountNO,1-first,2=last,3=street,4=apt,5=city,6=state,7=zip,8=phone,9=createDate,10=EndDate,11=status
		boolean ok = false;
		try {
			con = DriverManager.getConnection(source);
			String str = "update customers set firstName=?,lastName=?,Street=?"
					+ ",apartment=?,city=?,State=?,zip=?,phone=? where accountNo=?";
			System.out.println(str);
			PreparedStatement ste = con.prepareStatement(str);
			ste.setString(1, ss[1]);
			ste.setString(2, ss[2]);
			ste.setString(3, ss[3]);
			ste.setString(4, ss[4]);
			ste.setString(5, ss[5]);
			ste.setString(6, ss[6]);
			ste.setString(7, ss[7]);
			ste.setString(8, ss[8]);
			ste.setString(9, ss[0]);
			int ind = ste.executeUpdate();
			if (ind > 0) {
				ok = true;
			}
			ste.close();
			con.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return ok;

	}

	public static String[] getMeasueUpdate(String[] ss) {
		String[] out = new String[3];
		try {
			con = DriverManager.getConnection(source);
			String str = "select((select capacity/(RO*RO-RI*RI)*((RO-measurement)*(RO-measurement)-RI*RI) from emptybeam where size=beamsize)/end*453.6/"
					+ "(select Rawm.size*Rawm.modify from Rawm where Rawm.stock=Measurement.stock)*9000/"
					+ "((select (speed*efficiency/100) from MCS where MCS.MC=measurement.mc)/"
					+ "(SELECT pick from styles where styles.styleno=(select styleno from mcs where MCS.MC=measurement.mc))/36*0.9199*60)),"
					+

					"(select sum([status])from beams where(status=1 and beams.stock=measurement.stock and beams.end=measurement.end)),"
					+

					"(select capacity/(RO*RO-RI*RI)*((RO-measurement)*(RO-measurement)-RI*RI) from emptybeam where size=beamsize)/end*453.6/"
					+ "(select Rawm.size*Rawm.modify from Rawm where Rawm.stock=Measurement.stock)*9000*0.9199*"
					+ "(SELECT (pick/Fpick) from styles where styles.styleno=(select styleno from mcs where MCS.MC=measurement.mc))"
					+

					" from measurement where (mc='"
					+ ss[0]
					+ "' and position="
					+ ss[1] + ")";
			Statement ste = con.createStatement();
			ResultSet rs = ste.executeQuery(str);
			if (rs.next()) {

				long lon = (long) (rs.getDouble(1));
				out[0] = ""
						+ (new java.sql.Date(System.currentTimeMillis() + lon
								* 60 * 60 * 1000)).toString();// dateOUt
				out[1] = "" + rs.getInt(2);
				out[2] = "" + (int) (rs.getDouble(3));
			}
			ste.close();
			con.close();
			return out;
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return null;

	}

	public static String setInActive(String[] ss) {
		String ok = null;
		try {
			con = DriverManager.getConnection(source);
			String str = "update customers set status='InActive' where accountno=?";
			PreparedStatement ste = con.prepareStatement(str);
			for (int i = 0; i < ss.length; i++) {
				ste.setString(1, ss[i]);
				int ind = ste.executeUpdate();
				if (ind < 0) {
					if (ok == null)
						ok = "The Following item failed to set InActive from database:\nAccount#:";
					else
						ok = ok + "\nAccount#:" + ss[i];
				}
			}
			ste.close();
			con.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return ok;
	}

	public static boolean setLocalPort(String s) {
		boolean result = false;
		try {
			con = DriverManager.getConnection(source);

			String str = "update locals set localPort=?";
			PreparedStatement ste = con.prepareStatement(str);
			ste.setString(1, s);
			int ind = ste.executeUpdate();
			if (ind > 0) {
				result = true;
			}
			ste.close();
			con.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return result;
	}

	public static String getLocalPort() {
		try {
			con = DriverManager.getConnection(source);
			Statement ste = con.createStatement();
			ResultSet rs = null;
			String str = "select * from locals";

			rs = ste.executeQuery(str);
			if (rs.next()) {
				str = (rs.getString("LocalPort")).trim();
				rs.close();
				ste.close();
				con.close();
				return str;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return null;
	}

	public static String getNextValue(String field) {

		String s = "";

		try {
			con = DriverManager.getConnection(source);
			Statement ste = con.createStatement();
			ResultSet rs = null;
			String str = "select * from NextValue";

			rs = ste.executeQuery(str);
			if (rs.next()) {
				if (field.equalsIgnoreCase("Greeting"))
					s = rs.getString(field);
				else if (field.equalsIgnoreCase("ItemID"))
					s = "" + rs.getInt(field);
				else if (field.equalsIgnoreCase("Account"))
					s = "" + (int) (rs.getDouble(field));
				rs.close();
				ste.close();
				con.close();
				if (field.equalsIgnoreCase("About"))
					s = "Rental Managament System\n" + "Version 1.3\n\n"
							+ "Technical support:\n"
							+ "Email: support@hotmail.com\n"
							+ "Phone: 864 356 9607\n" + "Lion Service Co.\n"
							+ "110 Bragg  Drive\n" + "Williamston,SC 29697\n";

				return s;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return s;
	}

	public static boolean checkForValid(String id, String type) {
		boolean result = false;
		try {
			con = DriverManager.getConnection(source);

			ResultSet rs = null;
			String str = "";
			if (type.equalsIgnoreCase("ItemID"))
				str = "select * from items where itemid=?";
			if (type.equalsIgnoreCase("AccountNO"))
				str = "select * from customers where accountno=?";
			PreparedStatement ste = con.prepareStatement(str);
			ste.setString(1, id);
			rs = ste.executeQuery();

			if (rs.next()) {
				result = true;

			}
			rs.close();
			ste.close();
			con.close();
			return result;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			JOptionPane.showMessageDialog(null, type
					+ " unknown,database connection problem");

		}
		return true;
	}

	public static String[] getDetailedItem(String id) {
		String[] ss = new String[14];
		try {
			con = DriverManager.getConnection(source);

			ResultSet rs = null;
			String str = "select * from items where itemid=?";
			PreparedStatement ste = con.prepareStatement(str);
			ste.setString(1, id);
			rs = ste.executeQuery();
			// 0-id,1=title,2=version,3=location,4=catagory,5-publisher,6=summary,7=createDay,8=status,9=account
			if (rs.next()) {

				ss[0] = (rs.getString("itemid")).trim();
				ss[1] = (rs.getString("title")).trim();
				ss[2] = (rs.getString("version")).trim();
				ss[3] = (rs.getString("location")).trim();
				ss[4] = rs.getString("catagory").toString();
				ss[5] = (rs.getString("Status")).trim();
				ss[6] = (rs.getString("Accountno")).trim();
				Object o;
				o = rs.getDate("DateOut");
				ss[7] = (o == null) ? " " : o.toString();
				o = rs.getDate("DateDue");
				ss[8] = (o == null) ? " " : o.toString();
				o = rs.getDate("DateReturned");
				ss[9] = (o == null) ? " " : o.toString();

				ss[10] = (rs.getString("InOperator")).trim();
				ss[11] = (rs.getString("OutOperator")).trim();
				o = rs.getDate("CreateDate");
				ss[12] = (o == null) ? " " : o.toString();
				ss[13] = "" + rs.getInt("Points");
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return ss;
	}

	public static String[] getItem(String id) {
		String[] ss = new String[7];
		try {
			con = DriverManager.getConnection(source);

			ResultSet rs = null;
			String str = "select * from items where itemid=?";
			PreparedStatement ste = con.prepareStatement(str);
			ste.setString(1, id);
			rs = ste.executeQuery();
			// 0-id,1=title,2=version,3=location,4=catagory,5-publisher,6=summary,7=createDay,8=status,9=account
			if (rs.next()) {
				ss[0] = id;
				ss[1] = (rs.getString("title")).trim();
				ss[2] = (rs.getString("version")).trim();
				ss[3] = (rs.getString("location")).trim();
				ss[4] = (rs.getString("Catagory")).trim();
				ss[5] = (rs.getString("Publisher")).trim();
				ss[6] = (rs.getString("Summary")).trim();
				rs.close();
				ste.close();
				con.close();
				return ss;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return ss;

	}

	public static void addErrorMessage(String s) {

	}

	public static void toHistory(Vector returns) {
		String[] basic = new String[8];
		String str = "insert into History values(?,?,?,?,?,?)";

		try {
			con = DriverManager.getConnection(source);
			PreparedStatement pst = con.prepareStatement(str);
			for (int i = 0; i < returns.size(); i++) {
				basic = (String[]) (returns.get(i));
				pst.setString(1, basic[0]);
				pst.setString(2, basic[5]);
				pst.setDate(3, new java.sql.Date(System.currentTimeMillis()));
				pst.setString(4, basic[2]);
				pst.setString(5, basic[7]);
				pst.setString(6, basic[6]);

				int index = pst.executeUpdate();
				System.out.println("tohistory= " + basic[5] + "  index="
						+ index);

			}
			pst.close();
			con.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
	}

	public static String getItemInfo(String id, String type) {
		try {
			con = DriverManager.getConnection(source);

			ResultSet rs = null;
			String str = "select * from Items where itemID=?";
			PreparedStatement ste = con.prepareStatement(str);
			ste.setString(1, id);
			rs = ste.executeQuery();
			if (rs.next()) {
				if (type.equalsIgnoreCase("DateOut"))
					str = (rs.getDate(type)).toString();
				else if (type.equalsIgnoreCase("Points"))
					str = "" + rs.getInt(type);
				else
					str = (rs.getString(type)).trim();
				rs.close();
				ste.close();
				con.close();
				return str;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return null;
	}

	public static boolean[] itemsOut(String[] idInfo, String operator,
			String acc, Main main) {
		boolean[] ok = new boolean[idInfo.length];
		try {
			con = DriverManager.getConnection(source);
			String str = "update Items set accountno='" + acc
					+ "',status='Out',DateOut=?,DateDue=?,OutOperator='"
					+ operator + "' where itemID=?";
			;

			PreparedStatement pst = con.prepareStatement(str);
			for (int i = 0; i < idInfo.length; i++) {
				pst.setDate(1, new java.sql.Date(System.currentTimeMillis()));
				pst.setDate(2, main.getDueDate(idInfo[i]));
				pst.setString(3, idInfo[i]);
				int ind = pst.executeUpdate();
				if (ind > 0) {
					ok[i] = true;
					main.itemModel.updateItem(idInfo[i], operator, "OUT", acc);
				}
			}
			pst.close();
			con.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return ok;

	}

	public static boolean setGreetingMessage(String message) {
		boolean ok = false;
		try {
			con = DriverManager.getConnection(source);
			String str = "update NextValue set greeting=?";
			PreparedStatement ste = con.prepareStatement(str);
			ste.setString(1, message);
			int ind = ste.executeUpdate();
			if (ind > 0) {
				ok = true;
			}
			ste.close();
			con.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return ok;
	}

	public static boolean[] itemReturned(String[] itemIDs, String operator,
			Main mi) {
		int[] points = new int[itemIDs.length];
		for (int i = 0; i < points.length; i++) {
			points[i] = Integer.parseInt(getItemInfo(itemIDs[i], "Points"));
		}
		boolean[] ok = new boolean[itemIDs.length];
		try {
			con = DriverManager.getConnection(source);
			String str = "update Items set accountno='NA'"
					+ ",status='In',DateReturned=?,InOperator=?,points=? where itemID=?";
			;

			PreparedStatement pst = con.prepareStatement(str);
			for (int i = 0; i < itemIDs.length; i++) {
				pst.setDate(1, new java.sql.Date(System.currentTimeMillis()));
				pst.setString(2, operator);
				pst.setInt(3, (points[i] + 1));
				pst.setString(4, itemIDs[i]);
				int ind = pst.executeUpdate();
				if (ind > 0) {
					ok[i] = true;
					mi.itemModel.updateItem(itemIDs[i], operator, "IN", null);
				}

			}

			pst.close();
			con.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return ok;

	}

	public static String[] getCustomerInfoByItemID(String id) {
		String accountno = getItemInfo(id, "Accountno");
		;

		return getCustomerInfo(accountno);
	}

	public static Vector getUsers() {

		return null;
	}

	public static int getStep(String user, char[] psw) {

		return 0;
	}

	public static String[] getCustomerInfo(String id) {
		String[] ss = new String[9];
		try {
			con = DriverManager.getConnection(source);
			String str = "select * from customers where Accountno=?";
			ResultSet rs = null;
			PreparedStatement ste = con.prepareStatement(str);
			ste.setString(1, id);

			rs = ste.executeQuery();
			// 0-first,1=last,2=street,3=apt,4=city,5=state,6=zip,7=phone
			if (rs.next()) {
				ss[0] = id;
				ss[1] = (rs.getString("FirstName")).trim();
				ss[2] = (rs.getString("LastName")).trim();
				ss[3] = (rs.getString("Street")).trim();
				ss[4] = (rs.getString("Apartment")).trim();
				ss[5] = (rs.getString("City")).trim();
				ss[6] = (rs.getString("State")).trim();
				ss[7] = (rs.getString("Zip")).trim();
				ss[8] = (rs.getString("Phone")).trim();
				rs.close();
				ste.close();
				con.close();
				return ss;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return null;
	}

	public static String getItemStatus(String id) {
		String s = "";
		try {
			con = DriverManager.getConnection(source);

			ResultSet rs = null;
			String str = "select * from Items where itemid=?";
			PreparedStatement ste = con.prepareStatement(str);
			ste.setString(1, id);
			rs = ste.executeQuery();

			rs = ste.executeQuery(str);
			// 0-first,1=last,2=street,3=apt,4=city,5=state,6=zip,7=phone
			if (rs.next()) {

				s = (rs.getString("Status")).trim();
				rs.close();
				ste.close();
				con.close();
				return s;
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return s;
	}

	public static Vector getItems(String status) {
		Vector data = new Vector();
		String[] ss = null;
		try {
			con = DriverManager.getConnection(source);
			Statement ste = con.createStatement();
			ResultSet rs = null;
			String str = null;

			str = "select * from items";
			Object o = null;

			rs = ste.executeQuery(str);
			// 0-id,1=title,2=version,3=location,4=catagory,5-publisher,6=summary,7=createDay,8=status,9=account
			while (rs.next()) {
				if (status.equalsIgnoreCase("Detail"))
					ss = new String[14];
				else
					ss = new String[7];
				ss[0] = (rs.getString("itemid")).trim();
				ss[1] = (rs.getString("title")).trim();
				ss[2] = (rs.getString("version")).trim();
				ss[3] = (rs.getString("location")).trim();
				ss[4] = rs.getString("catagory").toString();
				ss[5] = (rs.getString("Status")).trim();
				ss[6] = (rs.getString("Accountno")).trim();
				if (status.equalsIgnoreCase("Detail")) {
					o = rs.getDate("DateOut");
					ss[7] = (o == null) ? " " : o.toString();
					o = rs.getDate("DateDue");
					ss[8] = (o == null) ? " " : o.toString();
					o = rs.getDate("DateReturned");
					ss[9] = (o == null) ? " " : o.toString();

					ss[10] = (rs.getString("InOperator")).trim();
					ss[11] = (rs.getString("OutOperator")).trim();
					o = rs.getDate("CreateDate");
					ss[12] = (o == null) ? " " : o.toString();
					ss[13] = "" + rs.getInt("Points");

				}
				data.add(ss);

			}

			rs.close();
			ste.close();
			con.close();
			return data;

		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return data;
	}

	public static Vector getCustomers(String status) {
		Vector data = new Vector();
		String[] ss = null;
		try {
			con = DriverManager.getConnection(source);
			Statement ste = con.createStatement();
			ResultSet rs = null;
			String str = null;
			if (status.equalsIgnoreCase("Active"))
				str = "select * from customers where status='Active'";
			else
				str = "select * from customers";

			rs = ste.executeQuery(str);
			// 0-first,1=last,2=street,3=apt,4=city,5=state,6=zip,7=phone
			while (rs.next()) {
				if (status.equalsIgnoreCase("ALL"))
					ss = new String[6];
				else
					ss = new String[5];
				ss[0] = (rs.getString("AccountNo")).trim();
				ss[1] = (rs.getString("FirstName")).trim();
				ss[2] = (rs.getString("LastName")).trim();
				ss[3] = (rs.getString("Phone")).trim();
				ss[4] = rs.getDate("CreateDate").toString();
				if (status.equalsIgnoreCase("ALL"))
					ss[5] = (rs.getString("Status")).trim();
				data.add(ss);

			}

			rs.close();
			ste.close();
			con.close();
			return data;

		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return data;
	}

	public static Vector search(String status, String type) {
		Vector data = new Vector();
		String[] ss = null;
		try {
			con = DriverManager.getConnection(source);
			Statement ste = con.createStatement();
			ResultSet rs = null;
			String str = null;
			if (type.equalsIgnoreCase("Account")) {
				str = "select * from History where accountno='" + status + "'";
				ss = new String[4];
			} else if (type.equalsIgnoreCase("ItemID")) {
				str = "select History.accountno,customers.firstname,customers.lastname,history.dateout,history.datein from History,customers where ((history.itemID='"
						+ status
						+ "') and (history.accountno=customers.accountno))";
				;
				ss = new String[5];

			}
			rs = ste.executeQuery(str);
			// 0-first,1=last,2=street,3=apt,4=city,5=state,6=zip,7=phone
			while (rs.next()) {
				if (type.equalsIgnoreCase("Account")) {
					ss = new String[4];
					ss[0] = (rs.getString(1)).trim();
					ss[1] = getItemInfo(ss[0], "Title");
					ss[2] = (rs.getDate(2)).toString();
					ss[3] = (rs.getDate(3)).toString();
				} else if (type.equalsIgnoreCase("ItemID")) {
					ss = new String[5];
					ss[0] = (rs.getString(1)).trim();
					ss[1] = (rs.getString(2)).trim();
					ss[2] = (rs.getString(3)).trim();
					ss[3] = (rs.getDate(4)).toString();
					ss[4] = (rs.getDate(5)).toString();

				}

				data.add(ss);

			}

			rs.close();
			ste.close();
			con.close();
			return data;

		} catch (SQLException sqle) {
			sqle.printStackTrace();

		}
		return data;
	}

}