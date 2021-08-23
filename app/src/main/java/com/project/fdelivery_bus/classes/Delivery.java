package com.project.fdelivery_bus.classes;

public class Delivery
{

    private Address destAddress;
    private String clientPhone;
    private String clientName;
    private String Note;
    private String Time;
    private String deliveredDate;
    private double price;
    private String id;
    private String status;



    public String getStatus() {
        return status;
    }



    public void setPrice(double price) {
        this.price = price;
    }



    @Override
    public String toString() {
        return  " client name: "+clientName+ "\n client phone: "+clientPhone+"\ncity:"+destAddress.getCity()+
                "\n street: "+destAddress.getStreet()+"\n number: "+destAddress.getNumber()+"\n price: "+price+ "\n note: "+Note+"\n status: "+status+"\n deliveredDate: "+deliveredDate;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Delivery(Address clientAddress, String clientPhone, String clientName, String clientNote, String time, String date,String status,double price) {
        this.destAddress= clientAddress;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.Time = time;
        this.Note = clientNote;
        this.deliveredDate = date;
        this.status=status;
        this.price=price;
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
