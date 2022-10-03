package com.win.academy;

import com.win.academy.beans.*;
import com.win.academy.utils.Constants;
import com.win.academy.utils.Pair;

import java.io.*;
import java.util.*;

public class DataHolder implements Serializable {
    private int currentUserId;
    private static DataHolder instance;
    public static User connectedUser=null;
    public static final String fileParentFolder="C:\\test\\";
    private static final String filePath="C:\\test\\data.ser";
    public static DataHolder getInstance(){
        if(DataHolder.instance==null) {
            if(!loadDataFromFile()){
                instance=new DataHolder();
                instance.loadDummyData();
            };
        }

        return instance;
    }
    //normal tables
    public List<User> users;
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

    private DataHolder() {
        users=new ArrayList<>();
        adresses=new HashSet<>();
        cities=new HashSet<>();
        departments=new HashSet<>();
        examGrades=new ArrayList<>();
        roles=new HashSet<>();
        rooms=new ArrayList<>();
        schools=new ArrayList<>();
        schoolClasses=new ArrayList<>();
        subjets=new ArrayList<>();
        //many-to-many tables
        roleUser=new ArrayList<>();
        classSubject=new ArrayList<>();
        classTeacher=new ArrayList<>();
        currentUserId=1;
    }

    private static boolean loadDataFromFile(){
        if(!new File(filePath).exists()) {
            System.out.println("no previous data found!");
            return false;
        }
        try{
            System.out.println("reading object from file");
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream(filePath));
            instance= (DataHolder) ois.readObject();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.exit(100);
            return false;
        }
    }
    public void saveDataToFile(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(instance);
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(0);
        }
    }
    private void loadDummyData(){
        //adding roles
        this.roles.add(new Role(Constants.ROLE_ADMIN_ID,"admin"));
        this.roles.add(new Role(Constants.ROLE_STUDENT_ID,"student"));
        this.roles.add(new Role(Constants.ROLE_TEACHER_ID,"teacher"));
        this.roles.add(new Role(Constants.ROLE_DEPARTMENT_RESPONSIBLE_ID,"department responsible"));

        //adding users each per role
        //admin users
        User admin=new Admin();
        admin.setEmail("admin@gmail.com");
        admin.setFirstName("omar");
        admin.setLastName("kazoum");
        admin.setPassword("pass");
        this.users.add(admin);
        //adding teachers
        Teacher teacherAhmed=new Teacher();
        teacherAhmed.setEmail("bouchra@teacher.com");
        teacherAhmed.setFirstName("bouchra");
        teacherAhmed.setLastName("marzak");
        teacherAhmed.setPassword("pass");
        teacherAhmed.setDepartmentId(1);
        teacherAhmed.setStartWorkDate(new Date(2021-1900,9,19));
        teacherAhmed.setSubjectId(Constants.SUBJECT_JAVA_ID);
        this.roleUser.add(new Pair<>(teacherAhmed.getId(),Constants.ROLE_DEPARTMENT_RESPONSIBLE_ID));

        this.users.add(teacherAhmed);

        Teacher teacherBouchra=new Teacher();
        teacherBouchra.setEmail("ahmed@teacher.com");
        teacherBouchra.setFirstName("ahmed");
        teacherBouchra.setLastName("errafie");
        teacherBouchra.setPassword("pass");
        teacherBouchra.setDepartmentId(1);
        teacherBouchra.setStartWorkDate(new Date(2021-1900,9,19));
        teacherBouchra.setSubjectId(Constants.SUBJECT_JS_ID);
        this.users.add(teacherBouchra);

        //students
        Student studentIbrahim=new Student();
        studentIbrahim.setEmail("student@gmail.com");
        studentIbrahim.setFirstName("ibrahim");
        studentIbrahim.setLastName("essydeq");
        studentIbrahim.setPassword("pass");
        studentIbrahim.setSchoolClassId(Constants.SCHOOL_CLASS_JAVA_ID);
        studentIbrahim.setStartStudyingDate(new Date(2021-1900,9,19));
        this.users.add(studentIbrahim);

        Student studentKhalil=new Student();
        studentKhalil.setEmail("student2@gmail.com");
        studentKhalil.setFirstName("khalil");
        studentKhalil.setLastName("elkadih");
        studentKhalil.setPassword("pass");
        studentKhalil.setSchoolClassId(Constants.SCHOOL_CLASS_JAVA_ID);
        studentKhalil.setStartStudyingDate(new Date(2021-1900,9,19));
        this.users.add(studentKhalil);

         Student studentAgra=new Student();
        studentAgra.setEmail("student3@gmail.com");
        studentAgra.setFirstName("abderrahim");
        studentAgra.setLastName("agra");
        studentAgra.setPassword("pass");
        studentAgra.setSchoolClassId(Constants.SCHOOL_CLASS_JS_ID);
        studentAgra.setStartStudyingDate(new Date(2021-1900,9,19));
        this.users.add(studentAgra);

        //TODO:: insert cities
        //TODO insert adresses and link them to schools
        //inserting schools
        School s1=new School();
        s1.setName("Youcode");
        s1.setId(1);
        s1.setSiteUrl("www.youcode.ma");
        this.schools.add(s1);
        //inserting departments
        Department d1=new Department();
        d1.setId(1);
        d1.setName("IT department");
        d1.setSchoolId(1);
        this.departments.add(d1);
        //inserting classes
        SchoolClass javaClass=new SchoolClass();
        javaClass.setId(1);
        javaClass.setName("Java/angular class");
        this.schoolClasses.add(javaClass);
        SchoolClass jsClass=new SchoolClass();
        jsClass.setId(2);
        jsClass.setName("Js/FullStack");
        this.schoolClasses.add(jsClass);

        //inserting rooms
        Room r1=new Room();
        r1.setName("Namek");
        r1.setId(1);
        this.rooms.add(r1);
        Room r2=new Room();
        r2.setName("404");
        r2.setId(2);
        this.rooms.add(r2);

        //inserting subjects
        Subject js=new Subject();
        js.setId(1);
        js.setName("JS");
        js.setRoomId(1);
        this.subjets.add(js);

        Subject react=new Subject();
        react.setId(2);
        react.setName("react");
        react.setRoomId(1);
        this.subjets.add(react);

        Subject java=new Subject();
        java.setId(3);
        java.setName("Java");
        java.setRoomId(2);
        this.subjets.add(java);

        Subject angular=new Subject();
        angular.setId(4);
        angular.setName("Angular");
        angular.setRoomId(2);
        this.subjets.add(angular);
        //link subject to class

        //java class students study java
        this.classSubject.add(new Pair<Integer,Integer>(Constants.SCHOOL_CLASS_JAVA_ID,Constants.SUBJECT_JAVA_ID));
        //java class students study angular
        this.classSubject.add(new Pair<Integer,Integer>(Constants.SCHOOL_CLASS_JAVA_ID,Constants.SUBJECT_ANGULAR_ID));
        //js class students study js
        this.classSubject.add(new Pair<Integer,Integer>(Constants.SCHOOL_CLASS_JS_ID,Constants.SUBJECT_JS_ID));
        //java class students study react
        this.classSubject.add(new Pair<Integer,Integer>(Constants.SCHOOL_CLASS_JS_ID,Constants.SUBJECT_REACT_ID));

        //java class is thought by bouchra
        this.classTeacher.add(new Pair<>(Constants.SCHOOL_CLASS_JAVA_ID,teacherBouchra.getId()));
        //js class is thought by ahmed
        this.classTeacher.add(new Pair<>(Constants.SCHOOL_CLASS_JS_ID,teacherAhmed.getId()));


        //inserting grades for students
        this.examGrades.add(new ExamGrade(1,12.5f,studentIbrahim.getId(),Constants.SUBJECT_JAVA_ID,new Date()));
        this.examGrades.add(new ExamGrade(1,12.5f,studentIbrahim.getId(),Constants.SUBJECT_ANGULAR_ID,new Date()));

        this.examGrades.add(new ExamGrade(1,15.5f,studentKhalil.getId(),Constants.SUBJECT_JAVA_ID,new Date()));
        this.examGrades.add(new ExamGrade(1,14.5f,studentKhalil.getId(),Constants.SUBJECT_ANGULAR_ID,new Date()));

        this.examGrades.add(new ExamGrade(1,18.5f,studentAgra.getId(),Constants.SUBJECT_JS_ID,new Date()));
        this.examGrades.add(new ExamGrade(1,14.5f,studentAgra.getId(),Constants.SUBJECT_REACT_ID,new Date()));
    }
    public int getNewUSerId(){
        return ++currentUserId;
    }
}
