/** 
 * @author Milton S Oliveira
 * @version 1.0, 29/11/2021
 */

import java.util.ArrayList;

public class WordGramTester {
    public void testWordGram() {
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        int size = 4;
        
        for(int index = 0; index <= words.length - size; index += 1) {
            WordGram wg = new WordGram(words,index,size);
            System.out.println(index+"\t"+wg.length()+"\t"+wg);
        }
    }
    
    public void testWordGramEquals() {
        String source = "this is a test this is a test this is a test of words";
        String[] words = source.split("\\s+");
        ArrayList<WordGram> list = new ArrayList<WordGram>();
        int size = 4;
        
        for(int index = 0; index <= words.length - size; index += 1) {
            WordGram wg = new WordGram(words,index,size);
            list.add(wg);
        }
        
        WordGram first = list.get(0);
        System.out.println("checking "+first);
        
        for(int k=0; k < list.size(); k++){            
              if (first.equals(list.get(k))) {
                System.out.println("matched at "+k+" "+list.get(k));
            }
        }
    }
    
    public void testShiftAdd() {
        String st = "this is just a test yes this is a simple test";
        String[] words = st.split("\\s+");
        WordGram wordTest = new WordGram(words, 0, words.length);
        System.out.println(wordTest.shiftAdd("shift"));
    }    
}