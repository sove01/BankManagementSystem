package ATM;

import databaseCON.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Balance extends JFrame implements ActionListener {

    String pin;
    JLabel balanceAmountLabel; // Renamed for clarity
    JButton backButton; // Renamed for clarity
    UserDAO userDAO; // Renamed for clarity

    public Balance(String pin) {
        this.pin = pin;
        this.userDAO = new UserDAO();

        setTitle("Account Balance");
        getContentPane().setBackground(new Color(230, 240, 250)); // Light blue-grey background
        setLayout(null);
        setSize(600, 400); // Adjusted size for a cleaner look
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window

        // ATM background image (scaled to fit smaller frame if desired, or remove if not enhancing)
        // Given the goal to match BankStatements, let's remove the large background image
        // and use a clean background color. If you want a small graphic, add it separately.

        // Main title label
        JLabel titleLabel = new JLabel("Your Current Balance");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28)); // Larger, modern font
        titleLabel.setForeground(new Color(30, 60, 90)); // Dark blue-grey text
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center text
        titleLabel.setBounds(0, 50, getWidth(), 35);
        add(titleLabel);

        // Separator line
        JSeparator separator = new JSeparator();
        separator.setBounds(50, 110, 500, 2);
        separator.setForeground(new Color(180, 180, 180));
        add(separator);

        // Label to display the balance amount
        balanceAmountLabel = new JLabel("Fetching balance...");
        balanceAmountLabel.setForeground(new Color(0, 128, 0)); // Green for balance
        balanceAmountLabel.setFont(new Font("Consolas", Font.BOLD, 30)); // Large, bold for amount
        balanceAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        balanceAmountLabel.setBounds(0, 150, getWidth(), 40);
        add(balanceAmountLabel);

        // Back button
        backButton = new JButton("BACK TO ATM");
        backButton.setBounds(200, 280, 200, 45); // Centered, larger button
        backButton.setBackground(new Color(65, 125, 128)); // Blue-green color
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setFocusPainted(false);
        backButton.addActionListener(this);
        add(backButton);

        // Fetch and display the balance
        double currentBalance = 0.0;
        String cardNumber = userDAO.getCardNumberByPin(pin);

        if (cardNumber != null) {
            currentBalance = userDAO.getBalance(cardNumber);
            if (currentBalance == -1.0) {
                JOptionPane.showMessageDialog(this, "Error retrieving balance. Please try again.", "Balance Error", JOptionPane.ERROR_MESSAGE);
                balanceAmountLabel.setText("Balance Error"); // Indicate error
                balanceAmountLabel.setForeground(Color.RED); // Change color to red for error
            } else {
                balanceAmountLabel.setText(String.format("%.2f CZK", currentBalance)); // Display formatted balance
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error: Could not find account for this PIN.", "Account Error", JOptionPane.ERROR_MESSAGE);
            balanceAmountLabel.setText("Account Error"); // Indicate error
            balanceAmountLabel.setForeground(Color.RED); // Change color to red for error
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
            new ATM(pin);
            this.dispose();
        }
    }
}