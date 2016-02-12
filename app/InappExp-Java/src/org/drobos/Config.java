package org.drobos;

import java.io.File;
import java.util.Scanner;

/**
 * All configurations are done here.
 * PythonFolder=location of udscrape.py
 * WordNetPath=location of wordNet dict
 * TrainingData=basis of Inappropriateness
 * NGramData=Inappropriateness Patterns
 * BasisData=Seed "Bad words"
 * ResampleData=Collection of words that are possibly inappropriate via Wordnet Synsets
 * stopWordData=Collection of words that will never have inappropriate usage to avoid Data noise
 */

/**
 *
 * @author Daikaiser
 */
public class Config {
    public static String PythonFolder(){
        try {
            Scanner scan=new Scanner(new File("propsconfig"));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                if(s.split("=")[0].equals("PythonFolder"))
                    return s.split("=")[1];
            }
        } catch (Exception ex) {
            
        }
        return "";
    }
    public static String WordNetPath(){
        try {
            Scanner scan=new Scanner(new File("propsconfig"));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                if(s.split("=")[0].equals("WordNetPath"))
                    return s.split("=")[1];
            }
        } catch (Exception ex) {
            
        }
        return "";
    }
    public static String SentiWordNetPath(){
        try {
            Scanner scan=new Scanner(new File("propsconfig"));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                if(s.split("=")[0].equals("SentiWordNetPath"))
                    return s.split("=")[1];
            }
        } catch (Exception ex) {
            
        }
        return "";
    }
    public static String TrainingData(){
        try {
            Scanner scan=new Scanner(new File("propsconfig"));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                if(s.split("=")[0].equals("TrainingData"))
                    return s.split("=")[1];
            }
        } catch (Exception ex) {
            
        }
        return "";
    }
    public static String NGramData(){
        try {
            Scanner scan=new Scanner(new File("propsconfig"));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                if(s.split("=")[0].equals("NGramData"))
                    return s.split("=")[1];
            }
        } catch (Exception ex) {
            
        }
        return "";
    }
    public static String BasisData(){
        try {
            Scanner scan=new Scanner(new File("propsconfig"));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                if(s.split("=")[0].equals("BasisData"))
                    return s.split("=")[1];
            }
        } catch (Exception ex) {
            
        }
        return "";
    }
    public static String ResampleData(){
        try {
            Scanner scan=new Scanner(new File("propsconfig"));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                if(s.split("=")[0].equals("ResampleData"))
                    return s.split("=")[1];
            }
        } catch (Exception ex) {
            
        }
        return "";
    }
    public static String ExceptionData(){
        try {
            Scanner scan=new Scanner(new File("propsconfig"));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                if(s.split("=")[0].equals("ExceptionData"))
                    return s.split("=")[1];
            }
        } catch (Exception ex) {
            
        }
        return "";
    }
    public static String StopWordData(){
        try {
            Scanner scan=new Scanner(new File("propsconfig"));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                if(s.split("=")[0].equals("StopWordData"))
                    return s.split("=")[1];
            }
        } catch (Exception ex) {
            
        }
        return "";
    }
}
