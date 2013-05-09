package cubix.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.util.ArrayList;

import cubix.Cubix;

public abstract class Region extends Entity
{

	public Entity parent;
	private ArrayList<Entity> radar = new ArrayList<Entity>();
	protected float r = 0, g = 0, b = 0, a = 0;

	public Region(double x, double y, double width, double height, float speed, Entity parent)
	{
		super(x, y, width, height, speed);
		this.parent = parent;
	}

	@Override
	public void render()
	{

		glBegin(GL_QUADS);
		glColor4f(r, g, b, a);
		glVertex2d(x, y);
		glVertex2d(x + width, y);
		glVertex2d(x + width, y + height);
		glVertex2d(x, y + height);
		glEnd();

		setX((parent.getX() + parent.getWidth() / 2) - (getWidth() / 2));
		setY((parent.getY() + parent.getHeight() / 2) - (getHeight() / 2));
		scan();

	}

	public void scan()
	{
		for (int i = 0; i < Cubix.sEngine.getLoadedEntities().size(); i++)
		{
			if (Cubix.sEngine.getLoadedEntities().get(i).collides(this) && !radar.contains(Cubix.sEngine.getLoadedEntities().get(i)))
			{
				radar.add(Cubix.sEngine.getLoadedEntities().get(i));
			} else if (radar.contains(Cubix.sEngine.getLoadedEntities().get(i)) && !Cubix.sEngine.getLoadedEntities().get(i).collides(this))
			{
				radar.remove(Cubix.sEngine.getLoadedEntities().get(i));
			}
		}

	}

	public ArrayList<Entity> getDetectedEntities()
	{
		return radar;

	}

}
