package homework1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Route is a path that traverses arbitrary GeoSegments, regardless of their
 * names.
 * <p>
 * Routes are immutable. New Routes can be constructed by adding a segment to
 * the end of a Route. An added segment must be properly oriented; that is, its
 * p1 field must correspond to the end of the original Route, and its p2 field
 * corresponds to the end of the new Route.
 * <p>
 * Because a Route is not necessarily straight, its length - the distance
 * traveled by following the path from start to end - is not necessarily the
 * same as the distance along a straight line between its endpoints.
 * <p>
 * Lastly, a Route may be viewed as a sequence of geographical features, using
 * the <tt>getGeoFeatures()</tt> method which returns an Iterator of GeoFeature
 * objects.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * 
 * <pre>
 *   start : GeoPoint            // location of the start of the route
 *   end : GeoPoint              // location of the end of the route
 *   startHeading : angle        // direction of travel at the start of the route, in degrees
 *   endHeading : angle          // direction of travel at the end of the route, in degrees
 *   geoFeatures : sequence      // a sequence of geographic features that make up this Route
 *   geoSegments : sequence      // a sequence of segments that make up this Route
 *   length : real               // total length of the route, in kilometers
 *   endingGeoSegment : GeoSegment  // last GeoSegment of the route
 * </pre>
 **/
public class Route {

    // TODO Write abstraction function and representation invariant
    private final GeoPoint   _startPoint;
    private GeoPoint         _endPoint;
    private final double     _startHeading;
    private double           _endHeading;
    private List<GeoSegment> _geoSegments;
    private List<GeoFeature> _geoFeatures;
    private double           _length;
    private GeoSegment       _endingGeoSegment;

    /**
     * Constructs a new Route.
     * 
     * @requires gs != null
     * @effects Constructs a new Route, r, such that r.startHeading = gs.heading
     *          && r.endHeading = gs.heading && r.start = gs.p1 && r.end = gs.p2
     **/
    public Route(GeoSegment gs) {
        this._startPoint = gs.getP1();
        this._endPoint = gs.getP2();
        this._startHeading = gs.getHeading();
        this._endHeading = gs.getHeading();
        this._geoSegments = new ArrayList<GeoSegment>();
        this._geoSegments.add(gs);
        this._length = gs.getLength();
        this._geoFeatures = new ArrayList<GeoFeature>();
        this._geoFeatures.add(new GeoFeature(gs));
        this._endingGeoSegment = gs;
    }

    /**
     * Returns location of the start of the route.
     * 
     * @return location of the start of the route.
     **/
    public GeoPoint getStart() {
        return this._startPoint;
    }

    /**
     * Returns location of the end of the route.
     * 
     * @return location of the end of the route.
     **/
    public GeoPoint getEnd() {
        return this._endPoint;
    }

    /**
     * Returns direction of travel at the start of the route, in degrees.
     * 
     * @return direction (in compass heading) of travel at the start of the
     *         route, in degrees.
     **/
    public double getStartHeading() {
        return this._startHeading;
    }

    /**
     * Returns direction of travel at the end of the route, in degrees.
     * 
     * @return direction (in compass heading) of travel at the end of the route,
     *         in degrees.
     **/
    public double getEndHeading() {
        return this._endHeading;
    }

    /**
     * Returns total length of the route.
     * 
     * @return total length of the route, in kilometers. NOTE: this is NOT
     *         as-the-crow-flies, but rather the total distance required to
     *         traverse the route. These values are not necessarily equal.
     **/
    public double getLength() {
        return this._length;
    }

    /**
     * Creates a new route that is equal to this route with gs appended to its
     * end.
     * 
     * @requires gs != null && gs.p1 == this.end
     * @return a new Route r such that r.end = gs.p2 && r.endHeading =
     *         gs.heading && r.length = this.length + gs.length
     **/
    public Route addSegment(GeoSegment gs) {
        Route r = new Route(this._geoSegments.get(0));
        r._geoSegments = new ArrayList<GeoSegment>(this._geoSegments);
        r._geoSegments.add(gs);
        r._geoFeatures = new ArrayList<GeoFeature>(this._geoFeatures);
        r._endPoint = gs.getP2();
        r._endHeading = gs.getHeading();
        r._length = this._length + gs.getLength();
        r._endingGeoSegment = gs;

        int featuresSize = this._geoFeatures.size();
        GeoFeature gf = r._geoFeatures.get(featuresSize - 1);
        if (gs.getName().equals(gf.getName())) {
            r._geoFeatures.set(featuresSize - 1, gf.addSegment(gs));
        } else {
            r._geoFeatures.add(new GeoFeature(gs));
        }
        return r;
    }

    /**
     * Returns an Iterator of GeoFeature objects. The concatenation of the
     * GeoFeatures, in order, is equivalent to this route. No two consecutive
     * GeoFeature objects have the same name.
     * 
     * @return an Iterator of GeoFeatures such that
     * 
     *         <pre>
     *      this.start        = a[0].start &&
     *      this.startHeading = a[0].startHeading &&
     *      this.end          = a[a.length - 1].end &&
     *      this.endHeading   = a[a.length - 1].endHeading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length - 1 => (a[i].name != a[i+1].name &&
     *                                     a[i].end  == a[i+1].start))
     *         </pre>
     * 
     *         where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoFeature
     **/
    public Iterator<GeoFeature> getGeoFeatures() {
        Iterator<GeoFeature> itr = this._geoFeatures.iterator();
        // TODO: add fields
        return itr;
    }

    /**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this route.
     * 
     * @return an Iterator of GeoSegments such that
     * 
     *         <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum (0 <= i < a.length) . a[i].length
     *         </pre>
     * 
     *         where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     **/
    public Iterator<GeoSegment> getGeoSegments() {
        Iterator<GeoSegment> itr = this._geoSegments.iterator();
        // TODO: add fields
        return itr;
    }

    /**
     * Compares the specified Object with this Route for equality.
     * 
     * @return true iff (o instanceof Route) && (o.geoFeatures and
     *         this.geoFeatures contain the same elements in the same order).
     **/
    public boolean equals(Object o) {
        if ((o != null) && (o instanceof Route)) {
            Iterator<GeoFeature> oItr = ((Route) o).getGeoFeatures();
            Iterator<GeoFeature> thisItr = this._geoFeatures.iterator();

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
        String str = "Route: " + "\n";
        for (GeoSegment gs : this._geoSegments) {
            str += gs.toString();
        }

        return str;
    }

}
