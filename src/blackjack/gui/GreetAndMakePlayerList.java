package blackjack.gui;
/*
 * @Author Jordan Greenfield, Tyler Kramer,
 * 		   Brandon Turner, Kyle Nyland 
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class GreetAndMakePlayerList extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1598803011951126541L;
	private Vector<Player> playerList = new Vector<Player>();
	private JTextField player1 = new JTextField();
	private JTextField player2 = new JTextField();
	private JTextField player3 = new JTextField();
	
	public GreetAndMakePlayerList() {
		setLayout(null);
		
		JLabel header1 = new JLabel("Welcome to Blackjack by Team Dragon!");
		JLabel header2 = new JLabel("Enter each player's name:");
		JLabel header3 = new JLabel("(Leave blank for empty seat)");
		JLabel p1 = new JLabel ("Player 1:");
		JLabel p2 = new JLabel ("Player 2:");
		JLabel p3 = new JLabel ("Player 3:");
		
		JButton jbtOK = new JButton("OK");
		jbtOK.addActionListener(this);
		
		header1.setBounds(15, 5, 250, 15);
		add(header1);
		header2.setBounds(15, 28, 250, 15);
		add(header2);
		header3.setBounds(15, 45, 250, 15);
		add(header3);
		
		p1.setBounds(30, 70, 100, 15);
		add(p1);
		player1.setBounds(35, 95, 100, 20);
		add(player1);
		
		p2.setBounds(30, 125, 100, 15);
		add(p2);
		player2.setBounds(35, 150, 100, 20);
		add(player2);
		
		p3.setBounds(30, 180, 100, 15);
		add(p3);
		player3.setBounds(35, 205, 100, 20);
		add(player3);
		
		jbtOK.setBounds(200, 130, 60, 40);
		add(jbtOK);
	}
	
	public Vector<Player> getPlayerList() {
		return playerList;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (!player1.getText().equals(""))
			playerList.add(new Player(player1.getText()));
		if (!player2.getText().equals(""))
			playerList.add(new Player(player2.getText()));
		if (!player3.getText().equals(""))
			playerList.add(new Player(player3.getText()));
	}
}