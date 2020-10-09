# Markov Word Generator
This is a project that was implemented as a part of the Coursera course by Duke University: "Java Programming: Principles of Software Design". The materials used for it are licensed under the Creative Commons Attribution 4.0 International License (CC BY 4.0).

It implements so called Markov word generators, which generate random text based on a training text. This program also provides the ability to choose the order of the Markov generator (= number of words used to predict the next one) and to choose the file to use for the training.

## Classes explanation 
### WordGram
This class serves the purpose of representing one or multiple words, mainly to use as the basis of predicting the next word during the text generation.
### IMarkovModel
The interface for the following classes to represent the Markov word generators.
#### MarkovWord
The class providing main functionality using a "naive" approach. Basically, it takes a number (= order) of words following each other (= WordGram) in the training text, looks through the training text to see which words followed this WordGram and randomly chooses one of them. 

This approach is very inefficient, as every time a set of words is encountered, it has to look for the words that followed it, regardless of whether it had already been encountered once or more. E.g. in the training text "this is a test yes this is a test", the simple MarkovWord would look for words that follow "this is" twice, despite being unnecessary the second time. 
#### EfficientMarkovWord
This is where the more efficient EfficientMarkovWord comes in. It derives all of the possible words from each WordGram of length "order" right as the training text is set and stores them in a HashMap<WordGram, ArrayList<String>> . So when generating text and looking for following words, it only has to look up the set of words (= WordGram) in the HashMap.
  
Because of dealing with HashMap, the hashCode() method had to be overriden in the WordGram class.
### MarkovRunner and WordGramTester
These classes serve the purpose of running the generator and testing the WordGram class, respectively. This is where the setting of the training text, seed of the random generator, order of the Markov generator and length of the generated text occurs.
