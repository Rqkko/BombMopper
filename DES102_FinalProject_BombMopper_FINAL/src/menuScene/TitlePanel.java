package menuScene;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

// Size in main frame: 600 x 280
public class TitlePanel extends JPanel implements ActionListener, MouseListener {
	
	private static final long serialVersionUID = 1L;
	private static final BasicStroke STROKE = new BasicStroke(5);
	private static final Font TEXTFONT = new Font("Comic Sans MS", Font.BOLD, 40);
	private Timer timer;
	private int counter;
	private boolean smiling;
	private static int[] SMILE_X = {292, 300, 308, 300};
	private static int[] SMILE_Y = {180, 185, 180, 183};

	TitlePanel() {
		this.setBackground(Color.GRAY);
		timer = new Timer(100, this);
		counter = 0;
		smiling = false;
		this.addMouseListener(this);
		timer.start();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		// Background
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillOval(0, 40, 600, 480);
		
		// Bomb string
		g2.setStroke(STROKE);
		g2.setColor(Color.GRAY);
		g2.drawArc(300, 85, 50, 50, 90, 90);
		g2.drawArc(300, 35, 50, 50, 270, 40);
		
		// Bomb body
		g2.setColor(Color.BLACK);
		g2.fillOval(250, 110, 100, 100);
		
		// Title text
		g2.setFont(TEXTFONT);
		g2.setColor(Color.GRAY);
		g2.drawString("Bomb", 120, 175);
		g2.drawString("Mopper", 380, 175);
		
		// Sparks
		if (counter%2 == 0) {
			paintSpark(g2, 349, 76);
			paintSpark(g2, 357, 70);
			paintSpark(g2, 358, 80);
			
		} else {		
			paintSpark(g2, 358, 75);
			paintSpark(g2, 348, 70);
			paintSpark(g2, 350, 82);
		}
		if (smiling) {
			paintSmile(g2);
		}
	}
	
	private void paintSpark(Graphics2D g2, int x, int y) {
		g2.setColor(Color.YELLOW);
		g2.fillRect(x-5, y-5, 10, 10);
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillArc(x-10, y-10, 10, 10 , 270, 90);
		g2.fillArc(x, y-10, 10, 10, 180, 90);
		g2.fillArc(x-10, y, 10, 10, 0, 90);
		g2.fillArc(x, y, 10, 10, 90, 90);
	}
	
	private void paintSmile(Graphics2D g2) {
		g2.setColor(Color.YELLOW);
		g2.fillOval(275, 170, 10, 10);
		g2.fillOval(315, 170, 10, 10);
		g2.fillPolygon(SMILE_X, SMILE_Y, 4);
	}
	
	public void stopTimer() {
		timer.stop();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		counter++;
		this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if (smiling) {
			smiling = false;
			
		} else {
			smiling = true;
			this.repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}