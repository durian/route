package org.durian.route;

import java.util.Vector;

/*
 * Cities.
 * 
 * Maybe extend with "things to see + time it needs". Plus costs (hotel?)
 */
public class City {

	public String name;
	public int max_visits;
	public Vector connections;
	public int score;
	
	/*
	 * Name is equality.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object c) {
		if ( (((City)c).name).equals(name) ) {
			return true;
		}
		return false;
	}
	
	public City(String n) {
		name        = n;
		max_visits  = 1;
		connections = new Vector();
		score       = 0;
	}
	
	public City(String n, int m) {
		name        = n;
		max_visits  = m;
		connections = new Vector();
		score       = 0;
	}
	
	public City( City c ) {
		name        = c.name;
		max_visits  = c.max_visits;
		connections = new Vector( c.connections );
		score       = c.score;
	}
	
	public void addConnection( Connection c ) {
		connections.add( c );
	}
	
	public void addBothConnections( Connection c ) {
		connections.add( c );
		c.to.addConnection( c.reverse() );
	}
	
	public Vector possibleNext() {
		Vector res = new Vector();
		
		// Check current cities connections.
		//
		Vector cxs = connections;
		for ( int i = 0; i < cxs.size(); i++ ) {
			res.add( (Connection)cxs.get(i) );
		}
		
		return res;
	}
	
	public String toString() {
		return name;
	}

}
