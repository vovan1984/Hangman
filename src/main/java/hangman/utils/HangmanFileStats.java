package hangman.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import hangman.HangmanGame;
import hangman.HangmanPlayer;
import hangman.HangmanStats;

/**
 * This class is used to load players statistics from 
 * an input file.
 * 
 * @author Vladimir Igumnov
 *
 * @since 1.0
 */
public class HangmanFileStats implements HangmanStats
{
    // Default file with names and scores of players
    private final static String DEF_PLAYERS_FILE = "player.txt";
    
    // default charset
    private final static Charset DEF_CHARSET = Charset.forName("ISO-8859-1");
    
    public final static int NAME_IDX = 0; // index of name in a record
    public final static int SCORE_IDX = 1;// index of score in a record
    public final static int DATE_IDX = 2; // index of date in a record
    
    private Charset charset;  // encoding of the statistics file.
    private File playersFile; // statistics file.

    /**
     * Default constructor. 
     */
    public HangmanFileStats()
    {
        this(DEF_PLAYERS_FILE, DEF_CHARSET);
    }
    
    /**
     * Constructor for a default charset. 
     * @param playersFilePath Path to a file with players statistics.
     */
    public HangmanFileStats(String playersFilePath)
    {
        this(playersFilePath, DEF_CHARSET);
    }
    
    /**
     * Constructor for an input charset.
     * @param playersFilePath Path to a file with players statistics.
     * @param charset Encoding of a players file
     */
    public HangmanFileStats(String playersFilePath, Charset charset)
    { 
        this.charset = charset;
         
        /*
         *  Use default file path if provided one is empty.
         */
        if (playersFilePath == null || playersFilePath.equals(""))
            this.playersFile = new File(DEF_PLAYERS_FILE);
        else
            this.playersFile = new File(playersFilePath);
    }
   
    /* 
     *  Compare strings by score.
     */
    private class CompScore implements Comparator<String>
    {

        @Override
        public int compare(String o1, String o2)
        {
            // field #9 contains score just after ':'
            int startScore1Indx = o1.trim().lastIndexOf(": ") + 2;
            int startScore2Indx = o2.trim().lastIndexOf(": ") + 2;
            int endScore1Indx = o1.trim().lastIndexOf(" points");
            int endScore2Indx = o2.trim().lastIndexOf(" points");
            
            // compare scores
            int score1 = Integer.parseInt(o1.trim().substring(startScore1Indx, endScore1Indx));
            int score2 = Integer.parseInt(o2.trim().substring(startScore2Indx, endScore2Indx));
            
            return score1 - score2;
        }
        
    }
    
    /* 
     *  Compare strings by date.
     */
    private class CompDate implements Comparator<String>
    {

        @Override
        public int compare(String o1, String o2)
        {
            // field #9 contains score just after ':'
            int start1 = o1.trim().lastIndexOf("[") + 5;
            int start2 = o2.trim().lastIndexOf("[") + 5;
            int end1 = o1.trim().lastIndexOf("] ");
            int end2 = o2.trim().lastIndexOf("] ");
            
            // compare scores
            String dateStr1 = o1.substring(start1, start1 + 7) + o1.substring(start1 + 20, end1);
            String dateStr2 = o2.substring(start2, start2 + 7) + o2.substring(start2 + 20, end2);
            
            Date date1, date2;
            try
            {
                date1 = new SimpleDateFormat("MMM dd yyyy").parse(dateStr1);
                date2 = new SimpleDateFormat("MMM dd yyyy").parse(dateStr2);
            } catch (ParseException e)
            {
                e.printStackTrace();
                throw new IllegalArgumentException("Wrong date format in players file!");
            }
            
            return date1.compareTo(date2);
        }
        
    }

    @Override
    public String[][] getResults()
    {
        String[][] data = null; // array of {name, score, date} sets
        
        if (playersFile.isFile())
            try
            {
                List<String> lines = Files.readAllLines(playersFile.toPath(), charset);          

                if (lines.size() <= 0)
                    return null;

                // Sort lines by score descending and then by date ascending
                lines.sort(new CompScore().reversed().thenComparing(new CompDate()));

                String[] stats = lines.toArray(new String[lines.size()]);
                data = new String[lines.size()][3]; // array of {name, score, date} sets

                for (int i=0; i < stats.length; i++)
                {
                    int start, end;

                    // get name
                    start = stats[i].trim().lastIndexOf("] ") + 2;
                    end = stats[i].trim().lastIndexOf(": ");
                    data[i][NAME_IDX] = stats[i].substring(start, end);

                    // get score
                    start = stats[i].trim().lastIndexOf(": ") + 2;
                    end = stats[i].trim().lastIndexOf(" points");
                    data[i][SCORE_IDX] = Integer.parseInt(stats[i].substring(start, end)) + "";

                    // get date
                    start = stats[i].trim().lastIndexOf("[") + 5;
                    end = stats[i].trim().lastIndexOf("] ");
                    data[i][DATE_IDX] = stats[i].substring(start, start + 7) + stats[i].substring(start + 20, end);
                }
            } 
            catch (IOException e)
            {
                e.printStackTrace();
            }
        
        return data; 
    }

    /**
     * Save result of the game into players file.
     * 
     * @param player User playing the game.
     * @param game Instance of the Hangman game which was played.
     */
    @Override
    public void saveResult(HangmanPlayer player, HangmanGame game)
    {
        try (
                // create file if it doesn't exist, or append to it if exists.
                PrintStream writer 
                = new PrintStream(new FileOutputStream(playersFile, playersFile.isFile()));
                )
        {   
            String postfix = game.getRounds() > 1 ? " rounds." : " round.";
            writer.println("[" + new Date() + "] " + 
                    player.getFirstName() + " " + player.getLastName() + ": " 
                    + game.getScore() + " points in " + game.getRounds() + postfix);
        } catch (IOException e)
        {
            System.out.println("Failed to store result of the game to "
                    + playersFile.getAbsolutePath() + "!");
            e.printStackTrace();
        } 
    }
}
