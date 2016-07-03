package com.nile.nile.model;

/**
 * Created by rieke on 02.07.16.
 */
public class NileAddress {
    private String street;
    private int number;
    private int zipcode;
    private String city;

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public int getZipcode() { return zipcode; }
    public void setZipcode(int zipcode) { this.zipcode = zipcode; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

}
