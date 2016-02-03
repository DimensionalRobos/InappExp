package org.drobos;

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
    public static String PythonFolder="C:\\Users\\Anj Lasala\\Documents\\GitHub\\InappExp\\app\\UrbanDictScraper-Python\\";
    public static String WordNetPath="C:\\Program Files (X86)\\WordNet\\2.1\\dict";
    public static String SentiWordNetPath="swn.txt";
    public static String TrainingData="MLearn.txt";
    public static String NGramData="NGram.txt";
    public static String BasisData="WordVal.txt";
    public static String ResampleData="Resample.txt";
    public static String ExceptionData="exceptionlist.txt";
    public static String StopWordData="stopwords.txt";
}
