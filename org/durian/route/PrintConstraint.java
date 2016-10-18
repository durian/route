package org.durian.route;

public class PrintConstraint implements Constraint {

	public PrintConstraint() {
		super();
	}

	public boolean check(Route r, Connection c) {
		System.out.println( r.toString() + "/" + c.toString() );
		return true;
	}

    public void set_relax(int r) {
    }
    
    public int get_relax() {
	return 0;
    }
    
}
