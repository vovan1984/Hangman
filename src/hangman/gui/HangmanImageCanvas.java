package hangman.gui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HangmanImageCanvas extends Canvas
{
    private static final long serialVersionUID = 1L;
    
    // Directory of resource files in the CLASSPATH.
    private final static String RES_DIR = "/resources/";
    private Image img;

    public HangmanImageCanvas(String fileName)
    {
        super();   
        setImg(RES_DIR + fileName);
    }
    
    public void setImg(String resource)
    {
        try (var imageStream = getClass().getResourceAsStream(resource))
        {
            img = ImageIO.read(imageStream);
        } catch (IOException e)
        {
            System.out.println("Failed to load image from " + resource);
            e.printStackTrace();
            System.exit(0);
        }
        repaint();
    }
    

    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(img.getWidth(this), img.getHeight(this));
    }

    @Override
    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }
    
    @Override
    public void paint(Graphics g)
    {
        Dimension d = getSize();
        
        if (img == null)
        {
            g.drawString("No image", 10, 30);
        }
        else
        {
            // scale to the canvas size.
            Image scaled = img.getScaledInstance(d.width, d.height, Image.SCALE_DEFAULT);
            g.drawImage(scaled, 0, 0, this);
        }
    }
    
}
