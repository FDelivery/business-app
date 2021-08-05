package com.project.fdelivery_bus;

public class Business {

    private String email;
    private String businessName;
    private String primaryPhone;
    private String secondaryPhone;
    private String password;
    private String firstName;
    private String lastName;
    private String id;
    private String token;
    private String role = "BUSINESS";
    private Address address;

    public Business(Business b) {
      //  this.email = b.email;
       // this.businessName = b.businessName;
      //  this.primaryPhone = b.primaryPhone;
     //   this.secondaryPhone = b.secondaryPhone;
       // this.password = b.password;
        this.firstName = b.firstName;
        this.lastName = b.lastName;
      //  this.role = b.role;

    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public String getRole() {
        return role;
    }




    public String getId() {
        return id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    Business(String e, String p1, String p2, Address a, String bn, String fn, String ln, String pass)
    {
        email = e;
        primaryPhone = p1;
        secondaryPhone = p2;
        address = a;
        businessName = bn;
        firstName=fn;
        lastName=ln;
        password = pass;
    }


    Business(String e, String p1, Address a, String bn, String fn, String ln, String pass)
    {
        email = e;
        primaryPhone = p1;
        address = a;
        businessName = bn;
        firstName=fn;
        lastName=ln;
        password = pass;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }


    public String getEmail() { return email; }
//In my opinion(sarah) you do not need it because in my opinion you should not allow the business / courier to change the email with which they registered
    public void setEmail(String email) { this.email = email; }

    public String getBusinessName() { return businessName; }

    public void setBusinessName(String businessName) { businessName = businessName; }

    public String getPrimaryPhone() { return primaryPhone; }

    public void setPrimaryPhone(String phone) { primaryPhone = phone; }

    public String getPhoneNumber2() { return secondaryPhone; }

    public void setPhoneNumber2(String phoneNumber2) { secondaryPhone = phoneNumber2; }


    public String getPassword() { return password; }
    public void deletePassword(){ password = "";}

    public void setId(String id) {
        this.id =id;
    }
}