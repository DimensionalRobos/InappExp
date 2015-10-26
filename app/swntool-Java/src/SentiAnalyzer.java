
import edu.mit.jwi.item.POS;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daikaiser
 */
public class SentiAnalyzer {
    public static void analyze(String taggedDoc){
        try {
            SentimentCorpus sentiWordNet=new SentimentCorpus("swn.txt");
            LinkedList<Sentiment> sentiments=new LinkedList<Sentiment>();
            taggedDoc=taggedDoc.toLowerCase();
            taggedDoc=taggedDoc.replace('_','#');
            String[]tokens=taggedDoc.split(" ");
            for(String token:tokens){
                try{
                    sentiments.addLast(new Sentiment(token.split("#")[0],sentiWordNet.extract(token)));
                }
                catch(Exception e){
                    LinkedList<String> stemList=Stemmer.stem(token.split("#")[0],token.split("#")[1]);
                    for(String stem:stemList){
                        try{
                            System.err.println(stem);
                            sentiments.addLast(new Sentiment(stem,sentiWordNet.extract(stem+"#"+token.split("#")[1])));
                            break;
                        }
                        catch(Exception ex){
                        
                        }
                    }
                }
            }
            for(Sentiment sentiment:sentiments){
                System.out.println(sentiment.word+"#"+sentiment.sentimentValue);
            }
            PlotTool.threshPlot(sentiments);
            PlotTool.funcPlot(sentiments);
        } catch (IOException ex) {
            Logger.getLogger(SentiAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SentiAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
