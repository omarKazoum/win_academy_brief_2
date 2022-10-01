package com.win.academy.utils;

import com.win.academy.DataHolder;
import com.win.academy.beans.Subject;

public class ShortCuts {
    public static boolean doesConnectedUserHaveRole(int roleId){
        int connectedUserID=DataHolder.connectedUser.getId();
        return DataHolder.getInstance().roleUser.stream().anyMatch(p->p.left==connectedUserID&&p.right==roleId);

    }
    public static Subject getSubjectById(int subjectId){
        return DataHolder.getInstance().subjets.stream().filter(s->s.getId()==subjectId).findFirst().get();
    }
}
