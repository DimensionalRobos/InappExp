package org.drobos;


/**
 * Contains Models used For Sentiment Analysis
 */

import java.util.LinkedList;

/**
 *
 * @author Daikaiser
 */
class Report {

    public String sentences;
    public String tokens;
    public String postag;
    public String nertag;
    public String ngram;
    public String ria;
    public Expression[] expressions;
}

class Expression {

    public String word;
    public LinkedList<String> baseForms = new LinkedList<String>();
    public boolean isInappropriate = false;
    public String postag;
    public String nertag;
    public double value = 0;
    public LinkedList<String> definitions = new LinkedList<String>();
    public LinkedList<String> urbanDefinitions = new LinkedList<String>();
    public boolean isInvoked = false;
    public double sentimentValue = 0;

    public Expression(String word, String postag) {
        this.word = word;
        this.postag = postag;
    }
}

class WordList extends LinkedList<String> {

    @Override
    public boolean contains(Object obj) {
        for (String word : this) {
            if (obj instanceof String) {
                if (word.equals((String) obj)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean contains(String s) {
        for (String word : this) {
            if (word.equals(s)) {
                return true;
            }
        }
        return false;
    }
    
    public WordList sort(){
        WordList sortedList=new WordList();
        int maxLen=0;
        for(String word:this){
            if(word.length()>maxLen){
                maxLen=word.length();
            }
        }
        for(int i=maxLen;i>=0;i--){
            for(String word:this){
                if(word.length()==i){
                    sortedList.add(word);;
                }
            }
        }
        return sortedList;
    }
}

class ExpressionList extends LinkedList<Sentiment> {

    public boolean contains(Sentiment s) {
        try {
            for (Sentiment sentiments : this) {
                if (sentiments.word.equals(s.word) & s.sentimentValue == sentiments.sentimentValue) {
                    return true;
                }
            }
        } catch (Exception e) {

        }
        return false;
    }
}

class Sentiment {

    public double sentimentValue;
    public String word;

    public Sentiment(String word, double sentimentValue) {
        this.word = word.toLowerCase();
        this.sentimentValue = sentimentValue;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Sentiment) {
            return (this.word.equals(((Sentiment) obj).word)
                    & this.sentimentValue == ((Sentiment) obj).sentimentValue);
        } else {
            return false;
        }
    }
}

public class Models {

}
