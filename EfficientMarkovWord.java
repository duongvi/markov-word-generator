
/**
 * Write a description of EfficientMarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class EfficientMarkovWord implements IMarkovModel{
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    HashMap<WordGram, ArrayList<String>> followMap;

    public EfficientMarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text){
        myText = text.split("\\s+");
        buildMap();
    }

    private void buildMap(){
        followMap = new HashMap<>();
        for (int i = 0; i < myText.length - myOrder; i++){
            WordGram a = new WordGram(myText, i, myOrder);
            ArrayList<String> newList = new ArrayList<>();
            if (followMap.containsKey(a)) {
                newList = followMap.get(a);
            }
            newList.add(myText[i + myOrder]);
            followMap.put(a, newList);
        }
        followMap.put(new WordGram(myText, myText.length - myOrder, myOrder), new ArrayList<>());
        printHashMapInfo();
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
        return followMap.get(kGram);
    }
    
    public void printHashMapInfo(){
        if (followMap.keySet().size() < 50) System.out.println(followMap);
        System.out.println("Number of keys: " + followMap.keySet().size());
        int max = getLargest();
        System.out.println("Largest size: " + max);
        System.out.println("The keys having largest size: "  + getLargestKeys(max));
    }
    
    
    private int getLargest(){
        int max = 0;
        for (WordGram a : followMap.keySet()){
            int current = followMap.get(a).size();
            if (current > max) max = current;
        }
        return max;
    }
    
    private ArrayList<WordGram> getLargestKeys(int max){
        ArrayList<WordGram> a = new ArrayList<>();
        for (WordGram b : followMap.keySet()){
            if(followMap.get(b).size() == max) a.add(b);
        }
        return a;
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

}
