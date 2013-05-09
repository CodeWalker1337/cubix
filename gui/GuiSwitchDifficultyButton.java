package cubix.gui;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2d;

import org.lwjgl.input.Mouse;

import cubix.Cubix;
import cubix.Difficulty;

public class GuiSwitchDifficultyButton extends GuiButton
{

	private int selectedIndex = 2;

	public GuiSwitchDifficultyButton(double x, double y, double width, double height, String texPath, String texPathClicked, String texPathHovered)
	{
		super(x, y, width, height, texPath, texPathClicked, texPathHovered);

	}

	public void setSelectedIndex(int i)
	{

		selectedIndex = selectedIndex + i;

		if (selectedIndex == 5 && i > 0)
		{
			selectedIndex = 1;
		}

		this.buttonTex = Cubix.loadTexture("diffButton_" + selectedIndex);

		if (selectedIndex == 1)
		{
			Cubix.sEngine.setDifficulty(Difficulty.EASY);
		} else if (selectedIndex == 2)
		{
			Cubix.sEngine.setDifficulty(Difficulty.MEDIUM);
		} else if (selectedIndex == 3)
		{
			Cubix.sEngine.setDifficulty(Difficulty.HARD);
		} else if (selectedIndex == 4)
		{
			Cubix.sEngine.setDifficulty(Difficulty.INSANE);
		}

	}

	boolean changeDiff = true;

	@Override
	public void render()
	{

		if (changeDiff && bounds.contains(Mouse.getX(), (Cubix.HEIGHT - Mouse.getY())) && Mouse.isButtonDown(0))
		{
			Cubix.soundSys.playSound("button_click");
			isClicked = true;
			changeDiff = false;

		} else
		{

			isClicked = false;

		}

		if (!Mouse.isButtonDown(0))
		{
			changeDiff = true;

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

	@Override
	public void reload()
	{

		this.buttonTex = Cubix.loadTexture("diffButton_" + selectedIndex);
	}
}
