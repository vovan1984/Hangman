<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE HTML>
<html lang="en">
    <jsp:include page="resources/HangmanTitle.jsp"/>
    
    <body>
        <jsp:include page="resources/HangmanHeader.jsp">
            <jsp:param name="HeaderTitle" value="Welcome to the Hangman game!"/>
        </jsp:include> 
    
        <main>
            <aside>
                <h1>Many ways to play.</h1>
                <figure>
                    <img alt="Screenshots of the game" src="resources/images/Hangman.gif">
                    <figcaption>Stages of the gallows</figcaption>
                </figure>
                <p>You can play Hangman in Console, Web, and on a Desktop.</p>
            </aside>
    
            <section>
                <h1>Dedicated to the Craft of Building Websites.</h1>
                <p>
                In this web-based game, server selects random word, and
                suggests a player to guess a part of it (or as a whole).
                Player can fail for up to 10 times, however, points are
                earned only if a word is uncovered in a less than a 5
                misses.
                </p>
            </section>
        </main>
    
        <jsp:include page="resources/HangmanFooter.html"/> 
    </body>
</html>
