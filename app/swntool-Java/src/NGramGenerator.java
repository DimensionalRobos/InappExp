
import edu.smu.tspell.wordnet.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

public class NGramGenerator {

    public static String generate(String input) {
        String s="";
        String taggedInput = POSTagger.tag(input);
        ExpressionList trainingData = MLDAO.getSentiments();
        String[] tokens = taggedInput.split(" ");
        Expression[] expressions = new Expression[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            expressions[i] = new Expression(tokens[i].split("_")[0], tokens[i].split("_")[1]);
        }
        for (Expression expression : expressions) {
            double sum = 0;
            int numberOfDefs = 0;
            if (shouldBeTested(expression.postag)) {
                try {
                    for (String look : DefinitionExtractor.extract(expression.word)) {
                        Sentiment senti = SentiAnalyzer.analyze(POSTagger.tag(look));
                        if (senti != null) {
                            sum += senti.sentimentValue;
                            numberOfDefs++;
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Learner.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    for (String scrape : UrbanDictScraper.scrape(expression.word)) {
                        Sentiment senti = SentiAnalyzer.analyze(POSTagger.tag(scrape));
                        if (senti != null) {
                            sum += senti.sentimentValue;
                            numberOfDefs++;
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Learner.class.getName()).log(Level.SEVERE, null, ex);
                }
                sum /= numberOfDefs;
                expression.value = sum;
                if (sum <= SentiAnalyzer.getMean(trainingData)+SentiAnalyzer.getVariance(trainingData)) {
                    expression.isInappropriate = true;
                }
            }
        }
        for (Expression expression : expressions) {
            if (expression.isInappropriate) {
                System.out.print("IE ");
                s+="IE ";
            } else {
                System.out.print(expression.postag+" ");
                try{
                    s+=expression.postag.substring(0,2)+" "; 
                }
                catch(Exception e){
                    s+=expression.postag+" ";
                }
            }
        }
        s+="\nExpressions:";
        for(int i=0;i<expressions.length;i++){
            if(expressions[i].isInappropriate)
            try{
                if(expressions[i+2].isInappropriate){
                    
                }
            }
            catch(Exception e){
                
            }
            try{
                if(expressions[i+1].isInappropriate){
                    
                }
            }
            catch(Exception e){
                    
            }
        }
        for (Expression expression : expressions) {
            System.err.println(expression.value);
        }
        return s;
    }

    private static boolean shouldBeTested(String postag) {
        return postag.startsWith("NN")|postag.startsWith("FW")|postag.startsWith("RB")|postag.startsWith("VB")|postag.startsWith("JJ");
    }
    
    private static String generateNGram(Expression[] expressions,int startIndex,int countGrams){
        return null;
    }
}
