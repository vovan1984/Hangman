package hangman.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 
 * The first screen displayed to a player in 
 * order to get player's details.
 *
 * @author Vladimir Igumnov
 *
 */
public class HangmanWelcomeScreen extends Frame
{
    private static final String GAME_NAME = "PENDU";
    private static final int INIT_WIDTH = 628;
    private static final int INIT_HEIGHT = 628;
    
    private static final long serialVersionUID = 1L;
    
    private Font defFont;
    
    public HangmanWelcomeScreen(String title)
    {
        super(title);
        

        
        // add listeners
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing (WindowEvent e)
            {
                System.exit(0);
            }
        });
        
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent e)
            {
                repaint(); // repaint the window if it was resized
            }
        });
        
        // make a cool Green-on-Black appearance
        setBackground(new Color(0xFFBEDDFC)); // light blue
        setForeground(Color.WHITE);
        
        defFont = new Font("Serif", Font.PLAIN, 80);       
        setSize(INIT_WIDTH, INIT_HEIGHT);
    }
    
    @Override
    public void paint(Graphics g)
    {
        Dimension d = getSize(); // get current dimension of the window
        
        // white area in the bottom occupies 25% of height
        int whiteSpaceHeight = d.height/4;
        g.fillRect(0, d.height - whiteSpaceHeight, d.width, whiteSpaceHeight);
        
        // font depends on the
        g.setFont(defFont.deriveFont((float)0.12*Math.min(d.height, d.width)));
        FontMetrics f = g.getFontMetrics(); // get font metrics to handle text 
        
        g.drawString(GAME_NAME, (int)(0.44 * d.width), d.height/2);
        // Draw a line for a length of a printed string.
        g.fillRect( (int)(0.44 * d.width), (int)(0.54 * d.height), 
                    f.stringWidth(GAME_NAME), 2);
    }   
}
