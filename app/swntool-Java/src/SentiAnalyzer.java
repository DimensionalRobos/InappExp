
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

    public static Sentiment analyze(String taggedDoc) {
        if (taggedDoc.trim() == "") {
            return null;
        }
        try {
            SentimentCorpus sentiWordNet = new SentimentCorpus("swn.txt");
            LinkedList<Sentiment> sentiments = new LinkedList<Sentiment>();
            taggedDoc = taggedDoc.toLowerCase();
            taggedDoc = taggedDoc.replace('_', '#');
            String[] tokens = taggedDoc.split(" ");
            for (String token : tokens) {
                try {
                    sentiments.addLast(new Sentiment(token.split("#")[0], sentiWordNet.extract(token)));
                } catch (Exception e) {
                    try {
                        LinkedList<String> stemList = Stemmer.stem(token.split("#")[0], token.split("#")[1]);
                        for (String stem : stemList) {

                            System.err.println(stem);
                            sentiments.addLast(new Sentiment(stem, sentiWordNet.extract(stem + "#" + token.split("#")[1])));
                            break;
                        }
                    } catch (Exception ex) {

                    }
                }
            }
            for (Sentiment sentiment : sentiments) {
                System.out.println(sentiment.word + "#" + sentiment.sentimentValue);
//                DefinitionExtractor.extract(sentiment.word);
//                try{
//                    UrbanDictScraper.scrape(sentiment.word);
//                }
//                catch(IOException e){
//                    System.out.println("Cannot get the Urban Dictionary definition of "+sentiment.word+" due to missing Internet Connection");
//                }
            }
            return getMainDescriptor(sentiments);

            //PlotTool.threshPlot(sentiments);
            //PlotTool.funcPlot(sentiments);
        } catch (IOException ex) {
            Logger.getLogger(SentiAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SentiAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Sentiment getMainDescriptor(LinkedList<Sentiment> sentiments) {
        try {
            Sentiment mainDescriptor = sentiments.getFirst();
            for (Sentiment descriptor : sentiments) {
                if (descriptor.sentimentValue < mainDescriptor.sentimentValue) {
                    mainDescriptor = descriptor;
                }
            }
            if (mainDescriptor.sentimentValue >= 0) {
                throw new Exception();
            } else {
                System.err.println(mainDescriptor.word + "#" + mainDescriptor.sentimentValue);
                return mainDescriptor;
            }
        } catch (Exception ex) {

        }
        return null;
    }

    public static double getMin(LinkedList<Sentiment> sentiments) {
        try {
            Sentiment mainDescriptor = sentiments.getFirst();
            for (Sentiment descriptor : sentiments) {
                if (descriptor.sentimentValue < mainDescriptor.sentimentValue) {
                    mainDescriptor = descriptor;
                }
            }
            //System.err.println(mainDescriptor.word + "#" + mainDescriptor.sentimentValue);
            return mainDescriptor.sentimentValue;
        } catch (Exception ex) {

        }
        return 0;
    }

    public static double getMax(LinkedList<Sentiment> sentiments) {
        try {
            Sentiment mainDescriptor = sentiments.getFirst();
            for (Sentiment descriptor : sentiments) {
                if (descriptor.sentimentValue > mainDescriptor.sentimentValue) {
                    mainDescriptor = descriptor;
                }
            }
            //System.err.println(mainDescriptor.word + "#" + mainDescriptor.sentimentValue);
            return mainDescriptor.sentimentValue;
        } catch (Exception ex) {

        }
        return 0;
    }

    public static double getMean(LinkedList<Sentiment> sentiments) {
        
        double sum = 0;
        for (Sentiment descriptor : sentiments) {
            sum += descriptor.sentimentValue;
        }
        return sum/sentiments.size();
    }
}
