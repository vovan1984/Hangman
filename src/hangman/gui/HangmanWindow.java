/**
 * 
 */
package hangman.gui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Basic window for a Hangman Game. It contains upper (light 
 * blue) and lower (white) panels.
 * 
 * @author Vladimir Igumnov
 *
 */
public class HangmanWindow extends Frame 
{
    private static final long serialVersionUID = 1L;
    
    protected static final int INIT_WIDTH = 628;
    protected static final int INIT_HEIGHT = 628;
    
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
        GridBagConstraints position = new GridBagConstraints();
        position.weightx = 1.0; // occupy entire area width
        position.fill = GridBagConstraints.BOTH; // fill entire cell
        
        // setup upper part of the window
        upperPane = new Panel();
        upperPane.setBackground(new Color(0xFFBEDDFC)); // light blue panel
        upperPane.setForeground(Color.WHITE); // white letters
        position.weighty = 0.75; // cell takes 75% of the area height
        position.gridx = 0; // coordinates of the cell
        position.gridy = 0;
        add(upperPane, position);
        
        // setup lower part of the window
        lowerPane = new Panel();
        lowerPane.setBackground(Color.WHITE); // white panel
        position.weighty = 0.25; // cell takes 25% of the area height
        position.gridx = 0; // coordinates of the cell
        position.gridy = 1;
        add(lowerPane, position);
        
        // set initial window size
        setSize(INIT_WIDTH, INIT_HEIGHT);
        
        // handler for close event
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing (WindowEvent e)
            {
                System.exit(0);
            }
        });
    }
}
