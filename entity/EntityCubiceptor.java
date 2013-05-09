package cubix.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import cubix.Cubix;

public class EntityCubiceptor extends EntityAntiCube implements ActionListener

{

	private Entity parent;
	private float f = 0;
	private int angle = (int) (Math.random() * 80);
	Timer timer = new Timer(25, this);

	RegionFreeze rr = new RegionFreeze(x, y, 20, 20, 0.0F, this);
	RegionKill kk = new RegionKill(x, y, width, height, 0.0F, this);

	public EntityCubiceptor(double x, double y, double width, double height, float speed, Entity parent)
	{
		super(x, y, width, height, speed);
		this.parent = parent;
		timer.start();
	}

	@Override
	public void render()
	{
		if (isDestroyed())
		{

			return;
		}

		if (!textureLoaded)
		{
			tex = Cubix.loadTexture("cubinator");
			textureLoaded = true;
		}

		f += 0.1f;
		setY((parent.getY() + parent.getHeight() / 2) + (angle + 50) * Math.cos(f * speed));
		setX((parent.getX() + parent.getWidth() / 2) + (angle + 50) * Math.sin(f * speed));

		rr.render();
		rr.freeze();

		kk.render();
		kk.kill();

		if (collides(Cubix.player))
		{
			Cubix.player.destroy();
		}

		glPushMatrix();
		tex.bind();
		glBegin(GL_QUADS);
		glColor4f(r, g, b, 0.99f);
		glTexCoord2f(0, 0);
		glVertex2d(x, y);
		glTexCoord2f(1, 0);
		glVertex2d(x + width, y);
		glTexCoord2f(1, 1);
		glVertex2d(x + width, y + height);
		glTexCoord2f(1, 1);
		glVertex2d(x + width, y + height);
		glEnd();
		glPopMatrix();

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(timer))
		{
			r = (float) (Math.random() * 1);
			g = (float) (Math.random() * 1);
			b = (float) (Math.random() * 1);
		}
	}
}
