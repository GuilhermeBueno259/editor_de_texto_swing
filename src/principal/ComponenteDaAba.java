package principal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

@SuppressWarnings("serial")
public class ComponenteDaAba extends JPanel {

	private final JTabbedPane pane;

	public ComponenteDaAba(final JTabbedPane pane) {
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.pane = pane;

		setOpaque(false);
		
		JLabel label = new JLabel() {
			
			public String getText() {
				int i = pane.indexOfTabComponent(ComponenteDaAba.this);
				if (i != -1)
					return pane.getTitleAt(i);
				return null;
			}
		};
		add(label);
		label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		add(new BotaoDaAba());
	}

	private class BotaoDaAba extends JButton implements ActionListener {

		public BotaoDaAba() {
			setBorderPainted(false);
			setOpaque(false);
			setPreferredSize(new Dimension(17, 17));
			setFocusable(false);
			setFocusPainted(false);
			addActionListener(this);
			setRolloverEnabled(true);
			setUI(new BasicButtonUI());
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int i = pane.indexOfTabComponent(ComponenteDaAba.this);
			if (i != -1) {
				pane.remove(i);
			}
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();

			if (getModel().isPressed()) {
				g2.translate(1, 1);
			}
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.black);
			if (getModel().isRollover()) {
				g2.setColor(Color.red);
			}
			int delta = 6;
			g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
			g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
			g2.dispose();

		}

	}
	
}
