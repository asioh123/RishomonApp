package com.example.assy.rishomon.classSql;

/**
 * Created by assy on 04/11/2015.
 */
public class Person {

    int id;
    String firstName;
    String lastName;
    String phone;
    String adress;
    int total;

    public  Person()
    {

    }

    public Person(int id,String firstName,String lastName, String phone, String adress) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.adress = adress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
