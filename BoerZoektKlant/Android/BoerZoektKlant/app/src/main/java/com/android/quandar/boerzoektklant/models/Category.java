package com.android.quandar.boerzoektklant.models;

import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    private String name;

    public Category(int id, String name){
        this.setId(id);
        this.setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
