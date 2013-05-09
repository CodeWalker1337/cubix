package cubix.entity.powerups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import cubix.Cubix;
import cubix.entity.Entity;
import cubix.entity.EntityCubinator;

public class Effect implements ActionListener
{

	protected double duration, effectMultiplier, currentTime;
	protected Entity effectedEntity;
	protected EffectType effectType;
	private Timer timer = new Timer(1000, this);

	public Effect(double duration, double effectMultiplier, Entity effectedEntity, EffectType type)
	{
		super();
		this.duration = duration;
		this.effectMultiplier = effectMultiplier;
		this.effectedEntity = effectedEntity;
		this.effectType = type;
		startEffect();
	}

	/** Starts the effect and applies the effect based on the effect type */
	public void startEffect()
	{
		timer.start();
		/*
		 * SLOW EFFECT:
		 * 
		 * Slows all of the entities (excluding the player) on the map.
		 */
		if (effectType.equals(EffectType.SLOW))
		{
			for (int i = 0; i < Cubix.sEngine.getLoadedEntities().size() - 1; i++)
			{
				Cubix.sEngine.getLoadedEntities().get(i).setSpeed((float) (Cubix.sEngine.getLoadedEntities().get(i).getSpeed() - effectMultiplier));

			}
			Cubix.soundSys.playSound("freeze");
		}
		/*
		 * SPEED EFFECT:
		 * 
		 * Speeds up the effected entity, as of v1.42 and lower, it effects only
		 * the player.
		 */
		else if (effectType.equals(EffectType.SPEED))
		{
			effectedEntity.setSpeed((float) (effectedEntity.getSpeed() + effectMultiplier));
		}
		/*
		 * STUN EFFECT:
		 * 
		 * Stuns all entities (excluding the player) on the map *
		 */
		else if (effectType.equals(EffectType.STUN))
		{
			for (int i = 0; i < Cubix.sEngine.getLoadedEntities().size() - 1; i++)
			{
				Cubix.sEngine.getLoadedEntities().get(i).setSpeed(0.0f);

			}
			Cubix.soundSys.playSound("stun");
		}
		/*
		 * SCORE EFFECT:
		 * 
		 * Modifies the player's score multiplier.
		 */
		else if (effectType.equals(EffectType.SCORE_MULTIPLIER))
		{
			effectedEntity.setScoreMultiplier(effectMultiplier);
		}
		/*
		 * BOMB EFFECT:
		 * 
		 * Removes (bombs) all entities on the map (again, excluding the player)
		 */
		else if (effectType.equals(EffectType.BOMB))
		{
			for (int i = 0; i < Cubix.sEngine.getLoadedEntities().size() - 1; i++)
			{
				Cubix.sEngine.getLoadedEntities().get(i).destroy();

				if ((Cubix.sEngine.getLoadedEntities().get(i)) instanceof EntityCubinator)
				{
					Cubix.score += 30000;
				} else
				{
					Cubix.score += 200;
				}

			}
			Cubix.soundSys.playSound("bomb");
		}
	}

	/** Stops the effect and removes the debuff/buff */
	public void stopEffect()
	{
		timer.stop();

		/*
		 * SLOW EFFECT:
		 * 
		 * Slows all of the entities (excluding the player) on the map.
		 */
		if (effectType.equals(EffectType.SLOW))
		{
			for (int i = 0; i < Cubix.sEngine.getLoadedEntities().size() - 1; i++)
			{
				Cubix.sEngine.getLoadedEntities().get(i).setDefaultSpeed();

			}
		}
		/*
		 * SPEED EFFECT:
		 * 
		 * Speeds up the effected entity, as of v1.42 and lower, it effects only
		 * the player.
		 */
		else if (effectType.equals(EffectType.SPEED))
		{
			effectedEntity.setDefaultSpeed();
		}
		/*
		 * STUN EFFECT:
		 * 
		 * Stuns all entities (excluding the player) on the map *
		 */
		else if (effectType.equals(EffectType.STUN))
		{
			for (int i = 0; i < Cubix.sEngine.getLoadedEntities().size() - 1; i++)
			{
				Cubix.sEngine.getLoadedEntities().get(i).setDefaultSpeed();

			}
		}
		/*
		 * SCORE EFFECT:
		 * 
		 * Modifies the player's score multiplier.
		 */
		else if (effectType.equals(EffectType.SCORE_MULTIPLIER))
		{
			effectedEntity.setScoreMultiplier(1);
		}
		/*
		 * BOMB EFFECT:
		 * 
		 * Removes (bombs) all entities on the map (again, excluding the player)
		 */
		else if (effectType.equals(EffectType.BOMB))
		{

		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(timer))
		{
			if (duration <= currentTime)
			{
				stopEffect();
			}
			currentTime++;
		}

	}

	public double getDuration()
	{
		return duration;
	}

	public void setDuration(double duration)
	{
		this.duration = duration;
	}

	public double getEffectMultiplier()
	{
		return effectMultiplier;
	}

	public void setEffectMultiplier(double effectMultiplier)
	{
		this.effectMultiplier = effectMultiplier;
	}

	public EffectType getEffectType()
	{
		return effectType;
	}

	public void setEffectType(EffectType effectType)
	{
		this.effectType = effectType;
	}

}
