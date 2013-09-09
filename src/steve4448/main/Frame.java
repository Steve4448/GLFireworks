package steve4448.main;

import static org.lwjgl.opengl.GL11.GL_ACCUM;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_MULT;
import static org.lwjgl.opengl.GL11.GL_RETURN;
import static org.lwjgl.opengl.GL11.glAccum;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glNewList;

import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import steve4448.entity.Firework;
import steve4448.entity.FireworkFlame;
import steve4448.entity.FireworkParticle;

public class Frame {
	public static int MADE, BY, STEVE4448;
	public static final double GRAVITY = 0.3;
	private static final float MOTION_BLUR_AMOUNT = 0.70f;
	public static final int FIREWORK_GL_ID = Main.getNewGLID();
	public static CopyOnWriteArrayList<Firework> firework = new CopyOnWriteArrayList<Firework>();
	private int mouseX = 0, mouseY = 0;
	private int FPS = 0;
	private long lastFPSDisplay = Sys.getTime();
	private boolean leftClickPressed = false;
	private boolean rightClickPressed = false;

	public Frame() {
		glNewList(FIREWORK_GL_ID, GL_COMPILE);
		Main.makeRectangle(Firework.FIREWORK_WIDTH, Firework.FIREWORK_HEIGHT);
		glEndList();
		gameLoop();
	}

	public void process() {
		for(Firework f : firework)
			f.process();
	}

	public void gameLoop() {
		while(true) {
			FPS++;
			Display.sync(40);
			doMouse();
			process();
			if(Sys.getTime() - lastFPSDisplay >= 1000) {
				lastFPSDisplay = Sys.getTime();
				Display.setTitle("Firework Fun - FPS: " + FPS + " - Steve4448");
				FPS = 0;
			}
			glClear(GL_COLOR_BUFFER_BIT); // - No need now, as we have a
											// background.
			for(Firework f : firework) {
				if(f.hasExploded)
					for(FireworkParticle fP : f.particle)
						fP.draw();
				else
					f.draw();
				for(FireworkFlame fF : f.flame)
					fF.draw();
			}
			glAccum(GL_MULT, MOTION_BLUR_AMOUNT);
			glAccum(GL_ACCUM, 1f - MOTION_BLUR_AMOUNT);
			glAccum(GL_RETURN, 1.0f);
			Display.update();
			if(Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				Display.destroy();
				break;
			}
		}
	}

	public void doMouse() {
		mouseX = Mouse.getX();
		mouseY = Main.windowSize.height - Mouse.getY(); // Reverse so
														// everything's correct.
		if(Mouse.isButtonDown(0)) {
			if(!leftClickPressed)
				// mousePressed(0);
				leftClickPressed = true;
		} else if(!Mouse.isButtonDown(0))
			if(leftClickPressed) {
				mouseReleased(0);
				leftClickPressed = false;
			}
		if(Mouse.isButtonDown(1)) {
			if(!rightClickPressed)
				// mousePressed(1);
				rightClickPressed = true;
		} else if(!Mouse.isButtonDown(1))
			if(rightClickPressed) {
				mouseReleased(1);
				rightClickPressed = false;
			}
	}

	public void mouseReleased(int ID) {
		if(ID == 0)
			firework.add(new Firework(mouseX - Firework.FIREWORK_WIDTH / 2, Main.windowSize.height, Firework.FIREWORK_WIDTH, Firework.FIREWORK_HEIGHT, mouseY));
		else if(ID == 1)
			for(int i = 0; i < 10; i++)
				firework.add(new Firework((int)(Math.random() * Main.windowSize.width), Main.windowSize.height, Firework.FIREWORK_WIDTH, Firework.FIREWORK_HEIGHT, (int)(Math.random() * Main.windowSize.height)));
	}
}
