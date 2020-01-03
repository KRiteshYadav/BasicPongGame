package com.main;

import javax.swing.JFrame;

public class Window {
	public Window(String gameTitle, Game game) {
		JFrame jf = new JFrame(gameTitle);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		jf.add(game);
		jf.pack();
		// jf.setLocationRelativeTo(null);
		jf.setVisible(true);

		game.start();
	}
}
