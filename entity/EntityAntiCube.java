package cubix.entity;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import cubix.Cubix;

public class EntityAntiCube extends Entity
{
	float r = 0, g = 0, b = 0;
	Texture tex;
	boolean textureLoaded = false;

	public EntityAntiCube(double x, double y, double width, double height, float speed)
	{
		super(x, y, width, height, speed);

		r = (float) (Math.random() * 1) + 0.5f;
		g = (float) (Math.random() * 1) + 0.5f;
		b = (float) (Math.random() * 1) + 0.5f;

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
			tex = Cubix.loadTexture("anticube");
			textureLoaded = true;
		}

		glPushMatrix();
		tex.bind();
		glBegin(GL_QUADS);
		glColor4f(r, g, b, 0.7f);
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

	public void moveToEntity(Entity e)
	{
		if (e.isDestroyed)
		{
			return;
		}
		if (collides(e))
		{

			e.destroy();

		}

		if (x < e.x)
		{
			moveHorizontal(1);
		}
		if (y < e.y)
		{
			moveVertical(1);
		}
		if (x > e.x)
		{
			moveHorizontal(-1);
		}
		if (y > e.y)
		{
			moveVertical(-1);
		}

	}

	public void moveFromEntity(Entity e)
	{
		if (e.isDestroyed)
		{
			return;
		}
		if (collides(e))
		{
			if (getWidth() > e.getWidth() && getHeight() > e.getHeight())
			{
				e.destroy();

			} else
			{

				e.consume(this);
				destroy();
			}

		}

		if (x < e.x)
		{
			moveHorizontal(-1);
		}
		if (y < e.y)
		{
			moveVertical(-1);
		}
		if (x > e.x)
		{
			moveHorizontal(1);
		}
		if (y > e.y)
		{
			moveVertical(1);
		}
		GL11.glCallList(1);
	}

	public void smartMoveTo(Entity e)
	{

		if (e.getWidth() > getWidth() && e.getHeight() > getHeight())
		{
			moveFromEntity(e);
		} else if (e.getWidth() < getWidth() && e.getHeight() < getHeight())
		{
			moveToEntity(e);
		}

	}

}
