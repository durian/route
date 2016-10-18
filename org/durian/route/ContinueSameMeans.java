package org.durian.route;

import java.util.Vector;

public class ContinueSameMeans implements Constraint {

    public int relax = 0;

    public ContinueSameMeans() {
	super();
    }
    
    /*
     *  (non-Javadoc)
     * @see org.durian.route.Constraint#check(org.durian.route.Route, org.durian.route.Connection)
     * 
     * Returns true if we continue travelling with the same means.
     */
    public boolean check(Route r, Connection c) {
	Vector cons = r.connections;
	if ( cons.size() > 0 ) {
	    Connection c1 = ((Connection)cons.get( cons.size()-1 ));
	    if ( c1.means != c.means ) {
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
