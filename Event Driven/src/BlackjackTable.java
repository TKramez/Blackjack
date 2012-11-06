import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class BlackjackTable extends JFrame{

	public BlackjackTable() {
		setLayout(null);
		DrawBlackjackTable blkjck = new DrawBlackjackTable();
		blkjck.setBounds(0, 0, 900, 600);
		
		HitButtonListener hitBtn = new HitButtonListener();
		JButton jbt1 = new JButton("Hit");
		jbt1.setBounds(245, 560, 80, 30);
		jbt1.addActionListener(hitBtn);
		
		StayButtonListener stayBtn = new StayButtonListener();
		JButton jbt2 = new JButton("Stay");
		jbt2.setBounds(355, 560, 80, 30);
		jbt2.addActionListener(stayBtn);
		
		DoubleButtonListener doubleBtn = new DoubleButtonListener();
		JButton jbt3 = new JButton("Double");
		jbt3.setBounds(465, 560, 80, 30);
		jbt3.addActionListener(doubleBtn);
		
		SplitButtonListener splitBtn = new SplitButtonListener();
		JButton jbt4 = new JButton("Split");
		jbt4.setBounds(575, 560, 80, 30);
		jbt4.addActionListener(splitBtn);
		
		add(jbt1);
		add(jbt2);
		add(jbt3);
		add(jbt4);
		add(blkjck);
	}

	public static void main(String[] args) {
		BlackjackTable table = new BlackjackTable();
		table.setSize(906, 628);
		table.setDefaultCloseOperation(EXIT_ON_CLOSE);
		table.setLocationRelativeTo(null);
		table.setTitle("Blackjack by Team Dragon");
		table.setResizable(false);
		table.setVisible(true);
	}
}

class HitButtonListener implements ActionListener {
		
	public void actionPerformed(ActionEvent e) {
		System.out.println("Hit button was pressed.");
	}
}

class StayButtonListener implements ActionListener {
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Stay button was pressed.");
	}
}

class DoubleButtonListener implements ActionListener {
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Double button was pressed.");
	}
}

class SplitButtonListener implements ActionListener {
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Split button was pressed.");
	}
}