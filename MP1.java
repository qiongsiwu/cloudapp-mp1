import java.io.File;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class MP1 {
    Random generator;
    String userName;
    String inputFileName;
    String delimiters = " \t,;.?!-:@[](){}_*/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};

    void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(seed.toLowerCase().trim().getBytes());
        byte[] seedMD5 = messageDigest.digest();

        long longSeed = 0;
        for (int i = 0; i < seedMD5.length; i++) {
            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
        }

        this.generator = new Random(longSeed);
    }

    Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        this.initialRandomGenerator(this.userName);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public MP1(String userName, String inputFileName) {
        this.userName = userName;
        this.inputFileName = inputFileName;
    }

    String[] getTokenizedString(String in) {
        StringTokenizer st = new StringTokenizer(in, delimiters);
        String[] tokens = new String[st.countTokens()];
        int index = 0;

        while (st.hasMoreTokens()) {
            tokens[index] = st.nextToken();
            index++;
        }

        return tokens;
    }

    void printTokens(String[] tokens)
    {
        for (int i = 0; i < tokens.length; i++) {
            System.out.println(tokens[i]);
        }
    }

    String[] toLowerCase(String[] tokens) {
        String[] lowerCaseToken = new String[tokens.length];
        for (int i = 0; i < tokens.length ; i++) {
            lowerCaseToken[i] = tokens[i].toLowerCase();
        }
        return lowerCaseToken;
    }

    String[] removeWhiteSpace(String[] tokens) {
        String[] noWhiteSpace = new String[tokens.length];
        for (int i = 0; i < tokens.length; i++ ) {
          noWhiteSpace[i] = tokens[i].replaceAll("\\s", "");
        }
        return noWhiteSpace;
    }

    List<String> removeStopWords(String[] tokens) {
        List<String> stopWordsList = Arrays.asList(stopWordsArray);
        List<String> processedTokens = new ArrayList<String>();
        for (int i = 0; i < tokens.length; i++ ) {
            if (!stopWordsList.contains(tokens[i])) {
                processedTokens.add(tokens[i]);
            }
        }

        return processedTokens;
    }

    void printStringList(List<String> tokens) {
        for (String token : tokens) {
            System.out.println(token);
        }
    }

    List<String> processThisLine(String thisLine) {
        return removeStopWords(
                               removeWhiteSpace(
                                          toLowerCase(
                                                      (getTokenizedString(thisLine))
                                                     )
                                         ));
    }


    public String[] process() throws Exception {
        String[] ret = new String[20];

        // Read the file
        File in = new File(inputFileName);
        FileReader fileRead = new FileReader(in);
        BufferedReader bufferedRead = new BufferedReader(fileRead);

        List<String> AllLines = new ArrayList<String>();
        String thisLine;
        while ((thisLine = bufferedRead.readLine())!=null) {
            AllLines.add(thisLine);
        }

        // Generate index
        Integer[] index = getIndexes();

        // Process lines for each index
        for (int i = 0; i < index.length; i++ ) {
            String thisRecord = AllLines.get(index[i]);
        }


        String test = "My dog has been eating an apple";
        String[] lowerCaseNoSpace = removeWhiteSpace(toLowerCase(getTokenizedString(test)));

        List<String> testList = removeStopWords(lowerCaseNoSpace);

        printStringList(testList);

        System.out.println("Length of file is: " + in.length() + "\n");

        return ret;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1){
            System.out.println("MP1 <User ID>");
        }
        else {
            String userName = args[0];
            String inputFileName = "./input.txt";
            MP1 mp = new MP1(userName, inputFileName);
            String[] topItems = mp.process();
            for (String item: topItems){
                System.out.println(item);
            }
        }
    }
}
