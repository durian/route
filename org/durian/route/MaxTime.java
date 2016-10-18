package org.durian.route;

public class MaxTime implements Constraint {

    public int mt;
    public int relax = 0;

    public MaxTime(int i) {
	super();
	mt = i;
    }
    
    public boolean check(Route r, Connection c) {
	
	if ( r.totalTime() + c.time > mt ) {
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
