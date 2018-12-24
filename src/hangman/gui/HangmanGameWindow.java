package hangman.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import javax.swing.JButton;

import hangman.HangmanDictionary;
import hangman.HangmanPlayersInfo;

public class HangmanGameWindow extends HangmanWindow
{
    private static final long serialVersionUID = 1L;
    private final static Font DEF_FONT = new Font("Serif", Font.PLAIN, 20);
    
    private Label hiddenWord;
    private HangmanImageCanvas imageArea;
    
    private HangmanDictionary dictionary;
    private HangmanPlayersInfo playersInfo;
    
    
    public HangmanGameWindow(String title,
           HangmanDictionary dictionary,
           HangmanPlayersInfo playersInfo)
    {
        super(title);
        this.dictionary = dictionary;
        this.playersInfo = playersInfo;

        setUpperPane();
        setLowerPane();

    }

    /*
     *  Split upper panel to two parts:
     *  header and image area.
     */
    private void setUpperPane()
    {
        // Setup header panel.
        Panel header = new Panel();    
        hiddenWord = new Label();
        hiddenWord.setFont(DEF_FONT);
        header.add(hiddenWord);
        
        upperPane.setLayout(new GridBagLayout());   
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        upperPane.add(header, gbc);
        
        // Setup image area.
        imageArea = new HangmanImageCanvas("pendu00.jpg");
        gbc.gridx = 0;
        gbc.gridy = 1;
        upperPane.add(imageArea, gbc);
    }


 // Setup alphabet letters in a lower pane.
    private void setLowerPane()
    {
        lowerPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;        
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.PAGE_END;
        
        // space label
        gbc.gridwidth = 10;
        gbc.fill = GridBagConstraints.BOTH;
        Label label = new Label();
        
        label.setBackground(new Color(0xFFBEDDFC));
        //lowerPane.add(label, gbc);
        
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weighty = 0;
        gbc.ipadx = 10;
        gbc.insets = new Insets(10, 5, 5, 0);  //top padding
        
        int position = 0;
   
        for (char c = 'A'; c <= 'Z'; c++)
        {
            handleButton(new JButton(c + ""), gbc, position++);
        }
        
        handleButton(new JButton("À"), gbc, position++);       
        handleButton(new JButton("É"), gbc, position++);
        handleButton(new JButton("È"), gbc, position++);        
        handleButton(new JButton("Ç"), gbc, position++);

    }
    
    private void handleButton(JButton letter, 
                              GridBagConstraints gbc,
                              int position)
    {
        letter.setFont(DEF_FONT);
        letter.setBackground(Color.WHITE);
        //letter.setBorder(BorderFactory.createEmptyBorder());
        //letter.setPreferredSize(new Dimension(62, 62));
        letter.setMargin(new Insets(0, 0, 0, 0));
        
        if (position > 0) // don't increase for the first letter 
        {
            gbc.gridx++;
            
            // next row
            if (position%10 == 0) 
            {
                gbc.gridy++; 
                gbc.gridx = 0;
                if (position >= 10)
                    gbc.weighty = 0.0;
            }
        }
            
        lowerPane.add(letter, gbc);        
    }

}
