package cubix.gui;

import java.awt.Rectangle;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import static org.lwjgl.opengl.GL11.*;

import cubix.Cubix;

public class GuiButton
{

	protected double x, y, width, height;
	protected Texture buttonTex;
	protected boolean isHovered = false;
	protected boolean isClicked = false;
	protected Rectangle bounds = new Rectangle();
	private String buttonHov, buttonClick, buttonStatic;
	protected boolean playSound = true;

	public GuiButton(double x, double y, double width, double height, String texPath, String texPathClicked, String texPathHovered)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		buttonStatic = texPath;
		buttonHov = texPathHovered;
		buttonClick = texPathClicked;
		bounds.setBounds((int) x, (int) y, (int) width, (int) height);

	}

	public void render()
	{

		bounds.setBounds((int) x, (int) y, (int) width, (int) height);
		if (bounds.contains(Mouse.getX(), (Cubix.HEIGHT - Mouse.getY())))
		{
			isHovered = true;
			buttonTex = Cubix.loadTexture(buttonHov);
			if (Mouse.isButtonDown(0))
			{
				buttonTex = Cubix.loadTexture(buttonClick);
				isClicked = true;
				if (playSound)
				{
					Cubix.soundSys.playSound("button_click");
				}

			}
		} else
		{

			isClicked = false;
			isHovered = false;
			buttonTex = Cubix.loadTexture(buttonStatic);

		}

		buttonTex.bind();
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
	}

	public boolean isHovered()
	{
		return isHovered;
	}

	public boolean isClicked()
	{
		return isClicked;
	}

	public void reload()
	{

		buttonTex = Cubix.loadTexture(buttonStatic);
	}

	public void hide()
	{

		buttonTex.release();
	}

	public void show()
	{

		buttonTex.bind();
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

	public Rectangle getBounds()
	{
		return bounds;
	}

	public void setBounds(Rectangle bounds)
	{
		this.bounds = bounds;
	}

}
