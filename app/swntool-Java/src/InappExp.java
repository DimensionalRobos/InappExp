
/**
 * Expression Inappropriateness Evaluation
 */
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Daikaiser
 */
public class InappExp {

    public static boolean isInappropriate(Expression expression) {
        ExpressionList trainingData = BWDAO.getSentiments();
        if (EWDAO.exists(expression.word)) {
            expression.value = 0;
            return false;
        }
        if (BWDAO.exists(expression.word)) {
            expression.value = BWDAO.extract(expression.word);
            return true;
        }
        for (String baseForm : expression.baseForms) {
            if (EWDAO.exists(baseForm)) {
                expression.value = 0;
                return false;
            }
            if (BWDAO.exists(baseForm)) {
                expression.value = BWDAO.extract(baseForm);
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
        Report report = new Report();
        report.postag = posTaggedInput;
        report.nertag = nerTaggedInput;
        String[] tokens = posTaggedInput.split(" ");
        String[] nerTokens = nerTaggedInput.split(" ");
        LinkedList<Expression> uniqueExpressions = new LinkedList<Expression>();
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
            if (shouldBeDefined(expression)) {
                ContextGenerator.generate(expression);
            }
        }
        report.expressions = expressions;
        String ngram = "";
        for (Expression expression : expressions) {
            if (expression.isInappropriate) {
                System.out.print("IE ");
                ngram += "IE ";
                expression.postag = "IE";
            } else {
                System.out.print(expression.postag + " ");
                try {
                    ngram += expression.postag.substring(0, 2) + " ";
                } catch (Exception e) {
                    ngram += expression.postag + " ";
                }
            }
        }
        ngram += "\nNGrams:\n";
        for (int i = 0; i < expressions.length; i++) {
            try {
                ngram += NGramGenerator.generateNGram(expressions, i - 1, 2);
            } catch (Exception e) {

            }
            try {
                ngram += NGramGenerator.generateNGram(expressions, i - 2, 3);
            } catch (Exception e) {

            }
            try {
                ngram += NGramGenerator.generateNGram(expressions, i - 3, 4);
            } catch (Exception e) {

            }
            try {
                ngram += NGramGenerator.generateNGram(expressions, i - 4, 5);
            } catch (Exception e) {

            }
            try {
                ngram += NGramGenerator.generateNGram(expressions, i, 2);
            } catch (Exception e) {

            }
            try {
                ngram += NGramGenerator.generateNGram(expressions, i, 3);
            } catch (Exception e) {

            }
            try {
                ngram += NGramGenerator.generateNGram(expressions, i, 4);
            } catch (Exception e) {

            }
            try {
                ngram += NGramGenerator.generateNGram(expressions, i, 5);
            } catch (Exception e) {

            }
        }
        report.ngram = ngram;
        for (Expression expression : expressions) {
            System.err.println(expression.value);
        }
        NGramParser.parse(expressions);
        String ria = "";
        ria += analyzeSemantics(expressions);
        report.ria = ria;
        for (Expression expression : expressions) {
            if (!expression.isInvoked) {
                s += expression.word + " ";
            } else if (expression.isInappropriate & expression.isInvoked) {
                s += "<Inapp>" + expression.word + "</Inapp> ";
            } else {
                s += expression.word + " ";
            }
        }
        new LogsUI(report);
        MessageBox.show(s);
        return s;
    }

    public static boolean unique(Expression e, LinkedList<Expression> expressions) {
        for (Expression expression : expressions) {
            if (e.word == expression.word) {
                return false;
            }
        }
        return true;
    }

    public static boolean shouldBeTested(Expression e) {
        if (!isSymbolToken(e.word) & !EWDAO.exists(e.word) & !e.nertag.equals("PERSON") & !e.nertag.equals("LOCATION")) {
            return e.postag.startsWith("NN") | e.postag.startsWith("FW") | e.postag.startsWith("RB") | e.postag.startsWith("VB") | e.postag.startsWith("JJ");
        }
        return false;
    }

    public static boolean shouldBeDefined(Expression e) {
        if (!isSymbolToken(e.word) & !e.nertag.equals("PERSON") & !e.nertag.equals("LOCATION")) {
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
                String tempString = "";
                tempString += "(";
                for (; i < expressions.length; i++) {
                    try {
                        if (expressions[i].postag.equals(".")) {
                            break;
                        }
                        if (expressions[i].isInvoked) {
                            tempString += " " + expressions[i].word + " ";
                            testExpressions.add(expressions[i]);
                        } else {
                            break;
                        }
                    } catch (Exception e) {

                    }
                }
                tempString += ")";
                if (!testExpressions.isEmpty()) {
                    if (!inappropriate(testExpressions)) {
                        tempString += " is not Inappropriate";
                        for (i = start; i < expressions.length; i++) {
                            try {
                                if (expressions[i].postag.equals(".")) {
                                    break;
                                }
                                if (!expressions[i].isInvoked) {
                                    break;
                                } else {
                                    expressions[i].isInvoked = false;
                                }
                            } catch (Exception e) {

                            }
                        }
                    } else {
                        tempString += " is Inappropriate";
                    }
                    s += tempString + "\n";
                }
            }
        }
        return s;
    }

