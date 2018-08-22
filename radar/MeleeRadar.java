package unnamed.radar;


import java.util.*;
import java.awt.geom.*;

import robocode.*;
import robocode.util.*;

import unnamed.*;
import unnamed.utils.*;

/*
 * Shamelessly stole from DrussGT. All credits to the original author Skilgannon.
 */
public class MeleeRadar extends BaseRadar {
    
    private Point2D.Double _myLocation;
    
    public MeleeRadar(AdvancedRobot robot) {
        super(robot);
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
        enemyInfo.location = GeometryUtils.project(_myLocation, enemyInfo.bearing, enemyInfo.distance);
        
        if (_robot.getOthers() <= Memory.enemies.size()) {
            Enumeration<Enemy> all = Memory.enemies.elements();
            int oldestScan = enemyInfo.ctime;
            while (all.hasMoreElements()) {
                Enemy tmp = all.nextElement();
                if (tmp.ctime < oldestScan) {
                    enemyInfo.bearing = GeometryUtils.absoluteAngle(_myLocation, tmp.location);
                    oldestScan = tmp.ctime;
                }
            }
            if (_robot.getOthers() == 1 && oldestScan == enemyInfo.ctime) {
                // 1 vs 1: Track the only remaining enemy
                double radarOffset = Utils.normalRelativeAngle(enemyInfo.bearing - _robot.getRadarHeadingRadians());
                // TODO: Investigate
                _robot.setTurnRadarRightRadians(Math.signum(radarOffset)
                                                * Math.min(Math.abs(radarOffset) + (Math.PI/4 - Math.PI/8 - Math.PI/18),
                                                           Math.PI/4));
            }
            else
                // Melee
                // TODO: Investigate
                _robot.setTurnRadarRightRadians(Utils.normalRelativeAngle(enemyInfo.bearing - _robot.getRadarHeadingRadians())
                                                * Double.POSITIVE_INFINITY);
        }
        else
            // Victory dance?
            _robot.setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
    }
    
    @Override
    public void onRobotDeath(RobotDeathEvent e){
        Memory.enemies.remove(e.getName());
    }
}