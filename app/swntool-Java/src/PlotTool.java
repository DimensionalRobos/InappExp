
import java.io.*;
import javax.swing.*;
import java.util.*;
import javafx.scene.paint.Color;
import org.math.plot.Plot2DPanel;

public class PlotTool {

    static void funcPlot(LinkedList<Sentiment> sentiments) throws Exception {
        double[] y = new double[sentiments.size()];
        double[] x = new double[sentiments.size()];
        int i=0;
        for(Sentiment sentiment:sentiments){
            if(sentiment==null)continue;
            y[i]=sentiment.sentimentValue;
            x[i]=i;
            i++;
        }
        
        // create your PlotPanel (you can use it as a JPanel)
        Plot2DPanel plot = new Plot2DPanel();

        // add a line plot to the PlotPanel
        plot.addLinePlot("InappExp Fluctuations",x, y);
        
        for(i=0;i<sentiments.size();i++){
            y[i]=SentiAnalyzer.getMin(sentiments);
        }
        
        System.err.println("Minimum is "+SentiAnalyzer.getMin(sentiments));
        
        plot.addLinePlot("Min",java.awt.Color.RED,x, y);
        
        for(i=0;i<sentiments.size();i++){
            y[i]=SentiAnalyzer.getMax(sentiments);
        }
        
        System.err.println("Maximum is "+SentiAnalyzer.getMax(sentiments));
        
        plot.addLinePlot("Max",java.awt.Color.GREEN,x, y);
        
        for(i=0;i<sentiments.size();i++){
            y[i]=SentiAnalyzer.getMean(sentiments);
        }
        
        plot.addLinePlot("Average",java.awt.Color.ORANGE,x, y);
        
        System.err.println(SentiAnalyzer.getMean(sentiments));
        
        // put the PlotPanel in a JFrame, as a JPanel
        JFrame frame = new JFrame("InappExp Plot");
        frame.setContentPane(plot);
        frame.setVisible(true);
    }
}
