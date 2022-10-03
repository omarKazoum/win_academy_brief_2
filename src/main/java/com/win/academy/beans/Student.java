package com.win.academy.beans;

import com.win.academy.DataHolder;
import com.win.academy.utils.Pair;

import java.util.Date;

import static com.win.academy.utils.Constants.ROLE_STUDENT_ID;
import static com.win.academy.utils.Constants.ROLE_TEACHER_ID;

public class Student extends User{
    private int schoolClassId;
    private Date startStudyingDate;
    public Student(){
        super();
        DataHolder.getInstance().roleUser.add(new Pair<>(getId(), ROLE_STUDENT_ID));
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
