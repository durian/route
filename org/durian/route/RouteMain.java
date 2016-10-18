package org.durian.route;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.io.BufferedWriter;
import java.io.FileWriter;


/*
 * pberck@lux15:~/work/Workspace % /usr/java/jdk1.5.0_04/bin/java 
 *                             -Xmx999M -cp .:r1.jar org.durian.route.RouteMain
 */
public class RouteMain {
	
    /**
     * @param args
     */
    public static void main(String[] args) {

	String route_file = "route2.cvs";
	String c_from = "";
	String c_to = "";

	if (args.length == 3 ) {
	    c_from = args[0];
	    c_to = args[1];
	    route_file = args[2];
	}

	Holiday h = new Holiday();
		
	Reader rr = new Reader();
	rr.read( route_file, h );
	h.printDot();
		
	// Create a Route to solve.
	//
	City c1 = h.getCityByName( c_from );
	City c2 = h.getCityByName( c_to );
	Route r = new Route( c1, c2 );
		
	Solver ds3 = new AllRoutesSolver();
	h.addSolver( ds3 );
		
	ConfigReader.read( "constraints.txt", h, "addConstraint" );
	//h.addConstraint( new ContinueSameMeans() );
		
	/*
	  int ms = 6;//new Integer( args[0] ).intValue();
	  h.addConstraint( ConstraintFactory.getConstraint( "MaximumSize", ms ));
	  //h.addConstraint( new NoDoubleCity() );
	  //h.addConstraint( new NoDirectReturn() );
	  h.addConstraint( new MaxCost( 2000 ) );
	  h.addConstraint( new MaxTime( 480 ) );
	  //h.addConstraint( new MinCityScore( 0 ) );
	  h.addConstraint( new AllowedMeans( Connection.BY_CAR ));//Connection.BY_PLANE + Connection.BY_TRAIN );
	  //h.addConstraint( new PrintConstraint() );
	  */
		
	ConfigReader.read( "endconstraints.txt", h, "addEndConstraint" );
		
	//h.addEndConstraint(new NotContainsConnection(new Connection(h,"c","d")));
	//h.addEndConstraint( new VisitsCity( h.getCityByName("CPH")));

		
	h.runSolvers( r );
		
	Vector good_routes = h.good_routes;
	System.out.println( "Size: " + good_routes.size() );

	try {
	    BufferedWriter out = new BufferedWriter(new FileWriter("last.txt"));
	    for ( int i = 0; i < good_routes.size(); i++ ) {
		r = (Route)good_routes.get( i );
		out.write( r.toString() + "\n" );
	    }
	    out.close();
	} catch (Exception e ) {
	    //
	}
	    

	if ( good_routes.size() > 13 ) {
			
	    Comparator cmp1 = new Comparator() { // Cheapest
		    public int compare( Object r0, Object r1 ) {		
			if ( ((Route)r0).totalCost() < ((Route)r1).totalCost() ) {
			    return -1;
			} else if ( ((Route)r0).totalCost() > ((Route)r1).totalCost() ) {
			    return 1;
			}
			return 0;
		    }
		};
	    Comparator cmp2 = new Comparator() { // Shortest time
		    public int compare( Object r0, Object r1 ) {		
			if ( ((Route)r0).totalTime() < ((Route)r1).totalTime() ) {
			    return -1;
			} else if ( ((Route)r0).totalTime() > ((Route)r1).totalTime() ) {
			    return 1;
			}
			return 0;
		    }
		};
	    Comparator cmp3 = new Comparator() { // Least connexions
		    public int compare( Object r0, Object r1 ) {		
			if ( ((Route)r0).size() < ((Route)r1).size() ) {
			    return -1;
			} else if ( ((Route)r0).size() > ((Route)r1).size() ) {
			    return 1;
			}
			return 0;
		    }
		};
	    Comparator cmp4 = new Comparator() { // Shortest distance
		    public int compare( Object r0, Object r1 ) {		
			if ( ((Route)r0).totalDistance() < ((Route)r1).totalDistance() ) {
			    return -1;
			} else if ( ((Route)r0).totalDistance() > ((Route)r1).totalDistance() ) {
			    return 1;
			}
			return 0;
		    }
		};
	    Comparator cmp5 = new Comparator() { // Score, NB sort reversed.
		    public int compare( Object r0, Object r1 ) {		
			if ( ((Route)r0).totalCityScore() < ((Route)r1).totalCityScore() ) {
			    return 1;
			} else if ( ((Route)r0).totalCityScore() > ((Route)r1).totalCityScore() ) {
			    return -1;
			}
			return 0;
		    }
		};
	    Comparator cmp6 = new Comparator() { // Most connexions
		    public int compare( Object r0, Object r1 ) {		
			if ( ((Route)r0).size() < ((Route)r1).size() ) {
			    return 1;
			} else if ( ((Route)r0).size() > ((Route)r1).size() ) {
			    return -1;
			}
			return 0;
		    }
		};

	    /*
	    Collections.sort( good_routes, cmp1 );
	    System.out.println( "\nCheapest:" );
	    for ( int i = 0; i < 3; i++ ) {
		r = (Route)good_routes.get( i );
		System.out.println( r.toString() );
	    }
	    */
			
	    Collections.sort( good_routes, cmp2 );
	    System.out.println( "\nShortest time:" );
	    for ( int i = 0; i < 3; i++ ) {
		r = (Route)good_routes.get( i );
		System.out.println( r.toString() );
	    }
			
	    Collections.sort( good_routes, cmp3 );
	    System.out.println( "\nLeast connexions:" );
	    for ( int i = 0; i < 3; i++ ) {
		r = (Route)good_routes.get( i );
		System.out.println( r.toString() );
	    }

	    /*
	    Collections.sort( good_routes, cmp6 );
	    System.out.println( "\nMost connexions:" );
	    for ( int i = 0; i < 3; i++ ) {
		r = (Route)good_routes.get( i );
		System.out.println( r.toString() );
	    }
	    */
	
	    Collections.sort( good_routes, cmp4 );
	    System.out.println( "\nShortest distance:" );
	    for ( int i = 0; i < 3; i++ ) {
		r = (Route)good_routes.get( i );
		System.out.println( r.toString() );
	    }

	    /*
	    Collections.sort( good_routes, cmp5 );
	    System.out.println( "\nScore:" );
	    for ( int i = 0; i < 3; i++ ) {
		r = (Route)good_routes.get( i );
		System.out.println( r.toString() );
	    }
	    */
	} else {
	    for ( int i = 0; i < good_routes.size(); i++ ) {
		r = (Route)good_routes.get( i );
		System.out.println( r.toString() );
	    }
	}
		
    }
}
