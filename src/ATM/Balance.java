package ATM;

import databaseCON.DatabaseConnection;
import databaseCON.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Balance extends JFrame implements ActionListener {

    String pin;
    JLabel label2;
    JButton b1;
    UserDAO dao;

    Balance(String pin) {
        this.pin = pin;
        dao = new UserDAO();

        ImageIcon i1 = new ImageIcon("src/Images/backbg.png");
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        JLabel label1 = new JLabel("Your Current Balance is");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(430, 180, 700, 35);
        l3.add(label1);

        label2 = new JLabel();
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("System", Font.BOLD, 16));
        label2.setBounds(430, 220, 400, 35);
        l3.add(label2);

        b1 = new JButton("Back");
        b1.setBounds(700, 406, 150, 35);
        b1.setBackground(new Color(65, 125, 128));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        l3.add(b1);

        double currentBalance = 0.0;
        String cardNumber = dao.getCardNumberByPin(pin); // Get card number using the pin

        if (cardNumber != null) {
            currentBalance = dao.getBalance(cardNumber); // Get balance using the card number
            if (currentBalance == -1.0) {
                JOptionPane.showMessageDialog(this, "Error retrieving balance. Please try again.");
                currentBalance = 0.0;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error: Could not find account for this PIN.");
            currentBalance = 0.0;
        }

        label2.setText("Rs. " + currentBalance); // Display the balance
        setLayout(null);
        setSize(1550, 1080);
        setLocation(0, 0);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        new ATM(pin);
    }

}