package com.win.academy.utils;

import com.win.academy.DataHolder;
import com.win.academy.beans.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static com.win.academy.utils.Constants.*;

public class UiUtils {
    private static Scanner mScanner;
    public static void printApplicationHeader(){
        System.out.println("-------Bonjour à l'application Win Academy---");
    }
    public static boolean logIn(){
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
    public static void printMainMenu(){
        Map<Integer,String> options=new HashMap<>();
        System.out.println("----------Main menu-------");
        if(!enableAuthentification ||ShortCuts.doesConnectedUserHaveRole(Constants.ROLE_ADMIN_ID) ) {
            options.put(MENU_ADMIN_STUDENT_AVERAGE_GRADE,"Calculer la moyenne générale d’un élève.");
            options.put(MENU_ADMIN_DEPARTMENT_AVERAGE_GRADE,"Calculer la moyenne par département");
            options.put(MENU_ADMIN_SUBJECT_AVERAGE_GRADE,"Calculer la moyenne par matière");
            options.put(MENU_ADMIN_STUDENT_FICHE_SIGNALÉTIQUE,"Imprimer la fiche signalétique d'un élève");
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
            System.out.println("tapez le numéro correspondant à l'option de votre choix:\n");
            option=scanner.nextInt();
        }while(!(option>=1 && option<=10));
        switch(option){
            //TODO:: handle other menu options
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
                menuOptionDisplayStudentSignalitiqueFile();
                break;

            default:
                throw new IllegalArgumentException("option "+option+" not supported yet !");
        }
    }

    private static void menuOptionDisplaySubjectAverageGrade() {
        boolean isSubjectIdValid=false;
        do {
            System.out.println("Veillez entrer l'identifiant de matière pour lequel vous souhaitez calculer la moyenne:");
            System.out.println("#\t\tNom");
            DataHolder.getInstance().subjets.stream().forEach(s->
                    System.out.println(String.format("%d\t\t%s",s.getId(),s.getName())));
            //TODO:: continue this from here pay attention cause it's a copy cut
            int subjectId = getScanner().nextInt();
            Optional<Department> department = DataHolder.getInstance().departments.stream().filter(d -> d.getId() == subjectId).findFirst();
            isSubjectIdValid=department.isPresent();
            if(department.isPresent()){
                //so we know which department to display average for
                //let's do so
                List<ExamGrade> departmentGrades=DataHolder.getInstance().users.stream()
                        //finding teachers from the department
                        .filter(u->u instanceof Teacher && ((Teacher)u).getDepartmentId()==subjectId)
                        //getting the subjects that these teachers are teaching
                        .map(t->
                                DataHolder.getInstance().subjets.stream().filter(
                                        s->s.getId()==((Teacher)t).getSubjectId()
                                ).findFirst().get()
                        ).map(
                                subject ->
                                        DataHolder.getInstance().examGrades.stream().filter(eg->eg.getSubjectId()
                                                ==subject.getId()).toList()
                        ).reduce(new LinkedList<ExamGrade>(),(pr,cu)->{pr.addAll(cu);return pr;});

                float avg=departmentGrades.stream().map(g->g.getExamGrade()).reduce(0f,(sum,n)->sum+n);
                avg/=departmentGrades.size();
                System.out.println(department.get().getName()+" has an average of "+avg);
            }
        }while(!isSubjectIdValid);
        promptToGoBack();
    }

    private static void menuOptionDisplayStudentSignalitiqueFile() {
        boolean isStudentIdValid=false;
        do {
            System.out.println("Veillez entrer l'identifinat de l'étudiant pour lequel vous souhaitez imprimer la fiche signalitique:");
            System.out.println("#\t\tNom\t\t\t\tprénom\t\t\t\temail");
            DataHolder.getInstance().users.stream().filter(u->u instanceof Student).forEach(s->
                    System.out.println(String.format("%d\t\t%s\t\t\t%s\t\t\t\t%s",s.getId(),s.getLastName(),s.getFirstName(),s.getEmail())));
            int studentId = getScanner().nextInt();
            Optional<User> s = DataHolder.getInstance().users.stream().filter(u -> u instanceof Student && u.getId() == studentId).findFirst();
            isStudentIdValid=s.isPresent();
            if(s.isPresent()){
                Student student= (Student) s.get();
                //so we know which student to display average for
                //let's do so
                System.out.println("-----fiche-signalétique------");
                System.out.println("Nom:"+student.getLastName());
                System.out.println("Prénom:"+student.getFirstName());
                System.out.println("Email:"+student.getEmail());
                System.out.println("N°tel:"+(student.getPhone()!=null?student.getPhone():"Non fornie"));
                System.out.println("Date de début de formation:"+student.getStartStudyingDate());
                System.out.println("Classe:"+ DataHolder.getInstance().schoolClasses.stream().filter(sc->sc.getId()==student.getSchoolClassId()).findFirst().get().getName());
                System.out.println("-----------------------------");
            }
        }while(!isStudentIdValid);
        promptToGoBack();
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
                        .filter(eg->eg.getStudentId()==s.get().getId()).toList();
                final float[] sum = {0};
                studentGrades.stream().forEach(g-> sum[0] +=g.getExamGrade());
                System.out.println(s.get().getFirstName()+" "+s.get().getLastName()+" has an average of "+sum[0]/studentGrades.size());
            }
        }while(!isStudentIdValid);
        promptToGoBack();
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
                                                ==subject.getId()).toList()
                        ).reduce(new LinkedList<ExamGrade>(),(pr,cu)->{pr.addAll(cu);return pr;});

                float avg=departmentGrades.stream().map(g->g.getExamGrade()).reduce(0f,(sum,n)->sum+n);
                avg/=departmentGrades.size();
                System.out.println(department.get().getName()+" has an average of "+avg);
            }
        }while(!isDepartmentIdValid);
        promptToGoBack();
    }
    public static void clearScreen(){
        System.out.print("------------------------------------------------");
    }
    public static Scanner getScanner(){
        if(mScanner==null)
            mScanner=new Scanner(System.in);
        return mScanner;
    }
    public static void promptToGoBack(){
        System.out.println("press 'Q' to exist or anything else to go to main menu");
        getScanner().nextLine();
        if(!getScanner().nextLine().equalsIgnoreCase("q")){
            printApplicationHeader();
            printMainMenu();
        }else{
            System.out.println("Bye!");
        }
    }
}
