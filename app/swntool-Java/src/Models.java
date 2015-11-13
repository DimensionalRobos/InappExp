/**
 * Contains Models used For Sentiment Analysis
 */

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

class Expression {

    public String word;
    public boolean isInappropriate = false;
    public String postag;
    public double value = 0;

    public Expression(String word, String postag) {
        this.word = word;
        this.postag = postag;
    }
}

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

public class Models{
    
}