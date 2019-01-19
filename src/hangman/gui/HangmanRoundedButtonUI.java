package hangman.gui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonListener;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * Rounded Button interface 
 */
public class HangmanRoundedButtonUI extends BasicButtonUI {
  private static final double ARC_WIDTH = 16d;
  private static final double ARC_HEIGHT = 16d;
  
  protected final Color ac = new Color(220, 225, 230); // grey color for pressed button
 
  private Shape shape;
  private Shape base;

  @Override 
  protected void installDefaults(AbstractButton b) {
    super.installDefaults(b);
    b.setBorderPainted(false);
    b.setOpaque(false);
    initShape(b);
  }

  @Override protected void installListeners(AbstractButton button) 
  {
    BasicButtonListener listener = new BasicButtonListener(button) 
    {
      @Override 
      public void mousePressed(MouseEvent e) 
      {
        AbstractButton b = (AbstractButton) e.getComponent();
        initShape(b);
        if (isShapeContains(e.getPoint())) {
          super.mousePressed(e);
        }
      }

      @Override public void mouseEntered(MouseEvent e) {
        if (isShapeContains(e.getPoint())) {
          super.mouseEntered(e);
        }
      }

      @Override public void mouseMoved(MouseEvent e) {
        if (isShapeContains(e.getPoint())) {
          super.mouseEntered(e);
        } else {
          super.mouseExited(e);
        }
      }
    };
    // if (listener != null)
    button.addMouseListener(listener);
    button.addMouseMotionListener(listener);
    button.addFocusListener(listener);
    button.addPropertyChangeListener(listener);
    button.addChangeListener(listener);
  }

  @Override 
  public void paint(Graphics g, JComponent c) 
  {
    initShape(c);

    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // ContentArea
    if (c instanceof AbstractButton) {
      AbstractButton b = (AbstractButton) c;
      ButtonModel model = b.getModel();
      if (model.isArmed()) // if button is pressed (but not yet triggered)
      {
        g2.setPaint(ac);
        g2.fill(shape);
      }
      else 
      {
        g2.setPaint(c.getBackground());
        g2.fill(shape);
      }
    }

    // Border
    g2.setPaint(c.getForeground());
    g2.draw(shape);
    g2.dispose();
    super.paint(g, c);
  }

  /**
   * Check if input position is inside the shape
   * @param pt Position on the plot
   * @return true or false
   */
  protected final boolean isShapeContains(Point pt) {
    return shape != null && shape.contains(pt.x, pt.y);
  }

  protected final void initShape(Component c) 
  {
    if (!c.getBounds().equals(base)) 
    {
      base = c.getBounds();
      shape = new RoundRectangle2D.Double(0, 0, c.getWidth() - 1, c.getHeight() - 1, 
                                          ARC_WIDTH, ARC_HEIGHT);
    }
  }
}
