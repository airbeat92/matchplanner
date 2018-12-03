package matchplanner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class FlatTabbedUI extends BasicTabbedPaneUI {
	
	
    public static final Color SELECTED_BG = new Color(255, 150, 0);
    public static final Color UNSELECTED_BG = new Color(255, 50, 0);
	
    public FlatTabbedUI() {
    	
    }
    
	 @Override 
	 protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
         // Do not paint anything
     }
     @Override protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
         // Do not paint anything
     }
     @Override  protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
         g.setColor(isSelected ? SELECTED_BG : UNSELECTED_BG);
         g.fillRect(x, y, w, h);
     }
     @Override protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
         g.setColor(SELECTED_BG);
         g.fillRect(x, y, w, h);
     }
     @Override protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
         g.setColor(SELECTED_BG);
         g.fillRect(x, y, w, h);
     }
     @Override protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
         g.setColor(SELECTED_BG);
         g.fillRect(x, y, w, h);
     }
     @Override protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
         g.setColor(SELECTED_BG);
         g.fillRect(x, y, w, h);
     }
     
    
    	 
     
     
     


}
