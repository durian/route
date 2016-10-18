package org.durian.route;

import java.lang.reflect.Method;

public class RouteConstraint {
    public String        f_name;
    public Integer       f_value;
    public String        f2_name;
    public static Class  r_class;
	
    /*
     * Constraint is applied before a new route is added - so it should return true if OK.
     */
    public RouteConstraint( String n, int v, String f ) {
	f_name  = n;
	f_value = new Integer( v );
	f2_name = f;
	try {
	    r_class = Class.forName( "org.durian.route.Route" );
	} catch ( Exception e ) {
	    e.printStackTrace();
	    System.exit(0);
	}	
    }
	
    public boolean apply( Route r ) {
	boolean res = false;
	try {			
	    Method m = r_class.getMethod( f_name, null );
	    Object o = m.invoke( r, null );
			
	    // We use this function on the result of the f_name.
	    // Assume f_name returns Integer, and f2_name takes one.
	    //
	    Method m1 = this.getClass().getMethod( f2_name, new Class[]{Integer.class} );
	    Object o1 = m1.invoke( this, new Object[]{o} );
			
	    if ( ((Integer)o1).intValue() > 0 ) {
		res = true;
	    }
	} catch ( Exception e ) {
	    e.printStackTrace();
	    System.exit(0);
	}
	return res;
    }
	
    public int less( Integer a ) {
	return this.f_value.compareTo( a );
    }
	
    public int more( Integer a ) {
	return -this.f_value.compareTo( a );
    }
	
}
