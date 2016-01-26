/**
 * Exception Words Data Access Object for words that will never be used inappropriately
 */

import java.io.File;
import java.util.Scanner;
/**
 *
 * @author Daikaiser
 */
public class EWDAO {
    public static boolean exists(String word){
        word = word.toLowerCase();
        try {
            Scanner scan = new Scanner(new File(Config.ExceptionData));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                if (s.equalsIgnoreCase(word)) {
                    return true;
                }
            }
        } catch (Exception ex) {
        }
        return false;
    }
}
