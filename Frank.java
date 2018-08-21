package unnamed;


import robocode.*;

import java.awt.Color;

import unnamed.Enemy;
import unnamed.gun.BaseGun;
import unnamed.gun.CircularGun;
import unnamed.gun.LinearGun;
import unnamed.radar.BaseRadar;
import unnamed.radar.TrackingRadar;
import unnamed.movement.BaseMovement;
import unnamed.movement.SittingDuck;


// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Frank - a robot by Nghia Truong
 */
public class Frank extends AdvancedRobot
{
	protected Enemy target;
    final double PI = Math.PI;
	
	protected static BaseGun _gun;
	protected static BaseMovement _movement;
	protected static BaseRadar _radar;
	
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
        	out.println("hoge");
//			_movement.doMove();
			_gun.shotToKill(target);
			_radar.doScan();
			execute();  // Execute all commands
        }
	}
	
	private void initRound() {
		// Make sure the turns of the robot, gun and radar are independant
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        
        _gun = new LinearGun(this);
//        _movement = new SittingDuck(this);
        _radar = new TrackingRadar(this, target);
	}
	
	int k = 0;
	public void onScannedRobot(ScannedRobotEvent e) {
		out.println(k++);
		if ((e.getDistance() < target.distance) || (target.name == e.getName())) {
            //the next line gets the absolute bearing to the point where the bot is
            double absbearing_rad = (getHeadingRadians()+e.getBearingRadians())%(2*PI);
            //this section sets all the information about our target
            target.name = e.getName();
            target.x = getX()+Math.sin(absbearing_rad)*e.getDistance(); //works out the x coordinate of where the target is
            target.y = getY()+Math.cos(absbearing_rad)*e.getDistance(); //works out the y coordinate of where the target is
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
