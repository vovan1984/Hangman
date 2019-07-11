<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE HTML>
<html lang="en">
    <jsp:include page="HangmanHead.jsp"/>   
    <body>
        <jsp:include page="HangmanHeader.jsp">
            <jsp:param name="HeaderTitle" value="Welcome to the Hangman game!"/>
        </jsp:include> 
    
        <main class="clear">
        
            <aside>
                <h1>Many ways to play.</h1>
                <p>You can play Hangman in Console, Web, and on a Desktop.</p>
            
                <figure>
                    <img alt="Screenshots of the Console game" src="resources/images/Console_Screenshot.jpg">
                    <figcaption>Console game</figcaption>
                </figure>

                <figure>
                    <img alt="Screenshots of the Desktop game" src="resources/images/GUI_Screenshot.jpg">
                    <figcaption>Desktop game</figcaption>
                </figure>            
            </aside>
            
            <section>
                <h1>Description of the game.</h1>
                <blockquote cite="https://en.wikipedia.org/wiki/Hangman_(game)">
                    Hangman is a guessing game for two or more players. One player thinks of a word, 
                    phrase or sentence and the other(s) tries to guess it by suggesting letters or numbers, 
                    within a certain number of guesses.
                </blockquote>
            </section>
            
            <section>
                <h1>Rules of Hangman.</h1>

                <p>
                    This project represents implementation of a Hangman game with a given dictionary.
                    A player is provided an opportunity to guess the secret word using up to 10 attempts. 
                    The player can enter both single letters and substrings (including whole word). 
                    After 10 incorrect attempts the game ends, and user can start another game. 
                    Positive score is only earned if the word is guessed using up to 5 attempts.
                </p>                
                <p>Below list represents the scoring matrix:</p>
                <ul>
                    <li>0 incorrect choices - 100 points</li>
                    <li>1 incorrect choices - 50 points</li>
                    <li>2 incorrect choices - 25 points</li>
                    <li>3 incorrect choices - 10 points</li>
                    <li>4 incorrect choices - 5 points</li>
                    <li>5 to 10 incorrect choices - 0 points</li>
                </ul>
                
            </section>
            
        </main>
    
        <jsp:include page="HangmanFooter.html"/> 
    </body>
</html>
