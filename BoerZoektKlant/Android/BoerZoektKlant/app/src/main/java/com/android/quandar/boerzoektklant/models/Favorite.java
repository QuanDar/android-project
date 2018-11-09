package com.android.quandar.boerzoektklant.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorite_table")
public class Favorite {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private float rating;

    public Favorite(String title, String description, float rating) {
        this.title = title;
        this.description = description;
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getRating() {
        return rating;
    }
}