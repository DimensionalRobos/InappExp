package org.drobos;

/**
 * Contains Computation utilities
 */

import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author Daikaiser
 */

class LinkedListUtils{
    public static LinkedList<Expression>reverse(LinkedList<Expression> expressions){
        LinkedList<Expression>reversedExpression=new LinkedList<Expression>();
        for(Expression expression:expressions){
            reversedExpression.addFirst(expression);
        }
        return reversedExpression;
    }
}

class InputVerifier{
    public static boolean isInjectable(String input){
        return input.contains("/")|input.contains("_");
    }
    public static boolean mustBeEvaluated(Expression[]expressions){
        return expressions.length>1;
    }
}

class MessageBox{
    public static void show(String input){
        JOptionPane.showMessageDialog(null, input);
    }
    public static void showError(String input){
        JOptionPane.showMessageDialog(null, input,"Error",JOptionPane.ERROR_MESSAGE);
    }
}

public class Utils {
    
}
