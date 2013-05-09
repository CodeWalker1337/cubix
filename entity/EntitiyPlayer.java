package cubix.entity;

import static org.lwjgl.opengl.GL11.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.newdawn.slick.opengl.Texture;

import cubix.Cubix;

public class EntitiyPlayer extends Entity implements ActionListener
{

	Timer timer = new Timer(250, this);
	private double defaultWidth, defaultHeight;
	Texture tex;

	int rand = (int) (Math.random() * 10);

	float r = 0, g = 0, b = 0;

	public EntitiyPlayer(double x, double y, double width, double height, float speed)
	{
		super(x, y, width, height, speed);
		defaultWidth = width;
		defaultHeight = height;
		tex = Cubix.loadTexture("cubi");
		timer.start();

	}

	@Override
	public void render()
	{
		if (isDestroyed())
		{
			timer.stop();
			Cubix.stopGame(false);
			return;
		}
		glPushMatrix();
		tex.bind();
		glBegin(GL_QUADS);
		glColor4f(r, g, b, 0.8f);
		glTexCoord2f(0, 0);
		glVertex2d(x, y);
		glTexCoord2f(1, 0);
		glVertex2d(x + width, y);
		glTexCoord2f(1, 1);
		glVertex2d(x + width, y + height);
		glTexCoord2f(0, 1);
		glVertex2d(x, y + height);
		glEnd();

		glPopMatrix();

	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if (arg0.getSource().equals(timer))
		{
			r = (float) (Math.random() * 1) + 0.6f;
			g = (float) (Math.random() * 1) + 0.6f;
			b = (float) (Math.random() * 1) + 0.6f;
		}

	}

	public void respawn()
	{
		setX(Cubix.WIDTH / 2);
		setY(Cubix.HEIGHT / 2);
		amtEaten = 0;
		setWidth(defaultWidth);
		setHeight(defaultHeight);
		timer.start();
	}

	public int getScoreMultiplier()
	{
		// TODO Auto-generated method stub
		return (int) scoreMultiplier;
	}

}
