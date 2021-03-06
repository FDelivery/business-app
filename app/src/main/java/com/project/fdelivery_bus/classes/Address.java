package com.project.fdelivery_bus.classes;

public class Address {
    private String city,street,number,apartment,floor,entrance;


    public Address(String city, String street, String number) {
        this.city = city;
        this.street = street;
        this.number = number;
    }


    public void setFloor(String floor) {
        this.floor = floor;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
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
