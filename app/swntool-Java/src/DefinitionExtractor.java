/**
 * Pulls out data in WordNet Dictionary
 */

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;
import java.util.LinkedList;

/**
 *
 * @author Daikaiser
 */
public class DefinitionExtractor {
    public static LinkedList<String> extract(String input){
        //Synset to POSTag
        //1=n
        //2=v
        //4=r
        //5=j->a in SWN & jwnl
        System.setProperty("wordnet.database.dir",Config.WordNetPath);
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        Synset[] synsets = database.getSynsets(input);
        LinkedList<String> extractedData=new LinkedList<String>();
        for(Synset synset:synsets){
            System.out.println(synset.getDefinition());
            extractedData.addLast(synset.getDefinition());
        }
        return extractedData;
    }
    public static LinkedList<String> resample(String input,String definition){
        System.setProperty("wordnet.database.dir", Config.WordNetPath);
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        Synset[] synsets = database.getSynsets(input);
        LinkedList<String> extractedData=new LinkedList<String>();
        for(Synset synset:synsets){
            if(definition.equals(synset.getDefinition()))
            for(String word:synset.getWordForms()){
                extractedData.add(word);
            }
        }
        return extractedData;
    }
}
