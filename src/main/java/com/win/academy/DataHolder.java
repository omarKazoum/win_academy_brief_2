package com.win.academy;

import com.win.academy.beans.*;
import com.win.academy.utils.Pair;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class DataHolder implements Serializable {
    //normal tables
    public List<User> users;
    public Set<Pair<Integer,Integer>> userRole;
    public Set<Adress> adresses;
    public Set<City> cities;
    public Set<Department> departments;
    public List<ExamGrade> examGrades;
    public Set<Role> roles;
    public List<Room> rooms;
    public List<School> schools;
    public List<SchoolClass> schoolClasses;
    public List<Subject> subjets;
    //many-to-many tables
    public List<Pair<Integer,Integer>> roleUser;
    public List<Pair<Integer,Integer>> classSubject;
    public List<Pair<Integer,Integer>> classTeacher;
}
