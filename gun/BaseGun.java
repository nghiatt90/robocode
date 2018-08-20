package unnamed.gun;

import robocode.*;

import unnamed.Enemy;


public abstract class BaseGun {
	private double _firingAngle;
	
	public BaseGun() {
		_firingAngle = 0.0;
	}
	
	abstract public void takeAim(Enemy e);
	abstract public double calculatePower(Enemy e);
}