package org.durian.route;

import java.util.Vector;

public class AllRoutesSolver implements Solver {

    public Vector routes;
    public Vector good_routes;
	
    public AllRoutesSolver() {
	super();
    }

    public void solve(Holiday h, Route r) {
	routes      = new Vector();
	good_routes = new Vector();
		
	routes.add( r );
		
	// If we want to end up wehre we started, we need to put the
	// first connexions in the route, otherwise we are ready without
	// trying anything else.
	//
	if ( r.to.equals(r.from) ) {
	    Vector v = r.possibleNext(); // Filter/score, not take all?
	    for ( int j = 0; j < v.size(); j++ ) {
		Connection con = (Connection)v.get( j );
		Route r1 = new Route( r ); // Check constraints?
		r1.addConnection( con );
		routes.add( r1 );
	    }
	}
		
	boolean ready = false;
	int generated = 0;
	int nonfiltered = 0;
		
	Vector constraints = h.constraints;
	Vector end_constraints = h.end_constraints;

	Vector relax_constraints = new Vector();
	for ( int c = 0; c < constraints.size(); c++ ) {
	    Constraint cnstr = (Constraint)constraints.get( c );
	    if ( cnstr.get_relax() > 0 ) {
		relax_constraints.add( cnstr );
	    }
	}

	long t_start = System.currentTimeMillis();
	Vector keep = new Vector();
		
	while ( ready == false ) {
			
	    int rs = routes.size();
	    
	    System.out.println( "Routes: " + rs ); 
	    
	    City c2 = r.to;
			
	    int added = 0;
			
	    /*
	     * Loop over all Routes, check if ready, add new ones, etc.
	     */
	    Vector blah = new Vector();
	    for ( int i = 0; i < rs; i++ ) {
					
		if ( System.currentTimeMillis()-t_start > 20000 ) {
		    System.out.println( "Out of time." );
		    ready = true;
		    break;
		}
				
		r = (Route)routes.get( i );
		
		// Check if Route is ready.
		//
		if ( r.current == c2 ) { // NB: no constraint checked here!
					
		    // Should check constraints here too, like MinScore,
		    // ContainsRoute/Conn, etc.
		    //
		    boolean all_passed = true;
		    for ( int cn = 0; cn < end_constraints.size(); cn++ ) {
			Constraint cnstr = (Constraint)end_constraints.get( cn );
			if ( cnstr.check( r, null ) == false ) { // must all be true
			    all_passed = false;
			    break;
			}
		    }

		    if ( all_passed == true ) {
			good_routes.add( r );
			System.out.println( "GOOD: " + r );
		    }
		    continue;
		}
								
		Vector v = r.possibleNext(); // Filter/score, not take all?
		generated += v.size();
		int added_for_route = 0;

		for ( int j = 0; j < v.size(); j++ ) {
		    Connection con = (Connection)v.get( j );
		    //System.out.println( "Check: " + con );

		    // Check if the contraint is true or not.
		    //
		    boolean c_failed = false;
		    for ( int cn = 0; cn < constraints.size(); cn++ ) {
			Constraint cnstr = (Constraint)constraints.get( cn );
			if ( cnstr.check( r, con ) == false ) {
			    c_failed = true;
			    //System.out.println( "failed: " + cnstr );
			    break;
			}
		    }
		    if ( c_failed == true ) {
			continue;
		    }
					
		    Route r1 = new Route( r );
		    r1.addConnection( con );				
		    keep.add( r1 );
		    ++added_for_route;
		    ++added;
		    nonfiltered++;
					
		}// j


		// RELAX
		// use relaxed contraint, put in list. after loop, subtract
		// one from relax factor.
		//
		if ( added_for_route == 0 ) {

		    for ( int j = 0; j < v.size(); j++ ) {
			Connection con = (Connection)v.get( j );
			//System.out.println( "R_Check: " + con );
			
			// Check if the contraint is true or not.
			//
			boolean c_failed = false;
			for ( int cn = 0; cn < constraints.size(); cn++ ) {
			    Constraint cnstr = (Constraint)constraints.get( cn );
			    if ( cnstr.check( r, con ) == false ) {
				// Try again, with relax.
				//
				if ( cnstr.get_relax() == 0 ) {
				    c_failed = true;
				    //System.out.println( "failed: " + cnstr );
				    break;
				} else {
				    System.out.println( "RELAXED: " + cnstr );
				    System.out.println( "     ON: " + con );
				    
				    if ( ! blah.contains( cnstr )) {
					blah.add( cnstr );
				    }
				}
			    }
			}
			if ( c_failed == true ) {
			    continue;
			}
			
			Route r1 = new Route( r );
			r1.addConnection( con );				
			keep.add( r1 );
			++added;
			nonfiltered++;
			
		    }// j
		    //
		    // RELAX
		}

		//if 0, check which contraint caused this? relax it, try again?
		// For each contraint, have a "relax" flag? (like 
		// for continuesamemeans)?
		//
		// Penalties, like a -1 when changing means of travel?
		
	    }// i

	    // Adjust relax counters.
	    //
	    for ( int cn = 0; cn < blah.size(); cn++ ) {
		Constraint cnstr = (Constraint)blah.get( cn );
		if ( cnstr.get_relax() > 0 ) {
		    System.out.println( cnstr + ":" + cnstr.get_relax() );
		    cnstr.set_relax( cnstr.get_relax()-1 );
		}
	    }
	    blah.clear();
	    
	    System.out.println( "Added: " + added );
	    routes = keep;
	    keep = new Vector();
	    System.out.println( "-- " + generated + "," + nonfiltered );
			
	    if ( added == 0 ) { ready = true; }
	}
	long t_end = System.currentTimeMillis();		
	System.out.println( "ms: " + (t_end - t_start) );
    }

    public Vector getResults() {
	return good_routes;
    }
	
}
