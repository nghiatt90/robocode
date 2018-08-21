package unnamed.gun;

import java.util.HashMap;
import java.util.Queue;

import robocode.AdvancedRobot;
import unnamed.Enemy;
import unnamed.EnemyHistory;

public class PatternGun extends BaseGun {

	protected static HashMap<String, Queue<EnemyHistory>> _patterns;
	
	public PatternGun(AdvancedRobot robot, HashMap<String, Queue<EnemyHistory>> patterns) {
		super(robot);
		_patterns = patterns;
	}

	@Override
	public void takeAim(Enemy e, double power) {
		EnemyHistory[] history = (EnemyHistory[]) _patterns.get(e.name).toArray();
		int size = history.length;
		
		for (int i = 0; i < size; ++i) {
			
		}
	}

	@Override
	public double calculatePower(Enemy e) {
		// TODO Auto-generated method stub
		return 0;
	}

}
