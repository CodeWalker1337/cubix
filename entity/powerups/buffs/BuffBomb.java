package cubix.entity.powerups.buffs;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2d;

import cubix.Cubix;
import cubix.entity.powerups.Effect;
import cubix.entity.powerups.EffectType;
import cubix.entity.powerups.EntityPowerUp;

public class BuffBomb extends EntityPowerUp
{

	public BuffBomb(double x, double y, double width, double height, double duration, double effectMultiplier)
	{
		super(x, y, width, height, duration, effectMultiplier);
		
	}

	@Override
	public void render()
	{

		if (isDestroyed())
		{
			return;
		}
		
		if (!textureLoaded){
			tex = Cubix.loadTexture("buff_bomb");
			textureLoaded = true;
		}

		glPushMatrix();
		tex.bind();
		glColor4f(1f, 1f, 1f, 1f);
		glBegin(GL_QUADS);
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

		if (this.collides(Cubix.player))
		{
			new Effect(duration, effectMultiplier, Cubix.player, EffectType.BOMB);
			this.destroy();
		}
	}

}
