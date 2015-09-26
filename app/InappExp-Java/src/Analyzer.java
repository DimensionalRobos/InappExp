
import edu.smu.tspell.wordnet.*;
import java.util.LinkedList;

/**
 * This is a class that does all of the system's preprocessing(Minus the parsing & PoSTag) and sentiment analysis
 * @cheerleader Coleen
 * @author Daikaiser
 */
class TokenType {

    public Synset synset;//Synonym sets
    public String definition;//A definition per term
    public SynsetType postag;//A corresponding PoS
    
    public TokenType(Synset synset, String definition, SynsetType postag) {
        this.synset = synset;
        this.definition = definition;
        this.postag = postag;
    }
}

class Token {

    String word;
    LinkedList<TokenType> tokenType;
    boolean isOffensive = false;
    boolean isNegator = false;
    int partOfSpeech = 0;

    public Token(String word, LinkedList<TokenType> tokenType) {
        this.word = word;
        this.tokenType = tokenType;
    }
}

public class Analyzer {

    LinkedList<Token> tokenlist = new LinkedList<Token>();
    String[] sentences;
    StepsGUI steps;

    public Analyzer(StepsGUI steps) {
        this.steps = steps;
    }

    public String analyze(String input) {
        String allOutput = "";
        steps.clearEverything();//Clear after new input
        input = input.toLowerCase().trim();
        Parser parser = new Parser(steps);//Parser instanciation
        System.setProperty("wordnet.database.dir", "C:\\Program Files\\WordNet\\2.1\\dict");
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        String sentStruct = "";
        String[] annotations;
        LinkedList<String> annotation = new LinkedList<String>();
        sentences = input.split("[.]|[?]|[!]");
        for (int i = 0; i < sentences.length; i++) {
            try {
                System.out.println(sentences[i].trim());
                sentences[i] = sentences[i].trim();
                tokenlist = new LinkedList<Token>();
                steps.putSentDivi("Sentence>>" + sentences[i].trim() + "\n");
                sentStruct = parser.parse(sentences[i]);
                annotations = sentStruct.split(" |_");
                if (sentStruct.equals("X")) {
                    continue;
                }
                for (String annotatedToken : annotations) {
                    if (annotatedToken.equals("")) {
                        continue;
                    }
                    annotation.add(annotatedToken.trim());
                }
                for (int x = 0; x < annotation.size(); x = x + 2) {
                    Token tContainer = new Token(annotation.get(x), new LinkedList<TokenType>());
                    Synset[] synsets = database.getSynsets(annotation.get(x));
                    for (Synset synset : synsets) {
                        TokenType ttContainer = new TokenType(synset, synset.getDefinition(), synset.getType());
                        tContainer.tokenType.add(ttContainer);
                    }
                    steps.putToken(annotation.get(x) + "\n");
                    tokenlist.add(tContainer);
                    if (annotation.get(x + 1).contains("NN")) {
                        tokenlist.getLast().partOfSpeech = 1;
                    }
                    if (annotation.get(x + 1).contains("VB")) {
                        tokenlist.getLast().partOfSpeech = 2;
                    }
                    if (annotation.get(x + 1).contains("PRP")) {
                        tokenlist.getLast().partOfSpeech = 3;
                    }
                    if (annotation.get(x + 1).contains("JJ")) {
                        tokenlist.getLast().partOfSpeech = 5;
                    }
                    if (annotation.get(x + 1).contains("RB")) {
                        tokenlist.getLast().partOfSpeech = 4;
                    }
                    steps.putMeaning("Lexeme>>" + tokenlist.getLast().word + " PoS:" + tokenlist.getLast().partOfSpeech + "\n");
                    for (TokenType tt : tokenlist.getLast().tokenType) {
                        steps.putMeaning("\tDefinition:" + tt.definition + "\n\tPoS:" + tt.postag + "\n");
                    }
                }
                boolean hasBadContext = false;//locates target if target of rude word makes it rude e.g. Screw you, Someone is shit
                boolean hasBadWord = false;//if has bad word
                //Polarity analysis here
                //Existence of possible bad word here
                for (Token t : tokenlist) {
                    boolean newBadWord = false;
                    steps.putPolarity(t.word + ' ');
                    for (TokenType tt : t.tokenType) {
                        if (Integer.parseInt(tt.postag.toString()) == t.partOfSpeech) {
                            if (tt.definition.contains("obscene")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("bad")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("force") & tt.definition.contains("sex")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("slang for")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("someone who moves slowly")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("by lust")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("from malice")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("woman adulterer")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("insulting")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("thoroughly disliked")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("procures")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("without civilizing influences")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("swindles")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("intercourse") & tt.definition.contains("money")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("no self-respect")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("anal copulation")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("anal sex")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("offensive")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("mentally irregular")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("deranged")) {
                                newBadWord = true;
                            }
//                if(tt.definition.contains("thief"))
//                    newBadWord=true;
                            if (tt.definition.contains("gullible")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("subnormal intelligence")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("contemptible")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("low opinion of someone's intelligence")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("insignificant student")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("stupid")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("derogatory")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("fradulent")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("sexually attracted")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("sexual intercourse")) {
                                newBadWord = true;
                            }
                            if (tt.definition.contains("intellectual acuity")) {
                                newBadWord = true;
                            }
                        }
                        if (newBadWord) {
                            t.isOffensive = true;
                        }
                    }
                    if (!newBadWord) {
                        steps.putPolarity(" is not inappropriate\n");
                    } else {
                        steps.putPolarity(" may be inappropriate\n");
                        hasBadWord = true;
                    }
                }
                if (hasBadWord) {
                    for (Token token : tokenlist) {
                        boolean newTargetPerson = false;
                        if (token.word.isEmpty()) {
                            continue;
                        }
                        if (!token.isOffensive) {
                            if (token.partOfSpeech == 1) {
                                for (TokenType type : token.tokenType) {
                                    if (type.definition.contains(" he ")
                                            | type.definition.contains(" she ")
                                            | type.definition.contains(" person ")
                                            | type.definition.contains(" who ")
                                            | type.definition.contains(" girl ")
                                            | type.definition.contains(" boy ")
                                            | type.definition.contains(" man ")
                                            | type.definition.contains(" woman ")) {
                                        newTargetPerson = true;
                                    }
                                }
                                if (token.tokenType.isEmpty()) {
                                    newTargetPerson = true;
                                }
                            }
                            if (token.partOfSpeech == 3
                                    & !(token.word.equalsIgnoreCase("i")
                                    | token.word.equalsIgnoreCase("me")
                                    | token.word.equalsIgnoreCase("my")
                                    | token.word.equalsIgnoreCase("mine")
                                    | token.word.equalsIgnoreCase("myself")
                                    | token.word.equalsIgnoreCase("it")
                                    | token.word.equalsIgnoreCase("its")
                                    | token.word.equalsIgnoreCase("itself"))) {
                                newTargetPerson = true;
                            }
                            if (token.word.equalsIgnoreCase("not") | token.word.equalsIgnoreCase("n't")) {
                                hasBadContext = false;
                                hasBadWord = false;
                                token.isNegator = true;
                                steps.putPolarity(token.word + " negated the inappropriate context\n");
                            }
                        }
                        if (newTargetPerson) {
                            steps.putPolarity(token.word + " is a possible target of the inappropriate expression\n");
                            hasBadContext = true;
                            hasBadWord = true;
                        }
                    }
                    //JOptionPane.showMessageDialog(null, "Finished Checking hasBadWord=" + hasBadWord + " hasBadContext=" + hasBadContext);
                }
                if (hasBadWord & hasBadContext) {
                    allOutput = allOutput + "Sentence ( " + sentences[i] + " ) is inappropriate in context\n";
                    for (Token token : tokenlist) {
                        if (token.isOffensive) {
                            allOutput = allOutput + "\t" + token.word + " is used in an inappropriate sense\n";
                        }
                    }
                    for (Token token : tokenlist) {
                        if (!token.isOffensive) {
                            if (token.partOfSpeech == 1) {
                                for (TokenType type : token.tokenType) {
                                    if (type.definition.contains(" he ")
                                            | type.definition.contains(" she ")
                                            | type.definition.contains(" person ")
                                            | type.definition.contains(" who ")
                                            | type.definition.contains(" man ")
                                            | type.definition.contains(" woman ")) {
                                        allOutput = allOutput + "\t" + token.word + " is an inappropriateness target\n";
                                    }
                                }
                                if (token.tokenType.isEmpty()) {
                                        allOutput = allOutput + "\t" + token.word + " is an inappropriateness target\n";
                                }
                            }
                            if (token.partOfSpeech == 3
                                    & !(token.word.equalsIgnoreCase("i")
                                    | token.word.equalsIgnoreCase("me")
                                    | token.word.equalsIgnoreCase("my")
                                    | token.word.equalsIgnoreCase("mine")
                                    | token.word.equalsIgnoreCase("myself")
                                    | token.word.equalsIgnoreCase("it")
                                    | token.word.equalsIgnoreCase("its")
                                    | token.word.equalsIgnoreCase("itself"))) {
                                allOutput = allOutput + "\t" + token.word + " is an inappropriateness target\n";
                            }
                        }
                    }
                } else {
                    allOutput = allOutput + "Sentence ( " + sentences[i] + " ) does not have an inappropriate sense\n";
                    boolean hasNegator = false;
                    for (Token token : tokenlist) {
                        if (token.isNegator) {
                            allOutput = allOutput + "\tthe keyword " + token.word + " negated the inappropriate sense\n";
                            hasNegator = true;
                        }
                    }
                    for (Token token : tokenlist) {
                        if (token.isOffensive & hasNegator) {
                            allOutput = allOutput + "\tthe word " + token.word + " is not used in an inappropriate sense\n";
                        }
                        if (token.isOffensive & !hasNegator) {
                            allOutput = allOutput + "\tthe word " + token.word + " does not have any relation to any words\n";
                        }
                    }
                }
                tokenlist.clear();
                annotation.clear();

            } catch (Exception e) {
                allOutput = allOutput + "Sentence ( " + sentences[i] + " ) is skipped because of improper sentence structure\n";
                tokenlist.clear();
                annotation.clear();
            }
        }

        //Closest entity must be human
        //Tokens to watch out is Human pronouns
//        LinkedList<Token> gramFunctTarget=new LinkedList<Token>();
//        for(Token token:tokenlist){
//            if(token.partOfSpeech==1|token.partOfSpeech==3){
//                gramFunctTarget.add(token);
//            }
//            if(token.isOffensive){
//                gramFunctTarget.add(token);
//            }
//        }
        return allOutput;
    }
}
