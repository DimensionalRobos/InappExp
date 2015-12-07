/**
 *
 * @author Daikaiser
 */
public class InappExp {
    public static boolean isInappropriate(Expression expression){
        ExpressionList trainingData = BWDAO.getSentiments();
        ExpressionList basisData = BWDAO.getSentiments();
        if(BWDAO.exists(expression.word))return true;
        return (expression.value<(SentiAnalyzer.getMean(basisData)+ SentiAnalyzer.getMean(trainingData))/2);
    }
}
