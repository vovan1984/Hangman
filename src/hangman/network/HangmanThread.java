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

/**
 * Main flow of the server to client communication 
 * during the Hangman game.
 * 
 * @author Vladimir Igumnov
 * @version 1.0.
 */
public class HangmanThread implements Runnable
{	
	private final Socket client;
	private final SocketAddress clientAddress;
	private final String logPrefix;
	private final String dictionaryFile;
	private final String playersFile;
	
	private final static Logger logger = Logger.getLogger(HangmanThread.class.getName());
	
	/**
	 * Create server-client interaction thread.
	 * 
	 * @param client Hangman game remote client.
	 * @param dictionaryFile Dictionary file.
	 * @param playersFile File to store players statistics. 
	 */
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

	/**
	 *  Interaction with a network client.
	 */
	@Override
	public void run()
	{
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
			
			var player = new HangmanNetworkPlayer(playersFile, 
                    firstName, 
                    lastName,
                    dictionary,
                    is,
                    os,
                    logPrefix);
			
			player.play();
			
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
