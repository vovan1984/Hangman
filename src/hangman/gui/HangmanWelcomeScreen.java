package hangman.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

/**
 * 
 * The first screen displayed to a player in 
 * order to get player's details.
 *
 * @author Vladimir Igumnov
 *
 */
public class HangmanWelcomeScreen extends HangmanWindow implements ActionListener
{
    private static final String GAME_NAME = "PENDU";
    private static final int DEF_BUTTON_HEIGHT = 40;
    private static final int DEF_BUTTON_WIDTH = 200;
    private static final long serialVersionUID = 1L;
    
    private Font defFont;
    private Label nameLabel; 
    
    public HangmanWelcomeScreen(String title)
    {
        super(title);

        // set font
        defFont = new Font("Serif", Font.PLAIN, 80); 
        
        // grid layout for panels
        upperPane.setLayout(new GridBagLayout());
        lowerPane.setLayout(new GridBagLayout());
        GridBagConstraints upAlignment = new GridBagConstraints();
        GridBagConstraints lowAlignment = new GridBagConstraints(); 
        
        // add button
        JButton newGameButton = new JButton("NEWGAME");
        newGameButton.setBackground(new Color(0xFFBEDDFC)); // light blue button
        newGameButton.setForeground(Color.WHITE);
        Dimension buttonPrefSize = new Dimension(DEF_BUTTON_WIDTH, DEF_BUTTON_HEIGHT);
        newGameButton.setPreferredSize(buttonPrefSize);

        // place button to the center of a lower panel
        lowAlignment.anchor = GridBagConstraints.CENTER;  
        
        Border emptyBorder = BorderFactory.createEmptyBorder();
        newGameButton.setBorder(emptyBorder); // remove border from the button
        
        newGameButton.addActionListener(this); // handler for button pressed event

        lowerPane.add(newGameButton, lowAlignment);
        
        upAlignment.fill = GridBagConstraints.BOTH;
        
        upAlignment.weightx = 0.4;
        upAlignment.weighty = 0.6; // cell takes 75% of the area height
        upAlignment.gridwidth = 1;
        upAlignment.gridx = 1; // coordinates of the cell
        upAlignment.gridy = 1;     
        
        // add label with the name of the game
        nameLabel = new Label(GAME_NAME, Label.CENTER);
        nameLabel.setFont(defFont);
        nameLabel.setForeground(Color.WHITE);
        upperPane.add(nameLabel, upAlignment);

        // handler for resize event
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                repaint(); // repaint the window if it was resized
            }
        });  
    }
    
    @Override
    public void paint(Graphics g)
    {
        Dimension d = getSize(); // get current dimension of the window
        
        nameLabel.setFont(defFont.deriveFont((float)0.12*Math.min(d.height, d.width)));
        
        /*
                
        Graphics upperPaneGraphics = upperPane.getGraphics();

        upperPaneGraphics.setFont(defFont.deriveFont((float)0.12*Math.min(d.height, d.width)));
        FontMetrics f = upperPaneGraphics.getFontMetrics(); // get font metrics to handle text 
        
        upperPaneGraphics.drawString(GAME_NAME, (int)(0.44 * d.width), d.height/2);
        // Draw a line for a length of a printed string.
        upperPaneGraphics.fillRect( (int)(0.44 * d.width), (int)(0.54 * d.height), 
                  f.stringWidth(GAME_NAME), 2);
         */
        
    }

    /**
     * Handler for pressed NEWGAME button.
     * It closes current screen, and returns control to the main flow.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //this.setVisible(false);
        System.exit(0);
    }   
}
