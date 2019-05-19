package hangman.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hangman.*;
import hangman.utils.HangmanFileDictionary;

/**
 * Web interface of the Hangman game
 */
public class HangmanWeb extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HangmanWeb() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException 
	{
	    HangmanWebPlayer player = null;
	    
	    // Set the content type
	    response.setContentType("text/html");
	    
	    // Get the PrintWriter
	    PrintWriter out = response.getWriter();
	       
	    // Get or setup the session
	    HttpSession session = request.getSession();
	    
	    // Validate user name
        String firstName = request.getParameter("FirstName");
        String lastName = request.getParameter("LastName");
              
        if (firstName != null && lastName != null &&
                firstName.length() != 0 && lastName.length() != 0)
        {
            // Create player
            HangmanStats stats = (HangmanStats)session.getAttribute("HangmanStats");
            HangmanDictionary dictionary = new HangmanFileDictionary();
            player = new HangmanWebPlayer(
                    request.getParameter("FirstName"),
                    request.getParameter("LastName"),
                    stats,
                    dictionary);
            session.setAttribute("HangmanPlayer", player);
            
        }
        else if (session.getAttribute("HangmanPlayer") != null)
	    {
            // Get existing player for the session
	        player = (HangmanWebPlayer)session.getAttribute("HangmanPlayer");
	        player.setInput(request.getParameter("UserInput"));
	    }
	    else // redirect
	    {
	        response.sendRedirect("HangmanLeaderboard.jsp");
	        return;
	    }
	    
	    // play the game
	    player.setOutput(out);
	    player.play();
	}
}
