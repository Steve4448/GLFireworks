package steve4448.main;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glVertex2i;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.Dimension;

import javax.swing.JOptionPane;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

public class Main {
	public static final Dimension windowSize = new Dimension(800, 600);
	public static int currentGLID = 0;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		try {
			Display.setDisplayMode(new DisplayMode(windowSize.width, windowSize.height));
			Display.setTitle("Firework Fun - Steve4448");
			Display.setFullscreen(false);
			Display.create(new PixelFormat(0, 0, 0, 0, 0, 0, 0, 1, false));
			// glClearAccum(0.0f, 0.0f, 0.0f, 1.0f);
			// glClear(GL_ACCUM_BUFFER_BIT);
			glDisable(GL_DEPTH_TEST);
			glDisable(GL_LIGHTING);
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			glOrtho(0, windowSize.width, windowSize.height, 0, 0f, 0.1f);
			glViewport(0, 0, windowSize.width, windowSize.height);
			new Frame();
		} catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Something fucked up and was unable to initalize OpenGL.");
		}
	}

	public static void makeRectangle(int width, int height) {
		glBegin(GL_QUADS);
		glVertex2i(0, 0);
		glVertex2i(0, height);
		glVertex2i(width, height);
		glVertex2i(width, 0);
		glEnd();
	}

	public static final int getNewGLID() {
		return currentGLID += 1;
	}

	public static final double[] generateRandomColor() {
		double returnDouble[] = new double[3];
		returnDouble[0] = 0.3 + Math.random() * 0.7;
		returnDouble[1] = 0.3 + Math.random() * 0.7;
		returnDouble[2] = 0.3 + Math.random() * 0.7;
		return returnDouble;
	}
}
