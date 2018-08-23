package unnamed.gun;

import java.util.HashMap;
import java.util.Queue;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import unnamed.Enemy;
import unnamed.EnemyHistory;

public class PatternGun extends BaseGun {

	protected static HashMap<String, Queue<EnemyHistory>> _patterns;
	
	public PatternGun(AdvancedRobot robot, HashMap<String, Queue<EnemyHistory>> patterns) {
		super(robot);
		_patterns = patterns;
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		// TODO Auto-generated method stub
		
	}

}
