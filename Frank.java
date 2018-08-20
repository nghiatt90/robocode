package unnamed;


import robocode.*;

import java.awt.Color;

import unnamed.Enemy;
import unnamed.gun.BaseGun;
import unnamed.radar.BaseRadar;
import unnamed.movement.BaseMovement;


// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Frank - a robot by Nghia Truong
 */
public class Frank extends AdvancedRobot
{
	Enemy target;
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

        // Make sure the turns of the robot, gun and radar are independant
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
		
		// Get the first view of the battle field
        turnRadarRightRadians(2*PI);
		
		// Generic behavior: move, scan, turn the gun, fire
        while(true) {
			_movement.doMove();
			_gun.takeAim(target);
			setFire(_gun.calculatePower(target));
			_radar.doScan();
			execute();  // Execute all commands
        }
	}
}
