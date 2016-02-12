package org.drobos;


/**
 *
 * @author Daikaiser
 */
public class Regression {
    public static double summation(double[]X){
        double n=0;
        for(double x:X){
           n+=x; 
        }
        return n;
    }
    
    public static double[] matrixPower(double[]X,double e){
        double[] n=new double[X.length];
        for(int i=0;i<X.length;i++){
           n[i]=Math.pow(X[i],e);
        }
        return n;
    }
    
    public static double[] matrixMultip(double[]X,double[]Y){
        double[]N=new double[X.length];
        for(int i=0;i<X.length;i++){
            N[i]=X[i]*Y[i];
        }
        return N;
    }
    
    public static double regression(double[]X,double[]Y){
        double r=X.length*summation(matrixMultip(X,Y));
        r-=(summation(X)*summation(Y));
        double denum=Math.sqrt(X.length*summation(matrixPower(X,2))-Math.pow(summation(X), 2));
        denum*=Math.sqrt(Y.length*summation(matrixPower(Y,2))-Math.pow(summation(Y), 2));
        return Math.pow(r/denum,2);
    }
}
