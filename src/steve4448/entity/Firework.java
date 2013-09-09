package steve4448.entity;

import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glColor4d;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslated;

import java.util.concurrent.CopyOnWriteArrayList;

import steve4448.main.Frame;
import steve4448.main.Main;

public class Firework {
	public static final int FIREWORK_WIDTH = 6, FIREWORK_HEIGHT = 16;
	public double x;
	public double y;
	public int width;
	public int height;
	public int endY = 0;
	public double yVelocity; // There's no X velocity as it's only going up.
	private double[] color = Main.generateRandomColor();
	public boolean hasExploded = false;
	public CopyOnWriteArrayList<FireworkFlame> flame = new CopyOnWriteArrayList<FireworkFlame>();
	public CopyOnWriteArrayList<FireworkParticle> particle = new CopyOnWriteArrayList<FireworkParticle>();

	public Firework(double x, double y, int width, int height, int endY) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.endY = endY;
		yVelocity = (y - endY) / 30;
	}

	public void process() {
		if(hasExploded) {
			for(FireworkParticle fP : particle) {
				fP.x += fP.xVelocity;
				fP.y += fP.yVelocity;
				fP.yVelocity += Frame.GRAVITY;
				if((fP.color[3] -= Math.random() * 0.02) <= 0)
					particle.remove(fP);
			}
			if(particle.isEmpty() && flame.isEmpty())
				Frame.firework.remove(this);
		} else {
			for(int i = 0; i < (y - endY) / 30; i++) {
				int flameSize = 1 + (int)(Math.random() * 5);
				flame.add(new FireworkFlame(x + width / 2 - flameSize / 2, y + height, flameSize, flameSize, 1 + Math.random() * 5));
			}
			if((y -= yVelocity) <= endY || yVelocity <= 0.3) {
				hasExploded = true;
				for(int i = 0; i < 100 + (int)(Math.random() * 50); i++) {
					int particleSize = 5 + (int)(Math.random() * 5);
					particle.add(new FireworkParticle(x, y, particleSize, particleSize, Math.random() * 10 - Math.random() * 10, Math.random() * 10 - Math.random() * 10));
				}
			}
		}
		for(FireworkFlame fF : flame) {
			fF.y += fF.yVelocity;
			if((fF.color[2] -= Math.random() * 0.3) <= 0)
				flame.remove(fF);
		}
	}

	public void draw() {
		glPushMatrix();
		glTranslated(x, y, 0);
		glColor4d(color[0], color[1], color[2], 1.0f);
		glCallList(Frame.FIREWORK_GL_ID);
		glPopMatrix();
	}
}
