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

public class BankStatements extends JFrame implements ActionListener {
    String pin;
    JButton button;

    BankStatements(String pin) {
        this.pin = pin;
        getContentPane().setBackground(new Color(255, 204, 204));
        setSize(400, 600);
        setLocation(20, 20);
        setLayout(null);

        JLabel label1 = new JLabel();
        label1.setBounds(20, 140, 400, 200);
        add(label1);

        JLabel label2 = new JLabel("TechCoder A.V");
        label2.setFont(new Font("System", Font.BOLD, 15));
        label2.setBounds(150, 20, 200, 20);
        add(label2);

        JLabel label3 = new JLabel();
        label3.setBounds(20, 80, 300, 20);
        add(label3);

        JLabel label4 = new JLabel();
        label4.setBounds(20, 400, 300, 20);
        add(label4);

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT card_number FROM login WHERE pin = ?")) {

            pstmt.setString(1, pin);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String fullCardNumber = resultSet.getString("card_number");
                // only shows first and last 4 numbers of card
                if (fullCardNumber != null && fullCardNumber.length() >= 16) {
                    label3.setText("Card Number:  " + fullCardNumber.substring(0, 4) + "XXXXXXXX" + fullCardNumber.substring(12));
                } else {
                    label3.setText("Card Number:  " + fullCardNumber);
                }
            } else {
                label3.setText("Card Number:  Not Found");
            }

        } catch (SQLException e) {
            System.err.println("SQL Error fetching card number: " + e.getMessage());
            e.printStackTrace();
            label3.setText("Card Number:  Error");
        }

        // takes transactions and calculates balance
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT date, type, amount FROM bank WHERE pin = ?")) {

            pstmt.setString(1, pin);
            ResultSet resultSet = pstmt.executeQuery();

            int balance = 0;
            StringBuilder transactionHistory = new StringBuilder("<html>");

            while (resultSet.next()) {
                String date = resultSet.getString("date");
                String type = resultSet.getString("type");
                int amount = resultSet.getInt("amount"); // Get as int directly

                transactionHistory.append(date).append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                        .append(type).append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                        .append(amount).append("<br><br>");

                if (type.equals("Deposit")) {
                    balance += amount;
                } else {
                    balance -= amount;
                }
            }
            transactionHistory.append("</html>");
            label1.setText(transactionHistory.toString()); // Set the accumulated text

            label4.setText("Your Total Balance is CZK " + balance);
        } catch (SQLException e) {
            System.err.println("SQL Error fetching transactions/balance: " + e.getMessage());
            e.printStackTrace();
            label1.setText("<html>Error loading transactions.</html>"); // Display error in UI
            label4.setText("Your Total Balance is Rs Error"); // Display error in UI
        }

        button = new JButton("Exit");
        button.setBounds(20, 500, 100, 25);
        button.addActionListener(this);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        add(button);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            setVisible(false);
        }
    }

}