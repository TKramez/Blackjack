/*
 * @Author Jordan Greenfield, Tyler Kramer,
 * 		   Brandon Turner, Kyle Nyland 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class PlayAgain extends JFrame implements ActionListener{
	private String tempAnswer = "";
	private JButton jbtNo = new JButton("No");
	private JButton jbtYes = new JButton("Yes");
	
	public PlayAgain(String name) {
		setLayout(null);
		setSize(286, 120);
		setResizable(false);
		
		JLabel ask = new JLabel(String.format("%s would you like to play again?", name));
		ask.setBounds(10, 10, 300, 15);
		add(ask);
		
		jbtYes.setBounds(52, 45, 80, 30);
		jbtYes.addActionListener(this);
		add(jbtYes);
		
		jbtNo.setBounds(147, 45, 80, 30);
		jbtNo.addActionListener(this);
		add(jbtNo);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtYes)
			tempAnswer = "yes";
		if (e.getSource() == jbtNo)
			tempAnswer = "no";
	}
	
	public boolean getAnswer() {
		boolean answer;
		
		if (tempAnswer.equals("yes"))
			answer = true;
		else
			answer = false;
		
		return answer;
	}

	public String wake() {
		return tempAnswer;
	}
}
