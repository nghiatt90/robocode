package unnamed.gun;

import robocode.*;

import unnamed.Enemy;


public abstract class BaseGun {
	
	protected AdvancedRobot _robot;
	
	public BaseGun(AdvancedRobot robot) {
		_robot = robot;
	}
	
	abstract public void takeAim(Enemy e, double power);
	abstract public double calculatePower(Enemy e);
	
	public void shotToKill(Enemy target) {
		double power = calculatePower(target);
		takeAim(target, power);
		_robot.setFire(power);
	}
}