package org.durian.route;

import java.util.Vector;

public class VisitsCity implements Constraint {

    public City city;
    public int relax = 0;
    
    public VisitsCity( City c ) {
	super();
	city = c;
    }
    
    public boolean check( Route r, Connection co ) {
	Vector cons = r.connections;
	for ( int i = 0; i < cons.size(); i++ ) {
	    Connection c = (Connection)cons.get(i);
	    if ( c.to.equals( city ) ) {
		return true;
	    }
	}
	return false;
    }

    public void set_relax(int r) {
	relax = r;
    }
    
    public int get_relax() {
	return relax;
    }

}
