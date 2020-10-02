
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class MarkovRunner {
    public void runModel(IMarkovModel markov, String text, int size){ 
        markov.setTraining(text); 
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runModel(IMarkovModel markov, String text, int size, int seed){ 
        markov.setTraining(text); 
        markov.setRandom(seed);
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runMarkov() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        //MarkovWordOne markovWord = new MarkovWordOne(); 
        //runModel(markovWord, st, 200); 
        MarkovWord markovWord = new MarkovWord(5);
        runModel(markovWord, st, 50, 844);
    } 

    public void testGetFollows(){
        String st = "this is a simple test just a simple test";
        MarkovWord m = new MarkovWord(3);
        m.setTraining(st);
        m.getRandomText(30);
    }

    public void testHashMap(){
        FileResource fr = new FileResource();
        String st = fr.asString();
        EfficientMarkovWord m = new EfficientMarkovWord(2);
        m.setRandom(65);
        m.setTraining(st);
        
    }
    
    public void compareMethods(){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        MarkovWord m = new MarkovWord(2);
        EfficientMarkovWord ef = new EfficientMarkovWord(2);
        long time1 = System.nanoTime();
        int size = 100;
        int seed = 42;
        runModel(m, st, size, seed);
        long time2 = System.nanoTime() - time1;
        System.out.format("Normal MarkovWord took: %d ms\n", time2 / 1000000);
        time1 = System.nanoTime();
        runModel(ef, st, size, seed);
        time2 = System.nanoTime() - time1;
        System.out.format("Efficient MarkovWord took: %d ms\n", time2 / 1000000);
    }
    
    private void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length; k++){
            System.out.print(words[k]+ " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println(); 
                psize = 0;
            } 
        } 
        System.out.println("\n----------------------------------");
    } 

}
