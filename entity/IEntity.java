package cubix.entity;

public interface IEntity
{

	public void render();

	public void update(int delta);

	public void setLocation(double x, double y);

	public void setX(double x);

	public void setY(double y);

	public void setWidth(double width);

	public void setHeight(double height);

	public double getX();

	public double getY();

	public double getWidth();

	public double getHeight();

	public boolean collides(Entity collidedEntity);

	public void destroy();

	public boolean isDestroyed();
	
	public void setDestroyed(boolean destroyed);

	public double getDX();

	public double getDY();

	public void setDX(double dx);

	public void setDY(double dy);

	public float getSpeed();

	public void setSpeed(float f);

	public void hide();

	public void show();

	public void consume(Entity e);

	public void setDefaultSpeed();

	public void moveTo(double x, double y, float speed);
	
	public void update();
	
	public int getAmountEaten();
}
