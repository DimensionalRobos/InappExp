
import edu.smu.tspell.wordnet.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daikaiser
 */
public class NGramGenerator {

    public static String generateAndRecognize(String input) {
        String s = "";
        String taggedInput = POSTagger.tag(input);
        ExpressionList trainingData = MLDAO.getSentiments();
        ExpressionList basisData=BWDAO.getSentiments();
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
                if (sum <= (SentiAnalyzer.getMean(trainingData)+SentiAnalyzer.getMean(basisData))/2) {
                    expression.isInappropriate = true;
                }
            }
        }
        for (Expression expression : expressions) {
            if (expression.isInappropriate) {
                System.out.print("IE ");
                s += "IE ";
                expression.postag = "IE";
            } else {
                System.out.print(expression.postag + " ");
                try {
                    s += expression.postag.substring(0, 2) + " ";
                } catch (Exception e) {
                    s += expression.postag + " ";
                }
            }
        }
        s += "\nNGrams:\n";
        for (int i = 0; i < expressions.length; i++) {
            if (expressions[i].isInappropriate) {
                try {
                    s += generateNGram(expressions, i, 2);
                    s += generateNGram(expressions, i, 3);
                } catch (Exception e) {

                }
            }
            try {
                if (expressions[i + 2].isInappropriate) {
                    s += generateNGram(expressions, i, 3);
                }
            } catch (Exception e) {

            }
            try {
                if (expressions[i + 1].isInappropriate) {
                    s += generateNGram(expressions, i, 2);
                }
            } catch (Exception e) {

            }
        }
        for (Expression expression : expressions) {
            System.err.println(expression.value);
        }
        NGramParser.parse(expressions);
        s += "\n";
        for (Expression expression : expressions) {
            if (expression.isInvoked) {
                System.out.println(expression.word);
            }
            if (expression.isInappropriate&expression.isInvoked) {
                s += "<Inapp>" + expression.word + "</Inapp> ";
            } else {
                s += expression.word + " ";
            }
        }
        return s;
    }

    public static String generateAndLearn(String input) {
        String s = "";
        String taggedInput = POSTagger.tag(input);
        ExpressionList trainingData = BWDAO.getSentiments();
        ExpressionList basisData=BWDAO.getSentiments();
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
                    Logger.getLogger(Learner.class
                            .getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(Learner.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                sum /= numberOfDefs;
                expression.value = sum;
                if (sum <= (SentiAnalyzer.getMean(trainingData)+SentiAnalyzer.getMean(basisData))/2) {
                    expression.isInappropriate = true;
                }
            }
        }
        for (Expression expression : expressions) {
            if (expression.isInappropriate) {
                System.out.print("IE ");
                s += "IE ";
                expression.postag = "IE";
            } else {
                System.out.print(expression.postag + " ");
                try {
                    s += expression.postag.substring(0, 2) + " ";
                } catch (Exception e) {
                    s += expression.postag + " ";
                }
            }
        }
        s += "\nNGrams:\n";
        for (int i = 0; i < expressions.length; i++) {
            if (expressions[i].isInappropriate) {
                try {
                    s += generateNGram(expressions, i, 2);
                    NGDAO.write(generateNGram(expressions, i, 2));
                    s += generateNGram(expressions, i, 3);
                    NGDAO.write(generateNGram(expressions, i, 3));
                } catch (Exception e) {

                }
            }
            try {
                if (expressions[i + 2].isInappropriate) {
                    s += generateNGram(expressions, i, 3);
                    NGDAO.write(generateNGram(expressions, i, 3));
                }
            } catch (Exception e) {

            }
            try {
                if (expressions[i + 1].isInappropriate) {
                    s += generateNGram(expressions, i, 2);
                    NGDAO.write(generateNGram(expressions, i, 2));
                }
            } catch (Exception e) {

            }
        }
        for (Expression expression : expressions) {
            System.err.println(expression.value);
        }
        return s;
    }

    private static boolean shouldBeTested(String postag) {
        return postag.startsWith("NN") | postag.startsWith("FW") | postag.startsWith("RB") | postag.startsWith("VB") | postag.startsWith("JJ");
    }

    private static String generateNGram(Expression[] expressions, int startIndex, int countGrams) {
        int noOfTags = 0;
        String s = "";
        for (int i = 0; i < countGrams; i++) {
            try {
                if (expressions[startIndex + i].isInappropriate) {
                    s += "IE ";
                    noOfTags++;
                } else {
                    System.out.print(expressions[startIndex + i].postag + " ");
                    try {
                        s += expressions[startIndex + i].postag.substring(0, 2) + " ";
                        noOfTags++;
                    } catch (Exception e) {
                        s += expressions[startIndex + i].postag + " ";
                        noOfTags++;
                    }
                }
            } catch (Exception e) {

            }
        }
        if (noOfTags > 1) {
            return s + "\n";
        }
        return "";
    }
}
