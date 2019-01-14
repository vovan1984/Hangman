package hangman.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;

public class HangmanRoundedButton extends JButton 
{
    private static final long serialVersionUID = 1L;

    public HangmanRoundedButton(String text) 
    {
        super(text);
        setBorderPainted(false);
    }

    public HangmanRoundedButton() 
    {
        this("");
    }

    @Override
    public void paint(Graphics g) 
    {
        super.paint(g);
        
        // make button invisible
        setBackground(getParent().getBackground());
        
        // Take advantage of Graphics2D to position string
        Graphics2D g2d = (Graphics2D)g;

        // Draw rectangle with rounded corners on top of 
        // button
        g2d.setColor(HangmanWindow.LIGHT_BLUE);
        g2d.fillRoundRect(0,0,getWidth(),getHeight(),18,18);

        // Finding size of text so can position in center.
        FontRenderContext frc = 
                new FontRenderContext(null, false, false);
        Rectangle2D r = getFont().getStringBounds(getText(), frc);

        float xMargin = (float)(getWidth()-r.getWidth())/2;
        float yMargin = (float)(getHeight()-getFont().getSize())/2;

        // Draw the text in the center
        g2d.setColor(Color.WHITE);
        g2d.drawString(getText(), xMargin, 
                (float)getFont().getSize() + yMargin);
    }
}
