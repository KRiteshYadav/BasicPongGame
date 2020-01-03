package com.main;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -7014561916727218286L;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = WIDTH * 9 / 16;

	public boolean running = false;
	private Thread gameThread;
	private Ball ball;
	private Paddle leftPaddle;
	private Paddle rightPaddle;

	public Game() {
		canvasSetup();
		initialize();
		new Window(" Basic Pong game", this);

		this.addKeyListener(new KeyInput(leftPaddle, rightPaddle));
		this.setFocusable(true);
	}

	private void initialize() {
		// initialize ball
		ball = new Ball();

		// initialize paddle
		leftPaddle = new Paddle(Color.orange, true);
		rightPaddle = new Paddle(Color.blue, false);

	}

	private void canvasSetup() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));

	}

	@Override
	public void run() {
		this.requestFocus();

		// game timer

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;

			}
			if (running) {
				draw();
			}
			frames += 1;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}

		}
		stop();

	}

	private void draw() {
		// Initialize drawing tools
		BufferStrategy buffer = this.getBufferStrategy();
		if (buffer == null) {
			this.createBufferStrategy(3);
			return;

		}

		Graphics g = buffer.getDrawGraphics();

		// draw background
		drawBackground(g);

		// draw ball
		ball.draw(g);

		// draw paddles and score
		leftPaddle.draw(g);
		rightPaddle.draw(g);

		// dispose, actually draw
		g.dispose();
		buffer.show();

	}

	private void drawBackground(Graphics g) {
		// black bg
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// The middle dotted line
		g.setColor(Color.white);
		Graphics2D g2d = (Graphics2D) g;
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 10 }, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);

	}

	private void update() {
		// update ball
		ball.update(leftPaddle, rightPaddle);

		// update paddles
		leftPaddle.update(ball);
		rightPaddle.update(ball);
	}

	public void start() {
		Thread gameThread = new Thread(this);
		gameThread.start();
		running = true;

	}

	public void stop() {
		try {
			gameThread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static int sign(double d) {
		if (d <= 0)
			return -1;
		return 1;

	}

	public static void main(String args[]) {
		Game game = new Game();
	}

	public static int ensureRange(int val, int min, int max) {

		return Math.min(Math.max(val, min), max);
	}

}
