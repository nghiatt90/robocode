package unnamed;


import java.awt.geom.*;


public class Enemy {
	public String name;
	public double bearing;
	public double heading;
    public int direction;  // 1 or -1
	public int ctime;      // game time that the scan was produced
	public double speed;
	public Point2D.Double location = new Point2D.Double();
	public double distance;
    public Enemy lastState;
    
    public void updateLastState() {
        if (lastState == null) {
            lastState = new Enemy();
            lastState.name = name;
        }
        lastState.bearing = bearing;
        lastState.heading = heading;
        lastState.direction = direction;
        lastState.ctime = ctime;
        lastState.speed = speed;
        lastState.location = location;
        lastState.distance = distance;
    }
}