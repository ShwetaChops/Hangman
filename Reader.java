package hangman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.Map;

/**
 * Reader class to read words from file and generate clean word list for Hangman
 * @author shwetachopra
 */
public class Reader {

	//Define instance variables
	/**
	 * Name of file being read
	 */
	protected String filename;

	/**
	 * List of clean words from dictionary
	 */
	protected ArrayList<String> list;

	/**
	 * Map of words by length of word
	 */
	protected Map<Integer, ArrayList<String>> map;

	//Define constructor
	/** 
	 * Constructor for the Reader class that sets the name of the file used,
	 * and sets up word list and word map 
	 * @param filename
	 */
	public Reader(String filename) {
		this.filename = filename;
		this.list = new ArrayList<String>();
		this.map = new HashMap<Integer, ArrayList<String>>();
	}

	//Define methods
	/**
	 * Method reads dictionary and creates list of all valid words. Also adds words to a map 
	 * based on the key - length of word
	 */
	public void readList(){

		//Create file object
		File myFile = new File(this.filename);

		//Declare buffered reader
		BufferedReader bufferedReader;

		try {
			bufferedReader = new BufferedReader(new FileReader(myFile));

			String readData = bufferedReader.readLine();

			//Read lines while text left to read
			while(readData != null) {

				readData  = readData.trim();

				//Clean and remove words that do not satisfy criteria
				if ((!readData.isEmpty()) && (!readData.contains("-")) && (!readData.contains("'")) &&
						(!readData.contains(".")) && (!readData.contains(" "))
						&& (!readData.matches(".*\\d+.*")) && (!readData.matches(".*[A-Z].*"))) {

					//Add word to word list
					this.list.add(readData);
					//Add word to word map
					if (!this.map.containsKey((Integer)readData.length())) {
						this.map.put((Integer)readData.length(), new ArrayList<String>());
					}
					this.map.get((Integer)readData.length()).add(readData);

				}	

				readData = bufferedReader.readLine();
			}

			//Close buffered reader
			bufferedReader.close();

		} catch (FileNotFoundException e1) {

			e1.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		} 
	}
}
