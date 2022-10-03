package com.win.academy.beans;

import com.win.academy.DataHolder;
import com.win.academy.utils.Pair;

import static com.win.academy.utils.Constants.ROLE_ADMIN_ID;
import static com.win.academy.utils.Constants.ROLE_TEACHER_ID;

public class Admin extends User{
    public Admin(){
        super();
        DataHolder.getInstance().roleUser.add(new Pair<>(getId(), ROLE_ADMIN_ID));

    }
}
