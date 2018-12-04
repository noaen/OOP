package homework1;

/**
 * A GeoSegment models a straight line segment on the earth. GeoSegments are
 * immutable.
 * <p>
 * A compass heading is a nonnegative real number less than 360. In compass
 * headings, north = 0, east = 90, south = 180, and west = 270.
 * <p>
 * When used in a map, a GeoSegment might represent part of a street, boundary,
 * or other feature. As an example usage, this map
 * 
 * <pre>
 *  Trumpeldor   a
 *  Avenue       |
 *               i--j--k  Hanita
 *               |
 *               z
 * </pre>
 * 
 * could be represented by the following GeoSegments: ("Trumpeldor Avenue", a,
 * i), ("Trumpeldor Avenue", z, i), ("Hanita", i, j), and ("Hanita", j, k).
 * </p>
 * 
 * </p>
 * A name is given to all GeoSegment objects so that it is possible to
 * differentiate between two GeoSegment objects with identical GeoPoint
 * endpoints. Equality between GeoSegment objects requires that the names be
 * equal String objects and the end points be equal GeoPoint objects.
 * </p>
 *
 * <b>The following fields are used in the specification:</b>
 * 
 * <pre>
 *   name : String       // name of the geographic feature identified
 *   p1 : GeoPoint       // first endpoint of the segment
 *   p2 : GeoPoint       // second endpoint of the segment
 *   length : real       // straight-line distance between p1 and p2, in kilometers
 *   heading : angle     // compass heading from p1 to p2, in degrees
 * </pre>
 **/
public class GeoSegment {

    // Abstraction Function:
	// GeoSegment represents a geographical segment defined by a starting GeoPoint P1 and an end GeoPoint P2.
	// It has a length (the physical distance between P1 and P2).
	// It has heading (the direction P2 is in relation to P1)
	// It has a name that represents it. (For example: street name)
	
	// Representation Invariant:
	// P1, P2 != null
	// 0 <= this._heading <= 360
	// this._length >= 0
	// this._name != null
	

    private final String   _name;
    private final GeoPoint _p1;
    private final GeoPoint _p2;
    private final double   _length;
    private final double   _heading;
    
    /**
     * checks that the representation invariant holds
     * 
     * @effects stops the program if the representation invariant does not hold.
     **/
    private void checkRep() {
    	assert (this._name != null && 0 <= this._heading && this._heading <= 360 
    			&& this._p1 != null && this._p2 != null && this._length >= 0)
    	: "GeoSegment is not valid";
    }

    /**
     * Constructs a new GeoSegment with the specified name and endpoints.
     * 
     * @requires name != null && p1 != null && p2 != null
     * @effects constructs a new GeoSegment with the specified name and
     *          endpoints.
     **/
    public GeoSegment(String name, GeoPoint p1, GeoPoint p2) {
        _name = name;
        _p1 = p1;
        _p2 = p2;
        _length = p1.distanceTo(p2);
        _heading = p1.headingTo(p2);
        this.checkRep();
    }

    /**
     * Returns a new GeoSegment like this one, but with its endpoints reversed.
     * 
     * @return a new GeoSegment gs such that gs.name = this.name && gs.p1 =
     *         this.p2 && gs.p2 = this.p1
     **/
    public GeoSegment reverse() {
        this.checkRep();
        GeoSegment gs = new GeoSegment(this._name, this._p2, this._p1);
        return gs;
    }

    /**
     * Returns the name of this GeoSegment.
     * 
     * @return the name of this GeoSegment.
     */
    public String getName() {
        this.checkRep();
        return this._name;
    }

    /**
     * Returns first endpoint of the segment.
     * 
     * @return first endpoint of the segment.
     */
    public GeoPoint getP1() {
        this.checkRep();
        return this._p1;
    }

    /**
     * Returns second endpoint of the segment.
     * 
     * @return second endpoint of the segment.
     */
    public GeoPoint getP2() {
        this.checkRep();
        return this._p2;
    }

    /**
     * Returns the length of the segment.
     * 
     * @return the length of the segment, using the flat-surface, near the
     *         Technion approximation.
     */
    public double getLength() {
        this.checkRep();
        return this._length;
    }

    /**
     * Returns the compass heading from p1 to p2.
     * 
     * @requires this.length != 0
     * @return the compass heading from p1 to p2, in degrees, using the
     *         flat-surface, near the Technion approximation.
     **/
    public double getHeading() {
        this.checkRep();
        return this._heading;
    }

    /**
     * Compares the specified Object with this GeoSegment for equality.
     * 
     * @return gs != null && (gs instanceof GeoSegment) && gs.name = this.name
     *         && gs.p1 = this.p1 && gs.p2 = this.p2
     **/
    public boolean equals(Object gs) {
        this.checkRep();
        return (gs != null) && (gs instanceof GeoSegment) && this._name.equals(((GeoSegment) gs).getName())
                && this._p1.equals(((GeoSegment) gs).getP1()) && ((GeoSegment) gs).getP2().equals(this._p2);
    }

    /**
     * Returns a hash code value for this.
     * 
     * @return a hash code value for this.
     **/
    public int hashCode() {
        this.checkRep();
        // This implementation will work, but you may want to modify it
        // for improved performance.

        return this._p1.hashCode() + this._p2.hashCode();
    }

    /**
     * Returns a string representation of this.
     * 
     * @return a string representation of this.
     **/
    public String toString() {
        this.checkRep();
        String str = this._name + ", " + this._p1.toString() + ", "
                + this._p2.toString() + "\n";

        return str;
    }

}
