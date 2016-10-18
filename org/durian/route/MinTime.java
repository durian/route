package org.durian.route;

// END Constraint

public class MinTime implements Constraint {

    public int mt;
    public int relax = 0;

    public MinTime(int i) {
	super();
	mt = i;
    }
    
    public boolean check(Route r, Connection c) {
	
	/*if ( r == null ) { 
	  System.out.println( "null route?!" );
	  return false;
	  }*/
	
	if ( r.totalTime() < mt ) {
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
