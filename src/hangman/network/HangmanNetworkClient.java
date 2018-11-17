/**
 * This class represents network client for the 
 * Hangman game. <br>
 * The program collects player information
 * and initiates communication with the server.
 */
package hangman.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Vladimir Igumnov
 * 
 * @version 1.0
 */
public class HangmanNetworkClient
{
	public static void main(String[] args)
	{
		// To read data from terminal
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Hi! Welcome to my hangman Game!");
		
		// Get player info
		String firstName, lastName;
		try
		{
			System.out.print("What's your last name?\n--> ");
			lastName = reader.readLine();
			System.out.print("And what's your first name?\n--> ");
			firstName = reader.readLine();
		} catch (IOException e)
		{
			System.out.println("I/O error while getting your name! Using default \"John Doe\"");
			firstName = "John";
			lastName = "Doe";
		}
		
		System.out.println(firstName + " " + lastName);
	}

}
