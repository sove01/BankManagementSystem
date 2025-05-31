package ATM;

import databaseCON.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Withdrawl extends JFrame implements ActionListener {

    String pin;
    TextField textField;
    JButton b1, b2;
    UserDAO dao;

    Withdrawl(String pin) {
        this.pin = pin;
        this.dao = new UserDAO();

        ImageIcon i1 = new ImageIcon("src/Images/backbg.png");
        Image i2 = i1.getImage().getScaledInstance(1550, 830, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 1550, 830);
        add(l3);

        JLabel label1 = new JLabel("MAXIMUM WITHDRAWAL IS 100,000 CZK");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("System", Font.BOLD, 16));
        label1.setBounds(460, 180, 700, 35);
        l3.add(label1);

        JLabel label2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("System", Font.BOLD, 16));
        label2.setBounds(460, 220, 400, 35);
        l3.add(label2);


        textField = new TextField();
        textField.setBackground(new Color(65, 125, 128));
        textField.setForeground(Color.WHITE);
        textField.setBounds(460, 260, 320, 25);
        textField.setFont(new Font("Raleway", Font.BOLD, 22));
        l3.add(textField);

        b1 = new JButton("WITHDRAW");
        b1.setBounds(700, 362, 150, 35);
        b1.setBackground(new Color(65, 125, 128));
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        l3.add(b1);

        b2 = new JButton("BACK");
        b2.setBounds(700, 406, 150, 35);
        b2.setBackground(new Color(65, 125, 128));
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        l3.add(b2);

        setLayout(null);
        setSize(1550, 1080);
        setLocation(0, 0);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b2) {
            setVisible(false);
            new ATM(pin);
            return;
        }

        if (e.getSource() == b1) {
            String amountString = textField.getText();

            if (amountString.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the Amount you want to withdraw.");
                return;
            }

            double withdrawalAmount;
            try {
                withdrawalAmount = Double.parseDouble(amountString);
                if (withdrawalAmount <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive amount to withdraw.");
                    return;
                }

                if (withdrawalAmount > 100000) {
                    JOptionPane.showMessageDialog(null, "Maximum withdrawal limit is 100,000 CZK.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a numerical value.");
                ex.printStackTrace();
                return;
            }

            if (dao.performDebitTransaction(pin, withdrawalAmount, "Withdrawal")) {
                JOptionPane.showMessageDialog(null, withdrawalAmount + " CZK Debited Successfully.");
                setVisible(false);
                new ATM(pin);
            } else {
                JOptionPane.showMessageDialog(null, "Transaction failed. Please try again or check balance.");
            }
        }
    }
}