import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Login extends JFrame implements ActionListener {
    JLabel label1, label2, label3;
    JTextField text1;
    JPasswordField password3;
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


}