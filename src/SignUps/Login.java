package SignUps;

import databaseCON.UserDAO;
import ATM.ATM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    JLabel welcomeLabel, cardNumberLabel, pinLabel;
    JTextField cardNumber;
    JPasswordField PIN;
    JButton button1, button2, button3;


    public Login() {
        //Bank icon
        super("Login System");
        ImageIcon bankIcon = new ImageIcon("src/Images/bank.png");
        Image image = bankIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);
        JLabel bankLabel = new JLabel(icon);
        bankLabel.setBounds(350, 10, 120, 120);
        add(bankLabel);

        //Card icon
        ImageIcon cardIcon = new ImageIcon("src/Images/card.png");
        Image image2 = cardIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon icon2 = new ImageIcon(image2);
        JLabel cardLabel = new JLabel(icon2);
        cardLabel.setBounds(650, 100, 120, 120);
        add(cardLabel);

        //Welcome label
        welcomeLabel = new JLabel("Welcome to ATM");
        welcomeLabel.setBounds(315, 135, 450, 40);
        welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.BLACK);
        add(welcomeLabel);

        //Card number label
        cardNumberLabel = new JLabel("Card Number: ");
        cardNumberLabel.setBounds(200, 190, 375, 30);
        cardNumberLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        cardNumberLabel.setForeground(Color.BLACK);
        add(cardNumberLabel);

        //Card number
        cardNumber = new JTextField(15);
        cardNumber.setBounds(310, 190, 230, 30);
        cardNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
        cardNumber.setForeground(Color.BLACK);
        cardNumber.setBackground(Color.WHITE);
        add(cardNumber);

        //PIN label
        pinLabel = new JLabel("PIN: ");
        pinLabel.setBounds(200, 250, 375, 30);
        pinLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        pinLabel.setForeground(Color.BLACK);
        add(pinLabel);


        //PIN
        PIN = new JPasswordField(20);
        PIN.setBounds(310, 250, 230, 30);
        PIN.setFont(new Font("Tahoma", Font.BOLD, 12));
        PIN.setForeground(Color.BLACK);
        PIN.setBackground(Color.WHITE);
        add(PIN);

        //Login button
        button1 = new JButton("Login");
        button1.setBounds(275, 300, 300, 30);
        button1.setFont(new Font("Tahoma", Font.BOLD, 12));
        button1.setForeground(Color.BLACK);
        button1.addActionListener(this);
        add(button1);

        //Clear  button
        button2 = new JButton("Clear");
        button2.setBounds(275, 400, 300, 30);
        button2.setFont(new Font("Tahoma", Font.BOLD, 12));
        button2.setForeground(Color.BLACK);
        button2.addActionListener(this);
        add(button2);

        //Sign up button
        button3 = new JButton("Sign up");
        button3.setBounds(275, 350, 300, 30);
        button3.setFont(new Font("Tahoma", Font.BOLD, 12));
        button3.setForeground(Color.BLACK);
        button3.addActionListener(this);
        add(button3);

        //Background
        ImageIcon background = new ImageIcon("src/Images/background.png");
        Image bg = background.getImage().getScaledInstance(850, 480, Image.SCALE_SMOOTH);
        ImageIcon background2 = new ImageIcon(bg);
        JLabel backgroundLabel = new JLabel(background2);
        backgroundLabel.setBounds(0, 0, 850, 480);
        add(backgroundLabel);


        //Frame properties
        setLayout(null);
        setSize(850, 480);
        setLocation(450, 200);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            String cardNum = cardNumber.getText();
            char[] pinChars = PIN.getPassword();
            String pin = new String(pinChars).trim(); // Convert char array to String and trim
            if (cardNum.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Card Number and PIN.");
                return;
            }

            UserDAO dao = new UserDAO();

            if (dao.validateLogin(cardNum, pin)) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                setVisible(false);
                new ATM(pin);
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect Card Number or PIN.");
            }

            //clear pin
            PIN.setText("");

        } else if (e.getSource() == button2) {
            cardNumber.setText(""); // Clear the card number field
            PIN.setText("");       // Clear the PIN field
        } else if (e.getSource() == button3) {
            setVisible(false);
            new SignUp();
        }
    }
}





