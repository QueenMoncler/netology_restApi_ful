package com.example.user_service.model;

public class User {
    private int id;
    private String fullName;
    private String deliveryAddress;
    private String phoneNumber;
    private String email;

    public User() {}

    public User(int id, String fullName, String deliveryAddress, String phoneNumber, String email) {
        this.id = id;
        this.fullName = fullName;
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}