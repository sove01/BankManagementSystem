import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener {
    JLabel welcomeLabel, cardNumberLabel, pinLabel;
    JTextField cardNumber;
    JPasswordField PIN;
    JButton button1, button2, button3;

    Login() {
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
        try {
            if (e.getSource() == button1) {
                Conn conn = new Conn();
                String cardNum = cardNumber.getText();
                String pin = PIN.getText();

                //SQlite prepared statement to prevent sql injection
                String query = "SELECT * FROM login WHERE card_number = ? AND pin = ?";

                //Prepare the statement and execute
                PreparedStatement pstmt = conn.con.prepareStatement(query);
                pstmt.setString(1, cardNum);
                pstmt.setString(2, pin);

//                ResultSet rs = pstmt.executeQuery();
//                if (rs.next()) {
//                    setVisible(false);
//                    new ATM(pin);
//                } else {
//                    JOptionPane.showMessageDialog(null, "Incorrect Card Number or PIN");
//                }

            } else if (e.getSource() == button2) {
                cardNumber.setText("");
                PIN.setText("");
            } else if (e.getSource() == button3) {
                new SignUp();
                setVisible(false);

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
//            ex.printStackTrace();
        }

    }


}