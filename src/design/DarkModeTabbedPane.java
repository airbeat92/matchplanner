package design;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.text.View;

import sun.swing.SwingUtilities2;
public class DarkModeTabbedPane extends BasicTabbedPaneUI {
	




		JTabbedPane tabbedPane;
		public static final Color SELECTED_BG = Color.BLACK;
		public static final Color UNSELECTED_BG = Color.DARK_GRAY;
		public Color tempColor = UNSELECTED_BG;


		
		public DarkModeTabbedPane(Color b, JTabbedPane tabbedPane) {
			tempColor = b;
			this.tabbedPane=tabbedPane;
			tabbedPane.setBackground(Color.LIGHT_GRAY);
		}
		
		
		
		

		@Override
		protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
			// Do not paint anything
		}

		@Override
		protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex,
				Rectangle iconRect, Rectangle textRect, boolean isSelected) {
			// Do not paint anything
		}

		@Override
		protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
				boolean isSelected) {
			// Do not paint anything
		}

		@Override
		protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h,
				boolean isSelected) {
			g.setColor(isSelected ? SELECTED_BG : UNSELECTED_BG);
			g.fillRect(x, y, w, h);
		}

		@Override
		protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w,
				int h) {
			g.setColor(tempColor);
			g.fillRect(x, y, w, h);
		}

		@Override
		protected void paintContentBorderRightEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w,
				int h) {
			g.setColor(tempColor);
			g.fillRect(x, y, w, h);
		}

		@Override
		protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w,
				int h) {
			g.setColor(tempColor);
			g.fillRect(x, y, w, h);
		}

		@Override
		protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w,
				int h) {
			g.setColor(tempColor);
			g.fillRect(x, y, w, h);
		}


		@Override
		protected void paintText(Graphics g, int tabPlacement, Font font, java.awt.FontMetrics metrics, int tabIndex,
				String title, Rectangle textRect, boolean isSelected) {

			g.setFont(font);

			View v = getTextViewForTab(tabIndex);
			if (v != null) {
				// html
				v.paint(g, textRect);
			} else {
				// plain text
				int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);

				if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
					Color fg = Color.WHITE;
					if (isSelected && (fg instanceof UIResource)) {
						Color selectedFG = Color.WHITE;
						if (selectedFG != null) {
							fg = selectedFG;
						}
					}
					g.setColor(fg);
					SwingUtilities2.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,
							textRect.y + metrics.getAscent());

				} else { // tab disabled
					g.setColor(tabPane.getBackgroundAt(tabIndex).brighter());
					SwingUtilities2.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,
							textRect.y + metrics.getAscent());
					g.setColor(tabPane.getBackgroundAt(tabIndex).darker());
					SwingUtilities2.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x - 1,
							textRect.y + metrics.getAscent() - 1);
				}

			}
		}

	}


