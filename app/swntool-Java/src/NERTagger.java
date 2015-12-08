import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ling.CoreLabel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daikaiser
 */
public class NERTagger {

    public static void main(String[] args) {
        try {
            System.out.print(tag("Hi, my name is Pooh and I live in New York City"));
        } catch (Exception ex) {
            Logger.getLogger(NERTagger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String tag(String sentence) {
        try {
            String serializedClassifier = "classifiers/english.all.3class.distsim.crf.ser.gz";

            AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);

            return classifier.classifyToString(sentence, "slashTags", false);
        } catch (Exception e) {

        }
        return "";
    }
}
