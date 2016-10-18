package org.durian.route;

import java.util.Vector;

/*
 * Contains info about which cities, max.price, days, etc. Maybe also connections,
 * so we can steer how to travel.
 */
public class Holiday {

    public Vector cities; // The cities we want to visit.
    public Vector routes; // The routes along the cities.
    public Vector connections; // Possible connections.
    public Vector solvers; // Solvers.
    public Vector constraints; // When adding new connections.
    public Vector end_constraints; // When ready, on end route.
    public Vector good_routes;
	
    public Holiday() {
	cities = new Vector();
	routes = new Vector();
	connections = new Vector();
	solvers = new Vector();
	constraints = new Vector();
	end_constraints = new Vector();
    }
		
    /*
     * Add a new one to the list...
     */
    public void addCity( City c ) {
	cities.add( c ); // Should copy.
    }
	
    public City getCityByName( String n ) {
	City c;
	n = n.toLowerCase();
	for ( int i = 0; i < cities.size(); i++ ) {
	    c = (City)cities.get(i);
	    if ( c.name.equals( n )) {
		return c;
	    }
	}
	// New, add it.
	//
	c = new City( n );
	addCity( c );
	return c;
    }
	
    public void addConnection( Connection c ) {
	c.from.addConnection( c );
	connections.add( c );
    }
	
    public void addBothConnections( Connection c ) {
	City c_from = c.from;
	c_from.addConnection( c );
	connections.add( c );
	c_from = c.to;
	Connection cr = c.reverse();
	c_from.addConnection( cr );
	connections.add( cr );
    }
	
    public void addRoute( Route r ) {
	routes.add( r );
    }
	
    public Route newRoute( City f, City t ) {
	Route r = new Route( f, t );
		
	return r;
    }
	
    public void addSolver( Solver s ) {
	solvers.add( s );
    }
	
    public void addConstraint( Constraint c ) {
	constraints.add( c );
    }
	
    public void removeConstraint( Constraint c ) {
	for ( int i = 0; i < constraints.size(); i++ ) {
	    Constraint c1 = (Constraint)constraints.get(i);
	    if ( c1.getClass().equals( c.getClass() )) {
		constraints.removeElement( c1 );
	    }
	}
    }
	
    public void removeAllConstraints() {
	constraints.clear();
    }
	
    public void addEndConstraint( Constraint c ) {
	end_constraints.add( c );
    }
	
    public void removeEndConstraint( Constraint c ) {
	for ( int i = 0; i < end_constraints.size(); i++ ) {
	    Constraint c1 = (Constraint)end_constraints.get(i);
	    if ( c1.getClass().equals( c.getClass() )) {
		end_constraints.removeElement( c1 );
	    }
	}
    }
	
    public void removeAllEndConstraints() {
	end_constraints.clear();
    }
	
    /*
     * Run solvers with starting route r.
     */
    public void runSolvers(Route r) {
	good_routes = new Vector();
	for ( int i = 0; i < solvers.size(); i++ ) {
	    Solver s = (Solver)solvers.get(i);
	    s.solve(this, r);
	    Vector res = s.getResults();
	    good_routes.addAll( res );
	}
    }
		
    /*
     * Check if we have visited all cities yet. Maybe return unvisited ones.
     */
    public Vector citiesLeft( Route r ) {
	Vector cs = new Vector( cities );
	Vector conns = r.connections; // Possible connections.
	for ( int i = 0; i < conns.size(); i++ ) {
	    Connection a_con = (Connection)conns.get(i);
	    City a_city = a_con.to;
	    cs.remove( a_city );
	}
	return cs;
    }
	
    /*
     * See how well a Route fits us. Do we visit all cities?
     */
    public int score( Route r ) {
	int score = 0;			
		
	return score;
    }
	
    public void info() {
	System.out.println( "Holiday" );
	for ( int i = 0; i < cities.size(); i++ ) {
	    System.out.println( ((City)cities.get(i)).toString() );
	}
	for ( int i = 0; i < connections.size(); i++ ) {
	    System.out.println( ((Connection)connections.get(i)).toString() );
	}
    }
	
    public void constraint_info() {
	System.out.println( "Constraints: " + constraints.size () );
	System.out.println( "End Constraints: " + end_constraints.size () );
    }
	
    public void printDot() {
	System.out.println( "graph G {" );
	System.out.println( "# neato -Tpng tst.dot -o foo.png" );
	for ( int i = 0; i < connections.size(); i += 2 ) {
	    Connection con = (Connection)connections.get(i);
	    System.out.print( con.from + " -- " + con.to );
	    System.out.println( " [label=\"" + con.distance + "\"];" );
	}
	System.out.println( "}" );
    }
	
}
