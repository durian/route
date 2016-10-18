package org.durian.route;

import java.util.Vector;

/*
 * Solve for the Route r in this Holiday.
 */
public interface Solver {	
	public void solve( Holiday h, Route r );
	public Vector getResults();
}
