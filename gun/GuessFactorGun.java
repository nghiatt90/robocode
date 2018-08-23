package unnamed.gun;


import java.awt.geom.*;

import robocode.*;
import robocode.util.*;

import unnamed.*;


public class GuessFactorGun extends BaseGun {
    
    public GuessFactorGun(AdvancedRobot robot) {
        super(robot);
    }
    
    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        Enemy enemyInfo = Memory.enemies.get(e.getName());
        if (enemyInfo == null) {
            // Should not happen
            return;
        }
        
        Point2D myLocation = new Point2D.Double(_robot.getX(), _robot.getY());
        double firingPower = _robot.getOthers() > 3 ? 3 : Math.min(400 / e.getDistance(), _robot.getEnergy() / 20);
        Wave wave = new Wave(_robot, enemyInfo, myLocation, firingPower);
        wave.setSegmentations(enemyInfo.distance, enemyInfo.speed, enemyInfo.lastState.speed);
        
        _robot.setTurnGunRightRadians(Utils.normalRelativeAngle(
        		enemyInfo.bearing
        		- _robot.getGunHeadingRadians()
        		+ wave.mostVisitedBearingOffset())
        );
        
        _robot.setFire(firingPower);
        if (_robot.getEnergy() >= 3.0) {
			_robot.addCustomEvent(wave);
		}
    }
}