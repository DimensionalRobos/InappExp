package org.drobos;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generates Definitions to be utilized for Contextual Analysis
 */
/**
 *
 * @author Daikaiser
 */
public class ContextGenerator {

    public static void generate(Expression expression) {
        double sum = 0;
        int numberOfDefs = 0;
        try {
            for (String look : DefinitionExtractor.extract(expression.word)) {
                Sentiment senti = SentiAnalyzer.analyze(POSTagger.tag(look));
                if (senti != null) {
                    sum += senti.sentimentValue;
                    numberOfDefs++;
                    expression.definitions.add(look);
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
                    expression.urbanDefinitions.add(scrape);
                    numberOfDefs++;
                }
            }
            if (expression.urbanDefinitions.isEmpty()) {
                for (String baseForm : expression.baseForms) {
                    for (String scrape : UrbanDictScraper.scrape(baseForm)) {
                        Sentiment senti = SentiAnalyzer.analyze(POSTagger.tag(scrape));
                        if (senti != null) {
                            sum += senti.sentimentValue;
                            expression.urbanDefinitions.add(scrape);
                            numberOfDefs++;
                        }
                        if (!expression.urbanDefinitions.isEmpty()) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            
        }
        sum /= numberOfDefs;
        expression.value = sum;
        if (InappExp.isInappropriate(expression)) {
            expression.isInappropriate = true;
        }
    }
}