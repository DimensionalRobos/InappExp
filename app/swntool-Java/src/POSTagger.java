/**
 * Produces POSTags based on Stanford NLP POStagger Module
 */

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 *
 * @author Daikaiser
 */
public class POSTagger {

    public static String tag(String doc) {
        try {
            String taggerPath = "models/english-caseless-left3words-distsim.tagger";
            MaxentTagger tagger = new MaxentTagger(taggerPath);
            String tagged = tagger.tagString(doc);
            return tagged;
        } catch (Exception e) {

        }
        return "";
    }
}
