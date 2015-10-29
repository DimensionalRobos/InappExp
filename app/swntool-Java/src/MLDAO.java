
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daikaiser
 */
public class MLDAO {
    public static void write(String feature) throws IOException{
        File file =new File("MLearn.txt");
        FileWriter f=new FileWriter(file,true);
        f.write(feature);
        f.close();
    }
    
    public static ExpressionList getSentiments(){
        ExpressionList expressions=new ExpressionList();
        try {
            Scanner scan=new Scanner(new File("MLearn.txt"));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                Sentiment sentiment = new Sentiment(s.split("#")[0], Double.valueOf(s.split("#")[1]));
                expressions.add(sentiment);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return expressions;
    }
}
