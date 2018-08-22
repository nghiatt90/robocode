package unnamed;


import java.awt.geom.*;

import robocode.*;
import robocode.util.*;

import unnamed.*;
import unnamed.utils.*;


public class Wave extends Condition {
	
	private double _velocity;
	private Point2D _center;
	private double _bearing;
	private double _lateralDirection;
	private double _distanceTraveled;
	private int[] _buffer;
	private Enemy _enemyInfo;
	
	private AdvancedRobot _robot;
 
	private static final double MAX_DISTANCE = 1000;
	private static final int DISTANCE_INDEXES = 5;
	private static final int VELOCITY_INDEXES = 5;
	private static final int BINS = 25;
	private static final int MIDDLE_BIN = (BINS - 1) / 2;
	private static final double MAX_ESCAPE_ANGLE = 0.7;
	private static final double BIN_WIDTH = MAX_ESCAPE_ANGLE / (double)MIDDLE_BIN;
 
	private static int[][][][] _statBuffers = new int[DISTANCE_INDEXES][VELOCITY_INDEXES][VELOCITY_INDEXES][BINS];
 
	public Wave(AdvancedRobot robot, Enemy enemyInfo, Point2D center, double firePower, double bearing, double lateralDirection) {
		_robot = robot;
		_enemyInfo = enemyInfo;
		_center = center;
		_velocity = GeometryUtils.bulletVelocity(firePower);
		_bearing = bearing;
		_lateralDirection = lateralDirection;
	}
 
	public boolean test() {
		_distanceTraveled += _velocity;
		_enemyInfo = Memory.enemies.get(_enemyName);
		if (_enemyInfo == null) {
			_robot.removeCustomEvent(this);
		}
		else if (_hasArrived()) {
			_buffer[_currentBin()]++;
			_robot.removeCustomEvent(this);
		}
		return false;
	}
 
	double mostVisitedBearingOffset() {
		return (_lateralDirection * BIN_WIDTH) * (_mostVisitedBin() - MIDDLE_BIN);
	}
 
	public void setSegmentations(double distance, double velocity, double lastVelocity) {
		int distanceIndex = (int)(distance / (MAX_DISTANCE / DISTANCE_INDEXES));
		int velocityIndex = (int)Math.abs(velocity / 2);
		int lastVelocityIndex = (int)Math.abs(lastVelocity / 2);
		_buffer = _statBuffers[distanceIndex][velocityIndex][lastVelocityIndex];
	}
 
	private boolean _hasArrived() {
		return _distanceTraveled > _center.distance(_enemyInfo.location) - 18;
	}
 
	private int _currentBin() {
		int bin = (int)Math.round(
						(Utils.normalRelativeAngle(GeometryUtils.absoluteAngle(_center, _enemyInfo.location) - _bearing))
						/ (_lateralDirection * BIN_WIDTH)
						+ MIDDLE_BIN);
		return GeometryUtils.boundedValue(bin, 0, BINS - 1);
	}
 
	private int _mostVisitedBin() {
		int mostVisited = MIDDLE_BIN;
		for (int i = 0; i < BINS; i++) {
			if (_buffer[i] > _buffer[mostVisited]) {
				mostVisited = i;
			}
		}
		return mostVisited;
	}	
}