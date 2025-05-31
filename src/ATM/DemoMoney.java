package ATM;

import databaseCON.UserDAO; // Import UserDAO
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoMoney extends JFrame implements ActionListener {

    String pin;
    JTextField amountField;
    JButton generateButton, backButton;
    UserDAO userDAO; // Declare UserDAO

    public DemoMoney(String pin) {
        this.pin = pin;
        this.userDAO = new UserDAO(); // Initialize UserDAO

        setTitle("Generate Initial Funds");
        setLayout(null);
        setSize(800, 400);
        setLocation(400, 200);
        getContentPane().setBackground(new Color(186, 35, 98));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel titleLabel = new JLabel("Initial Account Funding");
        titleLabel.setFont(new Font("System", Font.BOLD, 25));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(250, 50, 400, 30);
        add(titleLabel);

        JLabel instructionLabel = new JLabel("Enter initial amount (min 500 CZK):");
        instructionLabel.setFont(new Font("System", Font.PLAIN, 18));
        instructionLabel.setForeground(Color.WHITE);
        instructionLabel.setBounds(100, 120, 400, 25);
        add(instructionLabel);

        amountField = new JTextField();
        amountField.setFont(new Font("Raleway", Font.BOLD, 22));
        amountField.setBounds(450, 120, 200, 30);
        add(amountField);

        generateButton = new JButton("GENERATE FUNDS");
        generateButton.setBackground(Color.BLACK);
        generateButton.setForeground(Color.WHITE);
        generateButton.setBounds(200, 200, 180, 40);
        generateButton.addActionListener(this);
        add(generateButton);

        backButton = new JButton("BACK TO ATM");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(400, 200, 180, 40);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
            new ATM(pin); // Go back to ATM main menu
        } else if (e.getSource() == generateButton) {
            String amountStr = amountField.getText();

            if (amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an amount.");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountStr);
                if (amount < 500) {
                    JOptionPane.showMessageDialog(this, "Minimum initial funding is 500 CZK.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount. Please enter a numerical value.");
                return;
            }

            // Get card number using PIN
            String cardNumber = userDAO.getCardNumberByPin(pin);

            if (cardNumber == null) {
                JOptionPane.showMessageDialog(this, "Error: Could not retrieve card number for this PIN. Please contact support.");
                return;
            }

            // Get current balance
            double currentBalance = userDAO.getBalance(cardNumber);
            if (currentBalance == -1.0) { // Check for error value
                JOptionPane.showMessageDialog(this, "Error: Could not retrieve current balance. Please try again.");
                return;
            }

            // Calculate new balance
            double newBalance = currentBalance + amount;

            // Update balance in database
            if (userDAO.updateBalance(cardNumber, newBalance)) {
                JOptionPane.showMessageDialog(this, "Funds generated successfully! Your new balance is: " + newBalance + " CZK");


                try (Connection con = databaseCON.DatabaseConnection.getConnection();
                     PreparedStatement pstmt = con.prepareStatement("INSERT INTO bank (pin, date, type, amount) VALUES (?, ?, ?, ?)")) {

                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = dateFormat.format(date);

                    pstmt.setString(1, pin); // Use pin for the bank transaction table
                    pstmt.setString(2, formattedDate);
                    pstmt.setString(3, "Initial Deposit"); // Or "Fund Generation"
                    pstmt.setDouble(4, amount);
                    pstmt.executeUpdate();

                } catch (SQLException ex) {
                    System.err.println("SQL Error recording initial deposit transaction: " + ex.getMessage());
                    ex.printStackTrace();
                }

                setVisible(false);
                new ATM(pin);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to generate funds. Please try again.");
            }
        }
    }
}