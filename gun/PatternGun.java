package unnamed.gun;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import robocode.AdvancedRobot;
import unnamed.Enemy;

public class PatternGun extends BaseGun {

	private LinkedHashMap<Integer, Enemy> _patterns;
	
	public PatternGun(AdvancedRobot robot) {
		super(robot);
		_patterns = new LinkedHashMap<Integer, Enemy>() {
			 @Override
		     protected boolean removeEldestEntry(Map.Entry<Integer, Enemy> eldest)
		     {
		        return this.size() > 10;
		     }
		};
	}

	@Override
	public void takeAim(Enemy e, double power) {
		// TODO Auto-generated method stub

	}

	@Override
	public double calculatePower(Enemy e) {
		// TODO Auto-generated method stub
		return 0;
	}

}
