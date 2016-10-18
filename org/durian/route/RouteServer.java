package org.durian.route;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.management.MemoryUsage;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class RouteServer extends Thread {

	private Socket s;
	private final static int port = 2400;
	private static Holiday h;
	
	public RouteServer( Socket sock ) {
		super();
		s = sock;
		start();
	}

	public void run() {
		try {
			BufferedReader inp = new BufferedReader( new InputStreamReader(s.getInputStream()));
			PrintWriter pw = new PrintWriter( s.getOutputStream(), true ); 
			
			// ROUTE,hunserod,goteborg
			// ENDCONSTRAINT ... &c
			String str;
			String parts[] = null;
			int max_res = 10;
			Comparator cmp = ComparatorFactory.getComparator( ComparatorFactory.CMP_CHEAPEST );
			
			while ( (str = (String)inp.readLine()) != null  ) {
				parts = str.split(",");
				if (str.startsWith( "SOLVE" )) {
					break;
				}
				
				if ( str.startsWith( "QUIT" )) {
					System.exit( 0 );
				}
				
				if ( str.startsWith( "ADDC" )) { // Add Constraint
					if ( parts.length == 2 ) {
						Constraint c = ConstraintFactory.getConstraint( parts[1] );
						h.addConstraint( c );
						System.out.println( "ADDC ok." );
					}
				}
				if ( str.startsWith( "REMC" )) { // Remove Constraint
					if ( parts.length == 2 ) {
						Constraint c = ConstraintFactory.getConstraint( parts[1] );
						h.removeConstraint( c );
						System.out.println( "REMC ok." );
					}
				}
				// RTYP, results type, cheapest 6c?
				if ( str.startsWith( "RTYPE" )) { // Result Type (Comparator)
					if ( parts.length == 2 ) {
						cmp = ComparatorFactory.getComparator( ComparatorFactory.cmpNameToInt( parts[1] ));
						System.out.println( "RTYPE ok." );
					}
				}
				if ( str.startsWith( "INFO" )) { 
					System.out.println( "Mem: " + (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()) );
				}
			}
			
			if ( parts.length == 3 ) {
				City c1 = h.getCityByName( parts[1].trim() );
				City c2 = h.getCityByName( parts[2].trim() );
				if ( (c1 != null) && (c2 != null) ) { 
					Route r = new Route( c1, c2 );		
					
					setPriority(Thread.MAX_PRIORITY);
					
					h.runSolvers( r );
					Vector good_routes = h.good_routes;
					System.out.println( "Size: " + good_routes.size() );
					Collections.sort( good_routes, cmp );
					if ( good_routes.size() < max_res ) {
						max_res = good_routes.size();
					}
					for ( int i = 0; i < max_res; i++ ) {
						r = (Route)good_routes.get( i );
						pw.println( r.toString() );
					}
					
					setPriority(Thread.NORM_PRIORITY);
				} else {
					pw.println( "ERROR: cannot find cities." );
				}
			}
			inp.close();
			pw.flush();
			pw.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//
		h = new Holiday();
		Reader rr = new Reader();
		rr.read( "route.csv", h );
		Solver ds3 = new AllRoutesSolver();
		h.addSolver( ds3 );
		//
		ConfigReader.read( "constraints.txt", h, "addConstraint" );
		ConfigReader.read( "endconstraints.txt", h, "addEndConstraint" );
		//
		h.constraint_info();
		
		try {
		    //searchEngine = new AnnexSearchEngine(); // Holiday = ...
			if (args.length > 0) {
				if (args[0].equals("init")) {
					//searchEngine.init();
				} else if (args[0].equals("load")) {
					//searchEngine.load();
				}
			}
		    ServerSocket listen = new ServerSocket( port );
		    
		    // Load holiday here?
		    
		    System.out.println("The server is listening on port " + port + " ....");
		    while (true) {
		    	new RouteServer( listen.accept() );
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

}
