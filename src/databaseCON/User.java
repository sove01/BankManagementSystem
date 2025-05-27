
package databaseCON;
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


    public User(String firstName, String lastName, String nationality, String region, String city,
                String phoneNumber, String email, String gender, String maritalStatus, String pin, String homeAddress) {
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
    }

    // Getters for all properties
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getNationality() { return nationality; }
    public String getRegion() { return region; }
    public String getCity() { return city; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public String getGender() { return gender; }
    public String getMaritalStatus() { return maritalStatus; }
    public String getPin() { return pin; }
    public String getHomeAddress() { return homeAddress; }

    // You might want to add setters if you need to modify parts of the user later,
    // but for signup, the constructor is often sufficient.
}