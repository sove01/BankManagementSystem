package SignUps;

import databaseCON.User;
import databaseCON.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * The SignUp2 class represents the second page of the new user application form.
 * It collects additional personal details such as address, phone, email, gender,
 * and marital status, then registers the user in the database.
 */
public class SignUp2 extends JFrame implements ActionListener {
    JRadioButton marriedButton, unmarriedButton, otherButton, maleButton, femaleButton, otherGenderButton;
    JButton nextSignUpButton;
    JTextField homeAddressField, phoneNumberField, emailField; // Renamed for clarity

    // data passed from first signup
    private String firstName;
    private String lastName;
    private String nationality;
    private String region;
    private String city;
    private String pin;

    /**
     * Constructs a new SignUp2 frame for the second page of the application.
     * Initializes the GUI components for additional personal details and handles user registration.
     *
     * @param firstName The first name from SignUp page 1.
     * @param lastName The last name from SignUp page 1.
     * @param nationality The nationality from SignUp page 1.
     * @param region The region from SignUp page 1.
     * @param city The city from SignUp page 1.
     * @param pin The PIN from SignUp page 1.
     */
    public SignUp2(String firstName, String lastName, String nationality, String region, String city, String pin) {
        super("APPLICATION FORM - Page 2");

        // Initialize fields with data from the previous page
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.region = region;
        this.city = city;
        this.pin = pin;

        // Main frame setup
        getContentPane().setBackground(new Color(220, 230, 240));
        setLayout(null);
        setSize(850, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Bank icon label
        ImageIcon bankIcon = new ImageIcon("src/Images/bank.png");
        Image image = bankIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(image);
        JLabel bankLabel = new JLabel(icon);
        bankLabel.setBounds(60, 20, 100, 100);
        add(bankLabel);

        // Page 2 indicator label
        JLabel page2Label = new JLabel("Page 2: Contact & Other Details");
        page2Label.setBounds(200, 75, 400, 30);
        page2Label.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        page2Label.setForeground(new Color(50, 80, 120));
        add(page2Label);

        // Section title: Contact Information
        JLabel contactInfoTitle = new JLabel("Contact Information");
        contactInfoTitle.setBounds(100, 150, 400, 35);
        contactInfoTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        contactInfoTitle.setForeground(new Color(0, 102, 102));
        add(contactInfoTitle);

        // Address label
        JLabel addressLabel = new JLabel("Home Address:");
        addressLabel.setBounds(100, 210, 150, 25);
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(addressLabel);

        // Address text field
        homeAddressField = new JTextField();
        homeAddressField.setBounds(250, 210, 400, 30);
        homeAddressField.setFont(new Font("Arial", Font.PLAIN, 16));
        add(homeAddressField);

        // Phone Number label
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberLabel.setBounds(100, 260, 150, 25);
        phoneNumberLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(phoneNumberLabel);

        // Phone Number text field
        phoneNumberField = new JTextField();
        phoneNumberField.setBounds(250, 260, 300, 30);
        phoneNumberField.setFont(new Font("Arial", Font.PLAIN, 16));
        add(phoneNumberField);

        // Email label
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setBounds(100, 310, 150, 25);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(emailLabel);

        // Email text field
        emailField = new JTextField();
        emailField.setBounds(250, 310, 300, 30);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        add(emailField);

        // Section title: Other Details
        JLabel otherDetailsTitle = new JLabel("Other Details");
        otherDetailsTitle.setBounds(100, 380, 400, 35);
        otherDetailsTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        otherDetailsTitle.setForeground(new Color(0, 102, 102));
        add(otherDetailsTitle);

        // Gender label
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        genderLabel.setBounds(100, 440, 100, 25);
        add(genderLabel);

        // Gender radio buttons
        maleButton = new JRadioButton("Male");
        maleButton.setBounds(250, 440, 100, 25);
        maleButton.setFont(new Font("Arial", Font.PLAIN, 16));
        maleButton.setBackground(new Color(220, 230, 240));
        add(maleButton);

        femaleButton = new JRadioButton("Female");
        femaleButton.setBounds(370, 440, 100, 25);
        femaleButton.setFont(new Font("Arial", Font.PLAIN, 16));
        femaleButton.setBackground(new Color(220, 230, 240));
        add(femaleButton);

        otherGenderButton = new JRadioButton("Other");
        otherGenderButton.setBounds(490, 440, 150, 25);
        otherGenderButton.setFont(new Font("Arial", Font.PLAIN, 16));
        otherGenderButton.setBackground(new Color(220, 230, 240));
        add(otherGenderButton);

        ButtonGroup genderButtons = new ButtonGroup();
        genderButtons.add(maleButton);
        genderButtons.add(femaleButton);
        genderButtons.add(otherGenderButton);

        // Marital Status label
        JLabel maritalStatusLabel = new JLabel("Marital Status:");
        maritalStatusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        maritalStatusLabel.setBounds(100, 490, 150, 25);
        add(maritalStatusLabel);

        // Marital Status radio buttons
        marriedButton = new JRadioButton("Married");
        marriedButton.setBounds(250, 490, 100, 25);
        marriedButton.setBackground(new Color(220, 230, 240));
        marriedButton.setFont(new Font("Arial", Font.PLAIN, 16));
        add(marriedButton);

        unmarriedButton = new JRadioButton("Unmarried");
        unmarriedButton.setBackground(new Color(220, 230, 240));
        unmarriedButton.setBounds(370, 490, 120, 25);
        unmarriedButton.setFont(new Font("Arial", Font.PLAIN, 16));
        add(unmarriedButton);

        otherButton = new JRadioButton("Other");
        otherButton.setBackground(new Color(220, 230, 240));
        otherButton.setBounds(490, 490, 100, 25);
        otherButton.setFont(new Font("Arial", Font.PLAIN, 16));
        add(otherButton);

        ButtonGroup maritalStatusButtons = new ButtonGroup();
        maritalStatusButtons.add(marriedButton);
        maritalStatusButtons.add(unmarriedButton);
        maritalStatusButtons.add(otherButton);

        // Next button
        nextSignUpButton = new JButton("REGISTER");
        nextSignUpButton.setBounds(650, 680, 150, 45);
        nextSignUpButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nextSignUpButton.setBackground(new Color(0, 102, 102));
        nextSignUpButton.setForeground(Color.WHITE);
        nextSignUpButton.setFocusPainted(false);
        nextSignUpButton.addActionListener(this);
        add(nextSignUpButton);

        setVisible(true);
    }

    /**
     * Handles action events generated by the 'REGISTER' button.
     * Validates input fields, generates a card number, and registers the user in the database.
     *
     * @param e The ActionEvent generated by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextSignUpButton) {
            String homeAddress = homeAddressField.getText().trim();
            String phoneNumber = phoneNumberField.getText().trim();
            String email = emailField.getText().trim();

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
                JOptionPane.showMessageDialog(this, "Invalid email format. Please use a valid email address.");
                return;
            }
            if (dao.isEmailRegistered(email)) {
                JOptionPane.showMessageDialog(this, "Email already in use. Please use a different email.");
                return;
            }
            if (!dao.validatePhone(phoneNumber)) {
                JOptionPane.showMessageDialog(this, "Invalid phone number format. Expected: +420XXXXXXXXX.");
                return;
            }
            if (dao.isPhoneNumberRegistered(phoneNumber)) {
                JOptionPane.showMessageDialog(this, "Phone number already in use. Please use a different number.");
                return;
            }

            // Generate a unique card number
            String newCardNumber = generateRandomCardNumber();

            // Create a new User object with all collected data
            User newUser = new User(
                    firstName, lastName, nationality, region, city,
                    phoneNumber, email, currentSelectedGender, currentMaritalStatus, pin, homeAddress, newCardNumber
            );

            // Insert the new user into the database
            if (dao.insertNewUserFromSignUp(newUser)) {
                JOptionPane.showMessageDialog(this, "User registered successfully! Your new Card Number is: " + newCardNumber + "\nPlease keep it safe for login.");
                this.dispose();
                new SignUps.Login();
            } else {
                JOptionPane.showMessageDialog(this, "User registration failed. Please try again.");
            }
        }
    }

    /**
     * Generates a random 16-digit card number.
     * This is a simplified generation. In a real banking system, card numbers
     * follow specific algorithms (like Luhn algorithm) and are checked for uniqueness.
     *
     * @return A 16-digit string representing a generated card number.
     */
    private String generateRandomCardNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}