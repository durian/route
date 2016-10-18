package org.durian.route;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;

public class ConfigReader {

    public ConfigReader() {
	super();
    }
	
    public static void read( String fname, Holiday h, String fun_name ) {
	
	//Object o = m.invoke( r, null );
	System.out.println( "Reading: " + fname );
		
	try {
	    Class h_class = h.getClass();
	    Method m = h_class.getMethod( fun_name, new Class[]{Constraint.class} );
			
	    BufferedReader in = new BufferedReader( new FileReader(fname) );
	    String str;
	    while ((str = in.readLine()) != null) {
		if ( str.startsWith( "#" ) ) {
		    continue;
		}
		String parts[] = str.split(",");
		if ( parts.length == 1 ) {
		    System.out.println( "-> " + parts[0] );
		    Constraint c = ConstraintFactory.getConstraint( parts[0] );
		    //h.addConstraint( ConstraintFactory.getConstraint( parts[0] ));
		    m.invoke( (Object)h, new Object[]{c} );
		} // l == 1
		if ( parts.length == 2 ) {
		    System.out.println( "-> " + parts[0] + "," + parts[1] );
		    try {
			int i = Integer.parseInt( parts[1] );
			Constraint c = ConstraintFactory.getConstraint( parts[0], i );
			//h.addConstraint( ConstraintFactory.getConstraint( parts[0], i ));
			m.invoke( (Object)h, new Object[]{c} );
		    } catch ( NumberFormatException e  ) {
			// City? Connection...?
			City ct = h.getCityByName( parts[1] );
			if ( ct != null ) {
			    Constraint c = ConstraintFactory.getConstraint( parts[0], ct );
			    //h.addConstraint( ConstraintFactory.getConstraint( parts[0], c ));
			    m.invoke( (Object)h, new Object[]{c} );
			}
		    }
		} // l == 2
		if ( parts.length == 3 ) {
		    // to be implemented
		} // l == 3
	    }			
	}
	catch ( Exception e ) {
	    e.printStackTrace();
	}
    }

}
