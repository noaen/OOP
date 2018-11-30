package homework0;

/**
 * Checking Functionality of Ball and BallContainer.
 */
public class CheckBalls {
    /**
     * @param none
     */
    public static void main(String[] args) {
        // creating BallContaner object
        BallContainerV1 ballContainer = new BallContainerV1();
        // creating Ball objects, demonstrating Ball's methods
        Ball ball1 = new Ball(1);
        System.out.println("Creating ball 1: Volume of ball 1 is " + ball1.getVolume());
        ball1.setVolume(2.2);
        System.out.println("Changing volume of ball 1: New volume of ball 1 is " + ball1.getVolume());
        Ball ball2 = new Ball(3.4);
        System.out.println("Creating ball 2: Volume of ball 2 is " + ball2.getVolume());
        System.out.println("");
        // demonstrating BallContainer's methods
        System.out.println("After creating container:");
        System.out.println("Volume of balls in container is " + ballContainer.getVolume());
        System.out.println("Number of balls in container is " + ballContainer.size());
        System.out.println("");
        ballContainer.add(ball1);
        System.out.println("After adding ball 1:");
        System.out.println("ballContainer.contains(ball1) = " + ballContainer.contains(ball1));
        System.out.println("Volume of balls in container is " + ballContainer.getVolume());
        System.out.println("Number of balls in container is " + ballContainer.size());
        System.out.println("");
        // demonstrating "illegal" action
        System.out.println("Adding ball 1 again:");
        System.out.println("ballContainer.add(ball1) = " + ballContainer.add(ball1));
        System.out.println("ballContainer.contains(ball1) = " + ballContainer.contains(ball1));
        System.out.println("Volume of balls in container is " + ballContainer.getVolume());
        System.out.println("Number of balls in container is " + ballContainer.size());
        System.out.println("");
        ballContainer.add(ball2);
        System.out.println("After adding ball 2:");
        System.out.println("ballContainer.contains(ball2) = " + ballContainer.contains(ball2));
        System.out.println("Volume of balls in container is " + ballContainer.getVolume());
        System.out.println("Number of balls in container is " + ballContainer.size());
        System.out.println("");
        System.out.println("Removing ball 1 from container");
        ballContainer.remove(ball1);
        System.out.println("After removing ball 1:");
        System.out.println("ballContainer.contains(ball1) = " + ballContainer.contains(ball1));
        System.out.println("Volume of balls in container is " + ballContainer.getVolume());
        System.out.println("Number of balls in container is " + ballContainer.size());
        System.out.println("");
        ballContainer.clear();
        System.out.println("After clearing container:");
        System.out.println("ballContainer.contains(ball1) = " + ballContainer.contains(ball1));
        System.out.println("ballContainer.contains(ball2) = " + ballContainer.contains(ball2));
        System.out.println("Volume of balls in container is " + ballContainer.getVolume());
        System.out.println("Number of balls in container is " + ballContainer.size());
        System.out.println("");
        // demonstrating "illegal" actions
        System.out.println("Adding null ball:");
        System.out.println("ballContainer.add(null) = " + ballContainer.add(null));
        System.out.println("");
        System.out.println("Removing non-existing ball:");
        System.out.println("ballContainer.remove(ball1) = " + ballContainer.remove(ball1));
    }

}
