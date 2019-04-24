package com.edwardvanraak.materialbarcodescannerexample.madels;

public class Product {

    private String customer;
    private String date;
    private String description;
    private String title;

    public Product() {
    }

    public Product(String customer, String date, String description, String title) {
        this.customer = customer;
        this.date = date;
        this.description = description;
        this.title = title;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDoc(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
