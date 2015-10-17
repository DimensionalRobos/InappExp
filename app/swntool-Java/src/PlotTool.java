import java.io.*;
import javax.swing.*;
import java.util.*;

public class PlotTool {
    static void threshPlot(LinkedList<Sentiment> sentiments) throws Exception {
        String x="[";
        String y="[";
        for(int i=0;i<sentiments.size();i++){
            x+=sentiments.get(i).sentimentValue;
            y+=i;
            if(i<sentiments.size()-1){
                x+=",";
                y+=",";
            }
            else{
                x+="]";
                y+="]";
            }
        }
        ProcessBuilder builder = new ProcessBuilder(
            "ThresholdGraph",x,y,"0");
        builder.redirectErrorStream(false);
        Process p = builder.start();
    }
    static void funcPlot(LinkedList<Sentiment> sentiments) throws Exception {
        String x="[";
        String y="[";
        for(int i=0;i<sentiments.size();i++){
            x+=sentiments.get(i).sentimentValue;
            y+=i;
            if(i<sentiments.size()-1){
                x+=",";
                y+=",";
            }
            else{
                x+="]";
                y+="]";
            }
        }
        ProcessBuilder builder = new ProcessBuilder(
            "FuncGraph",x,y);
        builder.redirectErrorStream(false);
        Process p = builder.start();
    }
}