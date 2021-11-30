/**
 * This class that works for any number of words and uses the WordGram 
 * class to handle those words. It generates each word by randomly 
 * choosing a word from the training text that follows the current word(s) 
 * in the training text.
 * 
 * @author Milton S Oliveira 
 * @version 1.0, 29/11/2021
 */

import java.util.Random;
import java.util.ArrayList;

public class MarkovWord implements IMarkovModel {
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
    
    public void setTraining(String text) {
        myText = text.split("\\s+");
    }
    
    /*
     * This method should return the first position from start that has 
     * words in the array words that match the WordGram target. 
     * If there is no such match then return -1.
     */
    private int indexOf(String[] words, WordGram target, int start) {
        for (int k = start; k < words.length - target.length(); k++) {
            if (words[k].equals(target.wordAt(0))) {
                boolean targetFound = true;
                
                for (int i = 1; i < target.length(); i++) {
                    if (!words[k + i].equals(target.wordAt(i))) {
                        targetFound = false;
                        break;
                    }
                }
                
                if (targetFound) {
                    return k;
                }
            }
        }
        
        return -1;
    }   
    
    /*
     * This method generates and returns random text that has numWords words.
     */
    public String getRandomText(int numWords) {
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length - myOrder);  // random word to start with
        WordGram key = new WordGram(myText, index, myOrder);
        
        sb.append(key.toString()).append(" ");        
        
        for(int k = 0; k < numWords - 1; k++) {
            //System.out.println("key: " + key);
            ArrayList<String> follows = getFollows(key);
            //System.out.println("follows: " + follows);
            if (follows.size() == 0) 
                break;            
            
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            
            sb.append(next).append(" ");
            
            key = key.shiftAdd(next);
        }
        
        return sb.toString().trim();
    }
    
    /*
     * This method returns an ArrayList of all the single words that immediately 
     * follow an instance of the WordGram parameter somewhere in the training text.
     */
    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>(); 
        int pos = 0;        
        
        while(pos < myText.length - kGram.length()) {
            int index = indexOf(myText, kGram, pos);
            
            if (index == -1 || index + kGram.length() >= myText.length - 1) 
                break;            
            
            String next = myText[index + kGram.length()];
            follows.add(next);
            pos = index + kGram.length();
        }
        
        return follows;
    }
    
    /*public void testGetRandomText() {
        myText = "this is just a test yes this is a simple test i repeat this is a test just a test.".split("\\s");
        String randomText = getRandomText(100);
        for (int i=0;i<myText.length;i++) {
            System.out.println(myText[i]);
        }
        System.out.println(randomText);
    }
    
    public void testGetFollows() {
        myText = "this is just a test yes this is a simple test".split("\\s");
        String[] targetArray = "this".split("\\s");
        WordGram target = new WordGram(targetArray,0,targetArray.length);
        ArrayList<String> follows = getFollows(target);
        System.out.println(follows);
    }
    
    public void testIndexOf() {
        String[] textArray = "this is just a test yes this is a simple test".split("\\s");
        String[] targetArray = "this is".split("\\s");
        WordGram target = new WordGram(targetArray,0,targetArray.length);
        System.out.println(indexOf(textArray,target,0));
        System.out.println(indexOf(textArray,target,5));
        System.out.println(indexOf(textArray,target,8));
    }*/
}