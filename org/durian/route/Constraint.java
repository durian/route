package org.durian.route;

public interface Constraint {
    public boolean check( Route r, Connection c );
    public int get_relax();
    public void set_relax( int r );
}
