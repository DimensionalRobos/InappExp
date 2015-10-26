import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

/**
 *
 * @author Daikaiser
 */
public class DefinitionExtractor {
    public static void extract(String input){
        //Synset to POSTag
        //1=n
        //2=v
        //4=r
        //5=j->a in SWN & jwnl
        System.setProperty("wordnet.database.dir", "C:\\Program Files\\WordNet\\2.1\\dict");
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        Synset[] synsets = database.getSynsets(input);
        for(Synset synset:synsets){
            System.out.println(input+" means "+synset.getDefinition()+" when in "+synset.getType());
        }
    }
}
