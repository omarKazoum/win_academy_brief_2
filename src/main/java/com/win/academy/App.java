package com.win.academy;

import com.win.academy.beans.User;
import com.win.academy.utils.Pair;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Win Academy
 * @author OmarKazoum
 */
public class App 
{
    public static DataHolder dataHolder;
    public static final String fileParentFolder="C:\\test\\";
    private static final String filePath="C:\\test\\data.ser";
    public static void main( String[] args )
    {
        /*Renderer.printApplicationHeader();
        boolean isLoggedIn=false;
        do{
            isLoggedIn=Renderer.logIn();
        }while (!isLoggedIn);
      if(!loadDataFromFile()){
          loadDummyData();
      };
      saveDataToFile();*/

        /*Set<String> names=new HashSet<>();
        names.add("omar");
        names.add("khalil");
        names.add("khalil");
        names.remove("omar");
        if(names.contains(new String("omar"))){
            System.out.println("omar is in the list!");
        };
        names.stream().forEach(e-> System.out.println(e));*/
     /* Map<String,Integer> people=new HashMap<String,Integer>();
        people.put("omar",26);
        people.put("khalil",27);
        people.put("agra",26);
        people.keySet().stream().forEach(k-> System.out.println("key:"+k+"->"+people.get(k)));
*/

      /*  Pair<Integer,Integer> p1=new Pair<>(1,1);
        Pair<Integer,Integer> p2=new Pair<>(1,2);

        Set<Pair<Integer,Integer>> userRole=new HashSet<>();
        userRole.add(p1);
        userRole.add(p2);

        userRole.stream().filter(p->p.first==1&&p.second==2).forEach(p->System.out.println("user id:"+p.first+" role id:"+p.second));
        System.out.println(p1==p2);*/
       /* Map<String,Integer> people=new HashMap<>();
        people.put("omar",12);
        people.put("omar",22);
        people.values().stream().forEach(k-> System.out.println(k));*/
        //System.out.println(new Date().compareTo());
    }
    private static boolean loadDataFromFile(){
     if(!new File(filePath).exists()) {
         System.out.println("no previous data found!");
         dataHolder=new DataHolder();
         return false;
     }
     try{
         ObjectInputStream ois=new ObjectInputStream(new FileInputStream(filePath));
         dataHolder= (DataHolder) ois.readObject();
         System.out.println("reading object from file");
         dataHolder.users.stream().forEach(el-> System.out.println(el.getFirstName()));
        return true;
     }catch (Exception e){
         e.printStackTrace();
         System.exit(0);
         return false;
     }
    }
    private static void saveDataToFile(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(dataHolder);
            oos.close();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(0);
        }
    }
    private static void loadDummyData(){
        User u=new User();
        u.setId(12);
        u.setEmail("omarkazoum96@gmail.com");
        u.setFirstName("omar");
        u.setLastName("kazoum");
        u.setPassword("omarkazoum");
        dataHolder=new DataHolder();
        dataHolder.users=new ArrayList<>();
        dataHolder.users.add(u);
    }
}

