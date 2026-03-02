package uk.co.allica.customer.api;

public class CustomerResponse {

    private final String firstName;

    private final String lastName;

    private final String email;


    public CustomerResponse(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

}