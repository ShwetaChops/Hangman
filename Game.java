package hangman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Game class to manage the game variables and methods for Hangman
 * @author shwetachopra
 *
 */
public class Game {

	//Define instance variables

	/**
	 * Reader object used in the game
	 */
	private Reader dictionary;

	/**
	 * Word List in use
	 */
	protected ArrayList<String> wordList;

	/**
	 * Number of guesses made by user
	 */
	private int guesses;

	/** 
	 * Number of mistakes made by user
	 */
	private int mistakes;

	/**
	 * Word that the User must guess
	 */
	private Word word;

	/**
	 * List of Letters of the word that the user must guess
	 */
	private ArrayList<String> letters;

	/**
	 * List that records guesses
	 */
	private ArrayList<Boolean> guessArray;

	/**
	 * List that stores all incorrect guess values
	 */
	private ArrayList<String> incorrectGuesses;

	/**
	 * List that stores all guess values
	 */
	private ArrayList<String> allGuesses;

	/**
	 * Length of word/pattern at play
	 */
	protected int length;

	/**
	 * Pattern at play at any point in the game - game status
	 */
	private ArrayList<String> pattern;

	/**
	 * True/False value for whether Evil Hangman is being played or not
	 */
	protected boolean playEvil;

	/**
	 * Variable Word Map at play
	 */
	protected Map<ArrayList<String>, ArrayList<String>> wordMap;

	//Define constructor
	/**
	 * Constructor for the game class
	 * Decides which version of the game to play
	 * Sets up dictionary, word list and word map for the game
	 * Sets up arrays to store all guesses and incorrect guesses for the game
	 * @param r
	 * reader object
	 */
	public Game (Reader r) {

		//Set whether game is evil or traditional
		this.playEvil = this.playEvil();

		//Set up dictionary for the game
		this.dictionary = r;

		//Set variable word list for the game
		this.wordList = this.dictionary.list;

		//Create Incorrect Guesses Array to be populated through the game
		this.incorrectGuesses = new ArrayList<String>();

		//Create Correct Guesses Array to be populated through the game
		this.allGuesses = new ArrayList<String>();

		//Set variable word map for the game
		this.wordMap = this.wordMap();

	}

	//Define methods
	/**
	 * Method that randomly picks a word to play the game and sets up instance variables
	 * for storing the game's progress
	 */
	void chooseWord() {
		Random rand = new Random();

		int end = this.dictionary.list.size();
		int number = rand.nextInt(end);

		//Set word to play the game with
		this.word = new Word(this.dictionary.list.get(number));
		//this.word = new Word("happy");

		//Set list of letters to play game with
		this.letters = this.word.letters;

		//Set length of word/pattern
		this.length = this.word.length();

		//Set pattern
		this.pattern = this.word.pattern(this.length, this.allGuesses, this.incorrectGuesses);

		//Create Guess Array - true/false values depending on if a letter has been guessed
		this.guessArray = new ArrayList<Boolean>();
		for (int i = 0; i < this.letters.size(); i++) {
			this.guessArray.add(false);
		}

		//Change word list based on size of chosen word
		this.wordList = this.dictionary.map.get(this.length);


	}

	/**
	 * Method to record a user's guess as correct or wrong
	 * Game is altered after every turn that the user guesses if evil hangman is at play
	 * @param s
	 */
	void recordGuess(String s) {

		//Record a guess
		this.guesses++;
		this.allGuesses.add(s);

		//Change game word map

		if (this.playEvil) {
			this.changeGame();
		}

		else {
			//Update pattern for traditional hangman
			this.pattern = this.word.pattern(this.length, this.allGuesses, this.incorrectGuesses);
			//System.out.println(this.pattern);
		}


		//Check if incorrect guess, then store in incorrect guess array
		if (!this.pattern.contains(s)) {

			this.mistakes++;
			this.incorrectGuesses.add(s);

		}
		//if letter exists in the word mark correct guesses in the guess array
		else {

			for (int i = 0; i < this.pattern.size(); i++) {
				if (this.pattern.get(i).equals(s)) {
					this.guessArray.set(i,true);
				}
			}

		}
	}

	/**
	 * Method to check whether the game is over
	 * @return true or false
	 */
	boolean isGameOver() {

		for (int i = 0; i < this.guessArray.size(); i++) {

			if (!this.guessArray.get(i)) {
				return false;
			}
		}
		//if all letters guessed, return true
		return true;

	}

	/**
	 * Method to check whether an letter has been guessed before by the user
	 * @param s
	 * @return true or false
	 */
	boolean guessedBefore(String s) {

		if (this.allGuesses.contains(s)) {
			return true;
		}

		else {
			return false;
		}
	}

	/**
	 * Method to decide whether Evil hangman is to be played or not
	 * @return true or false
	 */
	private boolean playEvil() {

		Random rand = new Random();
		int number = rand.nextInt(2);

		if (number == 0) {
			return true;
		}

		else {
			return false;
		}

	}

