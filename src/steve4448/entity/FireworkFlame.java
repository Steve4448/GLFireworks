package steve4448.entity;

import static org.lwjgl.opengl.GL11.glColor4d;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;
import steve4448.main.Main;

public class FireworkFlame {
	public double x;
	public double y;
	public int width;
	public int height;
	public double yVelocity;
	public double[] color = new double[3];

	public FireworkFlame(double x, double y, int width, int height, double yVelocity) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.yVelocity = yVelocity;
		color[0] = 0.4 + Math.random() * 0.6;
		color[1] = Math.random() * 0.3;
		color[2] = 1.0f;
	}

	public void draw() {
		glPushMatrix();
		glTranslated(x, y, 0);
		glColor4d(color[0], color[1], 0, color[2]);
		Main.makeRectangle(width, height);
		glPopMatrix();
	}
}
