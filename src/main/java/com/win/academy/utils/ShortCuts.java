package com.win.academy.utils;

import com.win.academy.DataHolder;
import com.win.academy.beans.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShortCuts {
    public static boolean doesConnectedUserHaveRole(int roleId){
        int connectedUserID=DataHolder.connectedUser.getId();
        return DataHolder.getInstance().roleUser.stream().anyMatch(p->p.left==connectedUserID&&p.right==roleId);

    }
    public static Subject getSubjectById(int subjectId){
        return DataHolder.getInstance().subjets.stream().filter(s->s.getId()==subjectId).findFirst().get();
    }
    public static String formatDate(Date date){
        //System.out.println(date.toString());
        return new SimpleDateFormat("dd MMMM YYYY").format(date);
    }
    public static SchoolClass getClassById(int classId){
        return DataHolder.getInstance().schoolClasses.stream().filter(c->c.getId()==classId).findFirst().get();
    }
    public static List<Student> getStudentsForTeacher(int teacherId){
        return DataHolder.getInstance().classTeacher.stream().filter(ct->ct.right==teacherId).map(ct->ct.left)
                .map(
                        classId->
                                DataHolder.getInstance().users.stream().filter(u->u instanceof Student && ((Student)u).getSchoolClassId()==classId)
                                        .collect(()-> new ArrayList<User>(),(con,item)->con.add(item),(c1,c2)->c1.addAll(c2)))
                .reduce(new ArrayList<User>(),(all, pre)->{all.addAll( pre);return all;}).stream().map(u->(Student)u).collect(()-> new ArrayList<Student>(),(con,item)->con.add(item),(c1,c2)->c1.addAll(c2));
    }
}
