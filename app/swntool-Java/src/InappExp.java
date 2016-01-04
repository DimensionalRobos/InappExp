
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daikaiser
 */
public class InappExp {

    public static boolean isInappropriate(Expression expression) {
        ExpressionList trainingData = BWDAO.getSentiments();
        if (BWDAO.exists(expression.word)) {
            return true;
        }
        for (String baseForm : expression.baseForms) {
            if (BWDAO.exists(baseForm)) {
                return true;
            }
        }
        return expression.value < SentiAnalyzer.getMean(trainingData);
    }

    public static double threshold() {
        ExpressionList trainingData = BWDAO.getSentiments();
        return SentiAnalyzer.getMean(trainingData);
    }

    public static String recognize(String input) {
        String s = "";
        String posTaggedInput = POSTagger.tag(input);
        String nerTaggedInput = NERTagger.tag(input);
        s += posTaggedInput + "\n";
        s += nerTaggedInput + "\n";
        String[] tokens = posTaggedInput.split(" ");
        String[] nerTokens = nerTaggedInput.split(" ");
        Expression[] expressions = new Expression[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            expressions[i] = new Expression(tokens[i].split("_")[0], tokens[i].split("_")[1]);
        }
        for (int i = 0; i < tokens.length; i++) {
            expressions[i].nertag = nerTokens[i].split("/")[1];
        }
        for (Expression expression : expressions) {
            double sum = 0;
            int numberOfDefs = 0;
            expression.baseForms = Stemmer.stem(expression.word, expression.postag);
            try {
                SentimentCorpus sentiWordNet = new SentimentCorpus(Config.SentiWordNetPath);
                expression.sentimentValue = sentiWordNet.extract(input);
            } catch (Exception e) {
                for (String baseForm : expression.baseForms) {
                    try {
                        SentimentCorpus sentiWordNet = new SentimentCorpus(Config.SentiWordNetPath);
                        expression.sentimentValue = sentiWordNet.extract(input);
                        break;
                    } catch (Exception ex) {
                    }
                }
            }
            if (shouldBeTested(expression)) {
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
                            numberOfDefs++;
                        }
                    }
                    for (String baseForm : expression.baseForms) {
                        for (String scrape : UrbanDictScraper.scrape(baseForm)) {
                            Sentiment senti = SentiAnalyzer.analyze(POSTagger.tag(scrape));
                            if (senti != null) {
                                sum += senti.sentimentValue;
                                numberOfDefs++;
                            }
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Learner.class.getName()).log(Level.SEVERE, null, ex);
                }
                sum /= numberOfDefs;
                expression.value = sum;
                if (InappExp.isInappropriate(expression)) {
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
            try {
                s += NGramGenerator.generateNGram(expressions, i - 1, 2);
            } catch (Exception e) {

            }
            try {
                s += NGramGenerator.generateNGram(expressions, i - 2, 3);
            } catch (Exception e) {

            }
            try {
                s += NGramGenerator.generateNGram(expressions, i - 3, 4);
            } catch (Exception e) {

            }
            try {
                s += NGramGenerator.generateNGram(expressions, i - 4, 5);
            } catch (Exception e) {

            }
            try {
                s += NGramGenerator.generateNGram(expressions, i, 2);
            } catch (Exception e) {

            }
            try {
                s += NGramGenerator.generateNGram(expressions, i, 3);
            } catch (Exception e) {

            }
            try {
                s += NGramGenerator.generateNGram(expressions, i, 4);
            } catch (Exception e) {

            }
            try {
                s += NGramGenerator.generateNGram(expressions, i, 5);
            } catch (Exception e) {

            }
        }
        for (Expression expression : expressions) {
            System.err.println(expression.value);
        }
        NGramParser.parse(expressions);
        s += "\n";
        s += analyzeSemantics(expressions);
        for (Expression expression : expressions) {
            if (!expression.isInvoked) {
                s += expression.word + " ";
            } else if (expression.isInappropriate & expression.isInvoked) {
                s += "<Inapp>" + expression.word + "</Inapp> ";
            } else if (isSymbolToken(expression.word)) {
                s = s.substring(0, s.length() - 1) + expression.word + " ";
            } else {
                s += expression.word + " ";
            }
        }
        return s;
    }

