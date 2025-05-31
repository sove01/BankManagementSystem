package SignUps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * The SignUp class represents the first page of the new user application form.
 * It collects personal information and validates basic input before proceeding
 * to the next registration step.
 */
public class SignUp extends JFrame implements ActionListener {
    JButton nextSignUpButton;
    JTextField lastNameField, firstNameField, nationalityField, regionField, cityField, pinField; // Renamed for clarity

    // Generate random 2-digit number - represents application number
    String applicationNumber = String.format(" %02d", new Random().nextInt(100) + 1);

    /**
     * Constructs a new SignUp frame for the first page of the application.
     * Initializes the GUI components for personal information input.
     */
    public SignUp() {
        super("APPLICATION FORM - Page 1"); // Set window title

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

        // Application number label
        JLabel appNumberLabel = new JLabel("APPLICATION NO." + applicationNumber);
        appNumberLabel.setBounds(200, 30, 400, 40);
        appNumberLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        appNumberLabel.setForeground(new Color(30, 60, 90));
        add(appNumberLabel);

        // Page 1 indicator label
        JLabel page1Label = new JLabel("Page 1: Personal Details");
        page1Label.setBounds(200, 75, 400, 30);
        page1Label.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        page1Label.setForeground(new Color(50, 80, 120));
        add(page1Label);

        // title Personal Information
        JLabel personalInfoTitle = new JLabel("Personal Information");
        personalInfoTitle.setBounds(100, 150, 400, 35);
        personalInfoTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        personalInfoTitle.setForeground(new Color(0, 102, 102));
        add(personalInfoTitle);

        // First name label
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(100, 210, 150, 25);
        firstNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(firstNameLabel);

        // First name text field
        firstNameField = new JTextField();
        firstNameField.setBounds(250, 210, 300, 30); // Wider field
        firstNameField.setFont(new Font("Arial", Font.PLAIN, 16));
        add(firstNameField);

        // Last name label
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(100, 260, 150, 25);
        lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lastNameLabel);

        // Last name text field
        lastNameField = new JTextField();
        lastNameField.setBounds(250, 260, 300, 30);
        lastNameField.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lastNameField);

        // Nationality label
        JLabel nationalityLabel = new JLabel("Nationality:");
        nationalityLabel.setBounds(100, 360, 150, 25);
        nationalityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(nationalityLabel);

        // Nationality text field
        nationalityField = new JTextField();
        nationalityField.setBounds(250, 360, 300, 30);
        nationalityField.setFont(new Font("Arial", Font.PLAIN, 16));
        add(nationalityField);

        // Region label
        JLabel regionLabel = new JLabel("Region:");
        regionLabel.setBounds(100, 410, 150, 25);
        regionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(regionLabel);

        // Region text field
        regionField = new JTextField();
        regionField.setBounds(250, 410, 300, 30);
        regionField.setFont(new Font("Arial", Font.PLAIN, 16));
        add(regionField);

        // City label
        JLabel cityLabel = new JLabel("City:");
        cityLabel.setBounds(100, 460, 150, 25);
        cityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(cityLabel);

        // City text field
        cityField = new JTextField();
        cityField.setBounds(250, 460, 300, 30);
        cityField.setFont(new Font("Arial", Font.PLAIN, 16));
        add(cityField);

        // PIN label
        JLabel pinLabel = new JLabel("Create a 4-Digit PIN:");
        pinLabel.setBounds(100, 560, 200, 25);
        pinLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(pinLabel);

        // PIN text field
        pinField = new JTextField();
        pinField.setBounds(250, 560, 150, 30);
        pinField.setFont(new Font("Arial", Font.BOLD, 18));
        add(pinField);

        // Next button
        nextSignUpButton = new JButton("NEXT");
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
     * Handles action events generated by the 'NEXT' button.
     * Validates input fields and proceeds to SignUp2 if valid.
     *
     * @param e The ActionEvent generated by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextSignUpButton) {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String nationality = nationalityField.getText().trim();
            String region = regionField.getText().trim();
            String city = cityField.getText().trim();
            String pin = pinField.getText().trim();

            // check for required fields
            if (firstName.isEmpty() || lastName.isEmpty() || nationality.isEmpty() ||
                    region.isEmpty() || city.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields to proceed.");
                return;
            }

            // PIN validation - 4 digit
            if (!pin.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(this, "PIN must be a 4-digit number (e.g., 1234).");
                return;
            }

            // Proceed to the next sign-up page
            this.setVisible(false);
            new SignUp2(firstName, lastName, nationality, region, city, pin);
        }
    }

}