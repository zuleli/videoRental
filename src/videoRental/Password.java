package videoRental;

/* Author: Zule Li
 * Email:zule.li@hotmail.com
 * Last Modified Date:Mar.7,2015
 * */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
//import oracle.jdeveloper.layout.XYConstraints;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
public class Password extends JDialog implements ActionListener
{

  JPasswordField psw = new JPasswordField();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JTextField userName = new JTextField();
  JButton yes = new JButton();
  JButton no = new JButton();
  JRadioButton editUser = new JRadioButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  private Main mn;

  public Password(Main mn)
  {
    setTitle("Log On");
    yes.addActionListener(this);
    no.addActionListener(this);
    this.mn=mn;
    try
    {
      jbInit();
      pack();
      setVisible(true);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }
  public void actionPerformed(ActionEvent e)
  {
    Object source=e.getSource();
    if(source==no)
    {
      dispose();
    }
    if(source==yes)
    {
      int step=Database.getStep(userName.getText().trim(),psw.getPassword());
      if((mn.step=step)!=-1)
      mn.open();
      else
      JOptionPane.showMessageDialog(null,"Password is not correct");
      if(step==0&&editUser.isSelected())
      {
        UserEditor us=new UserEditor();
        dispose();
      }
      dispose();
    }

  }

  private void jbInit() throws Exception
  {
    this.getContentPane().setLayout(gridBagLayout1);
    psw.setText("980521");
    jLabel1.setText("User Name:");
    jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
    jLabel2.setText("Password:");
    jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
    yes.setText("OK");
    no.setText("Cancel");
    editUser.setText("Edit Users");
    this.getContentPane().add(editUser, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 5, 13, 0), 12, 5));
    this.getContentPane().add(no, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(15, 0, 13, 0), 1, 3));
    this.getContentPane().add(yes, new GridBagConstraints(3, 3, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 13, 7), 11, 8));
    this.getContentPane().add(userName, new GridBagConstraints(2, 0, 2, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 0), 132, 9));
    this.getContentPane().add(jLabel2, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(25, 20, 0, 0), 26, 8));
    this.getContentPane().add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 0, 0), 33, 8));
    this.getContentPane().add(psw, new GridBagConstraints(2, 2, 2, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(20, 0, 0, 0), 90, 9));
  }
}