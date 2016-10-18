package org.durian.route;

import java.lang.reflect.Method;

public class GeneralFilter {
    public String        f_name;
    public Integer       f_value;
    public String        f2_name;
    public Class         r_class;

    /*
     * Filter is applied before a new route is added - so it should return true if OK.
     * 
     * Function n becomes an accessor for class c. Then function f is applied with
     * value v on the result of the first invokation.
     * 
     * GeneralFilter rf0 = new GeneralFilter( "org.durian.route.Route", "totalCost", 1000, "lt" );
     */
    public GeneralFilter( String c, String n, int v, String f ) {
	f_name  = n;
	f_value = new Integer( v );
	f2_name = f;
	try {
	    r_class = Class.forName( c );
	} catch ( Exception e ) {
	    e.printStackTrace();
	    System.exit(0);
	}	
    }
	
    /*
     * Have Route, Connection, City, ---?
     */
    public boolean apply( Object r ) {
	boolean res = false;
	try {			
	    Method m = r_class.getMethod( f_name, null ); //new Class[]{r_class} );
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
	
    public int lt( Integer a ) {
	return this.f_value.compareTo( a );
    }
	
    public int gt( Integer a ) {
	return -this.f_value.compareTo( a );
    }
	
    public int eq( Integer a ) {
	if ( this.f_value.compareTo( a ) == 0 ) {
	    return 1;
	}
	return -1;
    }
	
    public int ne( Integer a ) {
	if ( this.f_value.compareTo( a ) == 0 ) {
	    return -1;
	}
	return 1;
    }
}
