import edu.smu.tspell.wordnet.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daikaiser
 */

class Expression{
    public String word;
    public boolean isInappropriate=false;
    public String postag;
    
    public Expression(String word,String postag){
        this.word=word;
        this.postag=postag;
    }
}

public class NGramGenerator {
    public static void main(String[] args){
        String input="Fuck you";
        String taggedInput=POSTagger.tag(input);
        String[] tokens = taggedInput.split(" ");
        Expression[] expressions=new Expression[tokens.length];
        for (int i=0;i<tokens.length;i++){
            expressions[i]=new Expression(tokens[i].split("_")[0],tokens[i].split("_")[1]);
        }
        for(Expression expression:expressions){
            try {
                for (String scrape : UrbanDictScraper.scrape(expression.word)) {
                    Sentiment senti = SentiAnalyzer.analyze(POSTagger.tag(scrape));
                    if (senti != null) {
                        //TODO InappExp Candidation Analysis here
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Learner.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                for (String look : DefinitionExtractor.extract(expression.word)) {
                    Sentiment senti = SentiAnalyzer.analyze(POSTagger.tag(look));
                    if(senti!=null){
                        //TODO InappExp Candidation Analysis here
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Learner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
