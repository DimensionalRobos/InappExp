import java.util.LinkedList;

/**
 *
 * @author Daikaiser
 */
public class SentenceSplitter {
    public static String[] split(String taggedText){
        String[] sentences=taggedText.split("_.");
        for(String sentence:sentences){
            sentence+="_.";
        }
        return null;
    }
}
