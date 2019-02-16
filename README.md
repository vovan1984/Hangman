# Hangman game  
## Description
This project represents implementation of a [Hangman game with a given dictionary][1]. A player is provided an opportunity to guess the secret word using up to 10 attempts.  The player can enter both single letters and substrings (including whole word). After 10 incorrect attempts the game ends, and user can start another game. Positive score is only earned if the word is guessed using up to 5 attempts.  

![Game can be played using GUI interface.][2]. ![It can be also played via console.][3]

Below table represents the scoring matrix:

* 0 incorrect choices - **100** points  
* 1 incorrect choices - **50** points  
* 2 incorrect choices - **25** points  
* 3 incorrect choices - **10** points  
* 4 incorrect choices - **5** points  
* 5 to 10 incorrect choices - **0** points  

 [1]: https://en.wikipedia.org/wiki/Hangman_(game) "Wiki"
 [2]: src/main/resources/GUI_Screenshot.jpg "GUI interface"
 [3]: src/main/resources/Console_Screenshot.jpg "Console interface"