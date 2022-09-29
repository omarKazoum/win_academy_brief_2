package com.win.academy.utils;

import com.win.academy.DataHolder;

public class ShortCuts {
    public static boolean doesConnectedUserHaveRole(int roleId){
        int connectedUserID=DataHolder.connectedUser.getId();
        return DataHolder.getInstance().roleUser.stream().anyMatch(p->p.left==connectedUserID&&p.right==Constants.ROLE_ADMIN_ID);

    }
}