    public static boolean inappropriate(LinkedList<Expression> expressions) {
        boolean inappropriateness = probablyInappropriate(expressions);
        if (passiveVoice(expressions)) {
            boolean hasTarget = false;
            LinkedList<Expression> reversedExpressions = LinkedListUtils.reverse(expressions);
            for (Expression expression : reversedExpressions) {
                if (negativeWord(expression)) {
                    inappropriateness = false;
                }
                if (expression.isInappropriate) {
                    if (hasTarget) {
                        inappropriateness = true;
                        continue;
                    }
                }
                if (targetableUnit(expression)) {
                    hasTarget = true;
                }
            }
        } else {
            boolean hasTarget = false;
            for (Expression expression : expressions) {
                if (negativeWord(expression)) {
                    inappropriateness = false;
                }
                if (expression.isInappropriate) {
                    if (hasTarget) {
                        inappropriateness = true;
                        continue;
                    }
                }
                if (targetableUnit(expression)) {
                    hasTarget = true;
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
        return expression.word.equalsIgnoreCase("not") | expression.word.equalsIgnoreCase("n't") | expression.word.equalsIgnoreCase("never");
    }

    public static boolean probablyInappropriate(LinkedList<Expression> expressions) {
        boolean inappropriateness = false;
        int inappropriateCount = 0;
        for (Expression expression : expressions) {
            if (expression.isInappropriate) {
                inappropriateCount++;
            }
            if (expression.isInappropriate & expression.value < threshold()) {
                inappropriateness = true;
            }
        }
        return inappropriateness | ((double) inappropriateCount / expressions.size()) > 0.4;
    }

    public static boolean targetableUnit(Expression expression) {
        if (expression.postag.contains("PR") & (!firstPerson(expression))) {
            return true;
        }
        if (expression.postag.contains("NN")) {
            if (expression.nertag.equalsIgnoreCase("person") | expression.nertag.equalsIgnoreCase("location")) {
                return true;
            }
        }
        if (expression.word.equalsIgnoreCase("somebody")
                | expression.word.equalsIgnoreCase("someone")
                | expression.word.equalsIgnoreCase("anybody")
                | expression.word.equalsIgnoreCase("anyone")
                | expression.word.equalsIgnoreCase("everybody")
                | expression.word.equalsIgnoreCase("everyone")) {
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
        return false;
    }

    public static boolean passiveVoice(LinkedList<Expression> expressions) {
        if (expressions.getFirst().word.equalsIgnoreCase("this")) {
            return false;
        }
        boolean isSubjectObject = false;
        for (Expression expression : expressions) {
            if (expression.word.equalsIgnoreCase("been") | expression.word.equalsIgnoreCase("being")) {
                return false;
            }
            if (expression.word.equalsIgnoreCase("will") | expression.word.equalsIgnoreCase("is") | expression.word.equalsIgnoreCase("are") | expression.word.equalsIgnoreCase("was") | expression.word.equalsIgnoreCase("were")) {
                return true;
            }
            if (isSubjectObject) {
                if (expression.postag.startsWith("VB")|expression.postag.equalsIgnoreCase("IE")) {
                    return true;
                }
                if (isSubjectObject(expression)) {
                    continue;
                } else {
                    return false;
                }
                
            }
            if (isSubjectObject(expression)) {
                isSubjectObject = true;
            }
        }
        return false;
    }

    public static boolean firstPerson(Expression expression) {
        return expression.word.equalsIgnoreCase("i")
                | expression.word.equalsIgnoreCase("we")
                | expression.word.equalsIgnoreCase("me")
                | expression.word.equalsIgnoreCase("mine")
                | expression.word.equalsIgnoreCase("us")
                | expression.word.equalsIgnoreCase("our")
                | expression.word.equalsIgnoreCase("ours")
                | expression.word.equalsIgnoreCase("yours")
                | expression.word.equalsIgnoreCase("it")
                | expression.word.equalsIgnoreCase("its")
                | expression.word.equalsIgnoreCase("theirs")
                | expression.word.equalsIgnoreCase("your")
                | expression.word.equalsIgnoreCase("his")
                | expression.word.equalsIgnoreCase("her")
                | expression.word.equalsIgnoreCase("their");
    }

    public static boolean isSubjectObject(Expression expression) {
        return expression.postag.startsWith("NN")
                | expression.postag.startsWith("PR");
    }
}
