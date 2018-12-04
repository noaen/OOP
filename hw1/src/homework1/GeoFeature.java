package homework1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A GeoFeature represents a route from one location to another along a single
 * geographic feature. GeoFeatures are immutable.
 * <p>
 * GeoFeature abstracts over a sequence of GeoSegments, all of which have the
 * same name, thus providing a representation for nonlinear or nonatomic
 * geographic features. As an example, a GeoFeature might represent the course
 * of a winding river, or travel along a road through intersections but
 * remaining on the same road.
 * <p>
 * GeoFeatures are immutable. New GeoFeatures can be constructed by adding a
 * segment to the end of a GeoFeature. An added segment must be properly
 * oriented; that is, its p1 field must correspond to the end of the original
 * GeoFeature, and its p2 field corresponds to the end of the new GeoFeature,
 * and the name of the GeoSegment being added must match the name of the
 * existing GeoFeature.
 * <p>
 * Because a GeoFeature is not necessarily straight, its length - the distance
 * traveled by following the path from start to end - is not necessarily the
 * same as the distance along a straight line between its endpoints.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * 
 * <pre>
 *   start : GeoPoint       // location of the start of the geographic feature
 *   end : GeoPoint         // location of the end of the geographic feature
 *   startHeading : angle   // direction of travel at the start of the geographic feature, in degrees
 *   endHeading : angle     // direction of travel at the end of the geographic feature, in degrees
 *   geoSegments : sequence	// a sequence of segments that make up this geographic feature
 *   name : String          // name of geographic feature
 *   length : real          // total length of the geographic feature, in kilometers
 * </pre>
 **/
public class GeoFeature {

    // Implementation hint:
    // When asked to return an Iterator, consider using the iterator() method
    // in the List interface. Two nice classes that implement the List
    // interface are ArrayList and LinkedList. If comparing two Lists for
    // equality is needed, consider using the equals() method of List. More
    // info can be found at:
    // http://docs.oracle.com/javase/8/docs/api/java/util/List.html

    // Representation Invariant:
    // this._name != null
    // for each segment in _geoSegments, segment.name == this._name
    // _startPoint, _endPoint != null
    // 0 <= _startHeading <= 360, 0 <= _endHeading <= 360
    // this._length >= 0
    // _geoSegments != null and every segment in the list is not null, and a
    // valid segment
    // _geoSegments[i].P2 == _geoSegments[i+1].P1

    // Abstraction function:
    // A GeoFeature represents a route from one location to another along a
    // single geographic feature.
    // GeoFeature is a collection of GeoSegments connected between them
    // Start point represents the starting coordinates of the GeoFeature
    // whereas End point represents the ending coordinates of the GeoFeature
    // Start and heading represent the angle for the start and end of the
    // GeoFeature,
    // respectively
    // this._length is the total length of the GeoSegments in the GeoFeature
    // All GeoSegments in the GeoFeature share a mutual name, and it is stored
    // in
    // GeoFeature's this._name

    private final GeoPoint   _startPoint;
    private GeoPoint         _endPoint;
    private final double     _startHeading;
    private double           _endHeading;
    private List<GeoSegment> _geoSegments;
    private final String     _name;
    private double           _length;

    /**
     * checks that the representation invariant holds
     * 
     * @effects stops the program if the representation invariant does not hold.
     **/
    private void checkRep() {
        assert (this._name != null) : "GeoFeature Name must be different than null";
        assert (this._geoSegments != null) : "GeoSegments list cannot be null";
        GeoPoint lastP2 = null;
        for (GeoSegment seg : this._geoSegments) {
            assert (seg != null) : "GeoSegment in GeoFeature is null";
            assert (this._name.equals(seg.getName())) : "Every GeoSegment name must equal this GeoFeature's name!";
            if (lastP2 == null) {
                lastP2 = seg.getP2();
            } else {
                assert (seg.getP1().equals(lastP2)) : "New segment's start point does not connect"
                        + " with existing GeoFeature's end point";
                lastP2 = seg.getP2();
            }
        }
        assert (this._startPoint != null && this._endPoint != null) : "Start point or end point is null";
        assert ((this._startHeading >= 0 && this._startHeading <= 360)
                && (this._endHeading >= 0 && this._endHeading <= 360)) : "Heading has illegal value";
        assert (this._length >= 0) : "Length cannot be a negative number";
    }

