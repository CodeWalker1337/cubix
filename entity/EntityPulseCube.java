package cubix.entity;

import cubix.Cubix;

public class EntityPulseCube extends EntityAntiCube
{

	private float pulsation = 1;
	private boolean dec = false;

	public EntityPulseCube(double x, double y, double width, double height, float speed)
	{
		super(x, y, width, height, speed);

	}

	public void pulse()
	{

		tex = Cubix.loadTexture("pulsecube");

		if (pulsation >= 50)
		{
			dec = true;
		}

		if (pulsation < 50 && dec == false)
		{
			pulsation += 0.2f * (Cubix.sEngine.getDifficultyMultiplier());
		}

		if (pulsation != 0 && dec == true)
		{
			pulsation -= 0.2f * (Cubix.sEngine.getDifficultyMultiplier());
			dec = true;
			if (pulsation <= 1)
			{
				dec = false;
			}
		}

		setWidth(pulsation);
		setHeight(pulsation);

	}

}
