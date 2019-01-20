package hangman;

import java.util.Scanner;

/**
 * Controller class to control the game play for Hangman
 * @author shwetachopra
 *
 */
public class Controller {

	public static void main(String[] args) {

		System.out.println("");
		System.out.println("Welcome to Hangman!");
		System.out.println("Guess the letters of the word to win.");


		//Initialize scanner
		Scanner userInput = new Scanner(System.in);
		Reader dictionary = new Reader("words.txt");
		dictionary.readList();

		//Allow for game to be played again
		boolean playAgain = true;

		while (playAgain) {

			Game newGame = new Game(dictionary);


			boolean gameOver = false;

			//Let computer choose word to start the game
			newGame.chooseWord();

			while (!gameOver) {

				//Testing
				//			System.out.println(newGame.playEvil);
				//			System.out.println(newGame.getWord());
				//			System.out.println(newGame.getLetters());
				//			System.out.println(newGame.getPattern());
				//			System.out.println(newGame.toString());
				//			System.out.println(newGame.wordList.size());
				//			System.out.println(newGame.length);
				//			System.out.println(newGame.wordMap.keySet());
				//			System.out.println(newGame.getAllGuesses());
				//			System.out.println(newGame.getAllGuesses().size());


				//Print out the game
				System.out.println("");
				System.out.println(newGame);
				System.out.println("");
				System.out.println("Incorrect Guesses: " + newGame.getIncorrectGuesses());
				System.out.println("");

				//Take user input
				boolean validInput = false;

				String input = null;

				while (!validInput) {

					System.out.println("");
					System.out.println("Enter a letter to guess: ");
					input = userInput.nextLine().trim().toLowerCase();

					//If the user has guessed the letter before, ask for input again
					if (newGame.guessedBefore(input)) {
						System.out.println("You have already guessed this letter before!");
						continue;
					}

					//If the input is not a single letter, ask for input again
					else if (input.length() != 1) {
						System.out.println("You can only guess one letter at a time!");
						continue;
					}

					//If the input is not a letter, ask for input again
					else if (!input.matches(".*[a-z].*")) {
						System.out.println("You can only guess letters. Please enter valid input!");
						continue;
					}

					else {
						validInput = true;
					}

				}

				newGame.recordGuess(input);
				gameOver = newGame.isGameOver();

			}

			//Game is over
			System.out.println("The word was: " + newGame.getWord());
			System.out.println("Congratulations! User Wins!");

			if (newGame.playEvil) {
				System.out.println("You were playing EVIL HANGMAN");
			}
			else {
				System.out.println("You were playing TRADITIONAL HANGMAN");
			}

			System.out.println("Number of Guesses: " + newGame.getGuesses());
			System.out.println("Number of Mistakes: " + newGame.getMistakes());

			//Check if user would like to play again
			System.out.println("\n");
			System.out.println("Would you like to play again? Enter Y for yes, anything else to exit.");
			String replay = userInput.nextLine().trim().toLowerCase();
			if (!replay.equals("y")) {
				playAgain = false;
			}

		}

		//Close scanner
		userInput.close();

	}

}
