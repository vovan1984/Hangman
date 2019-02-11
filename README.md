# Hangman game  
## Description
This project represents implementation of a [Hangman game with a given dictionary](https://en.wikipedia.org/wiki/Hangman_%28game%29 "Wiki"). A player is provided an opportunity to guess the secret word using up to 10 attempts.  The player can enter both single letters and substrings (including whole word). After 10 incorrect attempts the game ends, and user can start another game. Positive score is only earned if the word is guessed using up to 5 attempts. Below table represents the scoring matrix:

* 0 incorrect choices - 100 points  
* 1 incorrect choices - 50 points  
* 2 incorrect choices - 25 points  
* 3 incorrect choices - 10 points  
* 4 incorrect choices - 5 points  
* 5 to 10 incorrect choices - 0 points  