package SignUps;

import databaseCON.User;
import databaseCON.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SignUp2 extends JFrame implements ActionListener {
    JRadioButton marriedButton, unmarriedButton, otherButton, maleButton, femaleButton, otherGenderButton;
    JButton nextSignUp;
    JTextField homeAddressText, phoneNumberText, emailText;
    private String firstName;
    private String lastName;
    private String nationality;
    private String region;
    private String city;
    private String pin;

    public SignUp2(String firstName, String lastName, String nationality, String region, String city, String pin) {
        super("APPLICATION FORM - SEQUEL");

        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.region = region;
        this.city = city;
        this.pin = pin;

        ImageIcon bankIcon = new ImageIcon("src/Images/bank.png");
        Image image = bankIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);
        JLabel bankLabel = new JLabel(icon);
        bankLabel.setBounds(45, 10, 120, 120);
        add(bankLabel);

        JLabel page1 = new JLabel("Page 2");
        page1.setBounds(380, 70, 600, 45);
        page1.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(page1);

        JLabel address = new JLabel("Address:");
        address.setBounds(100, 190, 150, 30);
        address.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(address);

        homeAddressText = new JTextField();
        homeAddressText.setBounds(300, 190, 300, 30);
        homeAddressText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(homeAddressText);

        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setBounds(100, 240, 150, 30);
        phoneNumberLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(phoneNumberLabel);

        phoneNumberText = new JTextField();
        phoneNumberText.setBounds(300, 240, 300, 30);
        phoneNumberText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(phoneNumberText);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(100, 290, 100, 30);
        emailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(emailLabel);

        emailText = new JTextField();
        emailText.setBounds(300, 290, 300, 30);
        emailText.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(emailText);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Raleway", Font.BOLD, 15));
        genderLabel.setBounds(100, 340, 100, 30);
        add(genderLabel);

        maleButton = new JRadioButton("Male");
        maleButton.setBounds(300, 340, 100, 30);
        maleButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        maleButton.setBackground(new Color(186, 35, 98));
        add(maleButton);
        femaleButton = new JRadioButton("Female");
        femaleButton.setBounds(450, 340, 100, 30);
        femaleButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        femaleButton.setBackground(new Color(186, 35, 98));
        add(femaleButton);
        otherGenderButton = new JRadioButton("Other");
        otherGenderButton.setBounds(600, 340, 100, 30);
        otherGenderButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
        otherGenderButton.setBackground(new Color(186, 35, 98));
        add(otherGenderButton);

        ButtonGroup genderButtons = new ButtonGroup();
        genderButtons.add(maleButton);
        genderButtons.add(femaleButton);
        genderButtons.add(otherGenderButton);

        JLabel maritalStatusLabel = new JLabel("Marital Status:");
        maritalStatusLabel.setFont(new Font("Raleway", Font.BOLD, 15));
        maritalStatusLabel.setBounds(100, 390, 200, 30);
        add(maritalStatusLabel);

        marriedButton = new JRadioButton("Married");
        marriedButton.setBounds(250, 390, 100, 30);
        marriedButton.setBackground(new Color(186, 35, 98));
        marriedButton.setFont(new Font("Raleway", Font.BOLD, 15));
        add(marriedButton);

        unmarriedButton = new JRadioButton("Unmarried");
        unmarriedButton.setBackground(new Color(186, 35, 98));
        unmarriedButton.setBounds(400, 390, 120, 30);
        unmarriedButton.setFont(new Font("Raleway", Font.BOLD, 15));
        add(unmarriedButton);

        otherButton = new JRadioButton("Other");
        otherButton.setBackground(new Color(186, 35, 98));
        otherButton.setBounds(550, 390, 100, 30);
        otherButton.setFont(new Font("Raleway", Font.BOLD, 15));
        add(otherButton);

        ButtonGroup maritalStatusButtons = new ButtonGroup();
        maritalStatusButtons.add(marriedButton);
        maritalStatusButtons.add(unmarriedButton);
        maritalStatusButtons.add(otherButton);

        nextSignUp = new JButton("Next");
        nextSignUp.setBounds(700, 720, 80, 30);
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextSignUp) {
            String homeAddress = homeAddressText.getText().trim();
            String phoneNumber = phoneNumberText.getText().trim();
            String email = emailText.getText().trim();

            String currentSelectedGender = "";
            if (maleButton.isSelected()) {
                currentSelectedGender = maleButton.getText();
            } else if (femaleButton.isSelected()) {
                currentSelectedGender = femaleButton.getText();
            } else if (otherGenderButton.isSelected()) {
                currentSelectedGender = otherGenderButton.getText();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a gender.");
                return;
            }

            String currentMaritalStatus = "";
            if (marriedButton.isSelected()) {
                currentMaritalStatus = marriedButton.getText();
            } else if (unmarriedButton.isSelected()) {
                currentMaritalStatus = unmarriedButton.getText();
            } else if (otherButton.isSelected()) {
                currentMaritalStatus = otherButton.getText();
            } else {
                JOptionPane.showMessageDialog(this, "Please select marital status.");
                return;
            }

            if (homeAddress.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields on this page.");
                return;
            }

            UserDAO dao = new UserDAO();

            if (!dao.validateEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format.");
                return;
            }
            if (dao.isEmailRegistered(email)) {
                JOptionPane.showMessageDialog(this, "Email already in use.");
                return;
            }
            if (!dao.validatePhone(phoneNumber)) {
                JOptionPane.showMessageDialog(this, "Invalid phone number.");
                return;
            }
            if (dao.isPhoneNumberRegistered(phoneNumber)) {
                JOptionPane.showMessageDialog(this, "Phone number already in use.");
                return;
            }
            String newCardNumber = generateRandomCardNumber();
            User newUser = new User(
                    firstName, lastName, nationality, region, city,
                    phoneNumber, email, currentSelectedGender, currentMaritalStatus, pin, homeAddress, newCardNumber
            );

            if (dao.insertNewUserFromSignUp(newUser)) {
                JOptionPane.showMessageDialog(this, "User registered successfully!");
                this.dispose();
                new SignUps.Login();
            } else {
                JOptionPane.showMessageDialog(this, "User registration failed. Please try again.");
            }
        }
    }

    /**
     * Generates a random 16-digit card number.
     *
     * @return A 16-digit string representing a card number.
     */
    private String generateRandomCardNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        // A simple way to ensure 16 digits, but NO LUHN ALGO
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}