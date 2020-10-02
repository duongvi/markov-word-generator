
import java.util.*;
import java.util.stream.*;

public class WordGram {
    private String[] myWords;
    private int myHash;

    public WordGram(String[] source, int start, int size) {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
    }

    public String wordAt(int index) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt "+index);
        }
        return myWords[index];
    }

    public int length(){
        return myWords.length;
        //return 0;
    }

    public String toString(){
        String ret = "";
        for (String word : myWords){
            ret += word + " ";
        }

        return ret.trim();
    }

    public boolean equals(Object o) {
        WordGram other = (WordGram) o;
        if (other.length() != myWords.length) return false;
        for (int i = 0; i < myWords.length; i++) {
            if (!other.wordAt(i).equals(myWords[i])) return false;
        }
        return true;

    }

    public WordGram shiftAdd(String word) { 
        //WordGram out = new WordGram(myWords, 0, myWords.length);
        String [] newArr = new String[myWords.length];
        System.arraycopy(myWords, 1, newArr, 0, myWords.length - 1);
        newArr[newArr.length - 1] = word;
        // shift all words one towards 0 and add word at the end. 
        // you lose the first word
        WordGram out = new WordGram(newArr, 0, myWords.length);
        
        return out;
    }

    public int hashCode(){
        String joined = Arrays.stream(myWords).collect(Collectors.joining(" ")).trim();
        return joined.hashCode();
    }
}