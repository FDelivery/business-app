package com.project.fdelivery_bus.classes;

public class Business {

    private String email;
    private String businessName;
    private String primaryPhone;
    private String secondaryPhone;
    private String password;
    private String id;
    private String token;
    private String role = "BUSINESS";
    private Address address;





    public Business(String e, String p1, String p2, Address a, String bn, String pass)
    {
        email = e;
        primaryPhone = p1;
        secondaryPhone = p2;
        address = a;
        businessName = bn;
        password = pass;
    }


    public Business(String e, String p1, Address a, String bn, String pass)
    {
        email = e;
        primaryPhone = p1;
        address = a;
        businessName = bn;
        password = pass;
    }


    public Address getAddress() {
        return address;
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

    public String getEmail() { return email; }

    public String getBusinessName() { return businessName; }

    public String getPrimaryPhone() { return primaryPhone; }

    public String getPhoneNumber2() { return secondaryPhone; }

    public String getPassword() { return password; }

    public void setId(String id) {
        this.id =id;
    }
}
