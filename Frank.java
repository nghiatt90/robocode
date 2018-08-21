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
	
	// protected static HashMap<String, Queue<EnemyHistory>> _patterns;
	
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
			// _gun.shotToKill(target);
			_radar.onTick();
			execute();  // Execute all commands
			
			/*if (!isGunSwitched && getTime() > 500) {
				_gun = new PatternGun(this, _patterns);
				isGunSwitched = true;
			}*/
        }
	}
	
	private void initRound() {
		// Make sure the turns of the robot, gun and radar are independent
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        
        isGunSwitched = false;
        
        // _patterns = new HashMap<>();
        
        _gun = new LinearGun(this);
//        _movement = new SittingDuck(this);
        _radar = new MeleeRadar(this);
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		_radar.onScannedRobot(e);
	}
	
	public void onRobotDeath(RobotDeathEvent e) {
        if (e.getName() == target.name)
        	target.distance = 10000;
	}
}
