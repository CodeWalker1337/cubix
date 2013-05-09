package cubix.entity;

import cubix.Cubix;

public class RegionFreeze extends Region
{

	public RegionFreeze(double x, double y, double width, double height, float speed, Entity parent)
	{
		super(x, y, width, height, speed, parent);

	}

	public void freeze()
	{
		for (int i = 0; i < getDetectedEntities().size(); i++)
		{
			getDetectedEntities().get(i).setSpeed(0.1F);
		}

		for (int k = 0; k < Cubix.sEngine.getLoadedEntities().size(); k++)
		{
			if (!getDetectedEntities().contains((Cubix.sEngine.getLoadedEntities().get(k))))
			{
				Cubix.sEngine.getLoadedEntities().get(k).setDefaultSpeed();
			}
		}

		if (parent instanceof EntitiyPlayer)
		{
			return;
		} else
		{
			if (getDetectedEntities().contains(Cubix.player))
			{
				Cubix.player.setSpeed(0.1F);
			}
		}
	}

}
