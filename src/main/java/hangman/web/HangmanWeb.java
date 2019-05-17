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
	    HangmanWebPlayer player;
	    
	    // Step1: set the content type
	    response.setContentType("text/html");
	    
	    // Step2: get the PrintWriter
	    PrintWriter out = response.getWriter();
	       
	    // Step3: get the session
	    HttpSession session = request.getSession();
	    
	    // Step4: create a player if it is not yet created
	    if (session.getAttribute("HangmanPlayer") == null)
	    {
	        HangmanStats stats = (HangmanStats)session.getAttribute("HangmanStats");
	        HangmanDictionary dictionary = new HangmanFileDictionary();
	        player = new HangmanWebPlayer(
	                request.getParameter("FirstName"),
	                request.getParameter("LastName"),
	                stats,
	                dictionary);
	        session.setAttribute("HangmanPlayer", player);
	    }
	    else
	    {
	        player = (HangmanWebPlayer)session.getAttribute("HangmanPlayer");
	        player.setInput(request.getParameter("UserInput"));
	    }
	    
	    player.setOutput(out);
	    player.play();
	}
}
