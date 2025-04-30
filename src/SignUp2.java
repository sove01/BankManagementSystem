import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;

public class SignUp2 extends JFrame implements ActionListener {
    JRadioButton marriedButton, unmarriedButton, otherButton, maleButton, femaleButton, otherGenderButton;
    JButton nextSignUp;
    JTextField cardLimitText, homeAddressText, phoneNumberText, emailText ;

    SignUp2() {
        super("APPLICATION FORM - SEQUEL");
        ImageIcon bankIcon = new ImageIcon("src/Images/bank.png");
        Image image = bankIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);
        JLabel bankLabel = new JLabel(icon);
        bankLabel.setBounds(45, 10, 120, 120);
        add(bankLabel);

        //Page label
        JLabel page1 = new JLabel("Page 2");
        page1.setBounds(380, 70, 600, 45);
        page1.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(page1);


        //Home address label
        JLabel address = new JLabel("Adress");
        address.setBounds(100, 240, 100, 30);
        address.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(address);

        //Home address field
        homeAddressText = new JTextField();
        homeAddressText.setBounds(300, 240, 100, 30);
        homeAddressText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(homeAddressText);


        //Email label
        JLabel email = new JLabel("Email");
        email.setBounds(100, 290, 100, 30);
        email.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(email);

        //Email field
        emailText = new JTextField();
        emailText.setBounds(300, 290, 100, 30);
        emailText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(emailText);


        //Marital status label
        JLabel maritalStatus = new JLabel("Marital Status :");
        maritalStatus.setFont(new Font("Raleway", Font.BOLD, 15));
        maritalStatus.setBounds(100, 540, 200, 30);
        add(maritalStatus);

        //Married button
        marriedButton = new JRadioButton("Married");
        marriedButton.setBounds(250, 540, 100, 30);
        marriedButton.setBackground(new Color(222, 255, 228));
        marriedButton.setFont(new Font("Raleway", Font.BOLD, 15));
        add(marriedButton);

        //Unmarried button
        unmarriedButton = new JRadioButton("Unmarried");
        unmarriedButton.setBackground(new Color(222, 255, 228));
        unmarriedButton.setBounds(400, 540, 100, 30);
        unmarriedButton.setFont(new Font("Raleway", Font.BOLD, 15));
        add(unmarriedButton);

        //Other button
        otherButton = new JRadioButton("Other");
        otherButton.setBackground(new Color(222, 255, 228));
        otherButton.setBounds(550, 540, 100, 30);
        otherButton.setFont(new Font("Raleway", Font.BOLD, 15));
        add(otherButton);

        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(marriedButton);
        buttonGroup1.add(unmarriedButton);
        buttonGroup1.add(otherButton);

        //Gender Jlabel
        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setFont(new Font("Raleway", Font.BOLD, 15));
        genderLabel.setBounds(100, 340, 100, 30);
        add(genderLabel);


        //Gender Options
        maleButton = new JRadioButton("Male");
        maleButton.setBounds(300, 340, 100, 30);
        maleButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        add(maleButton);
        femaleButton = new JRadioButton("Female");
        femaleButton.setBounds(450, 340, 100, 30);
        femaleButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        add(femaleButton);
        otherGenderButton = new JRadioButton("Other gender");
        otherGenderButton.setBounds(600, 340, 100, 30);
        otherGenderButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        add(otherGenderButton);

        ButtonGroup genderButtons = new ButtonGroup();
        genderButtons.add(maleButton);
        genderButtons.add(femaleButton);
        genderButtons.add(otherGenderButton);

        //Next
        nextSignUp = new JButton("Next");
        nextSignUp.setBounds(750, 720, 80, 30);
        nextSignUp.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        nextSignUp.setBackground(Color.BLACK);
        nextSignUp.setForeground(Color.WHITE);
        nextSignUp.addActionListener(this);
        add(nextSignUp);

        getContentPane().setBackground(new Color(186, 35, 98));
        setLayout(null);
        setSize(850, 800);
        setLocation(360, 40);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cardLimit;
        String homeAddress;
        String phoneNumber;
        String email;
        String gender = null;
        String maritalStatus = null;



//        try{
//            if(cardLimit.isEmpty() || homeAddress.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || gender.isEmpty() || maritalStatus.isEmpty() ){
//                JOptionPane.showMessageDialog(null, "Please fill out everything in the form");
//            }
//            return;
//        }
//        String gender = null;
//        String maritalStatus = null;
//
//        if (maleButton.isSelected()) {
//            gender = "Male";
//        } else if (femaleButton.isSelected()) {
//            gender = "Female";
//        } else if (otherGenderButton.isSelected()) {
//            gender = "Other";
//        }
//
//        if (marriedButton.isSelected()) {
//            maritalStatus = "Married";
//        } else if (unmarriedButton.isSelected()) {
//            maritalStatus = "Unmarried";
//        } else if (otherButton.isSelected()) {
//            maritalStatus = "Other";
//        }
//
//        Conn conn = new Conn();
//
//        try {
//            PreparedStatement pstmtPersonalInfo2
//        }

    }
}
