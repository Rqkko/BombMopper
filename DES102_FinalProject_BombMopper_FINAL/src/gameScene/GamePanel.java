package gameScene;

import frame.MainFrame;
import frame.InstructionFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	public TablePanel tablePanel;
	public JButton menuBT;
	public JButton retryBT;
	private JButton instructionBT;
	JPanel timerPanel;
	private Timer timer;
	public int min;
	public int sec;
	JLabel timerLB;
	public JLabel radarLB;
	JLabel bombsLeftLB;
	// -1 when flagging a block
	int numPotentialBombsNotFlagged;
	private static final Font GAMEPANEL_FONT = new Font("Comic Sans MS", Font.PLAIN, 20);

	public GamePanel(MainFrame mainFrame, int row, int column, int numBombs, String tableSize) {
		this.mainFrame = mainFrame;
		numPotentialBombsNotFlagged = numBombs;
		tablePanel = new TablePanel(mainFrame, row, column, numBombs, tableSize);
		
		// Panel for timer & instruction button
		timerPanel = new JPanel();
		min = 0;
		sec = 0;
		timerLB = new JLabel("Timer: " + min + " m " + sec + " s");
		timerLB.setFont(GAMEPANEL_FONT);
		timerLB.setFocusable(false);
		timerLB.setForeground(Color.WHITE);
		timerPanel.add(timerLB);
		timerPanel.setBackground(Color.GRAY);
		timerPanel.setFocusable(false);
		timer = new Timer(1000, this);
		
		// Exit Panel
		JPanel exitPanel = new JPanel();
		menuBT = new JButton("Main Menu");
		menuBT.setFont(GAMEPANEL_FONT);
		menuBT.addActionListener(mainFrame);
		retryBT = new JButton("Retry");
		retryBT.setFont(GAMEPANEL_FONT);
		retryBT.addActionListener(mainFrame);
		instructionBT = new JButton("?");
		instructionBT.setFont(GAMEPANEL_FONT);
		instructionBT.setPreferredSize(new Dimension(35, 35));
		instructionBT.addActionListener(this);
		exitPanel.setLayout(new BorderLayout());
		JPanel exitSubPanel = new JPanel();
		exitSubPanel.add(menuBT);
		exitSubPanel.add(retryBT);
		exitSubPanel.add(instructionBT);
		exitSubPanel.setFocusable(false);
		exitPanel.add(exitSubPanel, BorderLayout.WEST);
		menuBT.setFocusable(false);
		retryBT.setFocusable(false);
		
		// Bottom Panel
		JPanel bottomPanel = new JPanel();
		radarLB = new JLabel("Radar: " + tablePanel.numRadar);
		radarLB.setFont(GAMEPANEL_FONT);
		radarLB.setHorizontalAlignment(SwingConstants.CENTER);
		bombsLeftLB = new JLabel("Bombs left: " + numPotentialBombsNotFlagged + "  ");
		bombsLeftLB.setFont(GAMEPANEL_FONT);
		bombsLeftLB.setHorizontalAlignment(SwingConstants.RIGHT);
		bottomPanel.setLayout(new GridLayout(1,3));
		bottomPanel.add(exitPanel);
		bottomPanel.add(radarLB);
		bottomPanel.add(bombsLeftLB);
		
		// Game Panel
		this.setLayout(new BorderLayout());
		this.add(tablePanel, BorderLayout.CENTER);
		this.add(timerPanel, BorderLayout.NORTH);
		this.add(bottomPanel, BorderLayout.SOUTH);
		this.setFocusable(false);
	}

	public void startTimer() {
		timer.start();
	}
	
	public void stopTimer() {
		timer.stop();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == instructionBT) {
			new InstructionFrame(mainFrame, this, "Instruction");
			
		} else if (e.getSource() == timer) {
			sec++;
			
			if (sec == 60) {
				min++;
				sec = 0;
			}
			
			timerLB.setText("Timer: " + min + " m " + sec + " s");
		}
	}
}
