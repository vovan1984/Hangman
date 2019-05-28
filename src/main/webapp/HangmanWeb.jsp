<%@page import="hangman.HangmanGame"%>
<%@page import="hangman.web.HangmanWebPlayer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hangman game</title>
    <link rel="icon" type="image/png" href="resources/Logo.png">
</head>
<body>    
    <% 
        HangmanGame game = null;
        HangmanWebPlayer player=null;
        
        if ( session.getAttribute("HangmanPlayer") == null)
            response.sendRedirect("index.jsp");
        else 
        {
            player = (HangmanWebPlayer)session.getAttribute("HangmanPlayer");
            game = player.getGame();
        }
    %>
    
    <jsp:include page="HangmanHeader.jsp">
        <jsp:param name="HeaderTitle" value="<%=\"Welcome to the Hangman game, \" 
               + player.getFirstName()%>"/>
    </jsp:include> 
      
    <%
        if (game.isGameCompleted())
        {    
            out.println("<section>");
            out.println("<strong>" + game + "</strong><br>");
            out.println(String.format("<img src=\"resources/pendu%02d.jpg\">", 
                    game.getFailures()));
            out.println("</section>");
            
            out.println("<form action=\"HangmanWeb\" method=\"post\">");
            out.println("<input type=\"submit\" name=\"action\" value=\"Play again!\">");
            out.println("<input type=\"submit\" name=\"action\" value=\"Exit the game\">");
            out.println("</form>"); 
        }
        else
        {
            out.println("<section>" + game.getMaskedWord() + "<br>");
            out.println(String.format("<img src=\"resources/pendu%02d.jpg\">", 
                    game.getFailures()));                        
            out.println("</section>");
            
            out.println("<section>");
            out.println("<form action=\"HangmanWeb\" method=\"post\">");
            out.println("Your guess: <input type=\"text\" name=\"UserInput\" autofocus><br>");
            out.println("<input type=\"submit\" value=\"Submit\">");
            out.println("<input type=\"submit\" name=\"action\" value=\"Exit the game\">");
            out.println("</form>");
            out.println("</section>");
        }
    %>
    
    <jsp:include page="HangmanFooter.html"/> 
</body>
</html>