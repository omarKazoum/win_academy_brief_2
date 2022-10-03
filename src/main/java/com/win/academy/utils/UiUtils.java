package com.win.academy.utils;

import com.win.academy.DataHolder;
import com.win.academy.beans.*;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.win.academy.utils.Constants.*;

public class UiUtils {
    public  static void printApplicationHeader(){
        System.out.println("-------Bonjour à l'application Win Academy---");
    }
    public  static boolean logIn(){
        System.out.println("Veillez saisir votre email");
        Scanner scanner=new Scanner(System.in);
        String email=scanner.nextLine();
        System.out.println("Veillez saisir votre Mot de passe");
        String password=scanner.nextLine();
        Optional<User> optionalUser=DataHolder.getInstance().users.stream().filter(u->u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password)).findFirst();
        if(optionalUser.isPresent()){
            DataHolder.connectedUser = optionalUser.get();
            return true;
        }
        return false;
    }
    public  static void printMainMenu(){
        Map<Integer,String> options=new HashMap<>();
        System.out.println("----------Main menu-------");
        if(!enableAuthentification ||ShortCuts.doesConnectedUserHaveRole(Constants.ROLE_ADMIN_ID) ) {
            options.put(MENU_ADMIN_STUDENT_AVERAGE_GRADE,"Calculer la moyenne générale d’un élève.");
            options.put(MENU_ADMIN_DEPARTMENT_AVERAGE_GRADE,"Calculer la moyenne par département");
            options.put(MENU_ADMIN_SUBJECT_AVERAGE_GRADE,"Calculer la moyenne par matière");
            options.put(MENU_ADMIN_STUDENT_FICHE_SIGNALÉTIQUE,"Imprimer la fiche signalétique d'un élève");
            options.put(MENU_ADMIN_ADD_STUDENT,"Ajouter un élève");
            options.put(MENU_ADMIN_ADD_TEACHER,"Ajouter un enseignant");
        }
        if(!enableAuthentification ||ShortCuts.doesConnectedUserHaveRole(Constants.ROLE_STUDENT_ID )){
            options.put(MENU_STUDENT_CHECK_MY_GRADES_PER_SUBJECT,"Consulter mes notes de controles");
            options.put(MENU_STUDENT_CHECK_MY_AVERAGE_GRADE,"Consulter ma moyenne générale");
            options.put(MENU_STUDENT_CHECK_MY_FICHE_SIGNALITIQUE,"Imprimer ma fiche signalétique");
        }
        if(!enableAuthentification ||ShortCuts.doesConnectedUserHaveRole(Constants.ROLE_TEACHER_ID)){
            options.put(MENU_TEACHER_CHECK_MY_STUDENTS_LIST,"consulter la liste de mes étudiants");
        }
        if(!enableAuthentification ||ShortCuts.doesConnectedUserHaveRole(Constants.ROLE_DEPARTMENT_RESPONSIBLE_ID)){
            options.put(MENU_RESPONSIBLE_MY_DEPARTMENT_AVERAGE_GRADE,"calculer la moyenne générale de mon départements");
            options.put(MENU_RESPONSIBLE_STUDENT_FICHE_SIGNALÉTIQUE,"la fiche signalétique d'un étudiants ");
        }
            options.keySet().stream().forEach(k-> System.out.println(k+"-> "+options.get(k)));
        Scanner scanner=new Scanner(System.in);
        int option=-1;
        do{
            System.out.println("Tapez le numéro correspondant à l'option de votre choix:\n");
            option=scanner.nextInt();
        }while(!(options.containsKey(option)));
        clearScreen();
        switch(option){
            case MENU_ADMIN_STUDENT_AVERAGE_GRADE:
                menuOptionAdminDisplayStudentAverageGrade();
                break;
            case MENU_ADMIN_DEPARTMENT_AVERAGE_GRADE:
                menuOptionDisplayDepartmentAverageGrade();
                break;
            case MENU_ADMIN_SUBJECT_AVERAGE_GRADE:
                menuOptionDisplaySubjectAverageGrade();
                break;
            case MENU_ADMIN_STUDENT_FICHE_SIGNALÉTIQUE:
                menuOptionAdminDisplayStudentSignalitiqueFile();
                break;
            case MENU_ADMIN_ADD_STUDENT:
                menuAddUser(ROLE_STUDENT_ID);
                break;
            case MENU_ADMIN_ADD_TEACHER:
                menuAddUser(ROLE_TEACHER_ID);
                break;
            //student main menu options
            case MENU_STUDENT_CHECK_MY_GRADES_PER_SUBJECT:
                menuOptionDisplayMySubjectGrades();
                ;break;
            case MENU_STUDENT_CHECK_MY_AVERAGE_GRADE:
                menuOptionStudentDisplayMyAverageGrade();
                ;break;
            case MENU_STUDENT_CHECK_MY_FICHE_SIGNALITIQUE:
                menuOptionDisplayStudentSignalitiqueFile();
                ;break;
            //teacher main menu options
            case MENU_TEACHER_CHECK_MY_STUDENTS_LIST:
                menuOptionTeacherCheckMyStudents();
                ;break;
            //department responsible
            case MENU_RESPONSIBLE_MY_DEPARTMENT_AVERAGE_GRADE:
                menuOptionResponsibleGetMyDepartmentAverage();
                ;break;
            case MENU_RESPONSIBLE_STUDENT_FICHE_SIGNALÉTIQUE:
                menuOptionResponsibleDisplayStudentSignalitiqueFile();
                ;break;
            default:
                throw new IllegalArgumentException("option "+option+" not supported yet !");
        }
        promptToGoBack();
    }
    private static void menuOptionResponsibleDisplayStudentSignalitiqueFile() {
        int departmentId=((Teacher)DataHolder.connectedUser).getDepartmentId();
        selectStudentFromListThen(DataHolder.getInstance().users.stream().
                        filter(u-> u instanceof Teacher && ((Teacher)u).getDepartmentId()==departmentId)
                        .map(t->DataHolder.getInstance().classTeacher.stream().filter(ct->ct.right==t.getId()).map(ct->ct.left).collect(Collectors.toCollection(()->new ArrayList<>())))
                        .flatMap(Collection::stream).distinct().map(classId->DataHolder.getInstance().users.stream().filter(u->u instanceof Student && ((Student)u).getSchoolClassId()==classId)
                        .collect(Collectors.toCollection(()->new ArrayList<>()))).flatMap((l)->l.stream()).map(u->(Student)u).collect(Collectors.toCollection(()->new ArrayList<>()))
                ,UiUtils::printFicheSignalitiqueForStudent);
    }
    private static void menuOptionResponsibleGetMyDepartmentAverage() {
            int departmentId = ((Teacher) DataHolder.connectedUser).getDepartmentId();
            Optional<Department> department = DataHolder.getInstance().departments.stream().filter(d -> d.getId() == departmentId).findFirst();
                //so we know which department to display average for
                //let's do so
                List<ExamGrade> departmentGrades=DataHolder.getInstance().users.stream()
                        //finding teachers from the department
                        .filter(u->u instanceof Teacher && ((Teacher)u).getDepartmentId()==departmentId)
                        //getting the subjects that these teachers are teaching
                        .map(t->
                                DataHolder.getInstance().subjets.stream().filter(
                                        s->s.getId()==((Teacher)t).getSubjectId()
                                ).findFirst().get()
                        ).map(
                                subject ->
                                        DataHolder.getInstance().examGrades.stream().filter(eg->eg.getSubjectId()
                                                ==subject.getId()).collect(Collectors.toCollection(()->new LinkedList<>()))
                        ).reduce(new LinkedList<ExamGrade>(),(pr,cu)->{pr.addAll(cu);return pr;});

                float avg=departmentGrades.stream().map(g->g.getExamGrade()).reduce(0f,(sum,n)->sum+n);
                avg/=departmentGrades.size();
                System.out.println(department.get().getName()+" has an average of "+avg);


    }
    private static void menuOptionTeacherCheckMyStudents() {
        int teacherId=DataHolder.connectedUser.getId();
        ShortCuts.getStudentsForTeacher(teacherId).forEach(s->{
                    System.out.printf("%d %-10s %-10s %-10s\n",s.getId(),s.getFirstName(),s.getLastName(),ShortCuts.getClassById(s.getSchoolClassId()).getName());

                });
    }
    private static void menuOptionDisplayStudentSignalitiqueFile() {
                printFicheSignalitiqueForStudent((Student) DataHolder.connectedUser);
            }
    private static void menuOptionStudentDisplayMyAverageGrade() {
        List<ExamGrade> studentGrades=DataHolder.getInstance().examGrades.stream()
                .filter(eg->eg.getStudentId()==DataHolder.connectedUser.getId()).collect(Collectors.toCollection(()->new ArrayList<>()));
        final float[] sum = {0};
        studentGrades.stream().forEach(g-> sum[0] +=g.getExamGrade());
        System.out.printf("votre moyenne générale est de  %.2f\n",sum[0]/studentGrades.size());
    }
    private static void menuOptionDisplayMySubjectGrades() {
        boolean isSubjectIdValid=false;
        do {
            System.out.println("Veillez entrer l'identifiant de matière pour lequel vous souhaitez consulter les notes:");
            System.out.println("#\t\tNom");
            DataHolder.getInstance().
                    examGrades.stream().filter(sg->sg.getStudentId()==DataHolder.connectedUser.getId())
                    .map(examGrade -> examGrade.getSubjectId()).distinct().map(subjectId->
                            DataHolder.getInstance().subjets.stream().filter(s->s.getId()==subjectId).findFirst().get()).forEach(s-> System.out.printf("%d\t\t%s\n",s.getId(),s.getName()));

            int subjectId = getScanner().nextInt();
            Optional<Subject> subject = DataHolder.getInstance().subjets.stream().filter(s -> s.getId() == subjectId).findFirst();
            isSubjectIdValid=subject.isPresent();
            if(subject.isPresent()){
                //so we know which subject to display average for
                //let's do so
                System.out.println("Nom de Matière\tNote\tDate d'exam");
                DataHolder.getInstance().examGrades.stream()
                        //finding teachers from the department
                        .filter(eg->eg.getSubjectId()==subjectId && eg.getStudentId()==DataHolder.connectedUser.getId())
                        //getting the subjects that these teachers are teaching
                        .map(eg->String.format("%-15s\t%2.2f\t%s",
                                ShortCuts.getSubjectById(eg.getSubjectId()).getName(),eg.getExamGrade(),ShortCuts.formatDate(eg.getExamDate())
                                )
                        ).forEach(System.out::println);

                }
        }while(!isSubjectIdValid);
    }
    private static void menuOptionDisplaySubjectAverageGrade() {
        boolean isSubjectIdValid=false;
        do {
            System.out.println("Veillez entrer l'identifiant de matière pour lequel vous souhaitez calculer la moyenne:");
            System.out.println("#\t\tNom");
            DataHolder.getInstance().subjets.stream().forEach(s->
                    System.out.println(String.format("%d\t\t%s",s.getId(),s.getName())));
            int subjectId = getScanner().nextInt();
            Optional<Subject> subject = DataHolder.getInstance().subjets.stream().filter(s -> s.getId() == subjectId).findFirst();
            isSubjectIdValid=subject.isPresent();
            if(subject.isPresent()){
                //so we know which department to display average for
                //let's do so
                List<ExamGrade> departmentGrades=DataHolder.getInstance().examGrades.stream().filter(eg->eg.getSubjectId()==subject.get().getId()).collect(Collectors.toCollection(()->new ArrayList<>()));
                float avg=departmentGrades.stream().map(g->g.getExamGrade()).reduce(0f,(sum,n)->sum+n);
                avg/=departmentGrades.size();
                System.out.println(subject.get().getName()+" has an average of "+avg);
            }
        }while(!isSubjectIdValid);
    }
    private static void menuOptionAdminDisplayStudentSignalitiqueFile() {
        selectStudentFromListThen( DataHolder.getInstance().users.stream().filter(u->u instanceof Student).map(u->(Student)u).collect(Collectors.toCollection(()->new ArrayList<>())),UiUtils::printFicheSignalitiqueForStudent);
    }
    private static void menuOptionAdminDisplayStudentAverageGrade(){
        boolean isStudentIdValid=false;
        do {
            System.out.println("Veillez entrer l'identifinat de l'étudiant pour lequel vous souhaitez calculer la moyenne:");
            System.out.println("#\t\tNom\t\t\t\tprénom\t\t\t\temail");
            DataHolder.getInstance().users.stream().filter(u->u instanceof Student).forEach(s->
                    System.out.println(String.format("%d\t\t%s\t\t\t%s\t\t\t\t%s",s.getId(),s.getLastName(),s.getFirstName(),s.getEmail())));
            int studentId = getScanner().nextInt();
            Optional<User> s = DataHolder.getInstance().users.stream().filter(u -> u.getId() == studentId).findFirst();
            isStudentIdValid=s.isPresent();
            if(s.isPresent()){
                //so we know which student to display average for
                //let's do so
                List<ExamGrade> studentGrades=DataHolder.getInstance().examGrades.stream()
                        .filter(eg->eg.getStudentId()==s.get().getId()).collect(Collectors.toCollection(()->new ArrayList<>()));
                final float[] sum = {0};
                studentGrades.stream().forEach(g-> sum[0] +=g.getExamGrade());
                System.out.println(s.get().getFirstName()+" "+s.get().getLastName()+" has an average of "+sum[0]/studentGrades.size());
            }
        }while(!isStudentIdValid);
    }
    private static void menuOptionDisplayDepartmentAverageGrade(){
        boolean isDepartmentIdValid=false;
        do {
            System.out.println("Veillez entrer l'identifinat du département pour lequel vous souhaitez calculer la moyenne:");
            System.out.println("#\t\tNom");
            DataHolder.getInstance().departments.stream().forEach(s->
                    System.out.println(String.format("%d\t\t%s",s.getId(),s.getName())));
            int departmentId = getScanner().nextInt();
            Optional<Department> department = DataHolder.getInstance().departments.stream().filter(d -> d.getId() == departmentId).findFirst();
            isDepartmentIdValid=department.isPresent();
            if(department.isPresent()){
                //so we know which department to display average for
                //let's do so
                List<ExamGrade> departmentGrades=DataHolder.getInstance().users.stream()
                        //finding teachers from the department
                        .filter(u->u instanceof Teacher && ((Teacher)u).getDepartmentId()==departmentId)
                        //getting the subjects that these teachers are teaching
                        .map(t->
                                DataHolder.getInstance().subjets.stream().filter(
                                        s->s.getId()==((Teacher)t).getSubjectId()
                                ).findFirst().get()
                        ).map(
                                subject ->
                                        DataHolder.getInstance().examGrades.stream().filter(eg->eg.getSubjectId()
                                                ==subject.getId()).collect(Collectors.toCollection(()->new ArrayList<>()))
                        ).reduce(new ArrayList<>(),(pr,cu)->{pr.addAll(cu);return (ArrayList<ExamGrade>) pr;});

                float avg=departmentGrades.stream().map(g->g.getExamGrade()).reduce(0f,(sum,n)->sum+n);
                avg/=departmentGrades.size();
                System.out.println(department.get().getName()+" has an average of "+avg);
            }
        }while(!isDepartmentIdValid);
    }
    private static void selectStudentFromListThen(List<Student> students, Consumer<Student> cunsumer){
        boolean isStudentIdValid=false;
        do {
            System.out.println("Veillez entrer l'identifinat de l'étudiant pour lequel vous souhaitez imprimer la fiche signalitique:");
            System.out.println("#\t\tNom\t\t\t\tprénom\t\t\t\temail");
            students.forEach(s->
                    System.out.println(String.format("%d\t\t%s\t\t\t%s\t\t\t\t%s",s.getId(),s.getLastName(),s.getFirstName(),s.getEmail())));
            int studentId = getScanner().nextInt();
            Optional<Student> s = students.stream().filter(u -> u.getId() == studentId).findFirst();
            isStudentIdValid=s.isPresent();
            if(s.isPresent()){
                cunsumer.accept(s.get());
            }
        }while(!isStudentIdValid);
    }
    private static void printFicheSignalitiqueForStudent(Student student){
                System.out.println("-----fiche-signalétique------");
                System.out.println("Nom:"+student.getLastName());
                System.out.println("Prénom:"+student.getFirstName());
                System.out.println("Email:"+student.getEmail());
                System.out.println("N°tel:"+(student.getPhone()!=null?student.getPhone():"Non fornie"));
                System.out.println("Date de début de formation:"+student.getStartStudyingDate());
                System.out.println("Classe:"+ DataHolder.getInstance().schoolClasses.stream().filter(sc->sc.getId()==student.getSchoolClassId()).findFirst().get().getName());
                System.out.println("-----------------------------");
            }
    public  static void menuAddUser(int newUserRole){
        User newUser;
        String userType="";
        switch (newUserRole){
            case ROLE_STUDENT_ID :
                newUser=new Student();
                userType=" élève ";
                ;break;
            case ROLE_TEACHER_ID:
                newUser=new Teacher();
                userType=" enseignant ";
                ;break;
            case ROLE_ADMIN_ID:
                newUser=new Admin();
                userType=" administrateur ";
                ;break;
            case ROLE_DEPARTMENT_RESPONSIBLE_ID:
                newUser=new Teacher();
                userType=" enseignant ";
                ;break;
            default:throw new IllegalArgumentException("unsupported user role "+newUserRole);
        }
        Scanner scanner=new Scanner(System.in);
        System.out.println("insertion d'un nouveau "+userType+":");
        do {
            System.out.println("veillez saisir le nom de l'"+userType+"(doit contenir au moins 3 caractères)");
            newUser.setFirstName(scanner.nextLine());
        }while(newUser.getFirstName().length()<3);

        do {
            System.out.println("veillez saisir le prénom de l'"+userType+" (doit contenir au moins 3 caractères)");
            newUser.setLastName(scanner.nextLine());
        }while(newUser.getLastName().length()<3);
        Pattern emailPattern= Pattern.compile("\\w+@\\w+(\\.\\w+)+");
        do {
            System.out.println("veillez saisir l'email de l'"+userType+" (doit etre un email valid)");
            newUser.setEmail(scanner.nextLine());
        }while(!emailPattern.matcher(newUser.getEmail()).matches());
        do {
            System.out.println("veillez saisir le mot de pass de l'"+userType+" (doit contenir au moins 8 caractères)");
            newUser.setPassword(scanner.nextLine());
        }while(newUser.getPassword().length()<8);
        switch (newUserRole){
            case ROLE_STUDENT_ID :
                ((Student)newUser).setSchoolClassId(selectClassById());
                ((Student)newUser).setStartStudyingDate(new Date());
                ;break;
            case ROLE_TEACHER_ID:
                Teacher teacher= (Teacher) newUser;
                teacher.setStartWorkDate(new Date());
                teacher.setDepartmentId(selectSubject());
                teacher.setSubjectId(selectDepartment());
                ;break;
            case ROLE_ADMIN_ID:

                ;break;
            case ROLE_DEPARTMENT_RESPONSIBLE_ID:
                Teacher teacherResponsible= (Teacher) newUser;
                teacherResponsible.setStartWorkDate(new Date());
                teacherResponsible.setDepartmentId(selectSubject());
                teacherResponsible.setSubjectId(selectDepartment());
                DataHolder.getInstance().roleUser.add(new Pair<>(teacherResponsible.getId(), ROLE_DEPARTMENT_RESPONSIBLE_ID));
                ;break;
            default:throw new IllegalArgumentException("unsupported user role "+newUserRole);
        }
        DataHolder.getInstance().users.add(newUser);
    }
    private static int selectDepartment() {
        boolean isDepartmentIdValid=false;
        int departmentId=0;
        do {
            System.out.println("choisissez le département auquel le nouveau enseignant va appartenir:");
            System.out.println("#\tnom département");
            DataHolder.getInstance().departments.forEach(c->
                    System.out.println(String.format("%d\t%s",c.getId(),c.getName())));
            int selectDepartmentId=getScanner().nextInt();
            Optional<Department> optionalSchoolClass=DataHolder.getInstance().departments.stream().filter(department->department.getId()==selectDepartmentId).findFirst();
            isDepartmentIdValid=optionalSchoolClass.isPresent();
            if(isDepartmentIdValid)
                departmentId=optionalSchoolClass.get().getId();
        }while(!isDepartmentIdValid);
        return departmentId;
    }
    private static int selectSubject() {
        boolean isSubjectIdValid=false;
        int subjectId=0;
        do {
            System.out.println("choisissez la matière auquelle le nouveau enseignant va enseigner:");
            System.out.println("#\tnom de matière");
            DataHolder.getInstance().subjets.forEach(c->
                    System.out.println(String.format("%d\t%s",c.getId(),c.getName())));
            int selectSubjectId=getScanner().nextInt();
            Optional<Subject> optionalSubject=DataHolder.getInstance().subjets.stream().filter(c->c.getId()==selectSubjectId).findFirst();
            isSubjectIdValid=optionalSubject.isPresent();
            if(isSubjectIdValid)
                subjectId=optionalSubject.get().getId();
        }while(!isSubjectIdValid);
        return subjectId;
    }
    public  static int selectClassById(){
        boolean isClassIdValid=false;
        int classId=0;
        do {
            System.out.println("choisissez la classe auquelle le nouveau élève va appartenir:");
            System.out.println("#\tnom de class");
            DataHolder.getInstance().schoolClasses.forEach(c->
                    System.out.println(String.format("%d\t%s",c.getId(),c.getName())));
            int selectClassId=getScanner().nextInt();
            Optional<SchoolClass> optionalSchoolClass=DataHolder.getInstance().schoolClasses.stream().filter(c->c.getId()==selectClassId).findFirst();
            isClassIdValid=optionalSchoolClass.isPresent();
            if(isClassIdValid)
                classId=optionalSchoolClass.get().getId();
        }while(!isClassIdValid);
        return classId;
    }
    public  static void clearScreen(){
            try{
                String operatingSystemName = System.getProperty("os.name");
                //System.out.println("system name:"+operatingSystemName);
                if(operatingSystemName.contains("Windows")){
                    ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                    Process startProcess = pb.inheritIO().start();
                    startProcess.waitFor();
                } else {
                    ProcessBuilder pb = new ProcessBuilder("clear");
                    Process startProcess = pb.inheritIO().start();
                    startProcess.waitFor();
                }
            }catch(Exception e){
                System.out.println(e);
            }

    }
    public  static Scanner getScanner(){
       // if(mScanner==null)
        //    mScanner=new Scanner(System.in);
        //return mScanner;
        return new Scanner(System.in);
    }
    public  static void promptToGoBack(){
        System.out.println("press 'Q' to exist or anything else to go to main menu");
        if(!getScanner().nextLine().equalsIgnoreCase("q")){
            clearScreen();
            printApplicationHeader();
            printMainMenu();
        }else{
            clearScreen();
            System.out.println("Bye!");
        }
    }
}
