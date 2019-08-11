package hangman.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hangman.*;
import hangman.shared.HangmanFileDictionary;
import hangman.shared.HangmanFileStats;

/**
 * Web interface of the Hangman game
 */
public class HangmanWeb extends HttpServlet 
{
    private static final String LOGIN_PAGE="HangmanLogin.jsp";
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
	 * GET method is not supported, so redirect to start page
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException 
	{
	    doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException 
	{
	    HangmanWebPlayer player;
          
	    // Step1: Get current session or create new one
	    HttpSession session = getSession(request);
	    
	    /* Step2: If session can't be retrieved, or was invalidated, 
	     redirect to login page */
	    if (session == null)
	    {
            response.sendRedirect(LOGIN_PAGE);
            return;
	    }
	    
        // Step3: Create or retrieve player
        if (session.getAttribute("HangmanPlayer") != null)
	    {
            // Get existing player for the session
	        player = (HangmanWebPlayer)session.getAttribute("HangmanPlayer");
	        player.setInput(request.getParameter("UserInput"));
	    }
	    else // create player
	    {
            // Validate user name
            String firstName = request.getParameter("FirstName");
            String lastName = request.getParameter("LastName");
                  
            if (firstName != null && lastName != null &&
                    firstName.length() != 0 && lastName.length() != 0)
            {   
                // Get storage
                HangmanStats stats; 
                if (session.getAttribute("HangmanStats") != null)
                   stats = (HangmanStats)session.getAttribute("HangmanStats");
                else
                    stats = new HangmanFileStats();
                
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
	            response.sendRedirect(LOGIN_PAGE);
	            return;
            }
	    }
	    
	    // Step4: play the game if user clicked on submit button
        if (!request.getMethod().equals("GET"))
	        player.play();
	    
	    // Step5: forward request and response to JSP for display
	    request.getRequestDispatcher("/HangmanWeb.jsp").forward(request, response);
	}

	
	/**
	 * This method sets up new session or retrieves existing one.
	 * @param request Request containing user details
	 * 
	 * @return Created or Retrieved session 
	 */
    private HttpSession getSession(HttpServletRequest request)
    {
        HttpSession session;
        
        // Get existing session
        session = request.getSession(false);
        
        if (session != null)
        {    
            // Check if user decided to leave the game
            String action;
            if ( (action = request.getParameter("action")) != null 
                && action.equals("Exit the game"))
            {
                session.invalidate();
                session = null;
            }
        }
        else
            session = request.getSession(true); // create new session
        
        return session;
    }
}
