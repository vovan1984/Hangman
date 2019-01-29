package hangman;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * This class is used to load players statistics from 
 * an input file.
 * 
 * @author Vladimir Igumnov
 *
 * @version 1.0
 */
public class HangmanStats
{
    // default charset
    private final static Charset def = Charset.forName("ISO-8859-1");
    
    private String[] names;  // names of players.
    private String[] scores; // scores of players.
    private String[] dates;  // dates when games were played.
 
    private String playersFilePath; // path to statistics file.

    /**
     * Constructor for a default charset. 
     * @param playersFilePath Path to a file with players statistics.
     */
    public HangmanStats(String playersFilePath)
    {
        this(playersFilePath, def);
    }
    
    /**
     *  Constructor for an input charset.
     * @param playersFilePath Path to a file with players statistics.
     * @param charset Encoding of a players file
     */
    public HangmanStats(String playersFilePath, Charset charset)
    {
        this.playersFilePath = playersFilePath; 
        File playersFile = new File(playersFilePath);          
        
        names = null; 
        scores = null; 
        dates = null; 
        
        // check if players file exists and is a regular file.
        if (!playersFile.isFile())
        {
            System.out.println("Players statistics file was not found!");
            return;
        }
        
        try
        {
            List<String> lines = Files.readAllLines(playersFile.toPath(), charset);          
            
            if (lines.size() <= 0)
                return;
            
            names = new String[lines.size()];
            scores = new String[lines.size()];
            dates = new String[lines.size()];
            
            // Sort lines by score descending and then by date ascending
            lines.sort(new CompScore().reversed().thenComparing(new CompDate()));
            
            String[] stats = lines.toArray(new String[lines.size()]);

            for (int i=0; i < stats.length; i++)
            {
                int start, end;
                
                start = stats[i].trim().lastIndexOf("] ") + 2;
                end = stats[i].trim().lastIndexOf(": ");
                names[i] = stats[i].substring(start, end);
                
                start = stats[i].trim().lastIndexOf(": ") + 2;
                end = stats[i].trim().lastIndexOf(" points");
                scores[i] = Integer.parseInt(stats[i].substring(start, end)) + "";
                
                start = stats[i].trim().lastIndexOf("[") + 5;
                end = stats[i].trim().lastIndexOf("] ");
                dates[i] = stats[i].substring(start, start + 7) + stats[i].substring(start + 20, end);
            }
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    
    /**
     * Retrieve path to the players statistics file.
     * 
     * @return Path to the statistics file.
     */
    public String getPlayersFilePath()
    {
        return playersFilePath;
    }

    public String[] getNames()
    {
        return names;
    }

    public String[] getScores()
    {
        return scores;
    }

    public String[] getDates()
    {
        return dates;
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
}
