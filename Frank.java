package unnamed;


import robocode.*;

import java.awt.Color;
import java.util.*;

import unnamed.*;
import unnamed.gun.*;
import unnamed.radar.*;
import unnamed.movement.*;


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
		
	public Frank() {
		_gun = new GuessFactorGun(this);
        _movement = new WallMovement(this);
        _radar = new TrackingRadar(this);
	}
	
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
        
        if (getOthers() > 1)
        	turnLeft(getHeading() % 90);
		
		// Generic behavior: move, scan, turn the gun, fire
        while(true) {
			_movement.onTick();
			_radar.onTick();
			execute();  // Execute all commands
        }
	}
	
	private void initRound() {
		// Make sure the turns of the robot, gun and radar are independent
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		_radar.onScannedRobot(e);
        _gun.onScannedRobot(e);
        _movement.onScannedRobot(e);
	}
	
	public void onRobotDeath(RobotDeathEvent e) {
        if (e.getName() == target.name)
        	target.distance = 10000;
	}
	
	public void onHitWall(HitWallEvent e) {
		setBack(28);
	}
}
