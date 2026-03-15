package com.Inventory_Microservice.Inventory.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;



@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inventory_id;

    private String name;



    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    Product product;

    private double quantity;

    public Inventory() {};
    public Inventory(String name, double quantity, Product product) {
        this.name = name;
        this.quantity = quantity;

    }
    public Inventory(String name) {
        this.name = name;
    }
    public int getId() {
        return inventory_id;
    }
    public void setId(int id) {
        this.inventory_id= id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
