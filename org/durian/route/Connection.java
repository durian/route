package org.durian.route;

public class Connection implements Comparable {

    public City from;
    public City to;
    public int means; //rail etc.
    public int distance;
    public int cost;
    public int time;
    public int score; // Nice route?
	
    public static final int BY_TRAIN  =   1;
    public static final int BY_PLANE  =   2;
    public static final int BY_FERRY  =   4;
    public static final int BY_CYCLE  =   8;
    public static final int BY_FOOT   =  16;
    public static final int BY_X2000  =  32;
    public static final int BY_CAR    =  64;
    public static final int BY_BUS    = 128;
	
    // --
	
    /*
     * Collections.sort( connections ).
     */
    public int compareTo( Object o)  {
	if ( ((Connection)this).cost < ((Connection)o).cost ) {
	    return -1;
	} else if ( ((Connection)this).cost > ((Connection)o).cost ) {
	    return 1;
	}
	return 0;
    }
	
    // --
	
    /*
     * Describes a connection between two places.
     * 
     */
    public Connection( City f, City t, int m, int d, int c, int mins ) {
	from     = f;
	to       = t;
	means    = m;
	distance = d;
	cost     = c;
	time     = mins;
	score    = 0;
    }

    public Connection( Holiday h, String c1, String c2 ) {
	from = h.getCityByName( c1 );
        to   = h.getCityByName( c2 );
	means = 0;
	distance = 0;
	cost = 0;
	time = 0;
	score = 0;
    }
	
    public Connection reverse() {
	Connection rev = new Connection( to, from, means, distance, cost, time );
	return rev;		
    }
	
    /*
     * Equal connection go to the same place, means is irrelevant.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object con) {
	if ( (from.equals(((Connection)con).from) ) && (to.equals(((Connection)con).to)) ) {
	    return true;
	}
	return false;
    }
	
	
    public String toString() {
	return from.name + "-(" + meansToString() + ")-" + to.name; 
    }
	
    public int getMeans() {
	return means;
    }
	
    public String meansToString() {		
		
	if ( means ==   0 ) { return ""; }
	if ( means ==   1 ) { return "BY_TRAIN"; }
	if ( means ==   2 ) { return "BY_PLANE"; }
	if ( means ==   4 ) { return "BY_FERRY"; }
	if ( means ==   8 ) { return "BY_CYCLE"; }
	if ( means ==  16 ) { return "BY_FOOT"; }
	if ( means ==  32 ) { return "BY_X2000"; }
	if ( means ==  64 ) { return "BY_CAR"; }
	if ( means == 128 ) { return "BY_BUS"; }
		
	return "UNKNOWN";
    }
}
