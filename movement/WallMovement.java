package unnamed.movement;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class WallMovement extends BaseMovement {

	private static final double WALL_MARGIN = 28D;
	
	public WallMovement(AdvancedRobot robot) {
		super(robot);
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		
	}

	@Override
	public void onTick() {
		double moveAmount = 0D;
		double heading = _robot.getHeadingRadians();
		if (Utils.isNear(heading, 0D)) {
			moveAmount = _robot.getBattleFieldHeight() - _robot.getY();
		} else if (Utils.isNear(heading, Math.PI)) {
			moveAmount = _robot.getY();
		} else if (Utils.isNear(heading, Math.PI / 2)) {
			moveAmount = _robot.getBattleFieldWidth() - _robot.getX();
		} else {
			moveAmount = _robot.getX();
		}
		_robot.ahead(moveAmount - WALL_MARGIN);
		_robot.turnRight(90);
	}

}
