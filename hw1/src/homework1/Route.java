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

    // Representation Invariant:
    // _startPoint, _endPoint != null
    // 0 <= _startHeading <= 360, 0 <= _endHeading <= 360
    // this._length >= 0
    // _geoSegments != null and every segment in the list is not null, and a
    // valid segment
    // _geoSegments[i].P2 == _geoSegments[i+1].P1
    // _geoFeatures != null and every feature in the list is not null, and a
    // valid feature
    // _geoFeatures[i].endPoint == _geoFeatures[i+1].startPoint
    // _geoFeatures[i].name != _geoFeatures[i+1].name
    // _endingGeoSegment != null

    // Abstraction function:
    // A Route is a path that traverses arbitrary GeoSegments, regardless of
    // their names.
    // Route is a collection of GeoFeatures connected between them
    // Start point represents the starting coordinates of the Route
    // whereas End point represents the ending coordinates of the Route
    // Start and heading represent the angle for the start and end of the Route,
    // respectively
    // this._length is the total length of the GeoFeatures in the Route
    // this._endingGeoSegment is the last GeoSegment of the Route

    private final GeoPoint   _startPoint;
    private GeoPoint         _endPoint;
    private final double     _startHeading;
    private double           _endHeading;
    private List<GeoSegment> _geoSegments;
    private List<GeoFeature> _geoFeatures;
    private double           _length;
    private GeoSegment       _endingGeoSegment;

    /**
     * checks that the representation invariant holds
     * 
     * @effects stops the program if the representation invariant does not hold.
     **/
    private void checkRep() {
        assert (this._endPoint != null && this._startPoint != null) : "Start or end point is null";
        assert ((this._startHeading >= 0 && this._startHeading <= 360)
                && (this._endHeading >= 0 && this._endHeading <= 360)) : "Start or end heading is not in valid range";
        assert (this._length >= 0) : "Length of route is a negative number";
        assert (this._endingGeoSegment != null) : "Ending GeoSegment is null";
        assert (this._geoSegments != null) : "GeoSegments list in route is null";
        assert (this._geoFeatures != null) : "GeoFeatures list in route is null";
        GeoPoint lastP2 = null;
        for (GeoSegment seg : this._geoSegments) {
            assert (seg != null) : "GeoSegment in Route is null";
            if (lastP2 == null) {
                lastP2 = seg.getP2();
            } else {
                assert (seg.getP1().equals(lastP2)) : "New segment's start point does not connect"
                        + " with existing Route's end point";
                lastP2 = seg.getP2();
            }
        }
        lastP2 = null;
        String lastName = null;
        for (GeoFeature feature : this._geoFeatures) {
            assert (feature != null) : "GeoFeature in Route is null";
            if (lastP2 == null) {
                lastP2 = feature.getEnd();
                lastName = feature.getName();
            } else {
                assert (feature.getStart().equals(lastP2)) : "New feature's start point does not connect"
                        + " with existing Route's end point";
                lastP2 = feature.getEnd();
                assert (!feature.getName().equals(lastName)) : "Consecutive features have equal names";
                lastName = feature.getName();
            }
        }
    }

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
        this.checkRep();
    }

    /**
     * Returns location of the start of the route.
     * 
     * @return location of the start of the route.
     **/
    public GeoPoint getStart() {
        this.checkRep();
        return this._startPoint;
    }

    /**
     * Returns location of the end of the route.
     * 
     * @return location of the end of the route.
     **/
    public GeoPoint getEnd() {
        this.checkRep();
        return this._endPoint;
    }

    /**
     * Returns direction of travel at the start of the route, in degrees.
     * 
     * @return direction (in compass heading) of travel at the start of the
     *         route, in degrees.
     **/
    public double getStartHeading() {
        this.checkRep();
        return this._startHeading;
    }

    /**
     * Returns direction of travel at the end of the route, in degrees.
     * 
     * @return direction (in compass heading) of travel at the end of the route,
     *         in degrees.
     **/
    public double getEndHeading() {
        this.checkRep();
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
        this.checkRep();
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
        this.checkRep();
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
        r.checkRep();
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
        this.checkRep();
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
        this.checkRep();
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
        this.checkRep();
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
        String str = "Route: " + "\n";
        for (GeoSegment gs : this._geoSegments) {
            str += gs.toString();
        }

        return str;
    }

}
