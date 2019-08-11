package hangman.shared;

import java.io.IOException;

/**
 * Standalone utilities for Hangman game
 * @author Vladimir Igumnov
 * @since 1.0
 */
public class HangmanUtils
{  
    /**
     * Clear the console window by ringing corresponding ANSI escape codes
     * (clear screen, followed by home).
     * On Windows we do the cleaning by running cls command from cmd terminal.
     */
    public static void clearScreen()
    {               
        try
        {
            final String os = System.getProperty("os.name");

            if (System.console() != null && os.contains("Windows"))
            {
                // Clear screen in Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else if (System.console() != null && System.getenv().get("TERM") != null)
            {
                // Clear screen in console supporting ANSI 
                System.out.print("\033[H\033[2J");  
                System.out.flush();  
            }
            else
            {
                // just print empty lines to clear the screen
                for (int i = 0; i<100; i++)
                    System.out.println();
            }
        }
        catch (IOException e) 
        {
            System.out.println("IO error while clearing the screen!");
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println();
    }
}
