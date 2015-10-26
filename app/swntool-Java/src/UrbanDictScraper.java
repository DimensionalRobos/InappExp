
/**
 *
 * @author Daikaiser
 */
import java.io.*;
public class UrbanDictScraper
{
    public static void scrape(String word) throws IOException{
        ProcessBuilder builder = new ProcessBuilder(
            "python",Config.MATLABFolder+"udscrape.py",word);
        Process pr = builder.start();
        // retrieve output from python script
        BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line="";
        while( (line=bfr.readLine())!= null) {
            // display each output line form python script
            System.out.println(line);
        }
    }
}
