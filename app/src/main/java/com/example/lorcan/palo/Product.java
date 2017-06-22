package com.example.lorcan.palo;

/**
 * Created by lorcan on 22.06.17.
 */

public class Product {

    private  String name;
    private int qty;
    private int price;

    public Product() {
    }

    public Product(String name, int qty, int price) {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
