package hangman;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Word class to determine the underlying pattern of words
 * @author shwetachopra
 *
 */
public class Word {

	//Create instance variables
	/**
	 * Name of the word
	 */
	protected String name;

	/**
	 * Array list of letters in a word
	 */
	protected ArrayList<String> letters;

	//Define constructor
	/**
	 * Constructor that sets the name of the word and an array list of letters in the word
	 * @param s
	 */
	public Word(String s) {
		this.name = s;

		//Set up letters array
		String[] array = this.name.split("");
		ArrayList<String> aList = new ArrayList<String>();
		aList.addAll(Arrays.asList(array));
		this.letters = aList;

	}

	//Define methods
	/**
	 * Method that allows a Word to be split based on a pattern
	 * @param s
	 * @return array of strings
	 */
	protected String[] split(String s) {
		return this.name.split(s);
	}

	/**
	 * Method to return the length of a Word
	 * @return length of word
	 */
	protected int length() {
		return this.name.length();
	}

	/**
	 * Method to generate a pattern for a word, based on user guess history
	 * @param length
	 * @param all
	 * @param wrong
	 * @return array list consisting of pattern
	 */
	protected ArrayList<String> pattern(int length, ArrayList<String> all, 
			ArrayList<String> wrong){

		//Initialize pattern
		ArrayList<String> pattern = new ArrayList<String>();
		for (int i = 0; i < length; i++) {
			pattern.add("_");
		}

		if (all.size() > 0) {
			//Create pattern
			//Reveal latest guess if present
			for (int i = 0; i < length; i++) {
				if (all.get(all.size() - 1).equals(this.letters.get(i))) {
					pattern.set(i, all.get(all.size() - 1));
				}

				//Reveal past correct guesses if present - exclude latest guess
				for (int j = 0; j < all.size() - 1; j++) {
					if (!wrong.contains(all.get(j))) {
						if (all.get(j).equals(this.letters.get(i))) {
							pattern.set(i, all.get(j));
						}
					}
				}

			}
		}

		//Return pattern
		return pattern;
	}

	/**
	 * Overrides the toString method for Object class
	 * @return name of the Word
	 */
	@Override
	public String toString() {
		return this.name;

	}

}
