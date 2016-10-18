package org.durian.route;

import java.util.Vector;

public class MaximumSize implements Constraint {

    public int ms;
    public int relax = 0;
    
    public MaximumSize(int i) {
	super();
	ms = i;
    }
    
    public boolean check(Route r, Connection c) {
	
	Vector cons = r.connections;
	if ( cons.size() >= ms ) {
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
