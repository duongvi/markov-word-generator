
/**
 * Write a description of MarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MarkovWord implements IMarkovModel{
    private String[] myText;
    private Random myRandom;
    private int myOrder;

    public MarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text){
        myText = text.split("\\s+");
    }

    private int indexOf(String [] words, WordGram target, int start){
        int targetLength = target.length();
        for (int i = start; i < words.length - targetLength; i++){
            WordGram found = new WordGram(words, i, targetLength);
            if (found.equals(target)) return i;
        }

        return -1;
    }


    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>();
        int targetLength = kGram.length();
        for (int i = 0; i < myText.length - targetLength; i++){
            int foundIdx = indexOf(myText, kGram, i);
            if (foundIdx > -1){ 
                follows.add(myText[foundIdx+targetLength]);
                i = foundIdx + targetLength;
            } else break;

        }
        return follows;
    }

    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with
        WordGram key = new WordGram(myText, index, myOrder);
        sb.append(key);
        sb.append(" ");
        for(int k=0; k < numWords-myOrder; k++){
            ArrayList<String> follows = getFollows(key);
            //System.out.format("Found %d keys for %s: %s\n", follows.size(), key, follows); 
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key = key.shiftAdd(next);
        }

        return sb.toString().trim();
    }
    
    public void testIndexOf(){
        String st = "this is a test on instagram facebook and this is a test on books";
        String [] words = st.trim().split("\\s+");
        WordGram thisIs = new WordGram(new String[] {"this", "is"}, 0, 2);
        WordGram isA = new WordGram(new String[] {"is", "a" }, 0, 2);
        WordGram facebook = new WordGram(new String[] {"facebook"}, 0, 1);
        WordGram dog = new WordGram(new String[] {"dog"}, 0, 1);
        WordGram books = new WordGram(new String[] {"books"}, 0, 1);
        System.out.println("Initial text is: " + st);
        System.out.format("Looking for index of '%s':  %d\n", thisIs, indexOf(words, thisIs, 0));
        System.out.format("Looking for index of '%s':  %d\n", thisIs, indexOf(words, thisIs, 3));
        System.out.format("Looking for index of '%s':  %d\n", isA, indexOf(words, isA, 5));
        System.out.format("Looking for index of '%s':  %d\n", facebook, indexOf(words, facebook, 0));
        System.out.format("Looking for index of '%s':  %d\n", facebook, indexOf(words, facebook, 10));
        System.out.format("Looking for index of '%s':  %d\n", dog, indexOf(words, dog, 0));
        System.out.format("Looking for index of '%s':  %d\n", books, indexOf(words, books, 0));
    }
}
