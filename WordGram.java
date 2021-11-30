/**
 * The class WordGram is a class that represents words that have an ordering. 
 * For example, a WordGram might be the four words “this” “is” “a” test”, 
 * in this order. The WordGram class has two private variables, a String array 
 * myWords to store the words in order, one word per slot, and a private integer 
 * myHash you will use to be able to use WordGrams as a key with a HashMap.
 * 
 * @author Milton S Oliveira 
 * @version 1.0, 29/11/2021
 */

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

    public int length() {        
        return myWords.length;
    }

    public String toString() {
        String ret = "";
        
        for (int i = 0; i < myWords.length; i++) {
            ret += myWords[i] + " ";
        }        

        return ret.trim();
    }

    public boolean equals(Object o) {
        WordGram other = (WordGram) o;
        
        if (this.length() != other.length())
            return false;
        
        for (int i = 0; i < myWords.length; i++) {
            if (!myWords[i].equals(other.wordAt(i)))
                return false;
        }
            
        return true;
    }
    
    public int hashCode() {
        if (myHash == 0)
            myHash = this.toString().hashCode();
            
        return myHash;
    }

    /* 
     * shift all words one towards 0 and add word at the end.
     * you lose the first word 
     */
    public WordGram shiftAdd(String word) {   
        WordGram out = new WordGram(myWords, 0, myWords.length);
        String[] shiftWords = new String[out.length()];        
        
        for (int i = 0; i < out.length() - 1; i++) {
            shiftWords[i] = out.wordAt(i + 1);
        }
        
        shiftWords[shiftWords.length - 1] = word;        
        
        return new WordGram(shiftWords, 0, shiftWords.length);
    }   
}