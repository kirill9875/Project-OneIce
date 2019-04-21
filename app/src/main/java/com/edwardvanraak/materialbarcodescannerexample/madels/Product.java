package com.edwardvanraak.materialbarcodescannerexample.madels;

public class Product {

    private String customer;
    private String date;
    private String doc;
    private String title;

    public Product() {
    }

    public Product(String customer, String date, String doc, String title) {
        this.customer = customer;
        this.date = date;
        this.doc = doc;
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

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
