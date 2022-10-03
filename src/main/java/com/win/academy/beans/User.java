package com.win.academy.beans;

import com.win.academy.DataHolder;

import java.io.Serializable;

public class User implements Serializable {
    protected int id;
    protected String firstName,
            lastName,
            phone,
            email,
            password;
    public User(){
        id=DataHolder.getInstance().getNewUSerId();
    }
    public int getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
