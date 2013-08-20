package org.tsutsurupa.dice.model;

import java.util.Random;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

import org.tsutsurupa.obs.*;

public final class Dice implements Subject {

	public static final int DEFAULT_NUMBER_OF_STATES = 6;
	
	private static final Random gen = new Random();

	private List<Observer> _observers = new LinkedList<Observer>();
	private Statistics _stats;
	private int _status;
	private int _states;

	public Dice() {
		this(DEFAULT_NUMBER_OF_STATES);
	}

	public Dice(int states) {
		if (states < 0)
			throw new IllegalArgumentException();
		_states = states;
		_stats = this.new Statistics();
	}

	public int getStatus() {
		return _status;
	}
	
	public int getStates() {
		return _states;
	}
	
	public void roll() {
		roll(1);
	}
	
	public void roll(int times) {
		for (int i = 0; i < times; i++) {
			_status = gen.nextInt(_states) + 1;
			updateStats();
		}
		notifyObs();
	}
	
	public void reset() {
		_stats.reset();
		notifyObs();
	}
	
	public void updateStats() {
		_stats.update();
	}
	
	public int valueOf(int point) {
		if (point < 1 || point > _states) {
			System.out.println(point);
			throw new IllegalArgumentException();
		}
		return _stats.valueOf(point);
	}
	
	public float getPercent(int point)  {
		return format(valueOf(point) * 100f / getTotal());
	}
	
	public int getTotal() {
		return _stats.getTotal();
	}
	
	public int[] getPointsStats() {
		return _stats.getPointsStats();
	}
	
	public float[] getPointsPercentage() {
		return _stats.getPercentage();
	}
	
	public void attachObs(Observer obs) {
		_observers.add(obs);
	}
	
	public void detachObs(Observer obs) {
		_observers.remove(obs);
	}
	
	public void notifyObs() {
		for (Observer obs : _observers) {
			obs.update();
		}
	}
	
	private float format(float val) {
		return ((int) (val * 1000)) / 1000f;
	}
	
	private class Statistics {
	
		private int _total = 0;
		private int[] _points = new int[getStates()];
		
		int getTotal() {
			return _total;
		}
		
		void update() {
			_total++;
			_points[getStatus() - 1]++;
		}
		
		void reset() {
			_total = 0;
			for (int i = 0; i < _points.length; i++) {
				_points[i] = 0;
			}
		}

		int valueOf(int point) {
			return _points[point - 1];
		}
		
		int[] getPointsStats() {
			int[] result = new int[_points.length];
			System.arraycopy(_points, 0, result, 0, result.length);
			return result;
		}
		
		float[] getPercentage() {
			float[] result = new float[_points.length];
			for (int i = 0; i < result.length; i++) {
				result[i] = getPercent(i + 1);
			}
			return result;
		}
		
	}

}