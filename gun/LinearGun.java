package unnamed.gun;

import robocode.AdvancedRobot;
import robocode.util.Utils;

import unnamed.Enemy;

public class LinearGun extends BaseGun {

	public LinearGun(AdvancedRobot robot) {
		super(robot);
	}

	@Override
	public void takeAim(Enemy e, double power) {
        // Offsets the gun by the angle to the next shot based on linear targeting provided by the enemy class
		double absBearing = e.bearing + _robot.getHeadingRadians();
        double gunOffset = absBearing - _robot.getGunHeadingRadians();
        _robot.setTurnGunRightRadians(Utils.normalRelativeAngle(gunOffset));
	}

	@Override
	public double calculatePower(Enemy e) {
		return 400.0 / e.distance;
	}

}
