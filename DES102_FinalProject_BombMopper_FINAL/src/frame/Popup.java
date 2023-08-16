package frame;

import javax.swing.JFrame;

// Parent of resultFrame & instructionFrame
class Popup extends JFrame {

	private static final long serialVersionUID = 1L;
	
	Popup(String title, int width, int height) {
		super(title);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
}
