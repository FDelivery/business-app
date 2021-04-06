package com.project.fdelivery;

public class Business {

    private String email, BusinessName, PhoneNumber1 ,PhoneNumber2, Address, Password;

    Business(String e, String p1, String p2, String a, String name,String password)
    {
        email = e;
        PhoneNumber1 = p1;
        PhoneNumber2 = p2;
        Address = a;
        BusinessName = name;
    }
    Business(String e, String p1, String a, String name, String password)
    {
        email = e;
        PhoneNumber1 = p1;
        Address = a;
        BusinessName = name;
    }

    public String getEmail() { return email; }
//In my opinion(sarah) you do not need it because in my opinion you should not allow the business / courier to change the email with which they registered
    public void setEmail(String email) { this.email = email; }

    public String getBusinessName() { return BusinessName; }

    public void setBusinessName(String businessName) { BusinessName = businessName; }

    public String getPhoneNumber1() { return PhoneNumber1; }

    public void setPhoneNumber1(String phoneNumber1) { PhoneNumber1 = phoneNumber1; }

    public String getPhoneNumber2() { return PhoneNumber2; }

    public void setPhoneNumber2(String phoneNumber2) { PhoneNumber2 = phoneNumber2; }

    public String getAddress() { return Address; }

    public void setAddress(String address) { Address = address; }

    public String getPassword() { return Password; }
    public void deletePassword(){ Password = "";}
}
