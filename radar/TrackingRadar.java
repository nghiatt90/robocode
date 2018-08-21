package unnamed.radar;


import robocode.*;
import robocode.util.Utils;

import unnamed.Enemy;


public class TrackingRadar extends BaseRadar {
	
	private Enemy _target;
	private final double PI = Math.PI; 
	
	public TrackingRadar(AdvancedRobot robot, Enemy target) {
		super(robot);
		_target = target;
	}
	
	@Override
	public void doScan() {
		double radarOffset;
		radarOffset = PI * 2;
        if (_robot.getTime() - _target.ctime > 4) {
        	// Rotate radar in case target lost
        	_robot.out.println("Lost target");
            radarOffset = PI * 2;
            _robot.setTurnRadarRightRadians(radarOffset);
        } else {
        	double absBearing = _target.bearing + _robot.getHeadingRadians();
        	
            // The amount we need to rotate the radar by to scan where the target is now
            radarOffset = absBearing - _robot.getRadarHeadingRadians();

            // This adds or subtracts small amounts from the bearing for the radar
            // to produce the wobbling and make sure we don't lose the target
//            if (radarOffset < 0)
//            	radarOffset -= PI/8;
//            else
//            	radarOffset += PI/8;
            _robot.setTurnRadarRightRadians(1.9 * Utils.normalRelativeAngle(radarOffset));
        }
	}
}
