
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

    static void funcPlot(LinkedList<Sentiment> sentiments,String title) throws Exception {
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

        double []linX=new double[2];
        double []linY=new double[2];
        linY[0]=0;
        linY[1]=SentiAnalyzer.getMin(sentiments);
        linX[0]=sentiments.size();
        linX[1]=SentiAnalyzer.getMax(sentiments);
        
        distributionPlot.addLinePlot("Regression", Color.GRAY, linX, linY);
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
