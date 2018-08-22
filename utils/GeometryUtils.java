package unnamed.utils;


import java.awt.geom.*;


/*
 * Shamelessly stole from DrussGT. All credits to the original author Skilgannon.
 */
public class GeometryUtils {
	
	public static final Point2D.Double project(Point2D.Double location, double angle, double distance){
        return new Point2D.Double(location.x + distance*FastTrig.sin(angle), location.y + distance*FastTrig.cos(angle));
    }
	
    public static final double absoluteAngle(Point2D source, Point2D target) {
        return FastTrig.atan2(target.getX() - source.getX(), target.getY() - source.getY());
    }
	
	public static double bulletVelocity(double power) {
		return 20 - 3 * power;
	}
	
	public static int sign(double v) {
		return v < 0 ? -1 : 1;
	}
	
	public static int boundedValue(int v, int min, int max) {
		return Math.max(min, Math.min(max, v));
	}
}