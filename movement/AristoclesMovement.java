package unnamed.movement;


import java.awt.geom.*;

import robocode.*;
import robocode.util.*;

import unnamed.*;
import unnamed.utils.*;


public class AristoclesMovement extends BaseMovement {
    
    private static double BATTLE_FIELD_WIDTH = 800;
	private static double BATTLE_FIELD_HEIGHT = 600;
	private static final double WALL_MARGIN = 18;
	private static final double REVERSE_TUNER = 0.421075;
	private static final double DEFAULT_EVASION = 1.2;
	private static final double WALL_BOUNCE_TUNER = 0.699484;
    private static final int MAX_TRIES = 125;
    
    private Rectangle2D _field;
    private double _enemyFiringPower = 3;
	private double _direction = 0.4;
    
    
    public AristoclesMovement(AdvancedRobot robot) {
        super(robot);
        // BATTLE_FIELD_WIDTH = _robot.getBattleFieldWidth();
        // BATTLE_FIELD_HEIGHT = _robot.getBattleFieldHeight();
        _field = new Rectangle2D.Double(WALL_MARGIN,
                                        WALL_MARGIN,
                                        BATTLE_FIELD_WIDTH - WALL_MARGIN * 2,
                                        BATTLE_FIELD_HEIGHT - WALL_MARGIN * 2);
    }
    
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        Enemy enemyInfo = Memory.enemies.get(e.getName());
        if (enemyInfo == null) {
            // Should not happen
            return;
        }

		Point2D myLocation = new Point2D.Double(_robot.getX(), _robot.getY());
		Point2D destination;
		int tries = 0;
		while (!_field.contains(destination = GeometryUtils.project(enemyInfo.location,
                                                                    enemyInfo.bearing + Math.PI + _direction,
                                                                    enemyInfo.distance * (DEFAULT_EVASION - tries / 100.0)))
                && tries < MAX_TRIES) {
			tries++;
		}
		if ((Math.random() < (GeometryUtils.bulletVelocity(_enemyFiringPower) / REVERSE_TUNER) / enemyInfo.distance
            || tries > (enemyInfo.distance / GeometryUtils.bulletVelocity(_enemyFiringPower) / WALL_BOUNCE_TUNER))) {
			_direction = -_direction;
		}
		// Jamougha's cool way
		double angle = GeometryUtils.absoluteAngle(myLocation, destination) - _robot.getHeadingRadians();
		_robot.setAhead(FastTrig.cos(angle) * 100);
		_robot.setTurnRightRadians(FastTrig.tan(angle));
	}

	@Override
	public void onTick() {
		// TODO Auto-generated method stub
		
	}
}