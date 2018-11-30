package homework0;

import java.util.ArrayList;
import java.util.List;

/**
 * A container that can be used to contain Balls. A given Ball may only appear
 * in a BallContainer once.
 */
public class BallContainerV1 {
    List<Ball> _ballList;

    /**
     * @effects Creates a new BallContainer.
     */
    public BallContainerV1() {
        _ballList = new ArrayList<>();
    }

    /**
     * @modifies this
     * @effects Adds ball to the container.
     * @return true if ball was successfully added to the container, i.e. ball
     *         is not already in the container; false otherwise.
     */
    public boolean add(Ball ball) {
        if (ball == null)
            return false;
        else if (contains(ball))
            return false;
        else {
            _ballList.add(ball);
            return true;
        }
    }

    /**
     * @modifies this
     * @effects Removes ball from the container.
     * @return true if ball was successfully removed from the container, i.e.
     *         ball is actually in the container; false otherwise.
     */
    public boolean remove(Ball ball) {
        if (ball == null)
            return false;
        else if (!contains(ball))
            return false;
        else {
            _ballList.remove(ball);
            return true;
        }

    }

    /**
     * @return the volume of the contents of the container, i.e. the total
     *         volume of all Balls in the container.
     */
    public double getVolume() {
        double total_vol = 0;
        for (Ball b : _ballList)
            total_vol += b.getVolume();
        return total_vol;

    }

    /**
     * @return the number of Balls in the container.
     */
    public int size() {
        return _ballList.size();

    }

    /**
     * @modifies this
     * @effects Empties the container, i.e. removes all its contents.
     */
    public void clear() {
        _ballList.clear();
    }

    /**
     * @return true if this container contains ball; false, otherwise.
     */
    public boolean contains(Ball ball) {
        return _ballList.contains(ball);

    }

}
