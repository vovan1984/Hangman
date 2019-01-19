package hangman.gui;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;

/**
 * This Dialog window is used to display game result to
 * a player.
 * 
 * @author Vladimir Igumnov
 * 
 * @version 1.0
 */
public class HangmanDialog extends Dialog
{
    private static final long serialVersionUID = 1L;
    private final static int DIALOG_WIDTH = 400;
    private final static int DIALOG_HEIGHT = 200;
    
    private Label result;

    public HangmanDialog(Frame owner, String title, boolean modal)
    {
        this(owner, title, modal, "");
    }
    
    public HangmanDialog(Frame owner, String title, boolean modal,
                         String message)
    {
        super(owner, title, modal);
        
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        setBackground(HangmanWindow.LIGHT_BLUE);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weightx = 1.0;
        gbc.ipadx = 400;
        gbc.insets = new Insets(10,10,20,10);
        result = new Label(message, Label.CENTER);
        result.setBackground(HangmanWindow.LIGHT_BLUE);
        add(result, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new Label("Do you want to continue?", Label.CENTER), gbc);
        
        gbc.ipadx = 0;
        gbc.gridwidth = 1;
        gbc.weightx = 0.3;
        gbc.gridx = 0;
        gbc.gridy = 2;
        JButton yes = new JButton("YES");
        yes.addActionListener(e -> dispose());
        add(yes, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        JButton no = new JButton("NO");
        no.addActionListener(e -> System.exit(0));
        add(no, gbc);
        
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent we)
            {
                System.exit(0);
            }
        });
        
        HangmanWindow.centreWindow(this);
    }

    public void setMessage(String msg)
    {
        result.setText(msg);
    }
}
