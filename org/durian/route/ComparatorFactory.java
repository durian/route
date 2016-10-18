package org.durian.route;

import java.util.Comparator;

public class ComparatorFactory {

	public static final int CMP_CHEAPEST       =   1;
	public static final int CMP_SHORTEST_TIME  =   2;
	public static final int CMP_SHORTEST_DIST  =   4;
	public static final int CMP_LEAST_CONX     =   8;
	public static final int CMP_BEST_SCORE     =  16;
	
	public ComparatorFactory() {
		super();
	}

	public static Comparator getComparator( int i ) {
		
		if ( i == CMP_CHEAPEST ) {
			return new Comparator() { // Cheapest
				public int compare( Object r0, Object r1 ) {		
					if ( ((Route)r0).totalCost() < ((Route)r1).totalCost() ) {
						return -1;
					} else if ( ((Route)r0).totalCost() > ((Route)r1).totalCost() ) {
						return 1;
					}
					return 0;
				}
			};
		}
		if ( i == CMP_SHORTEST_TIME ) {
			return new Comparator() { // Shortest time
				public int compare( Object r0, Object r1 ) {		
					if ( ((Route)r0).totalTime() < ((Route)r1).totalTime() ) {
						return -1;
					} else if ( ((Route)r0).totalTime() > ((Route)r1).totalTime() ) {
						return 1;
					}
					return 0;
				}
			};
		}
		if ( i == CMP_LEAST_CONX ) {
			return new Comparator() { // Least connexions
				public int compare( Object r0, Object r1 ) {		
					if ( ((Route)r0).size() < ((Route)r1).size() ) {
						return -1;
					} else if ( ((Route)r0).size() > ((Route)r1).size() ) {
						return 1;
					}
					return 0;
				}
			};
		}
		if ( i == CMP_SHORTEST_DIST ) {
			return new Comparator() { // Shortest distance
				public int compare( Object r0, Object r1 ) {		
					if ( ((Route)r0).totalDistance() < ((Route)r1).totalDistance() ) {
						return -1;
					} else if ( ((Route)r0).totalDistance() > ((Route)r1).totalDistance() ) {
						return 1;
					}
					return 0;
				}
			};
		}
		//if ( i == CMP_BEST_SCORE ) {
		return new Comparator() { // Score, NB sort reversed.
			public int compare( Object r0, Object r1 ) {		
				if ( ((Route)r0).totalCityScore() < ((Route)r1).totalCityScore() ) {
					return 1;
				} else if ( ((Route)r0).totalCityScore() > ((Route)r1).totalCityScore() ) {
					return -1;
				}
				return 0;
			}
		};
		//}
	}

	public static int cmpNameToInt( String n ) {
		if ( n.equals( "cheapest" )) {
			return CMP_CHEAPEST;
		}
		if ( n.equals( "time" )) {
			return CMP_SHORTEST_TIME;
		}
		if ( n.equals( "dist" )) {
			return CMP_SHORTEST_DIST;
		}
		if ( n.equals( "conx" )) {
			return CMP_LEAST_CONX;
		}
		if ( n.equals( "best" )) {
			return CMP_BEST_SCORE;
		}
		return CMP_CHEAPEST;
	}
	
}
