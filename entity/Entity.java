package cubix.entity;

import java.awt.Rectangle;
import java.util.ArrayList;

import cubix.Cubix;
import cubix.entity.powerups.Effect;

public abstract class Entity implements IEntity
{

	protected double x, y, width, height, dx, dy, maxGrowSize;
	protected ArrayList<Effect> effects = new ArrayList<Effect>();
	protected int amtEaten;
	protected Rectangle collisionBox;
	protected boolean isDestroyed;
	protected float speed, dSpeed;
	protected boolean isHidden;
	protected double scoreMultiplier;

	/** Creates a mobile entity with set parameters. */
	public Entity(double x, double y, double width, double height, float speed)
	{

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.dx = 0;
		this.dy = 0;
		this.speed = speed;
		dSpeed = speed;
		this.maxGrowSize = 50;
		isDestroyed = false;
		isHidden = false;
		collisionBox = new Rectangle();
		scoreMultiplier = 1;

	}

	/** Creates a immobile entity with set parameters. */
	public Entity(double x, double y, double width, double height)
	{

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.dx = 0;
		this.dy = 0;
		this.maxGrowSize = 50;
		this.speed = 0;
		dSpeed = speed;
		isDestroyed = false;
		isHidden = false;
		collisionBox = new Rectangle();

	}

	@Override
	public void setDefaultSpeed()
	{
		this.dSpeed = speed;
	}

	@Override
	public void setLocation(double x, double y)
	{
		this.x = x;
		this.y = y;

	}

	@Override
	public void setX(double x)
	{
		this.x = x;

	}

	@Override
	public void setY(double y)
	{
		this.y = y;

	}

	@Override
	public void setWidth(double width)
	{
		this.width = width;
	}

	@Override
	public void setHeight(double height)
	{
		this.height = height;
	}

	@Override
	public double getX()
	{

		return x;
	}

	@Override
	public double getY()
	{

		return y;
	}

	@Override
	public double getWidth()
	{

		return width;
	}

	@Override
	public double getHeight()
	{

		return height;
	}

	@Override
	public boolean collides(Entity collidedEntity)
	{
		if (isDestroyed)
		{
			return false;
		}
		collisionBox.setBounds((int) x, (int) y, (int) width, (int) height);
		return collisionBox.intersects(collidedEntity.getX(), collidedEntity.getY(), collidedEntity.getWidth(), collidedEntity.getHeight());
	}

	@Override
	public void destroy()
	{

		isDestroyed = true;
		setLocation(9999, 9999);

	}

	@Override
	public boolean isDestroyed()
	{
		return isDestroyed;
	}

	@Override
	public void setDestroyed(boolean destroyed)
	{
		if (destroyed)
		{
			this.isDestroyed = true;
		} else
		{
			this.isDestroyed = false;
		}
	}

	@Override
	public void update(int delta)
	{
		this.x += delta * dx;
		this.y += delta * dy;
	}

	@Override
	public double getDX()
	{
		return dx;

	}

	@Override
	public double getDY()
	{
		return dy;

	}

	@Override
	public void setDX(double dx)
	{
		this.dx = dx;
	}

	@Override
	public void setDY(double dy)
	{
		this.dy = dy;
	}

	@Override
	public float getSpeed()
	{
		return dSpeed;
	}

	@Override
	public void setSpeed(float f)
	{
		this.dSpeed = f;

	}

	@Override
	public void hide()
	{
		this.isHidden = true;

	}

	@Override
	public void show()
	{
		this.isHidden = false;
	}

	@Override
	public void update()
	{

		for (int i = 0; i < effects.size(); i++)
		{

		}

	}

	public void moveHorizontal(int direction)
	{

		if (!this.isDestroyed() && !isHidden)
		{

			if (x + this.getWidth() >= Cubix.WIDTH && direction > 0)
			{
				return;
			}

			if (x <= 0 && direction < 0)
			{
				return;
			}
			if (direction > 0)
			{
				this.x = x + getSpeed();
			}
			if (direction < 0)
			{
				this.x = x - getSpeed();
			}
		}

	}

	public void moveVertical(int direction)
	{
		if (!this.isDestroyed() && !isHidden)
		{
			if (y + this.getHeight() >= Cubix.HEIGHT && direction > 0)
			{
				return;
			}
			if (y <= 0 && direction < 0)
			{
				return;
			}
			if (direction > 0)
			{
				this.y = y + getSpeed();
			}
			if (direction < 0)
			{
				this.y = y - getSpeed();
			}
		}
	}

	@Override
	public void consume(Entity consumed)
	{
		if (consumed.getWidth() / 6 + getWidth() > getWidth() + maxGrowSize)
		{
			setWidth(maxGrowSize);
		}

		if (consumed.getHeight() / 6 + getHeight() > getHeight() + maxGrowSize)
		{
			setHeight(maxGrowSize);
		}

		setWidth(consumed.getWidth() / 6 + getWidth());
		setHeight(consumed.getHeight() / 6 + getHeight());
		amtEaten++;

		if (this instanceof EntitiyPlayer)
		{
			if (consumed instanceof EntityPulseCube)
			{
				Cubix.score += 5000 * scoreMultiplier * Cubix.sEngine.getDifficultyMultiplier();
			} else
			{
				Cubix.score += 1000 * scoreMultiplier * Cubix.sEngine.getDifficultyMultiplier();
			}

		}

	}

	@Override
	public int getAmountEaten()
	{
		return amtEaten;
	}

	@Override
	public void moveTo(double x, double y, float speed)
	{
		if (!isDestroyed() && !isHidden)
		{

			if (getX() < x)
			{

				if (x + this.getWidth() >= Cubix.WIDTH)
				{
					return;
				}
				this.x = this.x + speed;
			}
			if (getY() < y)
			{
				if (y + this.getHeight() >= Cubix.HEIGHT)
				{
					return;
				}

				this.y = this.y + speed;

			}
			if (getX() > x)
			{

				if (x <= 0)
				{
					return;
				}
				this.x = this.x - speed;

			}
			if (getY() > y)
			{
				if (y <= 0)
				{
					return;
				}

				this.y = this.y - speed;
			}
		}
	}

	public void setScoreMultiplier(double multi)
	{
		this.scoreMultiplier = multi;

	}

}
