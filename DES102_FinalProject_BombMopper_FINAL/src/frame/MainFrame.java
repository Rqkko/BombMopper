package frame;

import gameScene.GamePanel;
import menuScene.MenuPanel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

//import java.net.URL;

public class MainFrame extends JFrame implements ActionListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	public GamePanel gamePanel;
	MenuPanel menuPanel;
	public ResultFrame resultFrame;
	private static Dimension menuDimension = new Dimension(600, 400);
	private static Dimension gameDimension = new Dimension(1200, 800);
	// For retry
	private String currentTableSize;
	private String currentDifficulty;
	private ImageIcon bombIcon = new ImageIcon(this.getClass().getClassLoader().getResource("gameScene/bomb.png"));
	// Immoral stuff
	String keysHolder;
	private static String CHEATCODE = "cheat";

	public MainFrame(String title) {
		super(title);
		
		// Startup with Menu
		menuPanel = new MenuPanel(this);
		this.add(menuPanel);
		this.setSize(menuDimension);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setIconImage(bombIcon.getImage());
	}
	
	private void startGame(String tableSize, String difficulty) {
		this.getContentPane().removeAll();
		
		int row = 0;
		int column = 0;
		int numBombs = 0;
		
		switch(tableSize) {
			case "Small":
				row = 16;
				column = 24;
				break;
			case "Medium":
				row = 20;
				column = 30;
				break;
			case "Large":
				row = 30;
				column = 45;
				break;
		}
		
		switch(difficulty) {
			case "Baby Mode":
				numBombs = 1;
				break;
			case "Easy":
				numBombs = row * column / 8;
				break;
			case "Normal":
				numBombs = row * column / 6;
				break;
			case "Hard":
				numBombs = row * column / 4;
				break;
			case "Impossible":
				numBombs = column * row - 1;
		}

		gamePanel = new GamePanel(this, row, column, numBombs, tableSize);
		this.setSize(gameDimension);
		this.setLocationRelativeTo(null);
		this.add(gamePanel);
		gamePanel.tablePanel.grabFocus();
		gamePanel.startTimer();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		// Menu panel's play button
		if (source == menuPanel.playBT) {
			this.setVisible(false);
			menuPanel.titlePanel.stopTimer();
			currentTableSize = menuPanel.getGameSize();
			currentDifficulty = menuPanel.getDifficulty();
			this.startGame(currentTableSize, currentDifficulty);
			this.setVisible(true);
			
		// Block Blinking timer (Lose)
		} else if (source == gamePanel.tablePanel.blockBlinkTimer) {
			gamePanel.tablePanel.bombedBlock.activateBomb();
			
			if (gamePanel.tablePanel.counter == 5) {
				gamePanel.tablePanel.blockBlinkTimer.stop();
				resultFrame = new ResultFrame(this, "Lose");
				keysHolder = "";
			}
			gamePanel.tablePanel.counter++;
			
		// Game panel's menu button
		} else if (source == gamePanel.menuBT) {
			this.setVisible(false);
			menuPanel = new MenuPanel(this);
			this.getContentPane().removeAll();
			this.add(menuPanel);
			this.setSize(menuDimension);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
		
		// Game panel's retry button
		} else if (source == gamePanel.retryBT) {
			this.setVisible(false);
			if (resultFrame != null) {
				resultFrame.dispose();
			}
			this.startGame(currentTableSize, currentDifficulty);
			this.setVisible(true);
			
		// Result frame's menu button
		} else if (source == resultFrame.menuBT) {
			this.setVisible(false);
			resultFrame.dispose();
			menuPanel = new MenuPanel(this);
			this.getContentPane().removeAll();
			this.add(menuPanel);
			this.setSize(menuDimension);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			
		// Result frame's menu button
		} else if (source == resultFrame.retryBT) {
			this.setVisible(false);
			resultFrame.dispose();
			this.startGame(currentTableSize, currentDifficulty);
			this.setVisible(true);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char keyChar = e.getKeyChar();
		keysHolder += keyChar;
		
		if (keysHolder.endsWith(CHEATCODE)) {
			gamePanel.tablePanel.numRadar = (int) Math.pow(10, 6);
			gamePanel.radarLB.setText("Radar: ∞");
			gamePanel.tablePanel.playable = true;
			gamePanel.tablePanel.bombedBlock.activated = false;
			resultFrame.dispose();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	public static void main(String[] args) {
		new MainFrame("Bomb Mopper");
	}
}
