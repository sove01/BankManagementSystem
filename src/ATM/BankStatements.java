package ATM;

import databaseCON.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The BankStatements class provides a graphical user interface to display
 * a user's transaction history and current account balance.
 * It fetches data using the UserDAO and presents it in a readable format.
 */
public class BankStatements extends JFrame implements ActionListener {
    String pin;
    JButton backButton;
    JTextArea transactionArea;
    JLabel bankNameLabel, cardInfoLabel, balanceLabel;
    UserDAO userDAO;
    private ATM atmFrame;

    /**
     * Constructs a new BankStatements frame.
     *
     * @param pin The PIN of the currently logged-in user, used to retrieve
     *            transaction history and account balance.
     *            should return after closing this window.
     */
    BankStatements(String pin, ATM atmFrame) {
        this.pin = pin;
        this.userDAO = new UserDAO();
        this.atmFrame = atmFrame;

        setTitle("Bank Statements");
        getContentPane().setBackground(new Color(230, 240, 250));
        setSize(600, 750);
        setLocationRelativeTo(null);
        setLayout(null);

        // bank name
        bankNameLabel = new JLabel("Cong bank");
        bankNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        bankNameLabel.setForeground(new Color(30, 60, 90));
        bankNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bankNameLabel.setBounds(0, 25, getWidth(), 35);
        add(bankNameLabel);

        //Card Number Info
        cardInfoLabel = new JLabel();
        cardInfoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cardInfoLabel.setForeground(new Color(50, 50, 50));
        cardInfoLabel.setBounds(40, 80, 500, 20);
        add(cardInfoLabel);

        //separator Line
        JSeparator separator1 = new JSeparator();
        separator1.setBounds(30, 115, 540, 2);
        separator1.setForeground(new Color(180, 180, 180));
        add(separator1);

        // transaction history title
        JLabel historyTitle = new JLabel("Transaction History:");
        historyTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        historyTitle.setForeground(new Color(30, 60, 90));
        historyTitle.setBounds(40, 135, 200, 25);
        add(historyTitle);

        // transaction area
        transactionArea = new JTextArea();
        transactionArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        transactionArea.setEditable(false);
        transactionArea.setBackground(new Color(245, 250, 255));
        transactionArea.setForeground(new Color(40, 40, 40));
        transactionArea.setLineWrap(true);
        transactionArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(transactionArea);
        scrollPane.setBounds(30, 170, 540, 400);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150)));
        add(scrollPane);

        // separator line
        JSeparator separator2 = new JSeparator();
        separator2.setBounds(30, 590, 540, 2);
        separator2.setForeground(new Color(180, 180, 180));
        add(separator2);

        // current balance label
        balanceLabel = new JLabel();
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        balanceLabel.setForeground(new Color(0, 128, 0));
        balanceLabel.setBounds(40, 610, 500, 30);
        add(balanceLabel);

        backButton = new JButton("Back to ATM");
        backButton.setBounds(200, 660, 200, 45);
        backButton.setBackground(new Color(65, 125, 128));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("System", Font.BOLD, 18));
        backButton.setFocusPainted(false);
        backButton.addActionListener(this);
        add(backButton);

        loadBankStatementData();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void loadBankStatementData() {
        // Fetch the card number using the pin
        String actualCardNumber = userDAO.getCardNumberByPin(this.pin);

        if (actualCardNumber == null) { // If card number cannot be retrieved for the given PIN
            cardInfoLabel.setText("Account Info: N/A (Error retrieving card number)");
            transactionArea.setText("Error: Could not retrieve user details for statements. Please ensure your account is valid.");
            balanceLabel.setText("Current Balance: Error");
            JOptionPane.showMessageDialog(this, "Error: Could not retrieve user details for statements.", "Data Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Display masked card number
        if (actualCardNumber.length() >= 16) {
            cardInfoLabel.setText("Card Number:  " + actualCardNumber.substring(0, 4) + " **** **** " + actualCardNumber.substring(12));
        } else {
            cardInfoLabel.setText("Card Number:  " + actualCardNumber); // display normal if card length too short
        }

        // Displays transaction history
        List<UserDAO.Transaction> transactions = userDAO.getTransactionHistory(this.pin);

        StringBuilder transactionText = new StringBuilder();
        if (transactions.isEmpty()) {
            transactionText.append("No transactions found for this account.");
        } else {
            transactionText.append(String.format("%-20s %-15s %s%n%n", "DATE", "TYPE", "AMOUNT (CZK)"));
            for (UserDAO.Transaction tx : transactions) {
                transactionText.append(String.format("%-20s %-15s %.2f%n", tx.date, tx.type, tx.amount));
            }
        }
        transactionArea.setText(transactionText.toString());
        transactionArea.setCaretPosition(0);

        // Gets balance from users table
        double currentBalance = userDAO.getBalance(this.pin);
        if (currentBalance == -1.0) {
            balanceLabel.setText("Current Balance: Error");
        } else {
            balanceLabel.setText("Current Balance: " + String.format("%.2f", currentBalance) + " CZK");
        }
    }

    /**
     * Handles action events generated by the 'Back to ATM' button.
     * Closes the current bank statement display and makes the main ATM menu visible again.
     *
     * @param e The ActionEvent generated by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            this.dispose();
            atmFrame.setVisible(true);
        }
    }
}