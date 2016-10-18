package org.durian.route;

import java.util.Vector;

public class NoDoubleCity implements Constraint {

    public int relax = 0;

    public NoDoubleCity() {
	super();
    }
    
    public boolean check(Route r, Connection c) {
	
	Vector cons = r.connections;
	for ( int i = 0; i < cons.size(); i++ ) {
	    Connection c1 = (Connection)cons.get(i);
	    if ( (c1.to).equals(c.to) ) {
		return false;
	    }
	    if ( (c1.from).equals(c.to) ) {
		return false;
	    }
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
