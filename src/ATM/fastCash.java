package ATM;

import databaseCON.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The FastCash class provides a graphical user interface for users to perform quick withdrawals
 * of predefined amounts. It interacts with the UserDAO to debit the account balance.
 */
public class fastCash extends JFrame implements ActionListener {

    JButton amount1000Button, amount2000Button, amount3000Button,
            amount5000Button, amount8000Button, amount10000Button, backButton;
    String pin;
    UserDAO userDAO;
    private ATM atmFrame;

    /**
     * Constructs a new FastCash frame.
     *
     * @param pin The PIN of the currently logged-in user, used to identify
     * the account for withdrawal transactions.
     */
    fastCash(String pin, ATM atmFrame) {
        this.pin = pin;
        this.userDAO = new UserDAO();
        this.atmFrame = atmFrame;

        setTitle("Fast Cash Withdrawal");
        getContentPane().setBackground(new Color(230, 240, 250));
        setLayout(null);
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Title label
        JLabel titleLabel = new JLabel("SELECT WITHDRAWAL AMOUNT");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(30, 60, 90));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 50, getWidth(), 35);
        add(titleLabel);

        // Common button styling
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);
        Color buttonBgColor = new Color(0, 102, 102);
        Color buttonFgColor = Color.WHITE;
        int buttonWidth = 200;
        int buttonHeight = 45;
        int horizontalGap = 50;
        int verticalGap = 30;
        int startXLeft = (getWidth() - (2 * buttonWidth + horizontalGap)) / 2;
        int startXRight = startXLeft + buttonWidth + horizontalGap;
        int startY = 150;

        // Button 1: 1000 CZK
        amount1000Button = new JButton("1000 CZK");
        amount1000Button.setBounds(startXLeft, startY, buttonWidth, buttonHeight);
        styleButton(amount1000Button, buttonFont, buttonBgColor, buttonFgColor);
        amount1000Button.addActionListener(this);
        add(amount1000Button);

        // Button 2: 2000 CZK
        amount2000Button = new JButton("2000 CZK");
        amount2000Button.setBounds(startXRight, startY, buttonWidth, buttonHeight);
        styleButton(amount2000Button, buttonFont, buttonBgColor, buttonFgColor);
        amount2000Button.addActionListener(this);
        add(amount2000Button);

        // Button 3: 3000 CZK (New option)
        amount3000Button = new JButton("3000 CZK");
        amount3000Button.setBounds(startXLeft, startY + (buttonHeight + verticalGap), buttonWidth, buttonHeight);
        styleButton(amount3000Button, buttonFont, buttonBgColor, buttonFgColor);
        amount3000Button.addActionListener(this);
        add(amount3000Button);

        // Button 4: 5000 CZK
        amount5000Button = new JButton("5000 CZK");
        amount5000Button.setBounds(startXRight, startY + (buttonHeight + verticalGap), buttonWidth, buttonHeight);
        styleButton(amount5000Button, buttonFont, buttonBgColor, buttonFgColor);
        amount5000Button.addActionListener(this);
        add(amount5000Button);

        // Button 5: 8000 CZK (New option)
        amount8000Button = new JButton("8000 CZK");
        amount8000Button.setBounds(startXLeft, startY + 2 * (buttonHeight + verticalGap), buttonWidth, buttonHeight);
        styleButton(amount8000Button, buttonFont, buttonBgColor, buttonFgColor);
        amount8000Button.addActionListener(this);
        add(amount8000Button);

        // Button 6: 10000 CZK
        amount10000Button = new JButton("10000 CZK");
        amount10000Button.setBounds(startXRight, startY + 2 * (buttonHeight + verticalGap), buttonWidth, buttonHeight);
        styleButton(amount10000Button, buttonFont, buttonBgColor, buttonFgColor);
        amount10000Button.addActionListener(this);
        add(amount10000Button);

        // Back button
        backButton = new JButton("BACK TO ATM");
        backButton.setBounds((getWidth() - buttonWidth) / 2, startY + 3 * (buttonHeight + verticalGap) + 10, buttonWidth, buttonHeight); // Centered at bottom
        backButton.setBackground(new Color(65, 125, 128));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(buttonFont);
        backButton.setFocusPainted(false);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    /**
     * Applies consistent styling to a JButton.
     *
     * @param button  The JButton to style.
     * @param font    The font to apply.
     * @param bgColor The background color.
     * @param fgColor The foreground (text) color.
     */
    private void styleButton(JButton button, Font font, Color bgColor, Color fgColor) {
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }

    /**
     * Handles action events generated by the buttons in the FastCash frame.
     * This method processes the selected withdrawal amount or navigates back to the ATM menu.
     *
     * @param e The ActionEvent generated by a button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            this.dispose();
            atmFrame.setVisible(true);
            return;
        }

        // extract only numbers from for example 1000 czk
        String amountString = ((JButton) e.getSource()).getText().split(" ")[0];
        double withdrawalAmount;

        try {
            withdrawalAmount = Double.parseDouble(amountString);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Invalid amount selected. Please re-select.");
            ex.printStackTrace();
            return;
        }

        // Perform the debit transaction
        if (userDAO.performDebitTransaction(pin, withdrawalAmount, "Fast Cash")) {
            JOptionPane.showMessageDialog(this, String.format("%.2f CZK Debited Successfully.", withdrawalAmount));
            setVisible(false);
            new ATM(pin);
            this.dispose();
        } else {

            JOptionPane.showMessageDialog(this, "Transaction failed. Please try again or check your balance.");
        }
    }
}