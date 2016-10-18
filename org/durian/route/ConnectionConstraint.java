package org.durian.route;

/*
 * Similar to RouteContraint...make general Contraint? inherit?
 */
public class ConnectionConstraint {
    public String        f_name;
    public Integer       f_value;
    public String        f2_name;
    public static Class  r_class;
    
    public ConnectionConstraint( String n, int v, String f ) {
	f_name  = n;
	f_value = new Integer( v );
	f2_name = f;
	try {
	    r_class = Class.forName( "org.durian.route.Connection" ); // parameter?
	} catch ( Exception e ) {
	    e.printStackTrace();
	    System.exit(0);
	}	
    }
    
}
