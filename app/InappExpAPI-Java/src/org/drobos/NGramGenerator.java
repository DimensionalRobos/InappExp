package org.drobos;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daikaiser
 */
public class NGramGenerator {

    public static String generateAndLearn(String input) {
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
            if (InappExp.shouldBeDefined(expression)) {
                ContextGenerator.generate(expression);
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
                    s += generateNGram(expressions, i - 1, 2);
                    NGDAO.write(generateNGram(expressions, i - 1, 2));
                } catch (Exception e) {

                }
                try {
                    s += generateNGram(expressions, i - 2, 3);
                    NGDAO.write(generateNGram(expressions, i - 2, 3));
                } catch (Exception e) {

                }
                try {
                    s += generateNGram(expressions, i - 3, 4);
                    NGDAO.write(generateNGram(expressions, i - 3, 4));
                } catch (Exception e) {

                }
                try {
                    s += generateNGram(expressions, i - 4, 5);
                    NGDAO.write(generateNGram(expressions, i, 5));
                } catch (Exception e) {

                }
                try {
                    s += generateNGram(expressions, i, 2);
                    NGDAO.write(generateNGram(expressions, i, 2));
                } catch (Exception e) {

                }
                try {
                    s += generateNGram(expressions, i, 3);
                    NGDAO.write(generateNGram(expressions, i, 3));
                } catch (Exception e) {

                }
                try {
                    s += generateNGram(expressions, i, 4);
                    NGDAO.write(generateNGram(expressions, i, 4));
                } catch (Exception e) {

                }
                try {
                    s += generateNGram(expressions, i, 5);
                    NGDAO.write(generateNGram(expressions, i, 5));
                } catch (Exception e) {

                }
            }
        }
        for (Expression expression : expressions) {
            System.err.println(expression.value);
        }
        return s;
    }

    public static String generateNGram(Expression[] expressions, int startIndex, int countGrams) {
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
