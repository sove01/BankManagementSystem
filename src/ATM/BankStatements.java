package ATM;

import databaseCON.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BankStatements extends JFrame implements ActionListener {
    String cardNumber;
    JButton backButton;
    JTextArea transactionArea;
    JLabel bankNameLabel, cardInfoLabel, balanceLabel;
    UserDAO userDAO;

    BankStatements(String cardNumber) {
        this.cardNumber = cardNumber;
        this.userDAO = new UserDAO();

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
        backButton.setFocusPainted(false); // No focus border
        backButton.addActionListener(this);
        add(backButton);

        loadBankStatementData();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void loadBankStatementData() {
        String pinFromCard = userDAO.getCardNumberByPin(this.cardNumber);

        if (pinFromCard == null) {
            cardInfoLabel.setText("Card Number:  N/A (Error retrieving PIN)");
            transactionArea.setText("Error: Could not retrieve user details for statements. Please ensure your account is valid.");
            balanceLabel.setText("Current Balance: Error");
            JOptionPane.showMessageDialog(this, "Error: Could not retrieve user details for statements.", "Data Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //displays masked card
        if (cardNumber != null && cardNumber.length() >= 16) {
            // Mask all but first 4 and last 4
            cardInfoLabel.setText("Card Number:  " + cardNumber.substring(0, 4) + " **** **** " + cardNumber.substring(12));
        } else {
            cardInfoLabel.setText("Card Number:  " + (cardNumber != null ? cardNumber : "N/A"));
        }

        //displays transaction history
        List<UserDAO.Transaction> transactions = userDAO.getTransactionHistory(pinFromCard);

        StringBuilder transactionText = new StringBuilder();
        if (transactions.isEmpty()) {
            transactionText.append("No transactions found for this account.");
        } else {
            transactionText.append(String.format("%-20s %-15s %s%n%n", "DATE", "TYPE", "AMOUNT (CZK)"));
            for (UserDAO.Transaction tx : transactions) {
                //format
                transactionText.append(String.format("%-20s %-15s %.2f%n", tx.date, tx.type, tx.amount));
            }
        }
        transactionArea.setText(transactionText.toString());
        transactionArea.setCaretPosition(0);

        //gets balance from users table
        double currentBalance = userDAO.getBalance(this.cardNumber);
        if (currentBalance == -1.0) {
            balanceLabel.setText("Current Balance: Error");
        } else {
            balanceLabel.setText("Current Balance: " + String.format("%.2f", currentBalance) + " CZK");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
            new ATM(cardNumber);
        }
    }
}