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

    public Business(Business b) {
        this.email = b.email;
        this.businessName = b.businessName;
        this.primaryPhone = b.primaryPhone;
        if(!b.secondaryPhone.isEmpty()) {
            this.secondaryPhone = b.secondaryPhone;
        }
        this.password = b.password;
        this.role = b.role;
        this.id=b.id;

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


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBusinessName() { return businessName; }

    public void setBusinessName(String businessName) { businessName = businessName; }

    public String getPrimaryPhone() { return primaryPhone; }

    public void setPrimaryPhone(String phone) { primaryPhone = phone; }

    public String getPhoneNumber2() { return secondaryPhone; }

    public void setPhoneNumber2(String phoneNumber2) { secondaryPhone = phoneNumber2; }


    public String getPassword() { return password; }


    public void setId(String id) {
        this.id =id;
    }
}
