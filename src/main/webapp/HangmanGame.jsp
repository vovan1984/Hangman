<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>Welcome to the Hangman Game, <%= request.getParameter("FirstName") %>!</title>
    </head>
<body>

    <%
        if (session.getAttribute("HangmanFileStats") == null)
        {
            out.println("Wrong page!");
        }
        else
        {
            hangman.HangmanStats hfs = (hangman.HangmanStats)session.getAttribute("HangmanFileStats");
            out.println(hfs.toString());
        }
    %>
    
</body>
</html>