/**
 * This class builds a HashMap to calculate the follows ArrayList for 
 * each possible WordGram only once, and then uses the HashMap to look 
 * at the list of characters following when it is needed.
 * 
 * @author Milton S Oliveira
 * @version 1.0, 29/11/2021
 */

import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;

public class EfficientMarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<WordGram, ArrayList<String>> wordMap;
    
    public EfficientMarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
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
     * This method builds the HashMap: handle the case at the end where 
     * there is not a follow character. If that WordGram is not in the 
     * HashMap yet, then it should be put in mapped to an empty ArrayList. 
     * If that key is already in the HashMap, then do not enter anything for this case.
     */
    private HashMap<WordGram, ArrayList<String>> buildMap() {
        HashMap<WordGram, ArrayList<String>> mappedWords = new HashMap<WordGram,
                    ArrayList<String>>();
                    
        int pos = 0;
        //while the current location of the myText array is less 
        //than the array's length minus the order
        while (pos < myText.length - (myOrder - 1)) {
            //create a new wordgram object that starts at the current 
            //location of myText string array
            //  with a length of myOrder
            WordGram word = new WordGram(myText, pos, myOrder);
            
            //if the wordgram string array, acting as the key in the hashmap, 
            //is not in the hashmap
            if (!mappedWords.containsKey(word) && 
                pos + myOrder < myText.length) {
                //add new entry in hashmap with key of wordgram and value of 
                //word that follows
                mappedWords.put(word, 
                new ArrayList<String>(Arrays.asList(myText[pos + myOrder])));
                //System.out.println("Word that follows wordgram: "+myText[counter+myOrder]);
            }
            //if the wordgram string array is already in the hashmap
            else if (mappedWords.containsKey(word) && 
                pos + myOrder < myText.length) {
                //get entry and replace it with current value + value of word that follows
                ArrayList<String> currentValues = mappedWords.get(word);
                currentValues.add(myText[pos + myOrder]);
                mappedWords.replace(word, currentValues);
                //System.out.println("Words in wordgram: "+currentValues);
            }
            //if the wordgram string array is not in the hashmap and we're at 
            //the end of the myText array
            else if (!mappedWords.containsKey(word) && 
                pos + myOrder == myText.length) {
                //create new entry with key of wordgram and empty value set
                mappedWords.put(word, new ArrayList<String>());
            }
            
            pos++;
        }
        
        return mappedWords;
    }
    
    /*
     * This method generates and returns random text that has numWords words
     * using the hashMap.
     */
    public String getRandomText(int numWords) {
        wordMap = buildMap();
        //printHashMapInfo();
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length - myOrder);  // random word to start with
        WordGram key = new WordGram(myText, index, myOrder);
        
        sb.append(key.toString()).append(" ");        
        
        for(int k = 0; k < numWords - 1; k++){            
            ArrayList<String> follows = getFollows(key);
            
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
     * look up the hashMap, instead of computing it each time.
     */
    private ArrayList<String> getFollows(WordGram key) {
        return wordMap.get(key);
    }
    
    /*
     * To test your HashMap to make sure it is built correctly
     */
    public void printHashMapInfo() {
        int largest = 0;
        //print the hashmap
        for (WordGram wordGram : wordMap.keySet()) {            
            if (wordMap.get(wordGram).size() > largest) {
                largest = wordMap.get(wordGram).size();
            }
        }
        //print the number of keys in the hashmap
        System.out.println("Number of keys in hashmap: " + wordMap.size());
        //print the size of the largest value in the hashmap
        System.out.println("Size of largest value in hashmap: " + largest);
        //print the keys that have the maximum value
        System.out.println("Keys that have maximum value: ");
        for (WordGram wordGram : wordMap.keySet()) {
            if (wordMap.get(wordGram).size() == largest) {
                System.out.println(wordGram.toString());
            }
        }
    }
}