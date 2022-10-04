package com.win.academy;


import com.win.academy.utils.Constants;
import com.win.academy.utils.Pair;
import com.win.academy.utils.UiUtils;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.swing.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Unit test for simple App.
 */
@SuppressWarnings("deprecation")
public class AppTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    /*public void testApp()
    {
        assertTrue( true );
    }
    public void testDate(){
        System.out.println(new Date(2021,8,19));
    }
    public void testRegex(){
        Pattern pattern=Pattern.compile("[ab]");
        assertTrue(pattern.matcher("a").matches());
    }
    public void testUniCode() {
        System.out.println("from test: "+((char)190));
    }
    public void testAddNote(){
        System.out.println("notes count :"+DataHolder.getInstance().examGrades.size());
    }
    public void testAddClass(){
        System.out.println("you have "+DataHolder.getInstance().schoolClasses.size()+" school classes");
    }*/

    public void testAddSchool(){
        /*int countBeforeInsertion=DataHolder.getInstance().schools.size();
        UiUtils.menuAdminAddSchool();
        assertTrue(DataHolder.getInstance().schools.size()>countBeforeInsertion);*/
    }

}
