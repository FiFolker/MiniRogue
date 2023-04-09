package main;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setPreferredSize(new Dimension(1280,980));
		window.setTitle("Mini Rogue");
		window.setResizable(false);
		window.setBackground(Color.black);
		try {
			window.setIconImage(ImageIO.read(new File("assets/icon/miniRogue.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Game game = new Game(window);

		window.add(game);

		window.pack();


		window.setLocationRelativeTo(null);
		window.setVisible(true);

		game.startGameThread();

	}

}
