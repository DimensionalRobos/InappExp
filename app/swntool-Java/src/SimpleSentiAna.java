import edu.stanford.nlp.tagger.maxent.MaxentTagger;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daikaiser
 */
public class SimpleSentiAna {
    static String taggerPath = "models/english-caseless-left3words-distsim.tagger";
    static MaxentTagger tagger = new MaxentTagger(taggerPath);
    public static void main(String[]args){
        String text="Hello, my name is Josh";
        String tagged = tagger.tagString(text);
        System.out.println(tagged);
    }
}
