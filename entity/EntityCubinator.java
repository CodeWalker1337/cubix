package cubix.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.util.ArrayList;

import cubix.Cubix;

public class EntityCubinator extends EntityAntiCube
{

	private ArrayList<EntityCubiceptor> ceptors = new ArrayList<EntityCubiceptor>();

	public EntityCubinator(double x, double y, double width, double height, float speed)
	{
		super(x, y, width, height, speed);
		ceptors.add(new EntityCubiceptor(x + 10 + getWidth(), y, 10, 10, 5.0F, this));
		ceptors.add(new EntityCubiceptor(x - 10, y, 10, 10, 4.0F, this));
		ceptors.add(new EntityCubiceptor(x + 10 + getWidth(), y + 10 + getHeight(), 10, 10, 3.0F, this));
		ceptors.add(new EntityCubiceptor(x, y - 10, 10, 10, 2.0F, this));
		ceptors.add(new EntityCubiceptor(x, y + 10 + getHeight(), 10, 10, 1.0F, this));
	}

	@Override
	public void render()
	{
		for (int i = 0; i < ceptors.size(); i++)
		{
			ceptors.get(i).render();
		}
		if (isDestroyed)
		{
			return;
		}
		if (!textureLoaded){
			tex = Cubix.loadTexture("cubinator");
			textureLoaded = true;
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
		glTexCoord2f(0, 1);
		glVertex2d(x, y + height);
		glEnd();
		glPopMatrix();
	}

}
