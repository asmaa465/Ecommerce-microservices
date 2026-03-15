package com.Shop_Microservice.Shop.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    private int userId;

    @OneToMany(mappedBy="order")
    @JsonManagedReference
    private List<OrderItem> orderItemList;

    private String status;

    private double totalPrice;

    public Order() {}
    public Order(int userId, String status, double totalPrice, List<OrderItem> orderItemList) {
        this.userId = userId;
        this.status = status;
        this.totalPrice = totalPrice;
        this.orderItemList = orderItemList;

    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setOrderItems(OrderItem orderItem) {
        this.orderItemList.add(orderItem);
    }

}
