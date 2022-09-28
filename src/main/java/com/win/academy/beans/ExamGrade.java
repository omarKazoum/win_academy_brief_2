package com.win.academy.beans;

import java.io.Serializable;
import java.util.Date;

public class ExamGrade implements Serializable {
    private int id;
    private float examGrade;
    private int studentId;
    private int subjectId;
    private Date examDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getExamGrade() {
        return examGrade;
    }

    public void setExamGrade(float examGrade) {
        this.examGrade = examGrade;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }
}