    /**
     * Constructs a new GeoFeature.
     * 
     * @requires gs != null
     * @effects Constructs a new GeoFeature, r, such that r.name = gs.name &&
     *          r.startHeading = gs.heading && r.endHeading = gs.heading &&
     *          r.start = gs.p1 && r.end = gs.p2
     **/
    public GeoFeature(GeoSegment gs) {
        this._startPoint = gs.getP1();
        this._endPoint = gs.getP2();
        this._startHeading = gs.getHeading();
        this._endHeading = gs.getHeading();
        this._geoSegments = new ArrayList<GeoSegment>();
        this._geoSegments.add(gs);
        this._name = gs.getName();
        this._length = gs.getLength();
        this.checkRep();
    }

    /**
     * Returns name of geographic feature.
     * 
     * @return name of geographic feature
     */
    public String getName() {
        this.checkRep();
        return this._name;
    }

    /**
     * Returns location of the start of the geographic feature.
     * 
     * @return location of the start of the geographic feature.
     */
    public GeoPoint getStart() {
        this.checkRep();
        return this._startPoint;
    }

    /**
     * Returns location of the end of the geographic feature.
     * 
     * @return location of the end of the geographic feature.
     */
    public GeoPoint getEnd() {
        this.checkRep();
        return this._endPoint;
    }

    /**
     * Returns direction of travel at the start of the geographic feature.
     * 
     * @return direction (in standard heading) of travel at the start of the
     *         geographic feature, in degrees.
     */
    public double getStartHeading() {
        this.checkRep();
        return this._startHeading;
    }

    /**
     * Returns direction of travel at the end of the geographic feature.
     * 
     * @return direction (in standard heading) of travel at the end of the
     *         geographic feature, in degrees.
     */
    public double getEndHeading() {
        this.checkRep();
        return this._endHeading;
    }

    /**
     * Returns total length of the geographic feature, in kilometers.
     * 
     * @return total length of the geographic feature, in kilometers. NOTE: this
     *         is NOT as-the-crow-flies, but rather the total distance required
     *         to traverse the geographic feature. These values are not
     *         necessarily equal.
     */
    public double getLength() {
        this.checkRep();
        return this._length;
    }

    /**
     * Creates a new GeoFeature that is equal to this GeoFeature with gs
     * appended to its end.
     * 
     * @requires gs != null && gs.p1 = this.end && gs.name = this.name.
     * @return a new GeoFeature r such that r.end = gs.p2 && r.endHeading =
     *         gs.heading && r.length = this.length + gs.length
     **/
    public GeoFeature addSegment(GeoSegment gs) {
        this.checkRep();
        GeoFeature gf = new GeoFeature(this._geoSegments.get(0));
        gf._geoSegments = new ArrayList<GeoSegment>(this._geoSegments);
        gf._geoSegments.add(gs);
        gf._endPoint = gs.getP2();
        gf._endHeading = gs.getHeading();
        gf._length = this._length + gs.getLength();
        gf.checkRep();
        return gf;

    }

    /**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this GeoFeature. All the
     * GeoSegments have the same name.
     * 
     * @return an Iterator of GeoSegments such that
     * 
     *         <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length-1 => (a[i].name == a[i+1].name &&
     *                                   a[i].p2d  == a[i+1].p1))
     *         </pre>
     * 
     *         where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     */
    public Iterator<GeoSegment> getGeoSegments() {
        this.checkRep();
        Iterator<GeoSegment> itr = this._geoSegments.iterator();
        // TODO: add fields
        return itr;

    }

    /**
     * Compares the argument with this GeoFeature for equality.
     * 
     * @return o != null && (o instanceof GeoFeature) && (o.geoSegments and
     *         this.geoSegments contain the same elements in the same order).
     **/
    public boolean equals(Object o) {
        this.checkRep();
        if ((o != null) && (o instanceof GeoFeature)) {
            Iterator<GeoSegment> oItr = ((GeoFeature) o).getGeoSegments();
            Iterator<GeoSegment> thisItr = this._geoSegments.iterator();

            boolean isEqual = true;
            while (oItr.hasNext() && thisItr.hasNext()) {
                if (!oItr.next().equals(thisItr.next())) {
                    isEqual = false;
                }

            }
            if (oItr.hasNext() || thisItr.hasNext()) {
                isEqual = false;
            }

            return isEqual;
        } else
            return false;
    }

    /**
     * Returns a hash code for this.
     * 
     * @return a hash code for this.
     **/
    public int hashCode() {
        this.checkRep();
        int numGeoSegments = this._geoSegments.size();
        double hash = 0;
        for (GeoSegment gs : this._geoSegments) {
            hash += gs.getLength() / numGeoSegments;
        }

        return (int) hash;
    }

    /**
     * Returns a string representation of this.
     * 
     * @return a string representation of this.
     **/
    public String toString() {
        this.checkRep();
        String str = "GeoFeature name: " + this._name + "\n";
        for (GeoSegment gs : this._geoSegments) {
            str += gs.toString();
        }

        return str;
    }
}
