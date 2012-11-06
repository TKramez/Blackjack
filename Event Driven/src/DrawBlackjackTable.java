import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class DrawBlackjackTable extends JPanel {
	
	public DrawBlackjackTable() {
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		ImageIcon image = new ImageIcon(DrawBlackjackTable.class.getClassLoader().getResource("DragonBoard.jpg"));
		Image background = image.getImage();
		g.drawImage(background, 0, 0, 900, 600, this);
		
		//g.setColor(Color.WHITE);
		//g.drawLine(0, 550, 900, 550);
	}
}