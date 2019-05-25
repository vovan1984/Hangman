<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Hangman Game Leaderboard</title>
        <link rel="icon" type="image/png" href="resources/Logo.png">
    </head>
<body>
    <header>
        <h1>Board of Leaders</h1>
        <nav>
            <a href="index.html">Description</a>
            <a href="HangmanLeaderboard.jsp">Statistics</a>
            <a href="HangmanWeb">Game</a>
            <a rel="author" href="About.html">About</a>
        </nav>
    </header>
    
    <table>
        <tr>
            <th>Rank</th>
            <th>Player</th>
            <th>Score</th><th>Date</th>
        </tr>
        <% 
            hangman.HangmanStats storage = new hangman.utils.HangmanFileStats(
                    "C:/Users/Maestro/Projects/Hangman/src/main/resources/player.txt");
        
            session.setAttribute("HangmanStats", storage);
        
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
    
    <section>
        <form action="HangmanWeb" method="post">
            First name: <input type="text" placeholder="FIRSTNAME" name="FirstName">
            Last name: <input type="text" placeholder="LASTNAME" name="LastName"><br/>
            <input type="submit" value="START!">
        </form>
    </section>
    
    <footer>
        <small> &copy; Vladimir Igumnov<br> <a rel="author"
            href="mailto:vladimir.igumnov@zoho.com?subject=Hangman%20game">Contact</a>
        </small>
    </footer>
</body>
</html>