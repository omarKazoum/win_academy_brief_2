package com.win.academy.beans;

import com.win.academy.DataHolder;

import java.io.Serializable;
import java.util.Date;

public class ExamGrade implements Serializable {
    private int id;
    private float examGrade;
    private int studentId;
    private int subjectId;
    private Date examDate;

    public ExamGrade( float examGrade, int studentId, int subjectId, Date examDate) {
        this.examGrade = examGrade;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.examDate = examDate;
        this.id= DataHolder.getInstance().getNewExamGradeId();
    }

    public int getId() {
        return id;
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
