import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SignUp extends JFrame implements ActionListener {
    JRadioButton marriedButton, unmarriedButton, otherButton;
    JButton nextSignUp;
    JTextField lastNameText, firstNameText, emailText, genderText, nationalityText, regionText, cityText, pinText;

    //Generate random 2 digit number - represents application number
    String aN = String.format(" %02d", new Random().nextInt(100) + 1);


    SignUp() {

        //Application form label
        super("APPLICATION FORM");
        ImageIcon bankIcon = new ImageIcon("src/Images/bank.png");
        Image image = bankIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);
        JLabel bankLabel = new JLabel(icon);
        bankLabel.setBounds(45, 10, 120, 120);
        add(bankLabel);

        //Application number label
        JLabel applicationNumber = new JLabel("APPLICATION NUMBER" + aN);
        applicationNumber.setBounds(180, 20, 600, 40);
        applicationNumber.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(applicationNumber);

        //Page 1 label
        JLabel page1 = new JLabel("Page 1");
        page1.setBounds(360, 70, 600, 30);
        page1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(page1);

        //Personal information label
        JLabel personalInformation = new JLabel("Personal Information");
        personalInformation.setBounds(320, 90, 600, 30);
        personalInformation.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(personalInformation);

        //Name label
        JLabel firstName = new JLabel("First Name");
        firstName.setBounds(100, 190, 100, 30);
        firstName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(firstName);

        //First name field
        firstNameText = new JTextField();
        firstNameText.setBounds(300, 190, 100, 30);
        firstNameText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(firstNameText);

        //Last name label
        JLabel lastName = new JLabel("Last Name");
        lastName.setBounds(150, 190, 100, 30);
        lastName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(lastName);

        //Last name field
        lastNameText = new JTextField();
        lastNameText.setBounds(350, 190, 100, 30);
        lastNameText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(lastNameText);

        //Email label
        JLabel email = new JLabel("Email");
        email.setBounds(200, 190, 100, 30);
        email.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(email);

        //Email field
        emailText = new JTextField();
        emailText.setBounds(400, 190, 100, 30);
        emailText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(emailText);

        //Gender label
        JLabel gender = new JLabel("Gender");
        gender.setBounds(250, 190, 100, 30);
        gender.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(gender);

        //Gender field
        genderText = new JTextField();
        genderText.setBounds(450, 190, 100, 30);
        genderText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(genderText);

        //Nationality label
        JLabel nationality = new JLabel("Nationality");
        nationality.setBounds(300, 190, 100, 30);
        nationality.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(nationality);

        //Nationality field
        nationalityText = new JTextField();
        nationalityText.setBounds(500, 190, 100, 30);
        nationalityText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(nationalityText);

        //Region label
        JLabel region = new JLabel("Region");
        region.setBounds(350, 190, 100, 30);
        region.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(region);

        //Region field
        regionText = new JTextField();
        regionText.setBounds(550, 190, 100, 30);
        regionText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(regionText);

        //City label
        JLabel city = new JLabel("City");
        city.setBounds(400, 190, 100, 30);
        city.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(city);
        //City field
        cityText = new JTextField();
        cityText.setBounds(600, 190, 100, 30);
        cityText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(cityText);

        //Marital status label
        JLabel maritalStatus = new JLabel("Marital Status :");
        maritalStatus.setFont(new Font("Raleway", Font.BOLD, 20));
        maritalStatus.setBounds(100, 440, 200, 30);
        add(maritalStatus);

        //Married button
        marriedButton = new JRadioButton("Married");
        marriedButton.setBounds(300, 440, 100, 30);
        marriedButton.setBackground(new Color(222, 255, 228));
        marriedButton.setFont(new Font("Raleway", Font.BOLD, 14));
        add(marriedButton);

        //Unmarried button
        unmarriedButton = new JRadioButton("Unmarried");
        unmarriedButton.setBackground(new Color(222, 255, 228));
        unmarriedButton.setBounds(450, 440, 100, 30);
        unmarriedButton.setFont(new Font("Raleway", Font.BOLD, 14));
        add(unmarriedButton);

        //Other button
        otherButton = new JRadioButton("Other");
        otherButton.setBackground(new Color(222, 255, 228));
        otherButton.setBounds(635, 440, 100, 30);
        otherButton.setFont(new Font("Raleway", Font.BOLD, 14));
        add(otherButton);


        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(marriedButton);
        buttonGroup1.add(unmarriedButton);
        buttonGroup1.add(otherButton);

        //PIN label
        JLabel pin = new JLabel("PIN");
        pin.setBounds(300, 590, 200, 30);
        pin.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(pin);

        //PIN field
        pinText = new JTextField();
        pinText.setBounds(400, 590, 100, 30);
        pinText.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(pinText);

        //Next
        nextSignUp = new JButton("Next");
        nextSignUp.setBounds(620, 720, 80, 30);
        nextSignUp.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        nextSignUp.setBackground(Color.BLACK);
        nextSignUp.setForeground(Color.WHITE);
        nextSignUp.addActionListener(this);
        add(nextSignUp);

        getContentPane().setBackground(new Color(222, 255, 228));
        setLayout(null);
        setSize(850, 800);
        setLocation(360, 40);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
