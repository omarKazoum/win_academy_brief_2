package com.win.academy.beans;

import java.util.Date;

public class Student extends User{
    private int schoolClassId;
    private Date startStudyingDate;
    public Student(){
    }

    public int getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(int schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    public Date getStartStudyingDate() {
        return startStudyingDate;
    }

    public void setStartStudyingDate(Date startStudyingDate) {
        this.startStudyingDate = startStudyingDate;
    }
}
