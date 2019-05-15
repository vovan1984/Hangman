<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1">
        <title>Hangman Game Leaderboard</title>
    </head>
<body>
    <header>Leaderboard</header>
    <table>
        <tr>
            <th>Rank</th>
            <th>Player</th>
            <th>Score</th><th>Date</th>
        </tr>
        <% 
            hangman.HangmanStats storage = new hangman.utils.HangmanFileStats(
                    "C:/Users/Maestro/Projects/Hangman/src/main/resources/player.txt");
        
            session.setAttribute("HangmanFileStats", storage);
        
            String[][] records = storage.getResults();
            
            if (records!=null)
                for (int i=0; i<records.length; i++)
                {
                    out.println("<tr>\n<td>" + (i + 1) + ".</td>");
                    for (int j=0; j<3; j++)
                       out.println("<td>" + records[i][j] + "</td>");
                    out.println("</tr>");
                }
        %>
    </table>
    
    <form action="HangmanGame.jsp" method="post">
        First name: <input type="text" placeholder="FIRSTNAME" name="FirstName">
        Last name: <input type="text" placeholder="LASTNAME" name="LastName"><br/>
        <input type="submit" value="START!">
    </form>
</body>
</html>