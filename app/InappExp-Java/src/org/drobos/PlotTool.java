package org.drobos;


/**
 * Produces Plot of the Training Data Information
 */

import java.awt.Color;
import javax.swing.*;
import java.util.*;
import org.math.plot.Plot2DPanel;

/**
 *
 * @author Daikaiser
 */
public class PlotTool {

    static void learnPlot(LinkedList<Sentiment> sentiments,String title) throws Exception {
        double[] y = new double[sentiments.size()];
        double[] x = new double[sentiments.size()];
        int i = 0;
        for (Sentiment sentiment : sentiments) {
            y[i] = sentiment.sentimentValue;
            x[i] = i;
            i++;
        }

        for(Sentiment sentiment:sort(sentiments)){
            System.out.println(sentiment.word+"#"+sentiment.sentimentValue+"\n");
        }
        
        //Plot2DPanel scatteredPlot = new Plot2DPanel();
        Plot2DPanel distributionPlot = new Plot2DPanel();

        //scatteredPlot.addScatterPlot("InappExp Fluctuations", Color.PINK, x, y);

        Arrays.sort(y);
        distributionPlot.addLinePlot("InappExp Fluctuations", Color.BLUE, x, y);
        distributionPlot.addScatterPlot("InappExp Fluctuations", Color.PINK, x, y);

        System.err.println("Sample Count:" + sentiments.size());

        for (i = 0; i < sentiments.size(); i++) {
            y[i] = SentiAnalyzer.getMin(sentiments);
        }

        System.err.println("Minimum is " + SentiAnalyzer.getMin(sentiments));

        //scatteredPlot.addLinePlot("Min", java.awt.Color.RED, x, y);
        distributionPlot.addLinePlot("Min", java.awt.Color.RED, x, y);

        for (i = 0; i < sentiments.size(); i++) {
            y[i] = SentiAnalyzer.getMax(sentiments);
        }

        System.err.println("Maximum is " + SentiAnalyzer.getMax(sentiments));

        //scatteredPlot.addLinePlot("Max", java.awt.Color.GREEN, x, y);
        distributionPlot.addLinePlot("Max", java.awt.Color.GREEN, x, y);

        for (i = 0; i < sentiments.size(); i++) {
            y[i] = SentiAnalyzer.getMean(sentiments);
        }

        //scatteredPlot.addLinePlot("Average", java.awt.Color.MAGENTA, x, y);
        distributionPlot.addLinePlot("Average", java.awt.Color.ORANGE, x, y);

        System.err.println("Mean is " + SentiAnalyzer.getMean(sentiments));

        System.err.println("Variance is " + SentiAnalyzer.getVariance(sentiments));

        System.err.println("RMS is " + SentiAnalyzer.getRMS(sentiments));
        
        System.err.println("R^2 is " + Regression.regression(y, x));

        //JFrame scatterFrame = new JFrame("Support Vector Scatter Plot");
        //scatterFrame.setContentPane(scatteredPlot);
        //scatterFrame.setVisible(true);
        //scatterFrame.setSize(600, 600);

        JFrame distributionFrame = new JFrame(title);
        distributionFrame.setContentPane(distributionPlot);
        distributionFrame.setVisible(true);
        distributionFrame.setSize(600, 600);
    }

    static void expressionPlot(LinkedList<Expression> expressions, String title) throws Exception {
        //Plot2DPanel scatteredPlot = new Plot2DPanel();
        Plot2DPanel distributionPlot = new Plot2DPanel();
        ExpressionList trainingData = BWDAO.getSentiments();
        double[] y = new double[expressions.size()];
        double[] x = new double[expressions.size()];
        int i = 0;
        for (Expression expression:expressions) {
            y[i] = expression.sentimentValue;
            x[i] = i;
            i++;
        }
        

        distributionPlot.addLinePlot("Sentiment Fluctuations", Color.BLUE, x, y);
        
        i=0;
        for (Expression expression:expressions) {
            y[i] = expression.value;
            i++;
        }
        distributionPlot.addLinePlot("Inappropriateness Fluctuation", java.awt.Color.RED, x, y);
        
        i=0;
        for (Expression expression:expressions) {
            y[i] = SentiAnalyzer.getMean(trainingData);
            i++;
        }
        distributionPlot.addLinePlot("Inappropriateness BorderLine", java.awt.Color.BLACK, x, y);
        
        //JFrame scatterFrame = new JFrame("Support Vector Scatter Plot");
        //scatterFrame.setContentPane(scatteredPlot);
        //scatterFrame.setVisible(true);
        //scatterFrame.setSize(600, 600);

        JFrame distributionFrame = new JFrame(title);
        distributionFrame.setContentPane(distributionPlot);
        distributionFrame.setVisible(true);
        distributionFrame.setSize(600, 600);
    }
    
    public static LinkedList<Sentiment> sort(LinkedList<Sentiment> unsortedList) {
        for (int x = 0; x < unsortedList.size(); x++) {
            for (int y = x + 1; y < unsortedList.size(); y++) {
                if (unsortedList.get(x).sentimentValue > unsortedList.get(y).sentimentValue) {
                    Sentiment temp = new Sentiment(unsortedList.get(x).word, unsortedList.get(x).sentimentValue);

                    unsortedList.get(x).sentimentValue = unsortedList.get(y).sentimentValue;
                    unsortedList.get(x).word = unsortedList.get(y).word;

                    unsortedList.get(y).sentimentValue = temp.sentimentValue;
                    unsortedList.get(x).word = temp.word;
                }
            }
        }
        return unsortedList;
    }
}
