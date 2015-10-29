
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Daikaiser
 */
class WordList extends LinkedList<String> {

    @Override
    public boolean contains(Object obj) {
        for (String word : this) {
            if (obj instanceof String) {
                if (word.equals((String) obj)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contains(String s) {
        for (String word : this) {
            if (word.equals(s)) {
                return true;
            }
        }
        return false;
    }
}

class ExpressionList extends LinkedList<Sentiment> {

    public boolean contains(Sentiment s) {
        for (Sentiment sentiments : this) {
            if (sentiments.word.equals(s.word) & s.sentimentValue == sentiments.sentimentValue) {
                return true;
            }
        }
        return false;
    }
}

class Sentiment {

    public double sentimentValue;
    public String word;

    public Sentiment(String word, double sentimentValue) {
        this.word = word;
        this.sentimentValue = sentimentValue;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Sentiment) {
            return (this.word.equals(((Sentiment) obj).word)
                    & this.sentimentValue == ((Sentiment) obj).sentimentValue);
        } else {
            return false;
        }
    }
}

public class SimpleSentiAna {

    static String taggerPath = "models/english-caseless-left3words-distsim.tagger";
    static MaxentTagger tagger = new MaxentTagger(taggerPath);

    public static void main(String[] args) {
        try {
            SentimentCorpus sentiWordNet = new SentimentCorpus("swn.txt");
            String text = "Hello, my name is Josh so fuck you faggot, that is offensive";
            LinkedList<Sentiment> sentiments = new LinkedList<Sentiment>();
            String tagged = tagger.tagString(text);
            System.out.println(tagged);
            tagged = tagged.replace('_', '#');
            String[] tokens = tagged.split(" ");
            for (String token : tokens) {
                try {
                    sentiments.addLast(new Sentiment(token.split("#")[0], sentiWordNet.extract(token)));
                } catch (Exception e) {

                }
            }
            for (Sentiment sentiment : sentiments) {
                System.out.println(sentiment.word + "#" + sentiment.sentimentValue);
            }
            PlotTool.funcPlot(sentiments);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "sentiWordNet/POSTagger not configured");
        } catch (Exception ex) {
            Logger.getLogger(SimpleSentiAna.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
