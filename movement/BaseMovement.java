package unnamed.movement;


import robocode.*;


public abstract class BaseMovement {
	private AdvancedRobot _robot;
	
	public BaseMovement(AdvancedRobot robot) {
		_robot = robot;
	}
	
	abstract public void doMove();
}