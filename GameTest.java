package hangman;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * GameTest unit test class for testing the Game class 
 * @author shwetachopra
 *
 */
class GameTest {

	//Set up instance variable for game to be tested
	Game tGame;
	Reader testDictionary;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@BeforeEach
	void setUp() throws Exception {
		this.testDictionary = new Reader("words_clean.txt");
		this.testDictionary.readList();
		this.tGame = new Game(testDictionary);

	}

	@Test
	void testGame() {
		//Check that dictionary set is testDictionary
		assertEquals(this.tGame.getDictionary(), this.testDictionary);
		//Check that arrays have been created
		assertEquals(0, this.tGame.getAllGuesses().size());
		assertEquals(0, this.tGame.getIncorrectGuesses().size());
		//Game mode selected
		assertNotNull(this.tGame.playEvil);
		//Game word list set
		assertNotNull(this.tGame.wordList);

	}

	@Test
	void testChooseWord() {
		int oldLength = this.tGame.wordList.size();
		this.tGame.chooseWord();

		//Ensure word has been chosen and length of words and letters array is equal
		assertEquals(this.tGame.getWord().length(),this.tGame.getLetters().size());
		//Ensure guessArray length is same as lettersArray length
		assertEquals(this.tGame.getLetters().size(), this.tGame.getGuessArray().size());
		//Ensure guess array consists of all false values
		for (int i = 0; i < this.tGame.getGuessArray().size(); i++) {
			assertFalse(this.tGame.getGuessArray().get(i));
		}
		//Pattern array set
		assertEquals(this.tGame.getLetters().size(), this.tGame.getPattern().size());
		//Word list edited
		int newLength = this.tGame.wordList.size();
		assertTrue(newLength <= oldLength);		
	}

	@Test
	void testRecordGuess() {
		//Test traditional
		this.tGame.setPlayEvil(false);

		//Set up word and letters and guessArray
		this.tGame.setWord(new Word("happy"));
		this.tGame.setLength(5);
		this.tGame.wordList = this.tGame.getDictionary().map.get(this.tGame.getLength());

		String[] array = {"h","a","p","p","y"};
		ArrayList<String> tLetters = new ArrayList<String>(Arrays.asList(array));
		this.tGame.setLetters(tLetters);

		ArrayList<Boolean> testA = new ArrayList<Boolean>();
		for (int i = 0; i < 5; i++) {
			testA.add(false);
		}
		this.tGame.setGuessArray(testA);

		//Correct Guess
		this.tGame.recordGuess("p");

		//Test
		assertEquals(1,this.tGame.getGuesses());
		assertTrue(this.tGame.getGuessArray().get(2));
		assertTrue(this.tGame.getGuessArray().get(3));
		assertTrue(this.tGame.getAllGuesses().contains("p"));

		//Incorrect Guess
		this.tGame.recordGuess("x");
		//Test
		assertEquals(2,this.tGame.getGuesses());
		assertEquals(1,this.tGame.getMistakes());
		assertTrue(this.tGame.getAllGuesses().contains("x"));
		assertTrue(this.tGame.getIncorrectGuesses().contains("x"));

		//Test EVIL
		this.tGame = new Game(testDictionary);
		this.tGame.setPlayEvil(true);

		//Set up word and letters and guessArray
		this.tGame.setWord(new Word("happy"));
		this.tGame.setLength(5);
		this.tGame.wordList = this.tGame.getDictionary().map.get(this.tGame.getLength());

		String[] array2 = {"h","a","p","p","y"};
		ArrayList<String> sLetters = new ArrayList<String>(Arrays.asList(array2));
		this.tGame.setLetters(sLetters);

		ArrayList<Boolean> testB = new ArrayList<Boolean>();
		for (int i = 0; i < 5; i++) {
			testB.add(false);
		}
		this.tGame.setGuessArray(testB);

		//Correct Guess
		this.tGame.recordGuess("h");

		//Manual checking shows h will be selected as incorrect based on word family size
		assertEquals(1,this.tGame.getGuesses());
		assertEquals(1,this.tGame.getMistakes());
		assertTrue(this.tGame.getAllGuesses().contains("h"));
		assertTrue(this.tGame.getIncorrectGuesses().contains("h"));
		assertFalse(this.tGame.getGuessArray().get(0));

	}

	@Test
	void testIsGameOver() {

		//Test traditional
		this.tGame.setPlayEvil(false);

		//Set up word and letters and guessArray
		this.tGame.setWord(new Word("cat"));
		this.tGame.setLength(3);
		this.tGame.wordList = this.tGame.getDictionary().map.get(this.tGame.getLength());		

		String[] array = {"c","a","t"};
		ArrayList<String> tLetters = new ArrayList<String>();
		tLetters.addAll(Arrays.asList(array));
		this.tGame.setLetters(tLetters);

		ArrayList<Boolean> testA = new ArrayList<Boolean>();
		for (int i = 0; i < 3; i++) {
			testA.add(false);
		}
		this.tGame.setGuessArray(testA);

		//Record guesses
		this.tGame.recordGuess("c");
		this.tGame.recordGuess("a");

		//Test
		assertFalse(this.tGame.isGameOver());

		this.tGame.recordGuess("t");

		//Test
		assertTrue(this.tGame.isGameOver());

	}


	@Test
	void testGuessedBefore() {
		//Set up word and letters and guessArray
		this.tGame.setWord(new Word("cat"));

		String[] array = {"c","a","t"};
		ArrayList<String> tLetters = new ArrayList<String>();
		tLetters.addAll(Arrays.asList(array));
		this.tGame.setLetters(tLetters);

		ArrayList<Boolean> testA = new ArrayList<Boolean>();
		for (int i = 0; i < 3; i++) {
			testA.add(false);
		}
		this.tGame.setGuessArray(testA);

		//Record guesses
		this.tGame.recordGuess("c");
		this.tGame.recordGuess("a");

		//Test
		assertTrue(this.tGame.guessedBefore("c"));
		assertFalse(this.tGame.guessedBefore("b"));
	}

	@Test
	void testToString() {
		//Test using traditional game
		this.tGame.setPlayEvil(false);

		//Set up word and letters and guessArray
		this.tGame.setWord(new Word("cat"));
		this.tGame.setLength(3);
		this.tGame.wordList = this.tGame.getDictionary().map.get(this.tGame.getLength());		

		String[] array = {"c","a","t"};
		ArrayList<String> tLetters = new ArrayList<String>();
		tLetters.addAll(Arrays.asList(array));
		this.tGame.setLetters(tLetters);

		ArrayList<Boolean> testA = new ArrayList<Boolean>();
		for (int i = 0; i < 3; i++) {
			testA.add(false);
		}
		this.tGame.setGuessArray(testA);

		//Record guesses
		this.tGame.recordGuess("c");

		assertEquals("c _ _", this.tGame.toString());


	}

}
