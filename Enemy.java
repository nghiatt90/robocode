package unnamed;


import java.awt.geom.*;


public class Enemy {
	public String name;
	public double lastBearing;
	public double bearing;
	public double lastHeading;
	public double heading;	// enemy heading
	public int ctime;      // game time that the scan was produced
	public double speed;
	public Point2D.Double location = new Point2D.Double();
	public double distance;
}