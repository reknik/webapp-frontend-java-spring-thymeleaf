package com.reknik.webAppDemoFrontEnd.entity;


import java.io.Serializable;

public class Contact implements Serializable {

    private int id;

    private String phone;

    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
