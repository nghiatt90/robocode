package unnamed.gun;

import robocode.*;

import unnamed.Enemy;


public abstract class BaseGun {
	
	protected AdvancedRobot _robot;
	
	public BaseGun(AdvancedRobot robot) {
		_robot = robot;
	}
	
	abstract public void onScannedRobot(ScannedRobotEvent e);
}