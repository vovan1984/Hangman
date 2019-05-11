package hangman.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hangman.HangmanDictionary;
import hangman.utils.HangmanFileDictionary;

class HangmanFileDictionaryTest
{
    /**
     * Validate creation of default dictionary 
     * from resources included to the project.
     */
    @Test
    @DisplayName("Load a default dictionary")
    void testDefaultDictionaryCreation()
    {
        // load default test dictionary which has only one word.
        HangmanDictionary hd = new HangmanFileDictionary();
        assertEquals("Default", hd.getNextWord(), "Error in loading default dictionary.");
    }

    /**
     * Validate creation of dictionary from a file defined
     * by a user.
     */
    @Test
    @DisplayName("Load a dictionary from an input file")
    void testDictionaryCreationFromFile()
    {
        // load non-default test dictionary which has only one word.        
        HangmanDictionary hd = new HangmanFileDictionary("src/test/resources/nonDefaultDictionary.txt");
        assertEquals("Non-default", hd.getNextWord(), "Error in loading default dictionary.");
    }
}
