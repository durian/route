package org.durian.route;

public class ConstraintFactory {

	public ConstraintFactory() {
		super();
	}

	public static Constraint getConstraint( String s ) {	
		if ( s.equals( "NoDoubleCity" )) {
		    Constraint c = new NoDoubleCity();
		    c.set_relax(0);
		    return c;//new NoDoubleCity();
		} else if ( s.equals( "NoDirectReturn" )) {
			return new NoDirectReturn();
		} else if ( s.equals( "ContinueSameMeans" )) {
		    Constraint c = new ContinueSameMeans();
		    c.set_relax(0);
		    return c; //new ContinueSameMeans();
		}
		return new PrintConstraint();
	}
	
	public static Constraint getConstraint( String s, int arg ) {
		if ( s.equals( "MaximumSize" )) {
			return new MaximumSize( arg );
		} else if ( s.equals( "MaxCost" )) {
			return new MaxCost( arg );
		} else if ( s.equals( "MaxTime" )) {
			return new MaxTime( arg );
		} else if ( s.equals( "MaxConnectionTime" )) {
			return new MaxConnectionTime( arg );
		} else if ( s.equals( "MinTime" )) {
			return new MinTime( arg );
		} else if ( s.equals( "MinCityScore" )) {
			return new MinCityScore( arg );
		} else if ( s.equals( "AllowedMeans" )) {
			return new AllowedMeans( arg );
		}
		return new PrintConstraint();
	}
		
	public static Constraint getConstraint( String s, Connection arg ) {	
		if ( s.equals( "ContainsConnection" )) {
			return new ContainsConnection( arg );
		} else if ( s.equals( "NotContainsConnection" )) {
			return new NotContainsConnection( arg );
		}
		return new PrintConstraint();
	}
	
	public static Constraint getConstraint( String s, City arg ) {	
		if ( s.equals( "VisitsCity" )) {
			return new VisitsCity( arg );
		}
		return new PrintConstraint();
	}
}
