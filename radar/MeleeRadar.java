package unnamed.radar;


import java.util.*;
import java.awt.geom.*;

import robocode.*;
import robocode.util.*;

import unnamed.Enemy;
import unnamed.utils.*;

/*
 * Shamelessly stole from DrussGT. All credits to the original author Skilgannon.
 */
public class MeleeRadar extends BaseRadar {
	
	private Hashtable<String, Enemy> _enemies = new Hashtable<String, Enemy>();
    private Point2D.Double _myLocation;
	
    public MeleeRadar(AdvancedRobot robot) {
        super(robot);
        _enemies.clear();
    }
	
	/*
	 * What the radar does every tick, if no other event occured.
	 */
	@Override
	public void onTick() {
        if (_robot.getRadarTurnRemaining() == 0)
			// If radar stopped for some reason,
			// turn it around to find new target
            _robot.setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
        
		// Track my own location
        _myLocation =  new Point2D.Double(_robot.getX(), _robot.getY());   
    }
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {   
        String enemyName = e.getName();
                            
        Enemy enemyInfo = _enemies.get(enemyName);
        if (enemyInfo == null) {
            _enemies.put(enemyName, enemyInfo = new Enemy());
            enemyInfo.name = enemyName;
        }
        
        enemyInfo.ctime = (int)_robot.getTime();
        double absoluteBearing  = _robot.getHeadingRadians() + e.getBearingRadians();
        enemyInfo.location = GeometryUtils.project(_myLocation, absoluteBearing, e.getDistance());
            
        if (_robot.getOthers() <= _enemies.size()) {
            Enumeration<Enemy> all = _enemies.elements();
            int oldestScan = enemyInfo.ctime;
            while (all.hasMoreElements()) {
                Enemy tmp = all.nextElement();
                if (tmp.ctime < oldestScan) {
					absoluteBearing = GeometryUtils.absoluteAngle(_myLocation, tmp.location);
					oldestScan = tmp.ctime;
                }
            }
            if (_robot.getOthers() == 1 && oldestScan == enemyInfo.ctime) {
				// 1 vs 1: Track the only remaining enemy
                double angle = Utils.normalRelativeAngle(absoluteBearing - _robot.getRadarHeadingRadians());
				// TODO: Investigate
                _robot.setTurnRadarRightRadians(Math.signum(angle)*Math.min(Math.abs(angle) + (Math.PI/4 - Math.PI/8 - Math.PI/18), Math.PI/4));
            }
            else
				// Melee
				// TODO: Investigate
                _robot.setTurnRadarRightRadians(Utils.normalRelativeAngle(absoluteBearing - _robot.getRadarHeadingRadians())*Double.POSITIVE_INFINITY);
        }
        else
			// Victory dance?
            _robot.setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
    }
	
	@Override
	public void onRobotDeath(RobotDeathEvent e){
        _enemies.remove(e.getName());
    }
}