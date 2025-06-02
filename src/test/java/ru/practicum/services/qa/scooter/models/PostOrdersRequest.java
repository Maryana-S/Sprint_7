package ru.practicum.services.qa.scooter.models;

public class PostOrdersRequest {

    private String firstName;

    private String lastName;

    private String address;

    private String metroStation;

    private String phone;

    private Integer rentTime;

    private String deliveryDate;

    private String comment;

    private String[] color;

    public PostOrdersRequest(String[] color){
        firstName = "Звёздочка";
        lastName = "Баттерфляй";
        address = "Мьюни, Королевский дворец";
        metroStation = "4";
        phone = "+7 999 999 99 99";
        rentTime = 7;
        deliveryDate = "2025-07-06";
        comment = "На землю! К Марко <3";
        this.color = color;
    }

    public PostOrdersRequest() {
        firstName = "Звёздочка";
        lastName = "Баттерфляй";
        address = "Мьюни, Королевский дворец";
        metroStation = "4";
        phone = "+7 999 999 99 99";
        rentTime = 7;
        deliveryDate = "2025-07-06";
        comment = "На землю! К Марко <3";
        color = new String[]{"BLACK"};
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRentTime() {
        return rentTime;
    }

    public void setRentTime(Integer rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String[] getColor() {
        return color;
    }

    public void setColor(String[] color) {
        this.color = color;
    }

}
