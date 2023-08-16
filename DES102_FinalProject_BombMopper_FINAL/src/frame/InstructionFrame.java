package frame;

import gameScene.GamePanel;
import menuScene.MenuPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.io.IOException;

//import java.net.URL;

public class InstructionFrame extends Popup implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private JPanel panel;
	private static Font INSTRUCTION_FONT = new Font("Comic Sans MS", Font.PLAIN, 20);
	JButton okBT;
	private ImageIcon frameIcon = new ImageIcon(this.getClass().getClassLoader().getResource("gameScene/flag.png"));
	private URL flagURL = this.getClass().getClassLoader().getResource("gameScene/flag.png");
	private Icon flagIcon;
	private URL bombURL = this.getClass().getClassLoader().getResource("gameScene/bomb.png");
	private Icon bombIcon;
	private final int iconSize = 20;

	public InstructionFrame(MainFrame mainFrame, JPanel panel, String title) {
		super(title, 400, 250);
		this.mainFrame = mainFrame;
		this.panel = panel;
		this.setIconImage(frameIcon.getImage());
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
		this.setLayout(new GridLayout(6,1));
		
		JLabel infoLB1 = new JLabel(" - Click on a block to reveal");
		infoLB1.setFont(INSTRUCTION_FONT);
		
		JLabel infoLB2 = new JLabel(" - Hold 'Shift' and click to use radar");
		infoLB2.setFont(INSTRUCTION_FONT);
		
		JLabel infoLB3 = new JLabel(" - Hold any other key and click to flag");
		infoLB3.setFont(INSTRUCTION_FONT);
		
		// Get the appropriately sized icon
		try {
			BufferedImage flagBI = ImageIO.read(flagURL);
			flagIcon = new ImageIcon(flagBI.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT));
			BufferedImage bombBI = ImageIO.read(bombURL);
			bombIcon = new ImageIcon(bombBI.getScaledInstance(iconSize, iconSize, Image.SCALE_DEFAULT));
		} catch (IOException e) {
			System.out.println("Error loading icon");
		}
		
		JPanel flaggedInfoPanel = new JPanel();
		flaggedInfoPanel.setOpaque(false);
		JButton dummyFlaggedBT = new JButton();
		dummyFlaggedBT.setDisabledIcon(flagIcon); 
		dummyFlaggedBT.setEnabled(false);
		dummyFlaggedBT.setIcon(flagIcon);
		dummyFlaggedBT.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		dummyFlaggedBT.setOpaque(true);
		dummyFlaggedBT.setPreferredSize(new Dimension(30, 30));
		dummyFlaggedBT.setBackground(Color.GRAY);
		JLabel flaggedInfoLB = new JLabel("Flagged");
		flaggedInfoLB.setFont(INSTRUCTION_FONT);
		flaggedInfoPanel.add(dummyFlaggedBT);
		flaggedInfoPanel.add(flaggedInfoLB);
		
		JPanel bombInfoPanel = new JPanel();
		bombInfoPanel.setOpaque(false);
		JButton dummyBombBT = new JButton();
		dummyBombBT.setDisabledIcon(bombIcon);
		dummyBombBT.setEnabled(false);
		dummyBombBT.setIcon(bombIcon);
		dummyBombBT.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		dummyBombBT.setOpaque(true);
		dummyBombBT.setBackground(Color.RED);
		dummyBombBT.setPreferredSize(new Dimension(30, 30));
		JLabel bombInfoLB = new JLabel("Bomb");
		bombInfoLB.setFont(INSTRUCTION_FONT);
		bombInfoPanel.add(dummyBombBT);
		bombInfoPanel.add(bombInfoLB);
		
		JPanel okPanel = new JPanel();
		okPanel.setOpaque(false);
		okBT = new JButton("Okay");
		okBT.setFont(INSTRUCTION_FONT);
		okBT.setPreferredSize(new Dimension(100, 34));
		okBT.addActionListener(this);
		okPanel.add(okBT);
		
		this.add(flaggedInfoPanel);
		this.add(bombInfoPanel);
		this.add(infoLB1);
		this.add(infoLB2);
		this.add(infoLB3);
		this.add(okPanel);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (panel instanceof GamePanel) {
			mainFrame.gamePanel.tablePanel.grabFocus();
			
		} else if (panel instanceof MenuPanel) {
			mainFrame.menuPanel.grabFocus();
		}
		
		this.dispose();
	}
}
