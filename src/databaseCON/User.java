package databaseCON;

/**
 * The User class is a simple Plain Old Java Object (POJO) that represents a user's
 * personal and account information. It serves as a data model for transferring
 * user details between different parts of the application, such as the GUI
 * and the database access layer.
 */
public class User {
    private String firstName;
    private String lastName;
    private String nationality;
    private String region;
    private String city;
    private String phoneNumber;
    private String email;
    private String gender;
    private String maritalStatus;
    private String pin;
    private String homeAddress;
    private String cardNumber;


    /**
     * Constructs a new User object with comprehensive personal and account details.
     *
     * @param firstName     The first name of the user.
     * @param lastName      The last name of the user.
     * @param nationality   The nationality of the user.
     * @param region        The region of the user's residence.
     * @param city          The city of the user's residence.
     * @param phoneNumber   The phone number of the user.
     * @param email         The email address of the user.
     * @param gender        The gender of the user.
     * @param maritalStatus The marital status of the user.
     * @param pin           The personal identification number (PIN) for the user's account.
     * @param homeAddress   The home address of the user.
     * @param cardNumber    The unique card number associated with the user's account.
     */
    public User(String firstName, String lastName, String nationality, String region, String city,
                String phoneNumber, String email, String gender, String maritalStatus, String pin, String homeAddress, String cardNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.region = region;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.pin = pin;
        this.homeAddress = homeAddress;
        this.cardNumber = cardNumber;
    }

    /**
     * Returns the first name of the user.
     * @return The first name.
     */
    public String getFirstName() { return firstName; }

    /**
     * Returns the last name of the user.
     * @return The last name.
     */
    public String getLastName() { return lastName; }

    /**
     * Returns the nationality of the user.
     * @return The nationality.
     */
    public String getNationality() { return nationality; }

    /**
     * Returns the region of the user's residence.
     * @return The region.
     */
    public String getRegion() { return region; }

    /**
     * Returns the city of the user's residence.
     * @return The city.
     */
    public String getCity() { return city; }

    /**
     * Returns the phone number of the user.
     * @return The phone number.
     */
    public String getPhoneNumber() { return phoneNumber; }

    /**
     * Returns the email address of the user.
     * @return The email address.
     */
    public String getEmail() { return email; }

    /**
     * Returns the gender of the user.
     * @return The gender.
     */
    public String getGender() { return gender; }

    /**
     * Returns the marital status of the user.
     * @return The marital status.
     */
    public String getMaritalStatus() { return maritalStatus; }

    /**
     * Returns the personal identification number (PIN) of the user's account.
     * @return The PIN.
     */
    public String getPin() { return pin; }

    /**
     * Returns the home address of the user.
     * @return The home address.
     */
    public String getHomeAddress() { return homeAddress; }

    /**
     * Returns the unique card number associated with the user's account.
     * @return The card number.
     */
    public String getCardNumber() { return cardNumber; }
}