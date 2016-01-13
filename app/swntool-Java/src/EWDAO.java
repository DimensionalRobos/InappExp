/**
 * Exception Word/Stop Words Data Access Object for words that will never be used inappropriately
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Daikaiser
 */
public class EWDAO {
    public static boolean exists(String word){
        word = word.toLowerCase();
        try {
            Scanner scan = new Scanner(new File(Config.stopWordData));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                if (s.equalsIgnoreCase(word)) {
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EWDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
