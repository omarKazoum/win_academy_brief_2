package com.win.academy.beans;

import com.win.academy.DataHolder;
import com.win.academy.utils.Pair;

import java.util.Date;

import static com.win.academy.utils.Constants.ROLE_STUDENT_ID;
import static com.win.academy.utils.Constants.ROLE_TEACHER_ID;

public class Teacher extends User{
    private Date startWorkDate;
    private int subjectId;
    private int departmentId;

    public Teacher() {
        super();
        DataHolder.getInstance().roleUser.add(new Pair<>(getId(), ROLE_TEACHER_ID));
    }


    public Date getStartWorkDate() {
        return startWorkDate;
    }

    public void setStartWorkDate(Date startWorkDate) {
        this.startWorkDate = startWorkDate;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

}
