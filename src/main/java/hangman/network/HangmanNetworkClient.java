package hangman.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class represents network client for the 
 * Hangman game. <br>
 * The program collects player information
 * and initiates communication with the server.
 * 
 * @author Vladimir Igumnov
 * @version 1.0
 */
public class HangmanNetworkClient
{
	private static String host;
	private static int port;
	
	/**
	 * @param args address and port for Hangman server
	 */
	public static void main(String[] args)
	{
		if (args.length != 2)
		{
			System.out.println("Usage: HangmanNetwordClient <host> <port>");
			return;
		}

		host = args[0];

		try
		{
			port = Integer.parseInt(args[1]);
		}
		catch (NumberFormatException e)
		{
			System.out.println("Port number should be numeric!\n" +
			                   "Usage: HangmanNetwordClient <server> <port>");
			return;
		}
		
		// To read data from terminal
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Hi! Welcome to my hangman Game!");
		
		// Get player info
		String firstName, lastName;
		try
		{
			System.out.print("What's your last name?\n--> ");
			lastName = consoleReader.readLine();
			System.out.print("And what's your first name?\n--> ");
			firstName = consoleReader.readLine();
		} catch (IOException e)
		{
			System.out.println("I/O error while getting your name! Using default \"John Doe\"");
			firstName = "John";
			lastName = "Doe";
		}
		
		// connect to server
		try (var socket = new Socket(host, port))
		{
			String request;
			String response;
			
			// open reading and writing streams
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			
			// send user details to the server
			bw.write(firstName + "\r\n" + lastName + "\r\n");
			bw.flush();
			
			// process the 2-line greeting
			response = br.readLine();
			do
			{
				System.out.println(response);
				response = br.readLine();
				System.out.println(response);

				// process input request
				response = br.readLine();
				do
				{
					System.out.println(response);
					response = br.readLine();
					System.out.print(response);
					request = consoleReader.readLine();
					bw.write(request + "\r\n");
					bw.flush();

					// process the 1-line response
					response = br.readLine();
					System.out.println(response);

					// process the final outcome or continue
					response = br.readLine();
				}
				while (!response.contains("points earned after")); // the string containing final result

				System.out.println(response);

				// read question about game continuation
				response = br.readLine();
				System.out.println(response);
				response = br.readLine();
				System.out.print(response);
				request = consoleReader.readLine();
				bw.write(request + "\r\n");
				bw.flush();
				response = br.readLine();
			} while (!response.equals("Good Bye!")); // no more games will be played

			System.out.println(response);
		} catch (UnknownHostException e)
		{
			System.out.println("Host \"" + host + "\" was not found!");
			e.printStackTrace();
		} catch (IOException e)
		{
			System.out.println("IO error!");
			e.printStackTrace();
		}
	}

}
