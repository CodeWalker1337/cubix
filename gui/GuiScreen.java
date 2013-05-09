package cubix.gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex2d;

import org.newdawn.slick.opengl.Texture;

import cubix.Cubix;

public class GuiScreen implements IGuiScreen

{

	protected double x, y, width, height;
	protected String texPath;
	protected Texture tex;

	public GuiScreen(double x, double y, double width, double height, String texPath)
	{
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texPath = texPath;
		tex = Cubix.loadTexture(texPath);
	}

	float state = 0.0f;
	@Override
	public void render()
	{
		if (state != 1.0f){
			state+=0.03f;
		}
		tex.bind();
		glColor4f(1.0f, 1.0f, 1.0f,state);
		glBegin(GL_QUADS);
		glTexCoord2d(0, 0);
		glVertex2d(x, y);
		glTexCoord2d(1, 0);
		glVertex2d(x + width, y);
		glTexCoord2d(1, 1);
		glVertex2d(x + width, y + height);
		glTexCoord2d(0, 1);
		glVertex2d(x, y + height);
		glEnd();

	}

	@Override
	public void hide()
	{
		tex.release();
		state = 0.0f;

	}

	@Override
	public void show()
	{
		tex.bind();

	}

	@Override
	public void reload()
	{
		tex = Cubix.loadTexture(texPath);

	}

}
