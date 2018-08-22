package unnamed.movement;


import robocode.*;


public abstract class BaseMovement {
	protected AdvancedRobot _robot;
	
	public BaseMovement(AdvancedRobot robot) {
		_robot = robot;
	}
	
	abstract public void onScannedRobot(ScannedRobotEvent e);
}