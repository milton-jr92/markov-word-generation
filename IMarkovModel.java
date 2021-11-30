
/**
 * The Markov model interface that has the assignatures
 * of markov's methods: setTraining, setRandom and getRandomText
 * 
 * @author Milton S Oliveira 
 * @version 1.0, 29/11/2021
 */

public interface IMarkovModel {
    public void setTraining(String text);
    
    public void setRandom(int seed);
    
    public String getRandomText(int numChars);
}