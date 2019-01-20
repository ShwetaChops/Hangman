package hangman;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test class to test the functioning of the Word class
 * @author shwetachopra
 *
 */
class WordTest {

	//Define instance variables
	Word w;

	Word x;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@BeforeEach
	void setUp() throws Exception {

		this.w = new Word("hangman");
		this.x = new Word("homework");

	}

	@Test
	void testWord() {

		//Create expected letters array
		ArrayList<String> l = new ArrayList<String>();
		l.add("h");
		l.add("a");
		l.add("n");
		l.add("g");
		l.add("m");
		l.add("a");
		l.add("n");

		String s = "hangman";

		//Test constructor
		assertEquals(s, this.w.name);
		assertEquals(l, this.w.letters);
	}

	@Test
	void testSplit() {
		//Expected array
		String[] e = {"h","a","n","g","m","a","n"};
		String[] f = {"h","ngm","n"};

		assertTrue(Arrays.equals(e, this.w.split("")));
		assertTrue(Arrays.equals(f, this.w.split("a")));

	}

	@Test
	void testLength() {
		assertEquals(7, this.w.length());
		assertEquals(8, this.x.length());
	}

	@Test
	void testPattern() {

		//Set up all and wrong array lists
		ArrayList<String> all = new ArrayList<String>();
		all.add("e");
		all.add("g");
		all.add("a");


		ArrayList<String> wrong = new ArrayList<String>();
		wrong.add("e");
		wrong.add("g");

		//Expected array list
		String[] e = {"_","a","_","_","_","a","_"};
		ArrayList<String> exp = new ArrayList<String>(Arrays.asList(e));

		//Test
		assertEquals(exp, this.w.pattern(7, all, wrong));


	}

	@Test
	void testToString() {
		assertEquals(w.name, this.w.toString());
		assertEquals(x.name, this.x.toString());
	}

}
