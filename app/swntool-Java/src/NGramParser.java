
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
    public static void parse(Expression[]expressions){
        WordList NGrams=extractNGrams();
        for(int i=0;i<expressions.length;i++){
            testNGramInvoke(expressions,NGrams,i);
        }
    }
    public static boolean testNGramInvoke(Expression[]expressions,WordList NGrams,int startIndex){
        for(String NGram:NGrams){
            boolean isInvoked=false;
            String[] NGramPattern=NGram.split(" ");
            for(String NGramUnit:NGramPattern){
                try{
                    NGramUnit=NGramUnit.substring(0,2);
                }
                catch(Exception e){
                    
                }
            }
            for(int i=0;i<NGramPattern.length;i++){
                if(NGramPattern[i].equals(expressions[startIndex+i].postag))
                    isInvoked=true;
                else{
                    isInvoked=false;
                    break;
                }
            }
            if(isInvoked){
                return true;
            }
        }
        return false;
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
