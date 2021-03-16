package com.project.fdelivery;

public class Business {

    String email;
    String BusinessName;
    String PhoneNumber1;
    String PhoneNumber2;
    String Address;

    Business(String e, String p1, String p2, String a, String name)
    {
        email = e;
        PhoneNumber1 = p1;
        PhoneNumber2 = p2;
        Address = a;
        BusinessName = name;
    }
    Business(String e, String p1, String a, String name)
    {
        email = e;
        PhoneNumber1 = p1;
        Address = a;
        BusinessName = name;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getBusinessName() { return BusinessName; }

    public void setBusinessName(String businessName) { BusinessName = businessName; }

    public String getPhoneNumber1() { return PhoneNumber1; }

    public void setPhoneNumber1(String phoneNumber1) { PhoneNumber1 = phoneNumber1; }

    public String getPhoneNumber2() { return PhoneNumber2; }

    public void setPhoneNumber2(String phoneNumber2) { PhoneNumber2 = phoneNumber2; }

    public String getAddress() { return Address; }

    public void setAddress(String address) { Address = address; }




}
