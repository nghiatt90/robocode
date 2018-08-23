package unnamed.radar;


import java.awt.geom.Point2D;

import robocode.*;
import robocode.util.Utils;

import unnamed.Enemy;
import unnamed.Memory;
import unnamed.utils.FastTrig;
import unnamed.utils.GeometryUtils;


public class TrackingRadar extends BaseRadar {
	
	private final double PI = Math.PI;
	private Enemy _target;
	
	public TrackingRadar(AdvancedRobot robot) {
		super(robot);
	}
	
	@Override
	public void onTick() {
		if (_robot.getRadarTurnRemaining() == 0)
            // If radar stopped for some reason,
            // turn it around to find new target
            _robot.setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		
		String enemyName = e.getName();
        Point2D.Double myLocation =  new Point2D.Double(_robot.getX(), _robot.getY());
                            
        Enemy enemyInfo = Memory.enemies.get(enemyName);
        if (enemyInfo == null) {
            Memory.enemies.put(enemyName, enemyInfo = new Enemy());
            enemyInfo.name = enemyName;
        }
        
        enemyInfo.updateLastState();
        enemyInfo.ctime = (int)_robot.getTime();
        enemyInfo.speed = e.getVelocity();
        enemyInfo.bearing = _robot.getHeadingRadians() + e.getBearingRadians();
        enemyInfo.heading = e.getHeadingRadians();
        if (enemyInfo.speed != 0)
            enemyInfo.direction = GeometryUtils.sign(enemyInfo.speed * FastTrig.sin(enemyInfo.heading - enemyInfo.bearing));
        enemyInfo.distance = e.getDistance();
        enemyInfo.location = GeometryUtils.project(myLocation, enemyInfo.bearing, enemyInfo.distance);
        _target = enemyInfo;
		
		double radarOffset = PI * 2;
		double absBearing = e.getBearingRadians() + _robot.getHeadingRadians();
    	
        // The amount we need to rotate the radar by to scan where the target is now
        radarOffset = absBearing - _robot.getRadarHeadingRadians();

        _robot.setTurnRadarRightRadians(2 * Utils.normalRelativeAngle(radarOffset));
	}
	
	@Override
    public void onRobotDeath(RobotDeathEvent e) {
        Memory.enemies.remove(e.getName());
        if (_target.name.equals(e.getName())) {
        	_robot.setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
        }
    }
}
