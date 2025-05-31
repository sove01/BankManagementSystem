package ATM;

import SignUps.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATM extends JFrame implements ActionListener {
    JButton withdrawCashButton, depositCashButton, fastCashButton, checkBalanceButton, bankStatementsButton, PINChangeButton, moreOptionsButton, returnButton;
    String pin;

    public ATM(String pin) {
        this.pin = pin;
        //Bank icon
        ImageIcon ATMIcon = new ImageIcon("src/Images/atm2.png");
        Image image = ATMIcon.getImage().getScaledInstance(1920, 1080, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        JLabel ATMLabel = new JLabel(icon);
        ATMLabel.setBounds(-189, 0, 1920, 1080);
        add(ATMLabel);

        //Withdraw button
        withdrawCashButton = new JButton("Withdraw");
        withdrawCashButton.setBounds(255, 350, 50, 50);
        withdrawCashButton.setFont(new Font("Tahoma", Font.PLAIN, 4));

        withdrawCashButton.setForeground(Color.BLACK);
        withdrawCashButton.addActionListener(this);
        add(withdrawCashButton);

        //Deposit button
        depositCashButton = new JButton("Deposit");
        depositCashButton.setBounds(255, 410, 50, 50);
        depositCashButton.setForeground(Color.BLACK);
        depositCashButton.addActionListener(this);
        add(depositCashButton);
        depositCashButton.setOpaque(true);
        depositCashButton.setContentAreaFilled(true);

        //Transfer button
        fastCashButton = new JButton("Fast cash ");
        fastCashButton.setBounds(255, 470, 50, 50);
        fastCashButton.setForeground(Color.BLACK);
        fastCashButton.addActionListener(this);
        add(fastCashButton);

        //Check Balance button
        checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.setBounds(255, 530, 50, 50);
        checkBalanceButton.setForeground(Color.BLACK);
        checkBalanceButton.addActionListener(this);
        add(checkBalanceButton);

        //Bank statements button
        bankStatementsButton = new JButton("Bank Statements");
        bankStatementsButton.setBounds(884, 350, 50, 50);
        bankStatementsButton.setForeground(Color.BLACK);
        bankStatementsButton.addActionListener(this);
        add(bankStatementsButton);

        //More options button
        moreOptionsButton = new JButton("More");
        moreOptionsButton.setBounds(884, 410, 50, 50);
        moreOptionsButton.setForeground(Color.BLACK);
        moreOptionsButton.addActionListener(this);
        add(moreOptionsButton);

        //Change pin button
        PINChangeButton = new JButton("Pin Change");
        PINChangeButton.setBounds(884, 470, 50, 50);
        PINChangeButton.setForeground(Color.BLACK);
        PINChangeButton.addActionListener(this);
        add(PINChangeButton);

        //Return button
        returnButton = new JButton("Return");
        returnButton.setBounds(884, 530, 50, 50);
        returnButton.setForeground(Color.BLACK);
        returnButton.addActionListener(this);
        add(returnButton);


        setLayout(null);
        setSize(1550, 1080);
        setLocation(0, 0);
        setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == withdrawCashButton) {
            new Withdrawl(pin);
            setVisible(false);
        } else if (e.getSource() == depositCashButton) {
            new Deposit(pin);
            setVisible(false);
        } else if (e.getSource() == checkBalanceButton) {
            new Balance(pin);
            setVisible(false);
        } else if (e.getSource() == fastCashButton) {
            new fastCash(pin);
            setVisible(false);
        } else if (e.getSource() == bankStatementsButton) {
            new BankStatements(pin);
        } else if (e.getSource() == returnButton) {
            this.dispose();
            new Login();
        }

    }
}
