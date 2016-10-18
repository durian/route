package org.durian.route;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

    public Reader() {
	super();
	// TODO Auto-generated constructor stub
    }

    /*
     * Fill this holiday with data. Also a socket version?
     * CityReader and ConnectionReader?
     */
    public void read( String fname, Holiday h ) {
	try {
	    BufferedReader in = new BufferedReader( new FileReader(fname) );
	    String str;
	    while ((str = in.readLine()) != null) {
		//System.out.println( str );

		str = str.toLowerCase();

		//process(str);
		if ( str.startsWith( "#" ) ) {
		    continue;
		}
		String parts[] = str.split(",");
		if ( parts.length < 3 ) {
		    // City
		    System.out.println( "City: [" + parts[0] + "]" );
		    City c = new City( parts[0] );
		    h.addCity( c );
		} else if ( parts.length == 3 ) {
		    City c_from = h.getCityByName( parts[0].trim() );
		    City c_to   = h.getCityByName( parts[1].trim() );
		    String dist = parts[2].trim();
		    int dist_v;
		    dist_v = new Integer( dist ).intValue();
		    Connection con = new Connection( c_from, c_to, 0, dist_v, 1, 0 );
					
		    h.addBothConnections( con );

		} else if ( parts.length == 6 ) {
		    //System.out.println( "Connection" );
		    City c_from = h.getCityByName( parts[0].trim() );
		    if ( c_from == null ) {
			System.out.println( "c_from is null. [" + parts[0] + "]" );
		    }
		    City c_to   = h.getCityByName( parts[1].trim() );
		    if ( c_to == null ) {
			System.out.println( "c_to is null. [" + parts[1] + "]" );
		    }
					
		    String means = parts[2].trim();
		    int means_v = Connection.BY_CAR; // BY_CAR
		    String dist  = parts[3].trim();
		    int dist_v;
		    String cost  = parts[5].trim();
		    int cost_v;
		    String time  = parts[4].trim();
		    int time_v;
					
		    if ( means.equals("train")) {
			means_v = Connection.BY_TRAIN;
		    } else if ( means.equals("x2000")) {
			means_v = Connection.BY_X2000;
		    } else if ( means.equals("air")) {
			means_v = Connection.BY_PLANE;
		    } else if ( means.equals("bus")) {
			means_v = Connection.BY_BUS;
		    }
					
		    dist_v = new Integer( dist ).intValue();
					
		    cost_v = new Integer( cost ).intValue();
					
		    String time_parts[] = time.split(":");
		    time_v = (new Integer(time_parts[0]).intValue()) * 60 +
			(new Integer(time_parts[1]).intValue());
					
		    Connection con = new Connection( c_from, c_to, means_v, dist_v, cost_v, time_v );
					
		    h.addBothConnections( con );
		    System.out.println( con.toString() );
		}
	    }
	    in.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
