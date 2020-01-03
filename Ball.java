package com.main;

import java.awt.Color;
import java.awt.Graphics;

public class Ball {
	public static final int SIZE = 16;

	private int x, y;
	private int xVel, yVel; // value is either 1 or -1
	private int speed = 5;

	public Ball() {
		reset();

	}

	private void reset() {
		// Initial position

		x = Game.WIDTH / 2 - SIZE / 2;
		y = Game.HEIGHT / 2 - SIZE / 2;

		// Initial velocities
		xVel = Game.sign(Math.random() * 2.0 - 1);
		yVel = Game.sign(Math.random() * 2.0 - 1);

	}

	public void changeXDir() {
		xVel *= -1;
	}

	public void changeYDir() {
		yVel *= -1;
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, SIZE, SIZE);

	}

	public void update(Paddle leftPaddle, Paddle rightPaddle) {
		// Update movement
		x += xVel * speed;
		y += yVel * speed;

		// Collisions with roof and ceiling
		if (y + SIZE >= Game.HEIGHT || y <= 0) {
			changeYDir();
		}

		// with walls
		if (x + SIZE >= Game.WIDTH) {
			leftPaddle.addPoint();
			reset();
		}

		if (x <= 0) {
			rightPaddle.addPoint();
			reset();
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
