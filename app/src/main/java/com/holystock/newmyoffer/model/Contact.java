package com.holystock.newmyoffer.model;

import java.util.ArrayList;

public class Contact {

    private String name;
    private ArrayList<String> phones;
    private String image;

    public Contact(
            String name,
            ArrayList<String> phones,
            String image
    ) {
        this.name = name;
        this.phones = phones;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public String getImage() {
        return image;
    }
}