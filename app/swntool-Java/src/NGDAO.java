/**
 * N-Gram Data Access Object
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/**
 *
 * @author Daikaiser
 */

public class NGDAO {
    public static void write(String NGram) throws IOException{
        File file =new File(Config.NGramData);
        FileWriter f=new FileWriter(file,true);
        f.write(NGram);
        f.close();
    }
    public static WordList extractNGrams() {
        WordList NGrams = new WordList();
        try {
            Scanner scan = new Scanner(new File(Config.NGramData));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                if (!NGrams.contains(s)) {
                    NGrams.add(s);
                    System.out.println(s);
                }
            }
        } catch (Exception ex) {
            
        }
        NGrams=NGrams.sort();
        return NGrams;
    }
}
