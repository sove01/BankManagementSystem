package ATM;

import databaseCON.UserDAO; // Import UserDAO

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePin extends JFrame implements ActionListener {

    String pin;
    JPasswordField oldPinField, newPinField, reEnterPinField;
    JButton changeButton, backButton;
    UserDAO userDAO;

    public ChangePin(String pin) {
        this.pin = pin;
        this.userDAO = new UserDAO();

        setTitle("PIN Change");
        setLayout(null);
        setSize(800, 500);
        setLocation(400, 150);
        getContentPane().setBackground(new Color(186, 35, 98));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel titleLabel = new JLabel("CHANGE YOUR PIN");
        titleLabel.setFont(new Font("System", Font.BOLD, 25));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(280, 50, 300, 30);
        add(titleLabel);

        JLabel oldPinLabel = new JLabel("Old PIN:");
        oldPinLabel.setFont(new Font("System", Font.PLAIN, 18));
        oldPinLabel.setForeground(Color.WHITE);
        oldPinLabel.setBounds(150, 120, 150, 30);
        add(oldPinLabel);

        oldPinField = new JPasswordField(4); // Assuming PINs are 4 digits
        oldPinField.setFont(new Font("Raleway", Font.BOLD, 18));
        oldPinField.setBounds(350, 120, 200, 30);
        add(oldPinField);

        JLabel newPinLabel = new JLabel("New PIN:");
        newPinLabel.setFont(new Font("System", Font.PLAIN, 18));
        newPinLabel.setForeground(Color.WHITE);
        newPinLabel.setBounds(150, 170, 150, 30);
        add(newPinLabel);

        newPinField = new JPasswordField(4);
        newPinField.setFont(new Font("Raleway", Font.BOLD, 18));
        newPinField.setBounds(350, 170, 200, 30);
        add(newPinField);

        JLabel reEnterPinLabel = new JLabel("Re-Enter New PIN:");
        reEnterPinLabel.setFont(new Font("System", Font.PLAIN, 18));
        reEnterPinLabel.setForeground(Color.WHITE);
        reEnterPinLabel.setBounds(150, 220, 200, 30);
        add(reEnterPinLabel);

        reEnterPinField = new JPasswordField(4);
        reEnterPinField.setFont(new Font("Raleway", Font.BOLD, 18));
        reEnterPinField.setBounds(350, 220, 200, 30);
        add(reEnterPinField);

        changeButton = new JButton("CHANGE");
        changeButton.setBackground(Color.BLACK);
        changeButton.setForeground(Color.WHITE);
        changeButton.setBounds(200, 300, 150, 40);
        changeButton.addActionListener(this);
        add(changeButton);

        backButton = new JButton("BACK");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setBounds(400, 300, 150, 40);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            setVisible(false);
            new ATM(pin); // Go back to ATM main menu
        } else if (e.getSource() == changeButton) {
            String oldPin = new String(oldPinField.getPassword()).trim();
            String newPin = new String(newPinField.getPassword()).trim();
            String reEnterPin = new String(reEnterPinField.getPassword()).trim();

            if (oldPin.isEmpty() || newPin.isEmpty() || reEnterPin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all PIN fields.");
                return;
            }

            if (newPin.length() != 4 || !newPin.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(this, "New PIN must be exactly 4 digits.");
                return;
            }

            if (!newPin.equals(reEnterPin)) {
                JOptionPane.showMessageDialog(this, "New PIN and Re-entered PIN do not match.");
                return;
            }

            if (oldPin.equals(newPin)) {
                JOptionPane.showMessageDialog(this, "New PIN cannot be the same as the old PIN.");
                return;
            }

            //current pin
            if (!this.pin.equals(oldPin)) {
                JOptionPane.showMessageDialog(this, "Old PIN entered is incorrect.");
                return;
            }


            // update pin in db
            if (userDAO.updatePin(oldPin, newPin)) {
                JOptionPane.showMessageDialog(this, "PIN changed successfully!");
                // Update the 'pin' in the current ATM session
                this.pin = newPin;

                setVisible(false);
                new ATM(this.pin); // changes old to new in the atm
            } else {
                JOptionPane.showMessageDialog(this, "Failed to change PIN. Please try again.");
            }
        }
    }
}