	/**
	 * Method to return word map for the game
	 * @return map
	 */
	private Map<ArrayList<String>, ArrayList<String>> wordMap(){

		Map<ArrayList<String>, ArrayList<String>> map = new HashMap<ArrayList<String>, ArrayList<String>>();

		for (int i = 0; i < this.wordList.size(); i++) {

			ArrayList<String> wordPattern = new ArrayList<String>();

			Word word = new Word(this.wordList.get(i));

			wordPattern = word.pattern(this.length, this.allGuesses, this.incorrectGuesses);

			if (!map.containsKey(wordPattern)) {
				map.put(wordPattern, new ArrayList<String>());
			}

			map.get(wordPattern).add(word.name);

		}

		return map;
	}

	/**
	 * Method to change the game parameters in case the game is evil
	 */
	private void changeGame() {

		if (this.playEvil) {

			//Create word map
			this.wordMap = this.wordMap();

			ArrayList<String> newList = new ArrayList<String>();
			ArrayList<String> newPattern = new ArrayList<String>();

			//Select new pattern
			for (ArrayList<String> key : this.wordMap.keySet()) {
				if (this.wordMap.get(key).size() > newList.size()) {
					newList = this.wordMap.get(key);
					newPattern = key;
				}
			}

			//Change instance variables for the game
			this.pattern = newPattern;
			this.length = this.pattern.size();
			this.wordList = newList;


			for (int i = 0; i < pattern.size(); i++) {
				if (pattern.get(i) == "_") {
					this.guessArray.set(i, false);
				}

				else {
					this.guessArray.set(i, true);
				}
			}

			//Change word
			Random rand = new Random();

			int end = this.wordList.size();
			int number = rand.nextInt(end);

			//Set word to play the game with
			this.word = new Word(this.wordList.get(number));

			//Set list of letters to play game with
			this.letters = this.word.letters;
		}

	}

	/**
	 * Overrides the toString method for Object class and prints out the pattern
	 * status of the game at any point
	 */
	@Override
	public String toString() {
		String pattern = "";
		for (int i = 0; i < this.pattern.size(); i++) {
			pattern = pattern + this.pattern.get(i) + " ";
		}

		return pattern.trim();

	}

	/*
	 * Getters and Setters have been created using Eclipse's functionality
	 * and therefore have not been tested individually
	 */


	/**
	 * Getter to access the dictionary
	 * @return dictionary
	 */
	public Reader getDictionary() {
		return dictionary;
	}

	/**
	 * Setter to set the dictionary
	 * @param dictionary
	 */
	public void setDictionary(Reader dictionary) {
		this.dictionary = dictionary;
	}

	/**
	 * Getter to access the guess count
	 * @return guesses
	 */
	public int getGuesses() {
		return guesses;
	}

	/**
	 * Setter to set the guess count
	 * @param guesses
	 */
	public void setGuesses(int guesses) {
		this.guesses = guesses;
	}

	/**
	 * Getter to access the mistake count
	 * @return mistakes
	 */
	public int getMistakes() {
		return mistakes;
	}

	/**
	 * Setter to set the mistake count
	 * @param mistakes
	 */
	public void setMistakes(int mistakes) {
		this.mistakes = mistakes;
	}

	/**
	 * Getter to access the chosen word
	 * @return
	 */
	public Word getWord() {
		return this.word;
	}

	/**
	 * Setter to set the word
	 * @param word
	 */
	public void setWord(Word word) {
		this.word = word;
	}

	/**
	 * Getter to access the letters
	 * @return letters
	 */
	public ArrayList<String> getLetters() {
		return letters;
	}

	/**
	 * Getter to access the pattern
	 * @return pattern
	 */
	public ArrayList<String> getPattern() {
		return this.pattern;
	}

	/**
	 * Setter to set the letters array
	 * @param letters
	 */
	public void setLetters(ArrayList<String> letters) {
		this.letters = letters;
	}

	/**
	 * Getter to access the guess array
	 * @return
	 */
	public ArrayList<Boolean> getGuessArray() {
		return guessArray;
	}

	/**
	 * Setter to set the guess array
	 * @param guessArray
	 */
	public void setGuessArray(ArrayList<Boolean> guessArray) {
		this.guessArray = guessArray;
	}

	/**
	 * Getter to access the incorrect guess array
	 * @return incorrectGuesses
	 */
	public ArrayList<String> getIncorrectGuesses() {
		return incorrectGuesses;
	}

	/**
	 * Setter to set the incorrect guess array
	 * @param incorrectGuesses
	 */
	public void setIncorrectGuesses(ArrayList<String> incorrectGuesses) {
		this.incorrectGuesses = incorrectGuesses;
	}

	/**
	 * Getter to access the all guesses array
	 * @return
	 */
	public ArrayList<String> getAllGuesses() {
		return allGuesses;
	}

	/**
	 * Setter to set the all guesses array
	 * @param allGuesses
	 */
	public void setAllGuesses(ArrayList<String> allGuesses) {
		this.allGuesses = allGuesses;
	}

	/**
	 * Setter to set the mode of the game
	 * @param play - true/false
	 */
	public void setPlayEvil(boolean play) {
		this.playEvil = play;
	}

	/**
	 * Getter to access the length of the word in the game
	 */
	public int getLength() {
		return this.length;
	}

	/**
	 * Setter to set the length of the word in the game
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}

}
