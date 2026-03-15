package com.Shop_Microservice.Shop.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private int quantity;
    private int productid;




    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private ShoppingCart shoppingCart;

    public CartItem() {}
    public CartItem(int quantity, int productid) {

        this.quantity = quantity;
        this.productid = productid;


    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getProductid() {
        return productid;
    }
    public void setProductid(int productid) {
        this.productid = productid;
    }
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

}
