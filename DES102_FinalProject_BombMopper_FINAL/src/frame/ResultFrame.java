package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ResultFrame extends Popup implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	JButton menuBT;
	JButton retryBT;
	private static Font STATUS_FONT = new Font("Comic Sans MS", Font.BOLD, 40);
	private static Font BUTTON_FONT = new Font("Comic Sans MS", Font.PLAIN, 20);
	String keysHolder = "";
	private int r;
	private boolean rIncrease;
	private int g;
	private boolean gIncrease;
	private int b;
	private boolean bIncrease;
	private int counter;
	private static double frameWidth = Integer.parseInt("400");
	private static double frameHeight = 200;
	private ImageIcon flagIcon = new ImageIcon(this.getClass().getClassLoader().getResource("gameScene/flag.png"));

	public ResultFrame(MainFrame mainFrame, String status) {
		super("Result", (int) frameWidth, (int) frameHeight);
		this.setIconImage(flagIcon.getImage());
		this.setLayout(new BorderLayout());
		this.getContentPane().setBackground(Color.WHITE);
		
		JLabel statusLB = new JLabel("You " + status + "!");
		statusLB.setFont(STATUS_FONT);
		statusLB.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel exitPanel = new JPanel();
		exitPanel.setOpaque(false);
		menuBT = new JButton("Main Menu");
		menuBT.setFont(BUTTON_FONT);
		retryBT = new JButton("Retry");
		retryBT.setFont(BUTTON_FONT);
		exitPanel.add(menuBT);
		exitPanel.add(retryBT);
		menuBT.addActionListener(mainFrame);
		retryBT.addActionListener(mainFrame);
		
		this.add(statusLB, BorderLayout.CENTER);
		this.add(exitPanel, BorderLayout.SOUTH);
		
		if (status == "Lose") {
			this.addKeyListener(mainFrame);
		
		} else {
			Timer timer = new Timer(1, this);
			r = 255;
			g = 255;
			b = 255;
			rIncrease = false;
			gIncrease = false;
			bIncrease = false;
			counter = 0;
			timer.start();
		}
		
		this.setVisible(true);
		this.requestFocus();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (counter % 3 == 0) {
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
		
		if (counter % 5 == 0) {
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
		
		if (counter % 2 == 0) {
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
		
		this.getContentPane().setBackground(new Color(r,g,b));
		counter += 1;
	}
}
