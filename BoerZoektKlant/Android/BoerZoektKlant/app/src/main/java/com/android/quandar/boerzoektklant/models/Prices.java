package com.android.quandar.boerzoektklant.models;

import java.io.Serializable;

class Prices implements Serializable {
    private int Id;
    private String name;
    private double price;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}