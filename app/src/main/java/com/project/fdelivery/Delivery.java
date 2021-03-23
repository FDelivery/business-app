package com.project.fdelivery;

public class Delivery 
{

    private String clientAddress, clientPhone, clientName, Note, time, date;
    public Delivery(String clientAddress, String clientPhone, String clientName, String clientNote, String time, String date) {
        this.clientAddress = clientAddress;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.time = time;
        this.Note = clientNote;
        this.date = date;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }
}
