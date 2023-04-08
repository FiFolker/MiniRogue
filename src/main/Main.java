package main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setPreferredSize(new Dimension(1280,980));
		window.setTitle("Mini Rogue");
		window.setResizable(false);
		window.setBackground(Color.black);

		Game game = new Game(window);

		window.add(game);

		window.pack();


		window.setLocationRelativeTo(null);
		window.setVisible(true);

		game.startGameThread();

	}

}
