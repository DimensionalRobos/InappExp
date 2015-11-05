import edu.mit.jwi.Dictionary;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daikaiser
 */
public class Stemmer {
    public static LinkedList<String> stem(String word, String tag) {
        Dictionary dict = new Dictionary(new File(Config.WordNetPath));
        try {
            dict.open();
        } catch (IOException ex) {
            Logger.getLogger(SentiAnaGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        WordnetStemmer stemmer = new WordnetStemmer(dict);
        POS postag = null;
        if (tag.equals("a")) {
            postag = POS.ADJECTIVE;
        }
        if (tag.equals("v")) {
            postag = POS.VERB;
        }
        if (tag.equals("n")) {
            postag = POS.NOUN;
        }
        if (tag.equals("r")) {
            postag = POS.ADVERB;
        }
        List<String> stems = stemmer.findStems(word, postag);
        return new LinkedList<String>(stems);
    }
}
