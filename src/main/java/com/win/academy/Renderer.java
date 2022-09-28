package com.win.academy;

import java.io.Console;
import java.util.Scanner;

public class Renderer {
    public static void printApplicationHeader(){
        System.out.println("-------Bonjour à l'application Win Academy---");
    }
    public static boolean logIn(){
        System.out.println("Veillez saisir votre email");
        Scanner scanner=new Scanner(System.in);
        String email=scanner.nextLine();
        System.out.println("Veillez saisir votre Mot de passe");
        String password=scanner.nextLine();
        System.out.println("email entered is "+email);
        System.out.println("password entered is "+password);
        return true;
    }
    public static void printMainMenu(){
        System.out.println("%ain menu");
    }
}
