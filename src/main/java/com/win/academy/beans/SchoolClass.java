package com.win.academy.beans;

import java.io.Serializable;

public class SchoolClass implements Serializable {
    private int id;

    public SchoolClass() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
