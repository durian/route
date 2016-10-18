package org.durian.route;

public class AllowedMeans implements Constraint {
    
    public int am;
    public int relax;
    
    public AllowedMeans(int i) {
	super();
	am = i;
	relax = 0;
    }
    
    public boolean check(Route r, Connection c) {
	
	if ( (c.means & am) == 0  ) { // c.means not allowed
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
