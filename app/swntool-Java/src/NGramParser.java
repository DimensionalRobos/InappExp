/**
 *
 * @author Daikaiser
 */

public class NGramParser {

    public static void parse(Expression[] expressions) {
        WordList NGrams = NGDAO.extractNGrams();
        for (int i = 0; i < expressions.length; i++) {
            for (String NGram : NGrams) {
                System.err.println(NGram);
                String[] NGramPattern = NGram.split(" ");
                for (String NGramUnit : NGramPattern) {
                    try {
                        NGramUnit = NGramUnit.substring(0, 2);
                    } catch (Exception e) {

                    }
                }
                if (testNGramInvoke(expressions, NGramPattern, i)) {
                    for (int j = 0; j < NGramPattern.length; j++) {
                        expressions[i + j].isInvoked = true;
                    }
                    break;
                }
            }
        }
    }

    public static boolean testNGramInvoke(Expression[] expressions, String[] NGramPattern, int startIndex) {
        boolean isInvoked = false;
        for (int i = 0; i < NGramPattern.length; i++) {
            if (expressions[startIndex + i].postag.startsWith(NGramPattern[i])) {
                isInvoked = true;
            } 
            else {
                isInvoked = false;
                break;
            }
        }
        return isInvoked;
    }
}
