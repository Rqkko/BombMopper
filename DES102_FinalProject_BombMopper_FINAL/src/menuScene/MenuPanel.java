package menuScene;

import frame.MainFrame;
import frame.InstructionFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MenuPanel extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	public TitlePanel titlePanel;
	private JComboBox<String> sizeCB;
	private String[] sizeChoices = {"Small", "Medium", "Large"};
	private JComboBox<String> difficultyCB;
	private final String[] difficultyChoices = {"Baby Mode", "Easy", "Normal", "Hard", "Impossible"};
	private JRadioButton instructionRB;
	public JButton playBT;
	private static Font MENUPANEL_FONT = new Font("Comic Sans MS", Font.PLAIN, 20);
	private static Font CB_FONT = new Font("Comic Sans MS", Font.PLAIN, 15);

	public MenuPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		titlePanel = new TitlePanel();
		
		// Panel with size, difficulty and play
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.GRAY);
		bottomPanel.setLayout(new GridLayout(2,1));
		
		// Sub panel with size & difficulty
		JPanel setupPanel = new JPanel();
		setupPanel.setOpaque(false);
		
		JPanel sizePanel = new JPanel();
		sizePanel.setOpaque(false);
		JLabel sizeLB = new JLabel("Size");
		sizeLB.setFont(MENUPANEL_FONT);
		sizeCB = new JComboBox<String>(sizeChoices);
		sizeCB.setFont(CB_FONT);
		sizeCB.setSelectedIndex(1);
		sizePanel.add(sizeLB);
		sizePanel.add(sizeCB);
		
		JPanel difficultyPanel = new JPanel();
		difficultyPanel.setOpaque(false);
		JLabel difficultyLB = new JLabel("Difficulty");
		difficultyLB.setFont(MENUPANEL_FONT);
		difficultyCB = new JComboBox<String>(difficultyChoices);
		difficultyCB.setFont(CB_FONT);
		difficultyCB.setSelectedIndex(2);
		difficultyPanel.add(difficultyLB);
		difficultyPanel.add(difficultyCB);
		
		instructionRB = new JRadioButton("Instruction");
		instructionRB.setFont(CB_FONT);
		instructionRB.setSelected(true);
		instructionRB.setOpaque(false);
		instructionRB.addActionListener(this);
		instructionRB.setSelected(true);
		
		setupPanel.add(sizePanel);
		setupPanel.add(difficultyPanel);
		setupPanel.add(instructionRB);
		
		JPanel playPanel = new JPanel();
		playPanel.setOpaque(false);
		playBT = new JButton("Play!");
		playBT.setFont(MENUPANEL_FONT);;
		playBT.addActionListener(mainFrame);
		playPanel.add(playBT);
		
		bottomPanel.add(setupPanel);
		bottomPanel.add(playPanel);
		
		this.setLayout(new BorderLayout());
		this.add(titlePanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
	}
	
	public String getGameSize() {
		return (String) sizeCB.getSelectedItem();
	}
	
	public String getDifficulty() {
		return (String) difficultyCB.getSelectedItem();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		instructionRB.setSelected(true);
		new InstructionFrame(mainFrame, this, "Instruction");
	}
}
