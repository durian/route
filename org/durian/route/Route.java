package org.durian.route;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/*
 * Route: contains a "from" and a "to" City, connected through
 * a number of Connections.
 */
public class Route {

	public HashMap city_info;  // bookkeeping (city_object to something)
	public Vector connections; // Connections making up route.
	public City from;
	public City to;
	public City current;
	public int status;
	
	/*
	 * Empty Route, used in the copy() routine.
	 */
	public Route() {
		city_info   = new HashMap();
		connections = new Vector();
		status      = 0;
	}
	
	/*
	 * New Route, from "f" to "t".
	 */
	public Route( City f, City t ) {
		from        = f;
		current     = f;
		to          = t;
		city_info   = new HashMap();
		connections = new Vector();
		status      = 0;
	}
	
	public Route( Route r ) {
		from = r.from;
		to   = r.to;
		current = r.current;
		status = 0;
		connections = new Vector( r.connections );
		city_info = new HashMap( r.city_info );
	}
	
	/*
	 * Extend a route to the next City. current is set to the con.to
	 * City. CityInfo is updated.
	 */
	public void addConnection( Connection con ) {
		connections.add( con );
		current = con.to;
		
		// Bookkeep:
		//
		CityInfo ci = (CityInfo)city_info.get( current );
		if ( ci == null ) {
			ci = new CityInfo( current );
			city_info.put( current, ci );
		}
		ci.visits += 1;
	}
	
	/*
	 * How many time has this route visited c?
	 */
	public int getCityVisits( City c ) {
		int v = 0;
		CityInfo ci = (CityInfo)city_info.get( c );
		if ( ci != null ) {
			v = ci.visits;
		}
		return v;
	}
	
	/*
	 * Where can we go from here (i.e. current)?
	 */
	public Vector possibleNext() {
		Vector res = new Vector();
		
		// Check current cities connections.
		// Apply contrains... (from cities). Constraints on connections? Filter later (in Holiday)
		//
		Vector cxs = current.connections;
		for ( int i = 0; i < cxs.size(); i++ ) {
			res.add( (Connection)cxs.get(i) );
		}
		
		return res;
	}
	
	/*
	 * Count how many times this connection is in this Route.
	 */
	public int countConnections( Connection c ) {
		int count = 0;
		int idx = 0;
		
		while ( (idx = connections.indexOf( c, idx )) > -1 ) {
			++count;
			++idx;
		}
		
		return count;
	}
	
	/*
	 * Count how many times this connection is in this Route.
	 */
	public int size() {
		return connections.size();
	}
	
	public String toString() {
		String res = "R["+from.toString()+"-"+to.toString()+"]:";
		
		res += " "+Util.SEKString(totalCost())+" SEK, "+Util.timeString(totalTime())+", "+totalDistance()+" km.";
		res += " "+from;
		for ( int i = 0; i < connections.size(); i++ ) {
			Connection con = (Connection)connections.get(i);
			if ( con.means == 0 ) {
			    res += "->"+con.to;
			} else {
			    res += "->" + con.meansToString()+"->"+con.to;;//con.toString();
			}
		}
		
		return res;
	}
	
	public class CityInfo {
		public int visits;
		public int max_visits;
		
		public CityInfo() {
			visits = 0;
		}
		
		public CityInfo( City c ) {
			visits = 0;
			max_visits = c.max_visits;
		}
	}
	
	public int totalCost() {
		int tc = 0;
		
		Iterator it = connections.iterator ();
		while (it.hasNext ()) {
			tc += ((Connection)it.next()).cost ;
		}

		return tc;
	}
	
	public int totalTime() {
		int tt = 0;
		
		Iterator it = connections.iterator ();
		while (it.hasNext ()) {
			tt += ((Connection)it.next()).time ;
		}

		return tt;
	}
	
	public int totalDistance() {
		int td = 0;
		
		Iterator it = connections.iterator ();
		while (it.hasNext ()) {
			td += ((Connection)it.next()).distance ;
		}

		return td;
	}
	
	public int totalScore() {
		int ts = 0;
		
		Iterator it = connections.iterator ();
		while (it.hasNext ()) {
			ts += ((Connection)it.next()).score ;
		}

		return ts;
	}
	
	public int totalCityScore() {
		int ts = 0;
		
		Iterator it = connections.iterator ();
		while (it.hasNext ()) {
			ts += ((City)((Connection)it.next()).to).score ;
		}

		return ts;
	}
}
