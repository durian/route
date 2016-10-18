package org.durian.route;

import java.util.Vector;

public class NoDirectReturn implements Constraint {

    public int relax = 0;

    public NoDirectReturn() {
	super();
	// TODO Auto-generated constructor stub
    }
    
    /*
     *  (non-Javadoc)
     * @see org.durian.route.Constraint#check(org.durian.route.Route, org.durian.route.Connection)
     * 
     * Return true if no direct return is present.
     */
    public boolean check( Route r, Connection c ) {
	
	Vector cons = r.connections;
	if ( cons.size() > 0 ) {
	    Connection c1 = ((Connection)cons.get( cons.size()-1 )).reverse();
	    if ( c1.equals( c )) {
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
