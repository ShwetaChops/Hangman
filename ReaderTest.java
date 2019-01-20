package hangman;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * ReaderTest unit test class for testing the Reader class 
 * @author shwetachopra
 */
class ReaderTest {

	//Set instance variables
	Reader testDictionary;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		this.testDictionary = new Reader("words.txt");
	}

	@Test
	void testReader() {
		assertEquals("words.txt", this.testDictionary.filename);
	}

	@Test
	void testReadList() {
		this.testDictionary.readList();

		//Test for correctly cleaned word list
		for (int i = 0; i < this.testDictionary.list.size(); i++) {

			assertFalse(this.testDictionary.list.get(i).contains("-"));
			assertFalse(this.testDictionary.list.get(i).contains("'"));
			assertFalse(this.testDictionary.list.get(i).contains("."));
			assertFalse(this.testDictionary.list.get(i).contains(" "));
			assertFalse(this.testDictionary.list.get(i).matches(".*\\d+.*"));
			assertFalse(this.testDictionary.list.get(i).matches(".*[A-Z].*"));
		}

		//Check for created word map
		assertTrue(this.testDictionary.map.get(7).contains("letched"));
		assertTrue(this.testDictionary.map.get(13).contains("exceptionally"));
		assertTrue(this.testDictionary.map.get(8).contains("teamster"));
		assertFalse(this.testDictionary.map.containsKey(0));


	}
}
