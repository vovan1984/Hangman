package hangman.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

import hangman.HangmanDictionary;
import hangman.HangmanPlayer;
import hangman.HangmanStats;
import hangman.shared.HangmanFileStats;

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

    // set default font
    private static final Font DEF_HEADER_FONT = new Font("Serif", Font.PLAIN, 40);
    private static final Font DEF_FONT_BOLD = new Font("Serif", Font.BOLD, 20);

    //Percentage of column widths in the table. It should sum to 100%
    private static final float[] columnWidthPercentages = 
        {15.0f, 45.0f, 15.0f, 25.0f};

    private JTable table;
    private JTextField firstName;
    private JTextField lastName;
    private HangmanDictionary dictionary;
    private HangmanStats playersInfo;

    public HangmanLeaderboardWindow(String title,
            HangmanDictionary dictionary,
            HangmanStats playersInfo)
    {
        super(title);
        this.dictionary = dictionary;
        this.playersInfo = playersInfo;

        /*
         *  Split upper panel to two parts:
         *  header and statistics area.
         */
        
        // Setup upper panel.
        Panel header = new Panel();    
        Label leaderBoard = new Label("LEADERBOARD", Label.CENTER);
        leaderBoard.setFont(DEF_HEADER_FONT);
        header.add(leaderBoard);

        upperPane.setLayout(new BorderLayout());        
        upperPane.add(header, BorderLayout.NORTH);
        upperPane.add(createStatisticsPane(), BorderLayout.CENTER); // history of played games.

        // Setup lower panel.
        firstName = createNameField("FIRSTNAME");
        lastName = createNameField("LASTNAME");

        lowerPane.setLayout(new GridBagLayout());
        GridBagConstraints lowAlignment = new GridBagConstraints();
        lowAlignment.anchor = GridBagConstraints.CENTER;
        lowAlignment.weightx = 1.0;
        lowerPane.add(firstName, lowAlignment);
        lowerPane.add(lastName, lowAlignment);
        lowerPane.add(createStartButton(),lowAlignment);     
    }

    /**
     * Create text field for entering player's name
     */
    private JTextField createNameField(String defValue)
    {
        JTextField field = new JTextField(9);
        field.setFont(DEF_FONT_BOLD);
        field.setText(defValue);
        field.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, LIGHT_BLUE));
        field.setForeground(LIGHT_BLUE);
        
        return field;
    }

    /**
     * Setup button for starting the game. This method 
     * will also add action listener for pressing the button event.
     * 
     * @return Created button.
     */
    private JButton createStartButton()
    {
        JButton newGameButton = new JButton("START!");
        newGameButton.setFont(DEF_FONT);
        newGameButton.setBackground(LIGHT_BLUE); // light blue button
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setOpaque(true);
        newGameButton.setFocusPainted(false);
        newGameButton.setBorderPainted(false);
        newGameButton.setPreferredSize(new Dimension(200, 40));

        // if player presses Enter or clicks, then start the game session.
        
        setFocusable(true); // has to be focusable to receive key events
        getRootPane().setDefaultButton( newGameButton );
        
        // handler for pressing Start button.
        newGameButton.addActionListener(this);
        
        return newGameButton;
    }

    /**
     * This nested class defines table model for history of Hangman games. 
     * It loads data from for an input statistics object and defines table
     * as non-editable (by not overriding isCellEditable() method). 
     */
    class HangmanStatsTableModel extends AbstractTableModel 
    {
        private static final long serialVersionUID = 1L;

        private String[] columnNames = {"RANK", "PLAYER", "SCORE", "DATE"};
        private Object[][] data;

        /**
         * Constructor for loading table data.
         * @param playersInfo
         */
        public HangmanStatsTableModel(HangmanStats playersInfo)
        {
            String[][] records = null;

            int numOfGames = 0;

            // load players info
            if (playersInfo != null)
            {
                records = playersInfo.getResults();
                
                if (records != null)
                    numOfGames = records.length;
            }

            // 1 row for each played game.
            data = new String[numOfGames][];

            // load table data from players info.
            for (int i=0; i<numOfGames; i++)
            {
                data[i] = new String[4];
                data[i][0] = (i+1) + "."; // rank
                data[i][1] = records[i][HangmanFileStats.NAME_IDX].toUpperCase();
                data[i][2] = records[i][HangmanFileStats.SCORE_IDX];
                data[i][3] = records[i][HangmanFileStats.DATE_IDX]; // date of the game
            }    
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

    }


    /**
     * This function returns table with players statistics to be
     * displayed in a scrolling pane.
     * 
     * @param playersInfo Players statistics loaded from file and
     *        sorted in descended order by score.
     *        
     * @return JScrollPane Properly formatted and populated table added
     *                     to a scroll panel.
     */
    private JScrollPane createStatisticsPane()
    {        
        // load statistics into the table
        table = new JTable(new HangmanStatsTableModel(playersInfo));
        
        // setup table data area appearance
        table.setShowGrid(false);
        table.setFillsViewportHeight(true);
        table.setOpaque(true);
        table.setBackground(LIGHT_BLUE); 
        table.setForeground(Color.WHITE);
        table.setFont(DEF_FONT);
        table.setRowHeight(DEF_FONT.getSize() + 8);
        table.setRowSelectionAllowed(false);
        
        // setup table header appearance
        JTableHeader jth = table.getTableHeader(); 
        jth.setBackground(LIGHT_BLUE);
        jth.setForeground(Color.white);
        jth.setFont(DEF_FONT);
        ((DefaultTableCellRenderer)jth.getDefaultRenderer())
        .setHorizontalAlignment(JLabel.LEFT);
        
        table.setFocusable(false);
       
        // Add table to a ScrollPane
        JScrollPane stats = new JScrollPane(table);
        stats.setBorder(BorderFactory.createMatteBorder(0, 20, 20, 10, LIGHT_BLUE));
        stats.getVerticalScrollBar().setBackground(LIGHT_BLUE);
        stats.setBackground(LIGHT_BLUE);

        // handler for resize event to recalculate columns widths
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                resizeColumns(); // repaint the window if it was resized
            }
        }); 
        return stats;
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
            int pWidth = Math.round(columnWidthPercentages[i] * tW);
            column.setPreferredWidth(pWidth);
        }
    }

    /**
     * Process clicked button.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        initiatePlaySession();  
    }

    /**
     * This method is called when Start button is clicked or Enter is pressed.<br>
     * The method hides LeaderBoard window, creates player, and plays
     * games in a loop.
     */
    private void initiatePlaySession()
    {
        this.setVisible(false);

        // Create a player with name taken from text fields.
        HangmanPlayer player = new HangmanGuiPlayer(
                playersInfo, 
                dictionary,
                firstName.getText(), 
                lastName.getText());

        // Play the game.
        player.play();
    }
}
