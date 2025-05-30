package ATM;

import databaseCON.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class fastCash extends JFrame implements ActionListener {

    JButton b1, b2, b3, b4, b5, b6, b7;
    String pin;

    fastCash(String pin) {
        this.pin = pin;

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/atm2.png"));
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        JLabel label = new JLabel("SELECT WITHDRAWL AMOUNT");
        label.setBounds(445, 180, 700, 35);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("System", Font.BOLD, 23));
        l3.add(label);

        b1 = new JButton("Rs. 100");
        b1.setForeground(Color.WHITE);
        b1.setBackground(new Color(65, 125, 128));
        b1.setBounds(410, 274, 150, 35);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("Rs. 500");
        b2.setForeground(Color.WHITE);
        b2.setBackground(new Color(65, 125, 128));
        b2.setBounds(700, 274, 150, 35);
        b2.addActionListener(this);
        l3.add(b2);

        b3 = new JButton("Rs. 1000");
        b3.setForeground(Color.WHITE);
        b3.setBackground(new Color(65, 125, 128));
        b3.setBounds(410, 318, 150, 35);
        b3.addActionListener(this);
        l3.add(b3);

        b4 = new JButton("Rs. 2000");
        b4.setForeground(Color.WHITE);
        b4.setBackground(new Color(65, 125, 128));
        b4.setBounds(700, 318, 150, 35);
        b4.addActionListener(this);
        l3.add(b4);

        b5 = new JButton("Rs. 5000");
        b5.setForeground(Color.WHITE);
        b5.setBackground(new Color(65, 125, 128));
        b5.setBounds(410, 362, 150, 35);
        b5.addActionListener(this);
        l3.add(b5);

        b6 = new JButton("Rs. 10000");
        b6.setForeground(Color.WHITE);
        b6.setBackground(new Color(65, 125, 128));
        b6.setBounds(700, 362, 150, 35);
        b6.addActionListener(this);
        l3.add(b6);

        b7 = new JButton("BACK");
        b7.setForeground(Color.WHITE);
        b7.setBackground(new Color(65, 125, 128));
        b7.setBounds(700, 406, 150, 35);
        b7.addActionListener(this);
        l3.add(b7);

        setLayout(null);
        setSize(1550, 1080);
        setLocation(0, 0);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b7) {
            setVisible(false);
            new ATM(pin);
            return;
        }

        String amountString = ((JButton) e.getSource()).getText().substring(4);
        int withdrawalAmount;

        try {
            withdrawalAmount = Integer.parseInt(amountString);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Error: Invalid amount selected.");
            ex.printStackTrace();
            return;
        }

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmtSelect = con.prepareStatement("SELECT type, amount FROM bank WHERE pin = ?");
             PreparedStatement pstmtInsert = con.prepareStatement("INSERT INTO bank (pin, date, type, amount) VALUES (?, ?, ?, ?)")) {

            pstmtSelect.setString(1, pin);
            ResultSet resultSet = pstmtSelect.executeQuery();

            int currentBalance = 0;
            while (resultSet.next()) {
                if (resultSet.getString("type").equals("Deposit")) {
                    currentBalance += resultSet.getInt("amount");
                } else {
                    currentBalance -= resultSet.getInt("amount");
                }
            }
            resultSet.close();

            if (currentBalance < withdrawalAmount) {
                JOptionPane.showMessageDialog(null, "Insufficient Balance");
                return;
            }

            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(date);

            pstmtInsert.setString(1, pin);
            pstmtInsert.setString(2, formattedDate);
            pstmtInsert.setString(3, "Withdrawal");
            pstmtInsert.setInt(4, withdrawalAmount);

            int rowsAffected = pstmtInsert.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Rs. " + withdrawalAmount + " Debited Successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Withdrawal failed. Please try again.");
            }

        } catch (SQLException E) {
            System.err.println("SQL Error during Fast Cash transaction: " + E.getMessage());
            E.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred during transaction. Please try again.");
        }
        setVisible(false);
        new ATM(pin);
    }

}