package com.win.academy.beans;

import java.io.Serializable;

public class City implements Serializable {
    private int id;
    private String name;

    public City() {
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
}
