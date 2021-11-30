# markov-word-generation

## Intro: Markov Chains

A Markov chain is a stochastic process that models a sequence of events in which the probability of each event depends on the state of the previous event. The model requires a finite set of states with fixed conditional probabilities of moving from one state to another

```
The probability of each shift depends only on the previous state of the model, 
not the entire history of events.
```

## Markov chain for text generation

The generator analyzes the words and their probability of occurrence of a previous defined quantity (order) consecutive words. It then generates chains of words that are probably related. The generation is completely randomized and based on the associations' probabilities of each word.

## Training text

Setting the training text to be used when generating random text
* Might want to generate several random texts from the same training text

**Example**: In the senstence 'the raven and the fox', the word the has a 50% chance of being followed by raven and 50% chance of being followed by fox, while the word and has a 100% chance of being followed by the.

I'm using some classics excerpts in the folder data to training set, like Confucius, Romeo & Juliet etc.

## Classes and Relationships

![class_diagram](class_diagram.png)