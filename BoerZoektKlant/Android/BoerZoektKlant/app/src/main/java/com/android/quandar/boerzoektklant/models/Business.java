package com.android.quandar.boerzoektklant.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Business implements Serializable {
    private int id;
    private String title;
    private String description;
    private String excerpt;
    private String imageUrl;
    private int rating;
    private String phoneNumber;
    private String postalCode;
    private String address;
    private String houseNumber;
    private List<Prices> prices;
    private List<Category> categories;

    public Business() {
    }

    public Business(int id, String title, String description, String excerpt, String imageUrl, int rating, String phoneNumber, String postalCode, String address, String houseNumber, List<Prices> prices, List<Category> categories) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.excerpt = excerpt;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.address = address;
        this.houseNumber = houseNumber;
        this.prices = prices;
        this.categories = categories;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Prices> getPrices() {
        return prices;
    }

    public void setPrices(List<Prices> prices) {
        this.prices = prices;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Business fromJson(JSONObject jsonObject) {
        Business b = new Business();
        // Deserialize json into object fields
        try {
            b.id = jsonObject.getInt("id");
            b.title = jsonObject.getString("name");
            b.description = jsonObject.getString("display_phone");
            b.excerpt = jsonObject.getString("image_url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }

    public static ArrayList<Business> fromJson(JSONArray jsonArray) {
        JSONObject businessJson;
        ArrayList<Business> businesses = new ArrayList<Business>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i=0; i < jsonArray.length(); i++) {
            try {
                businessJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Business business = Business.fromJson(businessJson);
            if (business != null) {
                businesses.add(business);
            }
        }

        return businesses;
    }
}





