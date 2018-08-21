package unnamed;


import robocode.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import unnamed.*;
import unnamed.gun.BaseGun;
import unnamed.gun.CircularGun;
import unnamed.gun.LinearGun;
import unnamed.gun.PatternGun;
import unnamed.radar.BaseRadar;
import unnamed.radar.TrackingRadar;
import unnamed.movement.BaseMovement;


// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Frank - a robot by Nghia Truong
 */
public class Frank extends AdvancedRobot
{
	protected Enemy target;
	protected boolean isGunSwitched;
    final double PI = Math.PI;
	
	protected static BaseGun _gun;
	protected static BaseMovement _movement;
	protected static BaseRadar _radar;
	
	protected static HashMap<String, Queue<EnemyHistory>> _patterns;
	
	/**
	 * run: Frank's default behavior
	 */
	public void run() {
		target = new Enemy();
        target.distance = 100000;
		
		// Set robot colors: body, gun, radar
        setColors(Color.black, Color.black, Color.white);
        
        // Initiation
        initRound();
        
        // Get the first view of the battle field
        turnRadarRightRadians(2*PI);
		
		// Generic behavior: move, scan, turn the gun, fire
        while(true) {
//			_movement.doMove();
			_gun.shotToKill(target);
			_radar.doScan();
			execute();  // Execute all commands
			
			if (!isGunSwitched && getTime() > 500) {
				_gun = new PatternGun(this, _patterns);
				isGunSwitched = true;
			}
        }
	}
	
	private void initRound() {
		// Make sure the turns of the robot, gun and radar are independent
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        
        isGunSwitched = false;
        
        _patterns = new HashMap<>();
        
        _gun = new CircularGun(this);
//        _movement = new SittingDuck(this);
        _radar = new TrackingRadar(this, target);
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		String name = e.getName();
		double bearing = e.getBearingRadians();
        double heading = e.getHeadingRadians();
		
		if (!_patterns.containsKey(name)) {
			_patterns.put(name, new LinkedList<EnemyHistory>() {
				public boolean add(EnemyHistory object) {
	                boolean result;
	                if(this.size() < 500)
	                    result = super.add(object);
	                else
	                {
	                    super.removeFirst();
	                    result = super.add(object);
	                }
	                return result;
	            }
			});
		}
		_patterns.get(name).add(new EnemyHistory(name, bearing, heading));
		
		if ((e.getDistance() < target.distance) || (target.name.equals(name))) {
            //the next line gets the absolute bearing to the point where the bot is
            double absbearing_rad = (getHeadingRadians()+e.getBearingRadians())%(2*PI);
            //this section sets all the information about our target
            target.name = name;
            target.x = getX()+Math.sin(absbearing_rad)*e.getDistance(); //works out the x coordinate of where the target is
            target.y = getY()+Math.cos(absbearing_rad)*e.getDistance(); //works out the y coordinate of where the target is
            target.lastHeading = target.heading;
            target.lastBearing = target.bearing;
            target.bearing = e.getBearingRadians();
            target.heading = e.getHeadingRadians();
            target.ctime = getTime();				//game time at which this scan was produced
            target.speed = e.getVelocity();
            target.distance = e.getDistance();
		}
	}
	
	public void onRobotDeath(RobotDeathEvent e) {
        if (e.getName() == target.name)
        	target.distance = 10000;
	}
}
