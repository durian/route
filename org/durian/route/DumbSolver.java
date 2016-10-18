package org.durian.route;

import java.util.Vector;

public class DumbSolver implements Solver {

	public Vector routes;
	
	public DumbSolver() {
		super();
		// TODO Auto-generated constructor stub
		routes = new Vector();
	}

	public void solve(Holiday h, Route r) {
		
		routes.add( r );
		
		City c2 = r.to;
				
		boolean ready = false;
		
		while ( ready == false ) {
			
			int rs = routes.size();
			System.out.println( "Routes: " + rs ); 
					
			int added = 0;
			Vector garbage = new Vector();
			for ( int i = 0; i < rs; i++ ) {
				r = (Route)routes.get( i );
				
				//System.out.println( r.toString() );
				
				// Check if Route is ready.
				//
				if ( r.current.equals(c2) ) {
					//System.out.println( "Ready" );
					//System.out.println( route_count++ + ": " + r.toString() );
					//ready = true;
					if ( h.citiesLeft(r).size() == 0 ) {
						System.out.println( r.toString() );
						ready = true;
					}
				}
				
				Vector v = r.possibleNext();
				
				//if ( v.size() == 0 ) { ready = true; }
				for ( int j = 0; j < v.size(); j++ ) {
					Connection con = (Connection)v.get( j );
					City c = con.to;
					
					if ( r.from.equals(con.to) ) {// don't go back to starting city
						continue;
					}
					
					int visits = r.getCityVisits( c );
					//System.out.println( "to = " + c.toString() + "/" + visits );
					if ( visits > 0 ) {//= c.max_visits ) {
						continue;
					}
					Route r1 = new Route( r );
					if ( r1.countConnections( con ) == 0 ) {
						Connection rc = con.reverse();
						if ( r1.countConnections( rc ) == 0 ) { // no back
							r1.addConnection( con );
							//System.out.println( "added " + c.toString() );
							routes.add( r1 );
							++added;
							r.status = 1;
							
							// Remove r from routes?
							//
							garbage.add( r );
						}
					}
				}
				
			}
			routes.removeAll( garbage );
			
			System.out.println( "--" );
			
			if ( added == 0 ) { ready = true; }
		}
		
	}
	
	public Vector getResults() {
		return routes;
	}

}
