import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class SignUp extends JFrame implements ActionListener {
    JRadioButton marriedButton, unmarriedButton, otherButton, maleButton, femaleButton, otherGenderButton;
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
        applicationNumber.setBounds(250, 20, 600, 60);
        applicationNumber.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        add(applicationNumber);

        //Page 1 label
        JLabel page1 = new JLabel("Page 1");
        page1.setBounds(380, 70, 600, 45);
        page1.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(page1);

        //Personal information label
        JLabel personalInformation = new JLabel("Personal Information");
        personalInformation.setBounds(100, 140, 600, 40);
        personalInformation.setFont(new Font("Times New Roman", Font.BOLD, 25));
        add(personalInformation);

        //First name label
        JLabel firstName = new JLabel("First Name");
        firstName.setBounds(100, 190, 100, 30);
        firstName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(firstName);

        //First name field
        firstNameText = new JTextField();
        firstNameText.setBounds(200, 190, 100, 30);
        firstNameText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(firstNameText);

        //Last name label
        JLabel lastName = new JLabel("Last Name");
        lastName.setBounds(100, 240, 100, 30);
        lastName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(lastName);

        //Last name field
        lastNameText = new JTextField();
        lastNameText.setBounds(200, 240, 100, 30);
        lastNameText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(lastNameText);

        //Email label
        JLabel email = new JLabel("Email");
        email.setBounds(100, 290, 100, 30);
        email.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(email);

        //Email field
        emailText = new JTextField();
        emailText.setBounds(200, 290, 100, 30);
        emailText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(emailText);

        //Gender label
        JLabel gender = new JLabel("Gender");
        gender.setBounds(100, 340, 100, 30);
        gender.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(gender);

        //Gender Options
        maleButton = new JRadioButton("Male");
        setBounds(200, 340, 100, 30);
        maleButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        add(maleButton);
        femaleButton = new JRadioButton("Female");
        femaleButton.setBounds(300, 340, 100, 30);
        femaleButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        add(femaleButton);
        otherGenderButton = new JRadioButton("Other gender ( gay as hell )");
        otherGenderButton.setBounds(400, 340, 100, 30);
        otherGenderButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        add(otherGenderButton);


        //Nationality label
        JLabel nationality = new JLabel("Nationality");
        nationality.setBounds(100, 390, 100, 30);
        nationality.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(nationality);

        //Nationality field
        nationalityText = new JTextField();
        nationalityText.setBounds(200, 390, 100, 30);
        nationalityText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(nationalityText);

        //Region label
        JLabel region = new JLabel("Region");
        region.setBounds(100, 440, 100, 30);
        region.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(region);

        //Region field
        regionText = new JTextField();
        regionText.setBounds(200, 440, 100, 30);
        regionText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(regionText);

        //City label
        JLabel city = new JLabel("City");
        city.setBounds(100, 490, 100, 30);
        city.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(city);
        //City field
        cityText = new JTextField();
        cityText.setBounds(200, 490, 100, 30);
        cityText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(cityText);

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

        //PIN label
        JLabel pin = new JLabel("PIN");
        pin.setBounds(100, 590, 200, 30);
        pin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(pin);

        //PIN field
        pinText = new JTextField();
        pinText.setBounds(200, 590, 100, 30);
        pinText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(pinText);

        //Next
        nextSignUp = new JButton("Next");
        nextSignUp.setBounds(750, 720, 80, 30);
        nextSignUp.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        nextSignUp.setBackground(Color.BLACK);
        nextSignUp.setForeground(Color.WHITE);
        nextSignUp.addActionListener(this);
        add(nextSignUp);

        getContentPane().setBackground(new Color(70, 107, 236));
        setLayout(null);
        setSize(850, 800);
        setLocation(360, 40);
        setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        double balance = 0; // intial balance
        String formNumber = aN;
        String lastName = lastNameText.getText();
        String firstName = firstNameText.getText();
        String email = emailText.getText();
        String nationality = nationalityText.getText();
        String region = regionText.getText();
        String city = cityText.getText();
        String gender = null;
        String maritalStatus = null;

        if (maleButton.isSelected()) {
            gender = "Male";
        } else if (femaleButton.isSelected()) {
            gender = "Female";
        } else if (otherGenderButton.isSelected()) {
            gender = "Other";
        }

        if (marriedButton.isSelected()) {
            maritalStatus = "Married";
        } else if (unmarriedButton.isSelected()) {
            maritalStatus = "Unmarried";
        } else if (otherButton.isSelected()) {
            maritalStatus = "Other";
        }

        //to validate required fields
        try {
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || nationality.isEmpty() || region.isEmpty() || city.isEmpty() || gender == null || maritalStatus == null)
                ;
            JOptionPane.showMessageDialog(null, "Please fill out everything in the form");
            return;
        }

        //Random card number and pin generator
        String cNumGen = String.format("%04d-%04d-%04d-%04d",
                (int) (Math.random() * 10000),
                (int) (Math.random() * 10000),
                (int) (Math.random() * 10000),
                (int) (Math.random() * 10000));

        String pinGen = String.format("%04d",
                (int) (Math.random() * 10000));

        Conn conn = new Conn();
        String personalInfoQuery = "INSERT INTO personal_info " +
                "(form_number, first_name, last_name, email, nationality, region, city, gender, marital_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmtPersonalInfo = conn.con.prepareStatement(personalInfoQuery);
            pstmtPersonalInfo.setString(1, formNumber);
            pstmtPersonalInfo.setString(2, firstName);
            pstmtPersonalInfo.setString(3, lastName);
            pstmtPersonalInfo.setString(4, email);
            pstmtPersonalInfo.setString(5, nationality);
            pstmtPersonalInfo.setString(6, region);
            pstmtPersonalInfo.setString(7, city);
            pstmtPersonalInfo.setString(8, gender);
            pstmtPersonalInfo.setString(9, maritalStatus);
            pstmtPersonalInfo.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);

        }


    }
}
