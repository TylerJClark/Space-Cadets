import java.awt.Color;
import java.awt.Graphics;

public class GetPoint {
	double lx = 0;
	double ly = 0;
	double x = 0;
	double y = 0;
	double bigR;
	double smallR;
	double o;
	Color colour;
	int screenOffsetX;
	int screenOffsetY;
	/**
	 * set constants
	 * @param bigr big radius
	 * @param smallr small radius
	 * @param oo offset
	 */
	public GetPoint(double bigr,double smallr,
			double oo,Color colourr,int offsetX,int offsetY) {
		bigR = bigr;
		smallR = smallr;
		o = oo;
		colour = colourr;
		screenOffsetX = offsetX;
		screenOffsetY = offsetY;
	}
	/**
	 * carries out all the needed methods
	 * @param tt timer
	 */
	public void doNext(double tt,Graphics g) {
		getPointX(tt);
		getPointY(tt);
		drawPoint(g);
	}
	
	/**
	 * get x coord
	 * @param tt timer

	 */	
	public void getPointX(double tt) {
		lx = x;
		x += (bigR-smallR)*Math.cos(tt) + o*Math.cos(((bigR-smallR)/smallR)*tt);
	}
	
	/**
	 * get y coord
	 * @param tt timer
	 */
	public void getPointY(double tt) {
		ly = y;
		y += (bigR-smallR)*Math.sin(tt) - o*Math.sin(((bigR-smallR)/smallR)*tt);
	}
	
	public void drawPoint(Graphics g) {
		g.setColor(colour);
		g.drawLine((int)lx + screenOffsetX,(int) ly + screenOffsetY,
				(int)x + screenOffsetX, (int)y + screenOffsetY);

	}
	
	
	
	
}
