import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Login extends JFrame implements ActionListener {
    JLabel welcomeLabel, cardNumberLabel, pinLabel;
    JTextField cardNumber;
    JPasswordField password;
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
            Image image2  = cardIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon icon2 = new ImageIcon(image2);
        JLabel cardLabel = new JLabel(icon2);
        cardLabel.setBounds(670, 350, 120, 120);
        add(cardLabel);

        //Welcome label
        welcomeLabel = new JLabel("Welcome to ATM");
        welcomeLabel.setBounds(230, 125, 450, 40);
        welcomeLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        welcomeLabel.setForeground(Color.BLACK);
        add(welcomeLabel);

        //Card number label
        cardNumberLabel = new JLabel("Card Number: ");
        cardNumberLabel.setBounds(150, 190, 375, 30);
        cardNumberLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        cardNumberLabel.setForeground(Color.BLACK);
        add(cardNumberLabel);

        //Card number
        cardNumber = new JTextField(15);
        cardNumber.setBounds(325, 190, 230, 30);
        cardNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
        cardNumber.setForeground(Color.WHITE);
        add(cardNumber);

        //PIN label
        pinLabel = new JLabel("PIN: ");
        pinLabel.setBounds(150, 250, 375, 30);
        pinLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        pinLabel.setForeground(Color.BLACK);
        add(pinLabel);


        //Password
        password = new JPasswordField(20);
        password.setBounds(325, 250, 230, 30);
        password.setFont(new Font("Tahoma", Font.BOLD, 12));
        password.setForeground(Color.WHITE);
        add(password);






    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}