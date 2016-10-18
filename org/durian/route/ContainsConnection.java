package org.durian.route;

import java.util.Vector;

public class ContainsConnection implements Constraint {
    
    public Connection con;
    public int relax;

    public ContainsConnection( Connection c ) {
	super();
	con = c;
	relax = 0;
    }
    
    public boolean check( Route r, Connection co ) {
	Vector cons = r.connections;
	for ( int i = 0; i < cons.size(); i++ ) {
	    Connection c = (Connection)cons.get(i);
	    if ( c.equals( con ) ) {
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
