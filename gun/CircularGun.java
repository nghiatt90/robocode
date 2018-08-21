package unnamed.gun;

/* BUG */

import robocode.*;
import unnamed.*;

import java.awt.geom.*;

public class CircularGun extends BaseGun {
	
	public CircularGun(AdvancedRobot robot) {
		super(robot);
	}
	
	@Override
	public void takeAim(Enemy e, double power) {
		double headingOffset = e.heading - e.lastHeading;
		double absBearing = e.bearing + _robot.getHeadingRadians();
		double eX = e.distance * Math.sin(absBearing);
		double eY = e.distance * Math.cos(absBearing);
		
		double db = 0.0;
		double currentHeading = e.heading;
		double bulletVelocity = 20 - 3 * power;
		
		do {
			db += bulletVelocity;
			double dx = e.speed * Math.sin(currentHeading);
			double dy = e.speed * Math.cos(currentHeading);
			currentHeading += headingOffset;
			eX += dx;
			eY += dy;
		} while (db < Point2D.distance(0, 0, eX, eY));
		
		_robot.setTurnGunRightRadians(Math.asin(Math.sin(Math.atan2(eX, eY) - _robot.getGunHeadingRadians())));
	}
	
	@Override
	public double calculatePower(Enemy e) {
		return 400.0 / e.distance;
	}
}
