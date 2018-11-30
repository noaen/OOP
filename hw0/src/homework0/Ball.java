package homework0;

/**
 * A simple object that has a volume.
 */
public class Ball {
    private double _volume;

    /**
     * @requires volume > 0
     * @modifies this
     * @effects Creates and initializes new Ball object with the specified
     *          volume.
     */

    public Ball(double volume) {
        this._volume = volume;
    }

    /**
     * @requires volume > 0
     * @modifies this
     * @effects Sets the volume of the Ball.
     */
    public void setVolume(double volume) {
        this._volume = volume;
    }

    /**
     * @return the volume of the Ball.
     */
    public double getVolume() {
        return this._volume;
    }
}