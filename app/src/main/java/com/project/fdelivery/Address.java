package com.project.fdelivery;

public class Address {
    private String floor;
    private String number;
    private String apartment;
    private String entrance;
    private String city;
    private String street;



    public Address(String city, String street, String floor, String number, String apartment, String entrance) {
        this.city = city;
        this.street = street;
        this.floor = floor;
        this.number = number;
        this.apartment = apartment;
        this.entrance = entrance;
    }



    public Address(String city, String street, String number) {
        this.city = city;
        this.street = street;
        this.number = number;
    }





    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getFloor() {
        return floor;
    }

    public String getNumber() {
        return number;
    }

    public String getApartment() {
        return apartment;
    }

    public String getEntrance() {
        return entrance;
    }



}
