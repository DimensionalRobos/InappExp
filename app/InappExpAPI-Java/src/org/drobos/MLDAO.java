package org.drobos;

/**
 * Machine Learning Data Access Modifier for the feature sets of InappExp
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
/**
 *
 * @author Daikaiser
 */
public class MLDAO {
    public static void write(String feature) throws IOException{
        File file =new File(Config.TrainingData());
        FileWriter f=new FileWriter(file,true);
        f.write(feature);
        f.close();
    }
    
    public static ExpressionList getSentiments(){
        ExpressionList expressions=new ExpressionList();
        try {
            Scanner scan=new Scanner(new File(Config.TrainingData()));
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                Sentiment sentiment = new Sentiment(s.split("#")[0], Double.valueOf(s.split("#")[1]));
                expressions.add(sentiment);
            }
        } catch (Exception ex) {
            
        }
        return expressions;
    }
}
