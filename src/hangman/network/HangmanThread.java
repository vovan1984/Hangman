/**
 * 
 */
package hangman.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import hangman.HangmanDictionary;
import hangman.HangmanPlayer;

/**
 * @author Vladimir Igumnov
 *
 */
public class HangmanThread implements Runnable
{
	// "n" is to continue the game, "y" is for exit
	private final static String CONTINUE = "y";
	
	private final Socket client;
	private final SocketAddress clientAddress;
	private final String logPrefix;
	private final String dictionaryFile;
	private final String playersFile;
	
	private final static Logger logger = Logger.getLogger(HangmanThread.class.getName());
	
	public HangmanThread(Socket client,
			             String dictionaryFile,
			             String playersFile)
	{
		this.client = client;
		this.dictionaryFile = dictionaryFile;
		this.playersFile = playersFile;
		clientAddress = client.getRemoteSocketAddress();
		logPrefix = "[" + clientAddress + "] ";
	}

	/* Interaction with network client
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run()
	{
		String exitGame = CONTINUE;
		
		// process the connection
		try
		{
			// Load and randomly shuffle words in dictionary file
			HangmanDictionary dictionary = new HangmanDictionary(dictionaryFile);
			dictionary.shuffle(); 
			
			// open streams for reading and writing from network
			var is = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
			var os = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
			
			// create player
			String firstName = is.readLine();
			String lastName = is.readLine();
			
			HangmanPlayer player = new HangmanNetworkPlayer(playersFile, 
                    firstName, 
                    lastName, 
                    is,
                    os);
			
			// play games for words from dictionary
			while (exitGame.equalsIgnoreCase(CONTINUE))
			{	
				// play game for the next word from shuffled list
				player.playGame(dictionary.getNextWord());
				
				os.write("Do you want to play another game ? (y/n)" + "\r\n");
				os.write("--> " + "\r\n");
				os.flush();
				exitGame = is.readLine();
			}
			
			os.write("Good Bye!" + "\r\n");
			os.flush();
		} 
		catch (IllegalArgumentException e)
		{
			// problem with dictionary file or other critical issue
			logger.severe(e.getMessage());
			return;
		}
		catch (IOException e)
		{
			// Just print error message
			logger.log(Level.WARNING, logPrefix + "IO error!", e);
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				client.close();
			}
			catch (IOException e)
			{
				// Just print error message
				logger.log(Level.WARNING, logPrefix + "Failed to close connection", e);
				e.printStackTrace();
			}	
		}
		
		logger.info(logPrefix + "Connection terminated.");
	} // end of run()

}
