package gameScene;

import frame.MainFrame;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.io.IOException;

public class Block extends JButton {

	private static final long serialVersionUID = 1L;
	int posRow;
	int posColumn;
	boolean hasBomb;
	protected int nearbyBombs;
	protected boolean flagged;
	public boolean activated;
	private MainFrame mainFrame;
	private static Font BLOCK_NUM_FONT = new Font("Comic Sans MS", Font.PLAIN, 20);
	private ImageIcon bombIcon;
	private URL bombURL = this.getClass().getClassLoader().getResource("gameScene/bomb.png");
	private ImageIcon flagIcon;
	private URL flagURL = this.getClass().getClassLoader().getResource("gameScene/flag.png");
	static Icon EMPTY_ICON = UIManager.getIcon("");
	private static Border RAISED_BORDER = BorderFactory.createRaisedSoftBevelBorder();
	private static Border LOWERED_BORDER = BorderFactory.createLoweredSoftBevelBorder();
	
	Block(MainFrame mainFrame, int posRow, int posColumn, int iconSize) {
		this.mainFrame = mainFrame;
		this.posRow = posRow;
		this.posColumn = posColumn;
		hasBomb = false;
		nearbyBombs = 0;
		flagged = false;
		activated = false;
		
		// Setup icons
		try {
			BufferedImage bombBI = ImageIO.read(bombURL);
			bombIcon = new ImageIcon(bombBI.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
			BufferedImage flagBI = ImageIO.read(flagURL);
			flagIcon = new ImageIcon(flagBI.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT));
		} catch (IOException e) {
			System.out.println("Error creating bomb icon");
		}
		this.setBorder(RAISED_BORDER);
		this.setFont(BLOCK_NUM_FONT);
		this.setOpaque(true);
		this.setBackground(Color.LIGHT_GRAY);
		this.setFocusable(false);
	}
	
	void setBomb() {
		hasBomb = true;
	}
	
	void assignNum() {
		if (nearbyBombs != 0 && !hasBomb) {
			this.setText("" + nearbyBombs);
		}
	}
	
	// Press and no bomb
	void activateWhite() {
		this.setBackground(Color.WHITE);
		this.setBorder(LOWERED_BORDER);
		activated = true;
	}
	
	// Press and bomb
	public void activateBomb() {
		if (this.getBackground() == Color.LIGHT_GRAY) {
			this.setBackground(Color.RED);
			this.setBorder(LOWERED_BORDER);
			this.setIcon(bombIcon);
			
		} else {
			this.setBackground(Color.LIGHT_GRAY);
			this.setBorder(RAISED_BORDER);
			this.setIcon(EMPTY_ICON);
		}
	}
	
	// Flag
	void activateFlag() {
		if (flagged) {
			this.setIcon(EMPTY_ICON);
			this.setBackground(Color.LIGHT_GRAY);
			flagged = false;
			mainFrame.gamePanel.numPotentialBombsNotFlagged++;
			
		} else {
			this.setIcon(flagIcon);
			this.setBackground(Color.GRAY);
			flagged = true;
			mainFrame.gamePanel.numPotentialBombsNotFlagged--;
		}
		mainFrame.gamePanel.bombsLeftLB.setText("Bombs left: " + mainFrame.gamePanel.numPotentialBombsNotFlagged + "  ");
	}
}
