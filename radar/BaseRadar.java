package unnamed.radar;


import robocode.*;


public abstract class BaseRadar {
	protected AdvancedRobot _robot;
	
	public BaseRadar(AdvancedRobot robot) {
		_robot = robot;
	}
	
	public abstract void doScan();
}