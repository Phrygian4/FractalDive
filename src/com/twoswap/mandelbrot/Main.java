package com.twoswap.mandelbrot;

import com.twoswap.gui.GUI;

public class Main implements Runnable {
	public static final String TITLE = "Fractal Dive";

	private static  boolean running = false;
	private Thread thread;

	public Main() {
		new GUI();
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	@SuppressWarnings("unused")
	private synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void run() {
		while (running) tick();
	}

	private void tick() {
		GUI.render();
		if(Generator.record)
			Generator.savePic(GUI.pixels, "giffer/img"+Generator.time+".png");
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.start();
		Styler.inventCycle();
		//Generator.setupMagicPalette();
	}
}
