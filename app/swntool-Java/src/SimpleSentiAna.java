import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daikaiser
 */

class Sentiment{
    public double sentimentValue;
    public String word;
    public Sentiment(String word,double sentimentValue){
        this.word=word;
        this.sentimentValue=sentimentValue;
    }
}
public class SimpleSentiAna {
    static String taggerPath = "models/english-caseless-left3words-distsim.tagger";
    static MaxentTagger tagger = new MaxentTagger(taggerPath);
    public static void main(String[]args){
        try {
            SentimentCorpus sentiWordNet=new SentimentCorpus("swn.txt");
            String text="Hello, my name is Josh so fuck you faggot, that is offensive";
            LinkedList<Sentiment> sentiments=new LinkedList<Sentiment>();
            String tagged = tagger.tagString(text);
            System.out.println(tagged);
            tagged=tagged.replace('_','#');
            String[]tokens=tagged.split(" ");
            for(String token:tokens){
                try{
                    sentiments.addLast(new Sentiment(token.split("#")[0],sentiWordNet.extract(token)));
                }
                catch(Exception e){
                    
                }
            }
            for(Sentiment sentiment:sentiments){
                System.out.println(sentiment.word+"#"+sentiment.sentimentValue);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,"sentiWordNet/POSTagger not configured");
        }
    }
}
