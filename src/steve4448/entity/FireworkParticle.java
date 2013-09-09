package steve4448.entity;

import static org.lwjgl.opengl.GL11.glColor4d;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;
import steve4448.main.Main;

public class FireworkParticle {
	public double x;
	public double y;
	public int width;
	public int height;
	public double xVelocity;
	public double yVelocity;
	public double[] color = new double[4];

	public FireworkParticle(double x, double y, int width, int height, double xVelocity, double yVelocity) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		color[0] = 0.3 + Math.random() * 0.7;
		color[1] = 0.3 + Math.random() * 0.7;
		color[2] = 0.3 + Math.random() * 0.7;
		color[3] = 1.0f;
	}

	public void draw() {
		glPushMatrix();
		glTranslated(x, y, 0);
		glColor4d(color[0], color[1], color[2], color[3]);
		Main.makeRectangle(width, height);
		glPopMatrix();
	}
}
