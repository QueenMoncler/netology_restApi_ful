package com.example.bff_service.model;
public class OrderItem {
    private String productName;
    private Integer quantity;
    private Double price;

    public OrderItem() {}

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}