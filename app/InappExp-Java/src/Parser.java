import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;
import java.util.LinkedList;
import java.util.List;
/**
 * This is a class that verifies the validity of the constructed English sentence
 * @cheerleader Coleen
 * @author Daikaiser
 */
public class Parser {
    private StepsGUI steps;
    public String parse(String text) {
        text=text.trim();
        String modelPath = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
        String taggerPath = "models/english-caseless-left3words-distsim.tagger";
        
        MaxentTagger tagger = new MaxentTagger(taggerPath);
        LexicalizedParser parser = LexicalizedParser.loadModel(modelPath);

        String tagged = tagger.tagString(text);
        Tree parsed = parser.parse(tagged);
        steps.putPOSTag(tagged+"\n");
        List<Tree> allLeaves=parsed.getLeaves();
        List<Tree> allTreeMembersInPreOrder=allTreeDataInPreOrder(parsed,new LinkedList<Tree>());
//        List<Tree> allTreeMembersInPostOrder=allTreeDataInPostOrder(parsed,new LinkedList<Tree>());
//        for(Tree leaf:allLeaves){
//            steps.putParse(leaf.value()+" ");
//        }
//        steps.putParse("\n");
        
        steps.putParse("Parse Tree PreOrder:\n");
        for(Tree node:allTreeMembersInPreOrder){
            steps.putParse(node.value()+"\n");
        } 
//        System.out.println("\nParse Tree PostOrder");
//        for(Tree node:allTreeMembersInPostOrder){
//            System.out.println(node.value());
//        }
        return listOfTreesToString(allLeaves);
    }
    public static List<Tree> allTreeDataInPreOrder(Tree rootNode,List<Tree>retVal){
        retVal.add(rootNode);
        for(Tree child:rootNode.children()){
            allTreeDataInPreOrder(child,retVal);
        }
        return retVal;
    }
    public static String listOfTreesToString(List<Tree> tree){
        String retVal="";
        for(Tree treeNode:tree){
            String tempContainer=treeNode.value();
            retVal+=tempContainer+" ";
        }
        return retVal;
    }
    
//    public static List<Tree> allTreeDataInPostOrder(Tree rootNode,List<Tree>retVal){
//        for(Tree child:rootNode.children()){
//            allTreeDataInPostOrder(child,retVal);
//        }
//        retVal.add(rootNode);
//        return retVal;
//    }
    public Parser(StepsGUI steps){
        this.steps=steps;
    }
}