    public static boolean shouldBeTested(Expression e) {
        if (!isSymbolToken(e.word) & !EWDAO.exists(e.word) & !e.nertag.equals("PERSON") & !e.nertag.equals("LOCATION")) {
            return e.postag.startsWith("NN") | e.postag.startsWith("FW") | e.postag.startsWith("RB") | e.postag.startsWith("VB") | e.postag.startsWith("JJ");
        }
        return false;
    }

    public static boolean isSymbolToken(String s) {
        for (char c : s.toCharArray()) {
            if (!(Character.isAlphabetic(c) | Character.isDigit(c))) {
                return true;
            }
        }
        return false;
    }

    public static String analyzeSemantics(Expression[] expressions) {
        String s = "\n";
        for (int i = 0; i < expressions.length; i++) {
            if (expressions[i].isInvoked) {
                LinkedList<Expression> testExpressions = new LinkedList<Expression>();
                int start = i;
                s += "(";
                for (; i < expressions.length; i++) {
                    if (expressions[i].postag.equals(".")) {
                        break;
                    }
                    if (expressions[i].isInvoked) {
                        s += " " + expressions[i].word + " ";
                        testExpressions.add(expressions[i]);
                    } else {
                        break;
                    }
                }
                s += ")";
                if (!inappropriate(testExpressions)) {
                    s += " is not Inappropriate";
                    for (i = start; i < expressions.length; i++) {
                        if (expressions[i].isInvoked) {
                            expressions[i].isInvoked = false;
                        } else {
                            break;
                        }
                    }
                } else {
                    s += " is Inappropriate";
                }
                s += "\n";
            }
        }
        return s;
    }

    public static boolean inappropriate(LinkedList<Expression> expressions) {
        boolean inappropriateness = probablyInappropriate(expressions);
        if (passiveVoice(expressions)) {
            for (int i = expressions.size() - 1; i <= 0; i--) {
                if (negativeWord(expressions.get(i))) {
                    inappropriateness = false;
                }
                if (targetableUnit(expressions.get(i))) {
                    inappropriateness = true;
                }
            }
        } else {
            for (Expression expression : expressions) {
                if (negativeWord(expression)) {
                    inappropriateness = false;
                }
                if (targetableUnit(expression)) {
                    inappropriateness = true;
                }
            }
        }
        String s = "";
        for (Expression expression : expressions) {
            s += expression.word + " ";
        }
        try {
            PlotTool.expressionPlot(expressions, s);
        } catch (Exception e) {
        }
        return inappropriateness;
    }

    public static boolean negativeWord(Expression expression) {
        if (expression.postag.startsWith("RB")) {
            return expression.word.equalsIgnoreCase("not") | expression.word.equalsIgnoreCase("n't") | expression.word.equalsIgnoreCase("never");
        }
        return false;
    }

    public static boolean passiveVoice(LinkedList<Expression> expressions) {
        for (Expression expression : expressions) {
            if (expression.postag.contains("VB")) {
                if (expression.word.equalsIgnoreCase("'s") | expression.word.equalsIgnoreCase("is") | expression.word.equalsIgnoreCase("are") | expression.word.equalsIgnoreCase("was") | expression.word.equalsIgnoreCase("were")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean probablyInappropriate(LinkedList<Expression> expressions) {
        boolean inappropriateness = false;
        for (Expression expression : expressions) {
            if (expression.isInappropriate & expression.value < threshold()) {
                inappropriateness = true;
            }
        }
        return inappropriateness;
    }

    public static boolean targetableUnit(Expression expression) {
        if (expression.postag.contains("PR")) {
            return true;
        }
        if (expression.postag.contains("NN")) {
            if (expression.nertag.equalsIgnoreCase("person") | expression.nertag.equalsIgnoreCase("location")) {
                return true;
            }
            for (String definition : expression.definitions) {
                if (definition.contains("man ") | definition.contains("men ") | definition.contains(" he ")
                        | definition.contains(" she ")
                        | definition.contains(" person ")
                        | definition.contains(" people")
                        | definition.contains(" who ")
                        | definition.contains(" girl ")
                        | definition.contains(" boy ")
                        | definition.contains(" man ")
                        | definition.contains(" woman ")) {
                    return true;
                }
            }
        }
        return false;
    }
}
