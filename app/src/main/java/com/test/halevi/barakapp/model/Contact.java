package com.test.halevi.barakapp.model;

/**
 * Created by Barak on 24/08/2017.
 */

public class Contact {
    String name;
    String phone;
    String id;

    public Contact(String id, String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.id = id;
    }

    public Contact() {
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setId(String id) {
        this.id = id;
    }

}
