package cubix.gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2d;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import cubix.Cubix;

public class GuiSliderBase
{

	protected double x, y, width, height;
	protected double maxInc, currentInc;
	protected Texture baseTex;
	protected GuiSlider attachedSlider;
	protected String baseTexString;

	public GuiSliderBase(double x, double y, double width, double height, String baseTex, GuiSlider attachedSlider, float maxInc)
	{
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.baseTex = Cubix.loadTexture(baseTex);
		this.baseTexString = baseTex;
		this.attachedSlider = attachedSlider;
		this.maxInc = maxInc;
		attachedSlider.setY(this.getY() + this.getHeight() / 2 - attachedSlider.getHeight() / 2);
		attachedSlider.setX((this.getX() + this.getWidth() - 50));
		Cubix.soundSys.setVolume(attachedSlider.getPosition() / 10 + 20);
	}

	public void render()
	{

		baseTex.bind();
		glBegin(GL_QUADS);
		glColor4f(1f, 1f, 1f, 1f);
		glTexCoord2f(0, 0);
		glVertex2d(x, y);
		glTexCoord2f(1, 0);
		glVertex2d(x + width, y);
		glTexCoord2f(1, 1);
		glVertex2d(x + width, y + height);
		glTexCoord2f(0, 1);
		glVertex2d(x, y + height);
		glEnd();

		attachedSlider.render();
		if (attachedSlider.getX() <= this.getX() + 50)
		{
			currentInc = maxInc;
			attachedSlider.setX(this.getX() + 51);
			return;
		}

		if (attachedSlider.getX() + attachedSlider.getWidth() >= this.getX() + this.getWidth() - 50)
		{
			currentInc = 0;
			attachedSlider.setX(this.getX() + this.getWidth() - attachedSlider.getWidth() - 51);
			return;
		}

		if (attachedSlider.isClicked)
		{

			if (Mouse.isButtonDown(0))
			{

				attachedSlider.setX(Mouse.getX() - attachedSlider.getWidth() / 2);
				this.currentInc = (attachedSlider.getPosition() / 10 + 20);
				Cubix.soundSys.setVolume(currentInc);

			}

		}

	}

	public void hide()
	{
		attachedSlider.hide();
		baseTex.release();
	}

	public void show()
	{
		baseTex.bind();
		attachedSlider.show();
	}

	public void reload()
	{
		baseTex = Cubix.loadTexture(baseTexString);
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width)
	{
		this.width = width;
	}

	public double getHeight()
	{
		return height;
	}

	public void setHeight(double height)
	{
		this.height = height;
	}

	public GuiSlider getAttachedSlider()
	{
		return attachedSlider;
	}

	public void setAttachedSlider(GuiSlider attachedSlider)
	{
		this.attachedSlider = attachedSlider;
	}

}
