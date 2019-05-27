<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hangman game</title>
    <link rel="icon" type="image/png" href="resources/Logo.png">
</head>

<body>
    <jsp:include page="HangmanHeader.jsp">
        <jsp:param name="HeaderTitle" value="Welcome to the Hangman game!"/>
    </jsp:include> 
    
    <aside>
        <figure>
        <img alt="hangman gif" src="resources/Hangman.gif">
        <figcaption>Some hangman multimedia here</figcaption>
        </figure>
        <h3>Train your brain in style :)</h3>
        <p>Details on historical info</p>
    </aside>
    
    <section>
        <h2>Dedicated to the Craft of Building Websites.</h2>
        <p>In this web-based game, server selects random word, and
            suggests a player to guess a part of it (or as a whole).
            Player can fail for up to 10 times, however, points are
            earned only if a word is uncovered in a less than a 5
            misses.</p>
    </section>
    
    <jsp:include page="HangmanFooter.html"/> 
</body>
</html>
