
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daikaiser
 */
public class BWDAO {

    public static boolean exists(String word) {
        word = word.toLowerCase();
        try {
            Scanner scan = new Scanner(new File(Config.BasisData));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                Sentiment sentiment = new Sentiment(s.split("#")[0], Double.valueOf(s.split("#")[1]));
                sentiment.word=sentiment.word.trim();
                if (sentiment.word.equals(word)) {
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean existsAsResample(String word){
        try {
            Scanner scan = new Scanner(new File(Config.ResampleData));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                Sentiment sentiment = new Sentiment(s.split("#")[0], Double.valueOf(s.split("#")[1]));
                sentiment.word=sentiment.word.trim();
                if (sentiment.word.equals(word)) {
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public static void write(String word,boolean isResample) {
        try {
            File file = new File(isResample?Config.ResampleData:Config.BasisData);
            FileWriter f = new FileWriter(file, true);
            f.write(word);
            f.close();
        } catch (Exception e) {

        }
    }
}
