package de.bwvaachen.beamoflightgame.helper;

public class Timer {
	private final long startTime;
	private long endTime;
	private final long timeout;
	private boolean timeOver;
	
	public Timer(final long timeout) {
		startTime = System.currentTimeMillis();
		this.timeout = timeout;
		endTime = startTime + timeout;
	}
	
	public long startTime() {
		return startTime;
	}
	
	public long endTime() {
		return endTime;
	}
	
	public long currentTime() {;
		return timeOver ? endTime() : System.currentTimeMillis();
	}
	
	public boolean timeOver() {
		return timeOver || (timeOver = (currentTime() >= endTime));
	}
	
	public long pastTime() {
		return timeOver() ? timeout : (currentTime() - startTime());
	}
	
	public int pastTimePercentage() {
		return timeOver() ? 100 : (int) Math.round((double)(pastTime()) * 100D / (double)timeout);
	}
	
	public void stop() {
		endTime = currentTime();
	}

}
