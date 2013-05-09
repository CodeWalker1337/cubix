package cubix;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

import cubix.entity.Entity;
import cubix.entity.EntityAntiCube;
import cubix.entity.EntityCubinator;
import cubix.entity.EntityPulseCube;
import cubix.entity.powerups.buffs.BuffScoreMultiplier;
import cubix.entity.powerups.buffs.BuffSlow;
import cubix.entity.powerups.buffs.BuffSpeed;
import cubix.entity.powerups.buffs.BuffStun;
import cubix.entity.powerups.buffs.BuffBomb;

public class SpawnEngine implements ActionListener
{

	private int spawnRate = 4000;
	private Timer spawnTimer = new Timer(spawnRate, this);
	private ArrayList<Entity> loadedEntities = new ArrayList<Entity>();
	public Difficulty diff;

	public SpawnEngine(Difficulty d)
	{

		this.diff = d;
		spawnTimer.setDelay(spawnRate);

	}

	public void setDefaultSpawnRate()
	{
		this.spawnRate = 4000;
	}

	public ArrayList<Entity> getLoadedEntities()
	{
		return loadedEntities;
	}

	public void startSpawning()
	{
		setSpawnRate(getSpawnRate() / getDifficultyMultiplier());
		spawnTimer.start();
	}

	public void stopSpawning()
	{
		spawnTimer.stop();
	}

	public void clearEntities()
	{
		for (int i = 0; i < loadedEntities.size(); i++)
		{
			loadedEntities.get(i).destroy();
		}
		loadedEntities = new ArrayList<Entity>();
	}

	public int getSpawnRate()
	{
		return spawnRate;
	}

	public void setSpawnRate(int spawnRate)
	{
		this.spawnRate = spawnRate;
		spawnTimer.setDelay(spawnRate);
	}

	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (Cubix.gameState == GameState.PAUSED)
		{
			return;
		}

		float size = 0, speed = 1.5f;
		int r = (int) (Math.random() * 4);
		if (event.getSource().equals(spawnTimer))
		{
			size = (float) (Math.random() * (60 + (Cubix.player.getWidth())));
			speed = speed + getDifficultyMultiplier() / 6;

			if (size < Cubix.player.getWidth())
			{
				loadedEntities.add(new EntityAntiCube((float) (Math.random() * Cubix.WIDTH), (float) (Math.random() * Cubix.HEIGHT), size, size, 1.5F));
			}
			if (r == 1)
			{
				loadedEntities.add(new EntityAntiCube(0, -15, size, size, speed));
			} else if (r == 2)
			{
				loadedEntities.add(new EntityAntiCube(Cubix.WIDTH + 15, 0, size, size, speed));
			} else if (r == 3)
			{
				loadedEntities.add(new EntityAntiCube(0, Cubix.HEIGHT + 15, size, size, speed));
			} else if (r == 4)
			{
				loadedEntities.add(new EntityAntiCube(Cubix.WIDTH + 7, Cubix.HEIGHT + 7, size, size, speed));
			}

			if ((int) (Math.random() * 10 - getDifficultyMultiplier()) == 1)
			{
				loadedEntities.add(new EntityPulseCube((float) (Math.random() * Cubix.WIDTH), (float) (Math.random() * Cubix.HEIGHT), size, size, 0.5F));
			}

			if ((int) (Math.random() * 80 - getDifficultyMultiplier() * 2) == 1)
			{
				loadedEntities.add(new EntityCubinator((float) (Math.random() * Cubix.WIDTH), (float) (Math.random() * Cubix.HEIGHT), 50, 50, 0.5F));
			}

			// POWER UPS
			if ((int) (Math.random() * 2) == 1)
			{
				int i = (int) (Math.random() * 7);

				if (i == 1)
				{
					loadedEntities.add(new BuffScoreMultiplier(Math.random() * Cubix.WIDTH, Math.random() * Cubix.HEIGHT, 35, 35, Math.random() * 10 + 4, Math.random() * 3 + 1));
				} else if (i == 2)
				{
					loadedEntities.add(new BuffSpeed(Math.random() * Cubix.WIDTH, Math.random() * Cubix.HEIGHT, 35, 35, Math.random() * 4 + 1, Math.random() * 3 + 1));
				} else if (i == 3)
				{
					loadedEntities.add(new BuffStun(Math.random() * Cubix.WIDTH, Math.random() * Cubix.HEIGHT, 35, 35, Math.random() * 2 + 2, 0));
				} else if (i == 4)
				{
					loadedEntities.add(new BuffSlow(Math.random() * Cubix.WIDTH, Math.random() * Cubix.HEIGHT, 35, 35, Math.random() * 4, Math.random() * 1));
				} else if (i == 5)
				{
					loadedEntities.add(new BuffBomb(Math.random() * Cubix.WIDTH, Math.random() * Cubix.HEIGHT, 35, 35, 1.0, 0.0));
				}

			}
		}
	}

	public void setDifficulty(Difficulty d)
	{
		diff = d;
	}

	public Difficulty getDifficulty()
	{
		return diff;
	}

	public int getDifficultyMultiplier()
	{
		if (diff == Difficulty.EASY)
		{
			return 1;
		}
		if (diff == Difficulty.MEDIUM)
		{
			return 2;
		}
		if (diff == Difficulty.HARD)
		{
			return 3;
		}
		if (diff == Difficulty.INSANE)
		{
			return 5;
		}

		return 0;
	}
}
