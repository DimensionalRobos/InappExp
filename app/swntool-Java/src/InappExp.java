/**
 *
 * @author Daikaiser
 */
public class InappExp {
    public static boolean isInappropriate(Expression expression){
        ExpressionList trainingData = MLDAO.getSentiments();
        ExpressionList basisData = BWDAO.getSentiments();
        if(BWDAO.exists(expression.word))return true;
        for(String baseForm:expression.baseForms)
            if(BWDAO.exists(baseForm))return true;
        return expression.value<SentiAnalyzer.getMean(trainingData);
    }
    public static double threshold(){
        ExpressionList trainingData = MLDAO.getSentiments();
        ExpressionList basisData = BWDAO.getSentiments();
        return SentiAnalyzer.getMean(trainingData);
    }
    public static void analyzeSemantics(ExpressionList expressions){
        
    }
}
