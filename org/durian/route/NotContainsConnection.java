package org.durian.route;

import java.util.Vector;

public class NotContainsConnection implements Constraint {
    
    public Connection con;
    public int relax;

    public NotContainsConnection( Connection c ) {
	super();
	con = c;
	relax = 0;
    }
    
    public boolean check( Route r, Connection co ) {
	Vector cons = r.connections;
	for ( int i = 0; i < cons.size(); i++ ) {
	    Connection c = (Connection)cons.get(i);
	    if ( c.equals( con ) ) {
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
