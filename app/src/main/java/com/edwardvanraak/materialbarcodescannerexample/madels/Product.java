package com.edwardvanraak.materialbarcodescannerexample.madels;

public class Product {

    private int orderID;
    private String productName;
    private String customer;
    private  String shopperEmail;
    private  String shopperURL;
    private String description;
    private String date;
//    private URI photoURI; // last functional


    public Product() {
    }

    public Product(String customer, String date, String description, String productName, int orderID,
                   String shopperURL, String shopperEmail) {
        this.orderID = orderID;
        this.customer = customer;
        this.shopperEmail = shopperEmail;
        this.shopperURL = shopperURL;
        this.date = date;
        this.description = description;
        this.productName = productName;
    }


    public String getShopperEmail() {
        return shopperEmail;
    }

    public void setShopperEmail(String shopperEmail) {
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
        return productName;
    }

    public void setTitle(String productName) {
        this.productName = productName;
    }
}
