package cubix.gui;

public class GuiSlider extends GuiButton
{

	public GuiSlider(double x, double y, double width, double height, String texPath, String texPathClicked, String texPathHovered)
	{
		super(x, y, width, height, texPath, texPathClicked, texPathHovered);
		this.playSound = false;
	}
	
	public double getPosition(){
		return getX() + getWidth()/2;
	}

}
