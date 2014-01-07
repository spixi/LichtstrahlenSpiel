package de.bwvaachen.beamoflightgame.helper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author Marius
 * Diese Klasse stellt mathematische Hilfemethoden bereit
 *
 */
public final class MathHelper {
	//Helper-Klasse soll nicht instanziert werden dürfen
	private MathHelper() {}
	
	//multipliziere zwei Vektoren
	public static int multiplicateVector(int[] v1, int[] v2) {
		int result = 0;
		for(int i=0; i<v1.length; i++)
			result+=(v1[i]*v2[i]);	
		return result;
	}
	
	//bilde die Summe aus mehreren Vektoren ... oder der Spaltenvektoren einer Matrix!
	public static int[] vectorSum(int[]... v) {
		return vectorSum(v.length, v);
	}
	
	//rekursive Hilfsfunktion für vectorSum
	private static int[] vectorSum(int size, int[][] v) {
		//size = 1, also haben wir bereits das Ergebnis
		if(size == 1) return v[0];
		//ansonsten müssen wir weiter addieren
		return addVector(vectorSum(size-1,v),v[size-1]);
	}
	
	//addiere zwei Vektoren
	public static int[] addVector(int[] v1, int[] v2) {
		int[] result = new int[v1.length];
		for(int i=0; i<v1.length; i++)
			result[i] = v1[i]+v2[i];	
		return result;
	}
	
	//subtrahiere zwei Vektoren
	public static int[] subtractVector(int[] v1, int[] v2) {
		int[] result = new int[v1.length];
		for(int i=0; i<v1.length; i++)
			result[i] = v1[i]-v2[i];	
		return result;
	}
	
	//transponiere eine Matrix
	public static int[][] transpondMatrix(int[][] m) {
		int[][] mt = new int[m[0].length][m.length];
		for(int i=0; i<m.length; i++) {
			for(int j=0; j<m[i].length; j++) {
				mt[i][j] = m[j][i];
			}
		}
		return mt;
	}
	
	//multipliziere zwei Matrizen
	public static int[][] multiplicateMatrix(int[][] m1, int[][] m2) {
		final AtomicInteger finishedThreads = new AtomicInteger(0);
		final int[][] m2t = transpondMatrix(m2);
		final int[][] result = new int[m1.length][m2[0].length];
		
		for(int i=0; i<m1.length; i++) {
			final int[] v1 = m1[i];
			final int[] vr = result[i];
			new Thread() {
				public void run() {
					for(int j=0; j<v1.length; j++) {
						vr[j] = multiplicateVector(v1,m2t[j]);
					}
					finishedThreads.incrementAndGet();
				}
			}.start();
	    }
		//wait for the threads
		while(finishedThreads.get() < m1[0].length)
			Thread.yield();
		return result;
	}
	
	//addiere zwei Matrizen
	public static int[][] addMatrix(int[][] m1, int[][] m2) {
		int[][] result = new int[m1.length][m2[0].length];
		for(int i=0; i<m1.length; i++) {
			for(int j=0; j<m1[i].length; i++)
				result[i][j] = m1[i][j] + m2[i][j]; 
		}
		return result;
	}
	
	//subtrahiere zwei Matrizen
	public static int[][] subtractMatrix(int[][] m1, int[][] m2) {
		int[][] result = new int[m1.length][m2[0].length];
		for(int i=0; i<m1.length; i++) {
			for(int j=0; j<m1[i].length; i++)
				result[i][j] = m1[i][j] - m2[i][j]; 
		}
		return result;
	}
	
	//syntaktischer Zucker zum Erstellen von Vektoren und Matrizen
	public static int[] V(int ... value) {
		return value;
	}
	
}
