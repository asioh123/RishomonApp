package com.example.assy.rishomon.classSql;

/**
 * Created by assy on 09/11/2015.
 */
public class Item {

    int id;
    String name;
    String date;
    String item;
    float price;

    public Item()
    {

    }

    public Item(int id, String name, String date,String item, float price) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.item = item;
        this.price = price;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
