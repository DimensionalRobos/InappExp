package org.drobos;


/**
 * Splits sentences
 */

/**
 *
 * @author Daikaiser
 */
public class SentenceSplitter {

    public static String split(String taggedText) {
        String s = "";
        String[] tokenSplits = taggedText.split(" ");
        for (String tokenSplit : tokenSplits) {
            if (tokenSplit.endsWith("_.")) {
                s += "\n";
                continue;
            }
            s += tokenSplit.split("_")[0] + " ";
        }
        return s;
    }
}
