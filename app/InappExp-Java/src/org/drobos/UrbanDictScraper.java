package org.drobos;

/**
 * Retrieves Data from UrbanDictionary via JSoup Module
 */


/**
 *
 * @author Daikaiser
 */
import java.util.LinkedList;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UrbanDictScraper {
    public static LinkedList<String> scrape(String word){
        Document doc=null;
        LinkedList<String> definitions=new LinkedList<String>();
        try {
            doc = Jsoup.connect("http://www.urbandictionary.com/define.php?term="+word).get();
            Elements meanings=doc.select("div[class=meaning]");
            for(Element meaning:meanings){
                definitions.add(meaning.text());
            }        
        } 
        catch (IOException ex) {
            
        }
        return definitions;
    }
}