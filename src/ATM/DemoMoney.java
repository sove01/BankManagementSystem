package ATM;

import databaseCON.UserDAO;
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
    UserDAO userDAO;

    public DemoMoney(String pin) {
        this.pin = pin;
        this.userDAO = new UserDAO();

        setTitle("Initial Account Funding");
        getContentPane().setBackground(new Color(230, 240, 250)); // Light blue-grey background
        setSize(600, 450); // Adjusted size for better layout
        setLocationRelativeTo(null); // Center the window
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main title label
        JLabel titleLabel = new JLabel("Initial Account Funding");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Larger, modern font
        titleLabel.setForeground(new Color(30, 60, 90)); // Dark blue-grey text
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center text
        titleLabel.setBounds(0, 40, getWidth(), 35);
        add(titleLabel);

        // Instruction label
        JLabel instructionLabel = new JLabel("Enter amount (min 500 CZK):");
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // Clearer font
        instructionLabel.setForeground(new Color(50, 50, 50)); // Darker grey for body text
        instructionLabel.setBounds(80, 130, 350, 25);
        add(instructionLabel);

        // Amount input field
        amountField = new JTextField();
        amountField.setFont(new Font("Consolas", Font.BOLD, 20)); // Monospaced for numbers
        amountField.setBounds(400, 130, 150, 35); // Adjusted size and position
        amountField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150))); // Subtle border
        amountField.setHorizontalAlignment(JTextField.RIGHT); // Align text to the right
        add(amountField);

        // Generate Funds button
        generateButton = new JButton("GENERATE FUNDS");
        generateButton.setBackground(new Color(0, 102, 102)); // Teal button
        generateButton.setForeground(Color.WHITE);
        generateButton.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Clear font
        generateButton.setFocusPainted(false);
        generateButton.setBounds(100, 250, 190, 45); // Larger button
        generateButton.addActionListener(this);
        add(generateButton);

        // Back to ATM button
        backButton = new JButton("BACK TO ATM");
        backButton.setBackground(new Color(65, 125, 128)); // Slightly different blue-green
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setFocusPainted(false);
        backButton.setBounds(310, 250, 190, 45); // Larger button
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
            new ATM(pin);
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

            String cardNumber = userDAO.getCardNumberByPin(pin);

            if (cardNumber == null) {
                JOptionPane.showMessageDialog(this, "Error: Could not retrieve card number for this PIN. Please contact support.");
                return;
            }

            double currentBalance = userDAO.getBalance(cardNumber);
            if (currentBalance == -1.0) {
                JOptionPane.showMessageDialog(this, "Error: Could not retrieve current balance. Please try again.");
                return;
            }

            double newBalance = currentBalance + amount;

            if (userDAO.updateBalance(cardNumber, newBalance)) {
                JOptionPane.showMessageDialog(this, String.format("Funds generated successfully! Your new balance is: %.2f CZK", newBalance));

                try (Connection con = databaseCON.DatabaseConnection.getConnection();
                     PreparedStatement pstmt = con.prepareStatement("INSERT INTO bank (pin, date, type, amount) VALUES (?, ?, ?, ?)")) {

                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = dateFormat.format(date);

                    pstmt.setString(1, pin);
                    pstmt.setString(2, formattedDate);
                    pstmt.setString(3, "Initial Deposit");
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