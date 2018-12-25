package hangman.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import hangman.HangmanDictionary;
import hangman.HangmanPlayersInfo;

/**
 * This class retrieves list of players' scores in
 * descending order and displays them in the window.
 * 
 * @author Vladimir Igumnov
 *
 */
public class HangmanLeaderboardWindow extends HangmanWindow implements ActionListener
{
    private static final long serialVersionUID = 1L;
    
    //Percentage of column widths in the table. It should sum to 100%
    private static final float[] columnWidthPercentage = 
                           {15.0f, 45.0f, 15.0f, 25.0f};
    
    // set default font
    private final Font defHeaderFont = new Font("Serif", Font.PLAIN, 40);
    private final Font defFont = new Font("Serif", Font.PLAIN, 20);
    private final Font defFontBold = new Font("Serif", Font.BOLD, 20);
    
    private JTable table;
    private JTextField firstName;
    private JTextField lastName;
    private HangmanDictionary dictionary;
    private HangmanPlayersInfo playersInfo;

    public HangmanLeaderboardWindow(String title,
            HangmanDictionary dictionary,
            HangmanPlayersInfo playersInfo)
    {
        super(title);
        this.dictionary = dictionary;
        this.playersInfo = playersInfo;
        
        /*
         *  Split upper panel to two parts:
         *  header and statistics area.
         */
        // Setup header panel.
        Panel header = new Panel();    
        Label leaderBoard = new Label("LEADERBOARD", Label.CENTER);
        leaderBoard.setFont(defHeaderFont);
        header.add(leaderBoard);
        
        // Setup statistics area.
        table = loadStatsToTable(playersInfo);
        JScrollPane stats = new JScrollPane(table);
        stats.setBorder(BorderFactory.createMatteBorder(20, 40, 20, 0, new Color(0xFFBEDDFC)));
        resizeColumns(); // setup column widths in the table.
        
        upperPane.setLayout(new BorderLayout());        
        upperPane.add(header, BorderLayout.NORTH);
        upperPane.add(stats, BorderLayout.CENTER);
        
        // handler for resize event
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                resizeColumns(); // repaint the window if it was resized
            }
        }); 
        
        // Setup lower panel.
        firstName = new JTextField(9);
        firstName.setFont(defFontBold);
        firstName.setText("FIRSTNAME");
        firstName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, HangmanWindow.LIGHT_BLUE));
        firstName.setForeground(HangmanWindow.LIGHT_BLUE);
        
        lastName = new JTextField(9);
        lastName.setFont(defFontBold);
        lastName.setText("LASTNAME");
        lastName.setForeground(HangmanWindow.LIGHT_BLUE);
        lastName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, HangmanWindow.LIGHT_BLUE));
        JButton newGameButton = new JButton("START!");
        newGameButton.setFont(defFont);
        newGameButton.setBackground(HangmanWindow.LIGHT_BLUE); // light blue button
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setPreferredSize(new Dimension(200, 40));
        newGameButton.setBorder(BorderFactory.createEmptyBorder());
        
        lowerPane.setLayout(new GridBagLayout());
        GridBagConstraints lowAlignment = new GridBagConstraints();
        lowAlignment.anchor = GridBagConstraints.CENTER;
        lowAlignment.weightx = 0.1;
        lowerPane.add(firstName, lowAlignment);
        lowerPane.add(lastName,lowAlignment);
        lowerPane.add(newGameButton,lowAlignment);
        
        // handler for pressing Start button.
        newGameButton.addActionListener(this);
    }

    /**
     * This function returns table with players statistics to be
     * displayed in a scrolling pane.
     * 
     * @param playersInfo Players statistics loaded from file and
     *        sorted in descended order by score.
     *        
     * @return JTable Properly formatted and populated table to be
     *                added to a scroll panel.
     */
    private JTable loadStatsToTable(HangmanPlayersInfo playersInfo)
    {        
        String[] columnNames = {"RANK", "PLAYER", "SCORE", "DATE"};
        String[] emptyLine = {"","", "", ""};
        String[] scores = null, names = null, dates = null;
        
        int numOfGames = 0;
        
        // load players info
        if (playersInfo != null && playersInfo.getScores() != null)
        {
            scores = playersInfo.getScores();
            names = playersInfo.getNames();
            dates = playersInfo.getDates();
            
            numOfGames = scores.length;
        }
        
        // 2 rows (header and empty) + number of games
        Object[][] data = new String[numOfGames + 2][];
        
        data[0] = columnNames;
        data[1] = emptyLine;
        
        for (int i=0; i<numOfGames; i++)
        {
            data[i+2] = new String[4];
            data[i+2][0] = (i+1) + "."; // rank
            data[i+2][1] = names[i].toUpperCase();
            data[i+2][2] = scores[i];
            data[i+2][3] = dates[i];
        }
        
        JTable table = new JTable(data, columnNames);
        table.setShowGrid(false);
        table.setFillsViewportHeight(true);
        table.setOpaque(true);
        table.setBackground(HangmanWindow.LIGHT_BLUE); // light blue button
        table.setForeground(Color.WHITE);
        table.setFont(defFont);
        table.setRowHeight(defFont.getSize() + 8);
        table.setTableHeader(null);
     
        return table;
    }

    /*
     * Resize columns in table proportionally.
     */
    private void resizeColumns() 
    {
        int tW = table.getWidth();
        TableColumn column;
        TableColumnModel jTableColumnModel = table.getColumnModel();
        int cntCols = jTableColumnModel.getColumnCount();
        for (int i = 0; i < cntCols; i++) 
        {
            column = jTableColumnModel.getColumn(i);
            int pWidth = Math.round(columnWidthPercentage[i] * tW);
            column.setPreferredWidth(pWidth);
        }
    }

    /**
     * This method is called when Start button is pressed.<br>
     * The method hides LeaderBoard window, creates player, and plays
     * games in a loop.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
        
        // Create a player with name taken from text fields.
        HangmanGuiPlayer player = new HangmanGuiPlayer(playersInfo.getPlayersFilePath(), 
                                                       firstName.getText(), 
                                                       lastName.getText(),
                                                       dictionary);
        
        // Play the game.
        player.play();  
    }
}
