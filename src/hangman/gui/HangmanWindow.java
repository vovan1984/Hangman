/**
 * 
 */
package hangman.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JFrame;

/**
 * Basic window for a Hangman Game. It contains upper (light 
 * blue) and lower (white) panels.
 * 
 * @author Vladimir Igumnov
 *
 */
public class HangmanWindow extends JFrame 
{
    private static final long serialVersionUID = 1L;
    
    protected static final int INIT_WIDTH = 628;
    protected static final int INIT_HEIGHT = 628;
    protected static final Color LIGHT_BLUE = new Color(0xFFBEDDFC);
    
    // default font to be used in a game
    protected static final Font DEF_FONT = new Font("Serif", Font.PLAIN, 20); 
    
    // upper and lower panels 
    protected Panel upperPane, lowerPane;
    

    /**
     * Constructor for a basic Hangman Game window. <br>
     * It creates two panels:
     * <ul>
     *     <li>Upper (light blue) panel occupies 75% of the area</li>
     *     <li>Lower (white) panel occupies 25% of the area </li>
     * </ul>
     * 
     * @param title Title to be displayed in the window.
     */
    public HangmanWindow(String title)
    {
        super(title);   
        
        // grid layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0; // occupy entire width
        gbc.fill = GridBagConstraints.BOTH; // fill entire cell
        
        // setup upper part of the window
        upperPane = new Panel();
        upperPane.setBackground(LIGHT_BLUE); // light blue panel
        upperPane.setForeground(Color.WHITE); // white letters
        gbc.weighty = 0.75; // cell takes 75% of the area height
        gbc.gridx = 0; // coordinates of the cell
        gbc.gridy = 0;
        add(upperPane, gbc);
        
        // setup lower part of the window
        lowerPane = new Panel();
        lowerPane.setBackground(Color.WHITE); // white panel
        gbc.weighty = 0.25; // cell takes 25% of the area height
        gbc.gridx = 0; // coordinates of the cell
        gbc.gridy = 1;
        add(lowerPane, gbc);
        
        // set initial window size
        setSize(INIT_WIDTH, INIT_HEIGHT);
        lowerPane.setPreferredSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));
        upperPane.setPreferredSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));
        
        // place window to the center of the screen
        centreWindow(this);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    // place window to the center of the screen
    protected static void centreWindow(Window frame) 
    {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
