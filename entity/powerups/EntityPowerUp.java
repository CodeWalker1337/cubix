package cubix.entity.powerups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.newdawn.slick.opengl.Texture;

import cubix.entity.Entity;

public abstract class EntityPowerUp extends Entity implements ActionListener
{
	protected Texture tex;
	protected double duration, effectMultiplier, lifeTime;
	protected Timer timer = new Timer(1000, this);
	protected boolean textureLoaded = false;

	public EntityPowerUp(double x, double y, double width, double height, double duration, double effectMultiplier)
	{
		super(x, y, width, height);
		this.duration = duration;
		this.effectMultiplier = effectMultiplier;
		timer.start();

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(timer))
		{
			if (lifeTime == 5)
			{
				this.destroy();
			}
			lifeTime++;
		}

	}

}
