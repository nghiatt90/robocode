package unnamed;

public class Enemy {
	public String name;
	public double lastBearing;
	public double bearing;
	public double lastHeading;
	public double heading;	// enemy heading
	public long ctime;      // game time that the scan was produced
	public double speed;
	public double x, y;
	public double distance;
	
	public double guessX(long when)
	{
			long diff = when - ctime;
			return x+Math.sin(heading)*speed*diff;
	}
	public double guessY(long when)
	{
			long diff = when - ctime;
			return y+Math.cos(heading)*speed*diff;
	}
}