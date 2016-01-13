/**
 * Entity Recognition Tagger for targeting expressions
 */

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ling.CoreLabel;

/**
 *
 * @author Daikaiser
 */
public class NERTagger {

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