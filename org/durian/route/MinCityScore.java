package org.durian.route;

public class MinCityScore implements Constraint {

    public int ms;
    public int relax = 0;

    public MinCityScore( int i ) {
	super();
	ms = i;
    }
    
    public boolean check(Route r, Connection c) {
	if ( c.to.score < ms ) {
	    return false;
	}
	return true;
    }

    public void set_relax(int r) {
	relax = r;
    }
    
    public int get_relax() {
	return relax;
    }

}
