package org.durian.route;

import java.util.Vector;

public class MaxPriceSolver implements Solver {

	public Vector routes;
	public Vector good_routes;
	
	public MaxPriceSolver() {
		super();
		routes = new Vector(); // Maybe ad initial route here...?
		good_routes = new Vector();
	}
	
	public void solve(Holiday h, Route r) {
		routes.add( r );// fout, needs all of 'm?
		//Vector first_leg = r.possibleNext();
		//System.out.println( "Routes: " + first_leg.size() ); 
		//routes.addAll( first_leg );
		City c2 = r.to; // Endpoint. // Also a filter on city?
		
		Vector constraints = new Vector();
		
		boolean ready = false;
		
		//RouteFilter rf0 = new RouteFilter( "totalCost", 1000, "less" );
		GeneralFilter rf0 = new GeneralFilter( "org.durian.route.Route", "totalCost", 1000, "lt" );
		GeneralFilter rf1 = new GeneralFilter( "org.durian.route.Route", "totalTime", 440, "lt" );
		GeneralFilter rf2 = new GeneralFilter( "org.durian.route.Route", "size", 5, "lt" );
		// No car:
		GeneralFilter rf3 = new GeneralFilter( "org.durian.route.Connection", "getMeans", 10, "ne" );
		//GeneralFilter rf4 = new GeneralFilter( "org.durian.route.Connection", "countConnections", 0, "equals" );
		
		Constraint ndr = new NoDirectReturn();
		constraints.add( ndr );
		//Constraint ndc = new NoDoubleCity();
		//constraints.add( ndc );
		Constraint msc = new MaximumSize(2);
		constraints.add( msc );
		
		long t_start = System.currentTimeMillis();
		
		while ( ready == false ) {
			
			int rs = routes.size();
			
			System.out.println( "Routes: " + rs ); 
			
			int added = 0;
			Vector garbage = new Vector();
			
			/*
			 * Loop over all Routes, check if ready, add new ones, etc.
			 */
			for ( int i = 0; i < rs; i++ ) {
				r = (Route)routes.get( i );
				System.out.println( r.toString());
				
				// Check if Route is ready.
				//
				if ( r.current == c2 ) {
					//System.out.println( route_count++ + ": " + r.toString() );
					good_routes.add( r );
					garbage.add( r );
					continue;
					//ready = true;
				}
								
				Vector v = r.current.possibleNext();
				
				for ( int j = 0; j < v.size(); j++ ) {
					Connection con = (Connection)v.get( j );
					
					// Check if the NoDirectReturn contraint is true or not.
					//
					boolean c_failed = false;
					for ( int cn = 0; cn < constraints.size(); cn++ ) {
						Constraint cnstr = (Constraint)constraints.get( cn );
						if ( cnstr.check( r, con ) == false ) {
							c_failed = true;
							break;
						}
					}
					if ( c_failed == true ) {
						continue;
					}
					
					Route r1 = new Route( r );
					r1.addConnection( con );
					
					// Other constrains, like NotBack(r1, con). Add these to list,let intro-
					// spection find out the arguments?
					
					// IN apply, take r, con, city, etc?
					
					if ( rf0.apply(r1) && rf1.apply(r1) && rf2.apply(r1) && rf3.apply(con) ) {
						//System.out.println( "added " + c.toString() );
						routes.add( r1 );
						++added;
						garbage.add( r );
					}
					
				}// j
			}// i
			
			System.out.println( "Deleting " + garbage.size() + " routes." );
			routes.removeAll( garbage );
			
			System.out.println( "--" );
			
			if ( added == 0 ) { ready = true; }
		}
		long t_end = System.currentTimeMillis();
		
		for ( int i = 0; i < good_routes.size(); i++ ) {
			r = (Route)good_routes.get( i );
			System.out.println( i + "> " + r.toString() );
		}
		
		System.out.println( "ms: " + (t_end - t_start) );
		
	}
	
	public Vector getResults() {
		return good_routes;
	}

}
