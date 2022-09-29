package com.win.academy;

import com.win.academy.beans.User;
import com.win.academy.utils.Constants;
import com.win.academy.utils.Pair;
import com.win.academy.utils.UiUtils;

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
   private static DataHolder dataHolder;

    public static void main( String[] args ) throws InterruptedException {
        dataHolder=DataHolder.getInstance();
        UiUtils.printApplicationHeader();
        boolean isLoggedIn=false;
        if(Constants.enableAuthentification)
            while (!UiUtils.logIn()) {
                System.err.println("Les informations sont incorrect");
            }
        System.out.println("vous étes bien connecté");
        UiUtils.clearScreen();
        UiUtils.printMainMenu();
        dataHolder.saveDataToFile();
    }

}

