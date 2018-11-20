package hangman.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents network server for the 
 * Hangman game. <br>
 * The program starts pool of threads for processing network connections.<br>
 * 
 * @author Vladimir Igumnov
 * @version 1.0
 */
public class HangmanNetworkServer
{
	// file with names and scores of players
	private static String playersFile = "player.txt";
	
	// default dictionary file (can be overridden by user input)  
	private static String dictionaryFile = "dictionary.txt";
	
	private final static Logger logger = Logger.getLogger(HangmanNetworkServer.class.getName());
	
	/**
	 * @param args Input parameters for running the game server:
	 *             <ul>
	 *                <li><b>-p &lt;file_path&gt; </b> - Optional. Full path to the players file</li>
	 *                <li><b>-d &lt;file_path&gt; </b> - Optional. Full path to the dictionary file</li>
	 *             </ul>
	 */
	public static void main(String[] args)
	{
		// check if players and/or dictionary files are provided in input
		if (args.length > 0)
		{	
			for (int i = 0; i < args.length; i++)
			{
				if (args[i].equals("-d") && i < args.length-1)
				{
					i++;
					dictionaryFile = args[i];
				}
				else if (args[i].equals("-p") && i < args.length-1 )
				{
					i++;
					playersFile = args[i];					
				}
				else
				{
					System.out.println("Usage: java -jar HangmanConsole.jar [-d dictionary_file] [-p players_file]");
					return;				
				}
			}
		} // end of args.length > 0
		
		// Create pool with 3 threads
		ExecutorService pool = Executors.newFixedThreadPool(2);
		// Display IP address of the Server app
		try (var server = new ServerSocket(2501))
		{
			var localHost = InetAddress.getLocalHost();
			logger.info("Server's address : " + localHost.getHostAddress());
			logger.info("Server's name    : " + localHost.getHostName());
			logger.info("Listening to port: " + server.getLocalPort());
			
			// wait for clients to connect
			while (true)
			{
				try 
				{		
					var client = server.accept();
					logger.info("["+ client.getRemoteSocketAddress() + "] Got new connection. Starting new thread.");
					
					var interaction = new HangmanThread(client, dictionaryFile, playersFile);
					
					pool.submit(interaction);
				}
				catch (IOException ex) 
				{
					logger.log(Level.WARNING, "Error while getting connection from client!", ex);
				}		
			}
		} catch (UnknownHostException e)
		{
			logger.log(Level.SEVERE, "Could not get server address!", e);
		} catch (IOException e)
		{
			logger.log(Level.SEVERE, "Could not start server!", e);
		}
	} // end of main
}
