<%@page import="hangman.HangmanGame"%>
<%@page import="hangman.web.HangmanWebPlayer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <jsp:include page="resources/HangmanTitle.jsp"/>
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
    
    <jsp:include page="resources/HangmanHeader.jsp">
        <jsp:param name="HeaderTitle" value="<%=\"Welcome to the Hangman game, \" 
               + player.getFirstName()%>"/>
    </jsp:include> 
      
    <%
        if (game.isGameCompleted())
        {    
    %>
            <section>
                <strong><%=game %></strong><br>
                <img src=<%=String.format("\"resources/images/pendu%02d.jpg\"", game.getFailures())%>>
            </section>
            
            <form action="HangmanWeb" method="post">
                <input type="submit" name="action" value="Play again!">
                <input type="submit" name="action" value="Exit the game">
            </form> 
     <%
        }
        else
        {
     %>
            <section><%= game.getMaskedWord() %><br>  
            <img src=<%=String.format("\"resources/images/pendu%02d.jpg\"", game.getFailures())%>>         
            </section>
            
            <section>
                <form action="HangmanWeb" method="post">
                    Your guess: <input type="text" name="UserInput" autofocus><br>
                    <input type="submit" value="Submit">
                    <input type="submit" name="action" value="Exit the game">
                </form>
            </section>
            
    <%        
        }
    %>
    
    <jsp:include page="resources/HangmanFooter.html"/> 
</body>
</html>