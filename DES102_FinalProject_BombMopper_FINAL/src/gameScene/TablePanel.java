package gameScene;

import frame.MainFrame;
import frame.ResultFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class TablePanel extends JPanel implements ActionListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	// Track the position
	private Block[][] table;
	private int numBombs;
	public boolean playable;
	// Track the block that the bomb explodes
	public Block bombedBlock;
	// Win condition
	private int numUnactivatedBlocks;
	// Flagging when holding key
	private boolean flagging;
	public int numRadar = 2;
	// Radaring when holding shift
	private boolean radaring;
	public Timer blockBlinkTimer;
	public int counter;
	private MainFrame mainFrame;
	// Timer for rainbow flash in game panel's timer background when radaring
	Timer radarTimer;
	private boolean executeTimer;
	// For setting timerPanl's color when radaring
	private int r;
	private boolean rIncrease;
	private int g;
	private boolean gIncrease;
	private int b;
	private boolean bIncrease;
	private int radarCounter;
	
	TablePanel(MainFrame mainFrame, int row, int column, int numBombs, String tableSize) {
		this.mainFrame = mainFrame;
		this.numBombs = numBombs;
		table = new Block[row][column];
		numUnactivatedBlocks = row*column;
		flagging = false;
		radaring = false;
		playable = true;
		counter = 0;
		// Radar
		radarTimer = new Timer(1, this);
		executeTimer = true;
		
		// Add block to table
		int iconSize = 0;
		switch (tableSize) {
			case "Small":
				iconSize = 30;
				break;
			case "Medium":
				iconSize = 22;
				break;
			case "Large":
				iconSize = 15;
		}
		this.setBlocks(row, column, iconSize);
		this.setBombs(numBombs);
		this.assignNearbyBombNum();
		
		this.addKeyListener(this);
		this.setFocusable(true);
		
		// Timer for when a block's bomb blinks when a bombed block is pressed
		blockBlinkTimer = new Timer(250, mainFrame);
	}
	
	private void setBlocks(int row, int column, int iconSize) {
		this.setLayout(new GridLayout(row, column));
		for (int i=0; i<row; i++) {
			for (int j=0; j<column; j++) {
				table[i][j] = new Block(mainFrame, i, j, iconSize);
				this.add(table[i][j]);
				table[i][j].addActionListener(this);
			}
		}
	}
	
	private void setBombs(int numBombs) {
		int bombsNotSet = numBombs;
		
		while (bombsNotSet > 0) {
			int i = (int) Math.floor(Math.random() * table.length);
			int j = (int) Math.floor(Math.random() * table[0].length);
			if (table[i][j].hasBomb == false) {
				table[i][j].setBomb();
				bombsNotSet -= 1;
			}
		}
	}
	
	// Assign number on every blocks
	private void assignNearbyBombNum() {
		for (int i=0; i<table.length; i++) {
			for (int j=0; j<table[0].length; j++) {
				table[i][j].nearbyBombs = findNearbyBomb(i,j);
			}
		}
	}
	
	private ArrayList<Block> findNearby(int row, int column) {
		ArrayList<Block> nearby = new ArrayList<Block>();
		
		// Loop through every surrounding bombs
		for (int i = row-1; i < row+2; i++) {
			for (int j = column-1; j < column+2; j++) {
				// Check invalid positions
				if ((i>=0) && (j>=0) && !(i==row && j==column) && (i<table.length) && (j<table[0].length)) {
					nearby.add(table[i][j]);
				}
			}
		}
		return nearby;
	}
	
	protected int findNearbyBomb(int row, int column) {
		int numBombs = 0;
		ArrayList<Block> nearby = findNearby(row, column);
		
		for (int i=0; i<nearby.size(); i++) {
			if (nearby.get(i).hasBomb == true) {
				numBombs++;
			}
		}		
		return numBombs;
	}
	
	// Activate all next-door blocks
	private void toggleAllNoBomb(Block b) {
		ArrayList<Block> nearbyBlocks = findNearby(b.posRow, b.posColumn);
		
		for (int i=0; i<nearbyBlocks.size(); i++) {
			Block x = nearbyBlocks.get(i);
			
			if (!x.activated) {
				x.activateWhite();
				
				if (x.flagged) {
					mainFrame.gamePanel.numPotentialBombsNotFlagged++;
					x.setIcon(Block.EMPTY_ICON);
					x.flagged = false;
					mainFrame.gamePanel.bombsLeftLB.setText("Bombs left: " + mainFrame.gamePanel.numPotentialBombsNotFlagged + "  ");	
				}
				
				numUnactivatedBlocks--;
				
				if (x.nearbyBombs == 0) {
					this.toggleAllNoBomb(x);
				
				} else {
					x.assignNum();
				}
			}
		}
	}
	
	// Reveal a block
	private void reveal(Block b) {
		if (radaring && numRadar > 0) {
			this.radarReveal(b);
			
			if (mainFrame.gamePanel.radarLB.getText() != "Radar: ∞") {
				numRadar--;
				mainFrame.gamePanel.radarLB.setText("Radar: " + numRadar);
			}
		
		} else if (!b.activated) {
			if (flagging) {
				b.activateFlag();
				
			} else if (!b.flagged){
				if (b.hasBomb) {
					mainFrame.gamePanel.stopTimer();
					bombedBlock = b;
					bombedBlock.activateBomb();
					playable = false;
					counter = 0;
					blockBlinkTimer.start();
				
				} else {
					b.assignNum();
					b.activateWhite();
					numUnactivatedBlocks--;
					
					if (b.nearbyBombs == 0) {
						this.toggleAllNoBomb(b);
					}
				}
			}
		}
	}
	
	private void radarReveal(Block b) {
		int row = b.posRow;
		int column = b.posColumn;
		for (int i=row-2; i<=row+2; i++) {
			for (int j=column-2; j<=column+2; j++) {
				// Check for invalid positions
				if ((i>=0) && (j>=0) && (i<table.length) && (j<table[0].length)) {
					// Check for corners
					if (!(i==row-2 && j==column-2) && !(i==row-2 && j==column+2) && !(i==row+2 && j==column-2) && !(i==row+2 && j==column+2)) {
						Block x = table[i][j];
						
						if (!x.activated) {
							if (x.hasBomb) {
								if (!x.flagged) { 
									x.activateFlag();
								}
								
							} else {
								if (x.flagged) {
									x.activateFlag();
								}
								
								x.activateWhite();
								numUnactivatedBlocks--;
								
								if (x.nearbyBombs == 0) {
									this.toggleAllNoBomb(x);
									
								} else {
									x.assignNum();
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Block && playable) {
			Block b = ((Block) e.getSource());
			this.reveal(b);
			
			if (numUnactivatedBlocks == numBombs) {
				mainFrame.gamePanel.stopTimer();
				playable = false;
				radarTimer.stop();
				
				mainFrame.gamePanel.timerPanel.setBackground(Color.GRAY);
				mainFrame.gamePanel.radarLB.setForeground(Color.BLACK);
				mainFrame.resultFrame = new ResultFrame(mainFrame, "Win");
			}
			
		} else if (e.getSource() == radarTimer) {
			if (radarCounter % 3 == 0) {
				if (rIncrease) {
					r += 2;
					
				} else {
					r -= 2;
				}
				
				if (r == 59 && rIncrease == false) {
					rIncrease = true;
					
				} else if (r == 255 && rIncrease) {
					rIncrease = false;
				}
			}
			
			if (radarCounter % 5 == 0) {
				if (gIncrease) {
					g += 2;
					
				} else {
					g -= 2;
				}
				
				if (g == 59 && gIncrease == false) {
					gIncrease = true;
					
				} else if (g == 255 && gIncrease) {
					gIncrease = false;
				}
			}
			
			if (radarCounter % 2 == 0) {
				if (bIncrease) {
					b += 2;
					
				} else {
					b -= 2;
				}
				
				if (b == 59 && bIncrease == false) {
					bIncrease = true;
					
				} else if (b == 255 && bIncrease) {
					bIncrease = false;
				}
			}
			
			mainFrame.gamePanel.timerPanel.setBackground(new Color(r,g,b));
			mainFrame.gamePanel.radarLB.setForeground(new Color(r,g,b));
			radarCounter += 1;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// Key code of shift is 16
		if (e.getKeyCode() == 16) {
			if (numRadar != 0) {
				radaring = true;
				if (executeTimer) {
					executeTimer = false;
					r = 255;
					g = 255;
					b = 255;
					rIncrease = false;
					gIncrease = false;
					bIncrease = false;
					radarCounter = 0;
					radarTimer.start();
				}
			}
			
		} else {
			flagging = true;
			mainFrame.gamePanel.timerPanel.setBackground(Color.BLACK);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 16) {
			radaring = false;
			radarTimer.stop();
			executeTimer = true;
			mainFrame.gamePanel.radarLB.setForeground(Color.BLACK);
			
			if (numRadar == 0) {
				mainFrame.gamePanel.radarLB.setForeground(Color.RED);
			}
			
		} else {
			flagging = false;
		}
		
		mainFrame.gamePanel.timerPanel.setBackground(Color.GRAY);
	}
}