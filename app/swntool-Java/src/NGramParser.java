
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daikaiser
 */

public class NGramParser {
    public static void parse(){
        WordList NGrams=extractNGrams();
        for(String NGram:NGrams){
            String[]NGramPattern=NGram.split(" ");
        }
    }
    public static WordList extractNGrams(){
        WordList NGrams=new WordList();
        try {
            Scanner scan=new Scanner(new File(Config.NGramData));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                if(!NGrams.contains(s)){
                    NGrams.add(s);
                    System.out.println(s);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return NGrams;
    }
    public static void main(String[]args){
        extractNGrams();
    }
}
