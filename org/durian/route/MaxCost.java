package org.durian.route;

public class MaxCost implements Constraint {

    public int mc;
    public int relax = 0;

    public MaxCost(int i) {
	super();
	mc = i;
    }
    
    public boolean check(Route r, Connection c) {
		
	if ( r.totalCost() + c.cost > mc ) {
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
