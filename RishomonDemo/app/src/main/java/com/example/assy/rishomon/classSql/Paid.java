package com.example.assy.rishomon.classSql;

/**
 * Created by assy on 28/11/2015.
 */
public class Paid {

    int id;
    String name;
    String date;
    String kod;
    float price;

    public Paid(int id, String name, String date, String kod, float price) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.kod = kod;
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

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
