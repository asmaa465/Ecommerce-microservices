package com.Wallet_Microservice.Wallet.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity (name="Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double amount;
    private LocalDateTime date;
    private String type;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    @JsonBackReference
    private Wallet wallet;

    public Transaction() {
    }
    public Transaction( double amount,  String type) {
        this.amount = amount;
        this.type = type;
        this.date = LocalDateTime.now();
    }
    public Transaction(double amount, String type, Wallet wallet) {
        this.amount = amount;
        this.type = type;
        this.wallet = wallet;
        this.date = LocalDateTime.now();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public int getId() {
        return id;
    }
}
