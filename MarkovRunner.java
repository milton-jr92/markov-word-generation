/**
 * There are two runModel methods with different numbers of parameters. 
 * One void method runModel has three parameters, an IMarkovModel named markov, 
 * a String text that represents the training text, and an integer named size 
 * that represents the number of random words to generate. The second runModel 
 * method has a fourth parameter, an int named seed.
 * 
 * @author Milton S Oliveira
 * @version 1.0, 29/11/2021
 */

import edu.duke.*;

public class MarkovRunner {
    public void runModel(IMarkovModel markov, String text, int size) { 
        markov.setTraining(text); 
        System.out.println("running with " + markov); 
        
        for(int k = 0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runModel(IMarkovModel markov, String text, int size, int seed) { 
        markov.setTraining(text); 
        markov.setRandom(seed);
        System.out.println("running with " + markov);
        
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 
    
    /*
     * This method reads in a file the user chooses, creates a 
     * MarkovWord object, and then calls runModel to generate 
     * and print three sets of randomly generated text using the 
     * file read in to choose the random words.
     */
    public void runMarkov() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        int order = 5;
        int seed = 844;
        MarkovWord markov = new MarkovWord(order);
        
        runModel(markov, st, 200, seed); 
    }
    
    /*
     * This method reads in a file the user chooses, creates a 
     * EfficientMarkovWord object, and then calls runModel to generate 
     * and print three sets of randomly generated text using the 
     * file read in to choose the random words.
     */
    public void runMarkovWithHash() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        int order = 2;
        int seed = 65;
        EfficientMarkovWord markov = new EfficientMarkovWord(order);
        
        runModel(markov, st, 200, seed); 
    }
    
    public void compareMethods() {
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        int order = 2;
        int seed = 42;
        int textSize = 100;
        
        MarkovWord markov = new MarkovWord(order);
        EfficientMarkovWord markovWithHash = new EfficientMarkovWord(order);
        
        long nano_startTime = System.nanoTime();
        runModel(markov, st, textSize, seed);
        long nano_endTime = System.nanoTime();
        
        System.out.println("Time taken in MarkovWord: "
                           + (nano_endTime - nano_startTime));
                           
        nano_startTime = System.nanoTime();
        runModel(markovWithHash, st, textSize, seed);
        nano_endTime = System.nanoTime();
        
        System.out.println("Time taken in markovWithHash: "
                           + (nano_endTime - nano_startTime));                           
    }
    
    public void testHashMap() {
        String st = "this is a test yes this is really a test yes a test this is wow";
        st = st.replace('\n', ' ');
        int order = 2;
        int seed = 42;
        int textSize = 50;
        EfficientMarkovWord markov = new EfficientMarkovWord(order);
        
        runModel(markov, st, textSize, seed);
    }

    private void printOut(String s) {
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