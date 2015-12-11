/**
 *
 * @author Daikaiser
 */
public class InappExp {
    public static boolean isInappropriate(Expression expression){
        ExpressionList trainingData = MLDAO.getSentiments();
        ExpressionList basisData = BWDAO.getSentiments();
        if(BWDAO.exists(expression.word))return true;
        return (expression.value<(SentiAnalyzer.getMean(basisData)+ SentiAnalyzer.getMean(trainingData))/2);
    }
    public static double threshold(){
        ExpressionList trainingData = MLDAO.getSentiments();
        ExpressionList basisData = BWDAO.getSentiments();
        return (SentiAnalyzer.getMean(basisData)+ SentiAnalyzer.getMean(trainingData))/2;
    }
    public static void analyzeSemantics(ExpressionList expressions){
        
    }
}
