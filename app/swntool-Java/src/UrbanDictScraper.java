
/**
 *
 * @author Daikaiser
 */
import java.io.*;
import java.util.LinkedList;
public class UrbanDictScraper
{
    public static LinkedList<String> scrape(String word) throws IOException{
        LinkedList<String> scrapedData=new LinkedList<String>();
        ProcessBuilder builder = new ProcessBuilder(
            "python",Config.MATLABFolder+"udscrape.py",word);
        Process pr = builder.start();
        // retrieve output from python script
        BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line="";
        String s="";
        while( (line=bfr.readLine())!= null) {
            // display each output line form python script
            //System.out.println(line);
            s+=" "+line;
        }
        for(String input:s.split("<p>")){
            System.out.println(input);
            scrapedData.add(input);
        }
        return scrapedData;
    }
}
