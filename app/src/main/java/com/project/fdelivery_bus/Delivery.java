package com.project.fdelivery_bus;

public class Delivery
{

    private Address destAddress;
    private String clientPhone;
    private String clientName;
    private String Note;
    private String Time;
    private String deliveredDate;
    private String pickedDate;
    private double price;

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    public Delivery(Address clientAddress, String clientPhone, String clientName, String clientNote, String time, String date) {
        this.destAddress= clientAddress;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.Time = time;
        this.Note = clientNote;
        this.deliveredDate = date;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public Address getClientAddress() {
        return destAddress;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDate() {
        return deliveredDate;
    }

    public void setDate(String date) {
        this.deliveredDate = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public void setClientAddress(Address clientAddress) {
        this.destAddress = clientAddress;
    }
}
