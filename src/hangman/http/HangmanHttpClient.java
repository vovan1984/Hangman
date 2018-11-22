package hangman.http;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * This class implements HTTP client which connects
 * to HTTP server to play a Hangman game.
 * 
 * @author Vladimir Igumnov
 *
 * @version 1.0
 */
public class HangmanHttpClient
{
	private final static String SERVER = "http://localhost:2501"; // server to connect to
	private final static Logger logger = Logger.getLogger(HangmanHttpClient.class.getCanonicalName());

	public static void main(String[] args)
	{
		try
		{
			var url = new URL(SERVER);
			var is = url.openStream();
		} catch (MalformedURLException e)
		{
			logger.severe("Malformed URL: " + SERVER);
			e.printStackTrace();
			return;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
