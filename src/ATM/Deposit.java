package ATM;

import databaseCON.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Deposit extends JFrame implements ActionListener {
    String pin;
    TextField textField;

    JButton b1, b2;

    Deposit(String pin) {
        this.pin = pin;
        ImageIcon i1 = new ImageIcon("src/Images/backbg.png");
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        JLabel label1 = new JLabel("ENETR AMOUNT YOU WANT TO DEPOSIT");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(460, 180, 400, 35);
        l3.add(label1);

        textField = new TextField();
        textField.setBackground(new Color(65, 125, 128));
        textField.setForeground(Color.WHITE);
        textField.setBounds(460, 230, 320, 25);
        textField.setFont(new Font("Raleway", Font.BOLD, 22));
        l3.add(textField);

        b1 = new JButton("DEPOSIT");
        b1.setBounds(700, 362, 150, 35);
        b1.setBackground(new Color(65, 125, 128));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(700, 406, 150, 35);
        b2.setBackground(new Color(65, 125, 128));
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        l3.add(b2);


        setLayout(null);
        setSize(1550, 1080);
        setLocation(0, 0);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String amount = textField.getText();

        if (amount.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the Amount you want to Deposit");
            return;
        }


        int depositAmount;
        try {
            depositAmount = Integer.parseInt(amount);
            if (depositAmount <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a positive amount to Deposit.");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a numerical value.");
            return;
        }

        Date date = new Date();
        //db format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(date);

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement("INSERT INTO bank (pin, date, type, amount) VALUES (?, ?, ?, ?)")) {

            if (e.getSource() == b1) {
                pstmt.setString(1, pin);
                pstmt.setString(2, formattedDate);
                pstmt.setString(3, "Deposit");
                pstmt.setInt(4, depositAmount);

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Rs. " + amount + " Deposited Successfully");
                    setVisible(false);
                    new ATM(pin);
                } else {
                    JOptionPane.showMessageDialog(null, "Deposit failed. Please try again.");
                }
            } else if (e.getSource() == b2) {
                setVisible(false);
                new ATM(pin);
            }
        } catch (SQLException E) {
            System.err.println("SQL Error during deposit: " + E.getMessage());
            E.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred during deposit. Please try again.");
        }

    }
}