package com.edwardvanraak.materialbarcodescannerexample.madels;

import java.io.Serializable;
import java.net.URI;

public class Product {

    private int orderID;
    private String title; //productName
    private String customer; //shopperName
    private  String shopperEmail;
    private  String shopperURL;
    private String description;
    private String date;
//    private URI photoURI; // last functional


    public Product() {
    }

    public Product(String customer, String date, String description, String title, int orderID,
                   String shopperURL, String shopperEmail) {
        this.orderID = orderID;
        this.customer = customer;
        this.shopperEmail = shopperEmail;
        this.shopperURL = shopperURL;
        this.date = date;
        this.description = description;
        this.title = title;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getShopperEmail() {
        return shopperEmail;
    }

    public void setShopperEmail(String customer) {
        this.shopperEmail = shopperEmail;
    }

    public String getShopperURL() {
        return shopperURL;
    }

    public void setShopperURL(String shopperURL) {
        this.shopperURL = shopperURL;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
