package org.drobos;


/**
 * Tokenizes Sentences
 */

/**
 *
 * @author Daikaiser
 */
public class Tokenizer {

    public static String tokenize(String taggedText) {
        String s = "";
        String[] tokenSplits = taggedText.split(" ");
        for (String tokenSplit : tokenSplits) {
            s += tokenSplit.split("_")[0] + "\n";
        }
        return s;
    }
}
