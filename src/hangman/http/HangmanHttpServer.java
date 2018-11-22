package hangman.http;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * This class constructs HTTP server which listens for 
 * incoming connections and submits them to threads pool for
 * processing.
 * 
 * @author Vladimir Igumnov
 *
 */
public class HangmanHttpServer
{
	private final static int MAX_THREADS = 3;
	private final static int PORT = 2501; // port to listen for incoming connections
	private final static Logger logger = Logger.getLogger(HangmanHttpServer.class.getCanonicalName());

	public static void main(String[] args)
	{
		// create threads pool to serve incoming connections
		ExecutorService threadPool = Executors.newFixedThreadPool(MAX_THREADS);
		
		// create ServerSocket to listen on predefined port
		try (var server = new ServerSocket(PORT))
		{
			var localHost = InetAddress.getLocalHost();
			System.out.println("Server's address : " + localHost.getHostAddress());
			System.out.println("Server's name    : " + localHost.getHostName());
			System.out.println("Listening to port: " + server.getLocalPort());
			
			while (true)
			{
				var socket = server.accept();
				InputStreamReader isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
				int c;
				
				System.out.print("Received request from client: \"");
				while ((c = isr.read()) != -1 )
					System.out.print((char)c);
				System.out.println("\"");
				
				System.out.println("Closing connection...");
				socket.close();
			}
		} catch (IOException e)
		{
			logger.severe("IO error during server setup");
			return;
		}

	}

}
