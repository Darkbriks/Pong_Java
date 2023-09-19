import java.awt.*;
import java.awt.geom.Rectangle2D;

public class AIController
{
    ///////////////////////////// FIELD /////////////////////////////

    private PlayerController playerController;
    private Rect ball;
    private Ball ballController;
    private int difficulty; // 0 = easy, 1 = medium, 2 = hard, 3 = impossible
    // In easy mode, the AI will move up and down in a sine wave
    // In medium mode, the AI will predict where the ball will land with 1 iteration and move paddle there with a speed of 0.4
    // In hard mode, the AI will predict where the ball will land with 50 iterations and move paddle there with a speed of 0.25 to 0.75
    // In impossible mode, the AI will predict where the ball will land with 1000 iterations and move paddle there with a speed of 0 to 1, and add effects with moving the paddle just before the ball hits

    private double[] position;

    ///////////////////////////// CONSTRUCTORS /////////////////////////////

    /**
     * Constructor for AIController class
     * @param playerController PlayerController object to copy (PlayerController)
     * @param ball Rect object to copy (Rect)
     * @param ballController Ball object to copy (Ball)
     * @param difficulty difficulty of AI (int)
     */
    public AIController(PlayerController playerController, Rect ball, Ball ballController, int difficulty)
    {
        this.playerController = playerController;
        this.ball = ball;
        this.ballController = ballController;
        this.difficulty = difficulty;
        this.position = new double[2];
    }

    /**
     * Copy constructor for AIController class
     * @param aiController AIController object to copy (AIController)
     */
    public AIController(AIController aiController)
    {
        // TODO: Copy playerController, ball, ballController, difficulty, and position
        this.playerController = new PlayerController(aiController.playerController.getRect());
        this.ball = new Rect(aiController.ball);
        this.ballController = new Ball(aiController.ballController);
        this.difficulty = aiController.difficulty;
        this.position = new double[2];
    }

    ///////////////////////////// GETTERS /////////////////////////////

    /**
     * Returns playerController
     * @return playerController (PlayerController)
     */
    public PlayerController getPlayerController() { return this.playerController; }

    /**
     * Returns ball
     * @return ball (Rect)
     */
    public Rect getBall() { return this.ball; }

    /**
     * Returns ballController
     * @return ballController (Ball)
     */
    public Ball getBallController() { return this.ballController; }

    /**
     * Returns difficulty
     * @return difficulty (int)
     */
    public int getDifficulty() { return this.difficulty; }

    /**
     * Returns position
     * @return position (double[])
     */
    public double[] getPosition() { return this.position; }

    ///////////////////////////// SETTERS /////////////////////////////

    /**
     * Sets playerController
     * @param playerController PlayerController object to set to (PlayerController)
     */
    public void setPlayerController(PlayerController playerController) { this.playerController = playerController; }

    /**
     * Sets ball
     * @param ball Rect object to set to (Rect)
     */
    public void setBall(Rect ball) { this.ball = ball; }

    /**
     * Sets ballController
     * @param ballController Ball object to set to (Ball)
     */
    public void setBallController(Ball ballController) { this.ballController = ballController; }

    /**
     * Sets difficulty
     * @param difficulty difficulty to set to (int)
     */
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    /**
     * Sets position
     * @param position position to set to (double[])
     */
    public void setPosition(double[] position) { this.position = position; }

    ///////////////////////////// METHODS /////////////////////////////

    /**
     * Updates AI
     * @param dt change in time (double)
     */
    public void update(double dt)
    {
        playerController.update(dt);

        switch (this.difficulty)
        {
            case 0:
                moveEasy(dt);
                break;
            case 1:
                moveMedium(dt);
                break;
            case 2:
                moveHard(dt);
                break;
            case 3:
                moveImpossible(dt);
                break;
        }
    }

    /**
     * Predicts where the ball will land with a certain number of iterations
     * @param steps number of iterations (int)
     * @return position (double[])
     */
    public double[] predictPosition(int steps)
    {
        // Copy ball
        Ball predictedBall = new Ball(this.ballController);

        for (int i = 0; i < steps; i++)
        {
            //if (predictedBall.simulateUpdate(0.01) != 0) { break; }
            switch (predictedBall.simulateUpdate(0.01))
            {
                case 0:
                    continue; // Continue if not major event, run the next iteration
                case 1:
                    this.position[0] = predictedBall.getRect().getX();
                    this.position[1] = predictedBall.getRect().getY();
                    return this.position; // The ball is in the right paddle's x range, but not y range. Paddle moves to predicted position
                case 2:
                    this.position[0] = -1;
                    this.position[1] = -1;
                    return this.position; // The paddle is correctly positioned, return -1, -1
                case 3:
                    this.position[0] = 0;
                    this.position[1] = (Constants.SCREEN_HEIGHT - Constants.TOOLBAR_HEIGHT - Constants.INSETS_BOTTOM) / 2;
                    break; // The ball moves into the left paddle's x range. Paddle moves in middle of screen
            }
        }
        this.position[0] = predictedBall.getRect().getX();
        this.position[1] = predictedBall.getRect().getY();
        return this.position;
    }

    /**
     * Moves AI in easy mode
     * @param dt change in time (double)
     */
    public void moveEasy(double dt)
    {
        // Move paddle using a sine wave. The center of the sine wave is the center of screen, and the amplitude is the height of the screen
        double amplitude = (float)Constants.SCREEN_HEIGHT;
        double frequency = 0.1;
        double y = amplitude * Math.sin(2 * Math.PI * frequency * Time.getTimeSeconds()) + (Constants.SCREEN_HEIGHT - Constants.TOOLBAR_HEIGHT - Constants.INSETS_BOTTOM) / 2;
        if (y < this.playerController.getRect().getY()) { this.playerController.moveUp(dt, 0.35); }
        else if (y > this.playerController.getRect().getY()) { this.playerController.moveDown(dt, 0.35); }
    }

    /**
     * Moves AI in medium mode
     * @param dt change in time (double)
     */
    private void moveMedium(double dt)
    {
        // Predict where the ball will land with 1 iteration and move paddle there
        double[] position = predictPosition(1);

        // If the paddle is around the ball's y position, return
        if (position[0] == -1 && position[1] == -1) { return; }

        if (position[1] < this.playerController.getRect().getY()) { this.playerController.moveUp(dt, 0.4); }
        else if (position[1] > this.playerController.getRect().getY()) { this.playerController.moveDown(dt, 0.4); }
    }

    /**
     * Moves AI in hard mode
     * @param dt change in time (double)
     */
    private void moveHard(double dt)
    {
        // Predict where the ball will land with 10 iterations and move paddle there
        double[] position = predictPosition(50);

        // If the paddle is around the ball's y position, return
        if (position[0] == -1 && position[1] == -1) { return; }

        // Smooth the movement
        double speedMultiplier = smoothMove(0.25, 0.75, position[1], this.playerController.getRect().getY());

        // If the paddle is around the ball's y position, return
        if (this.playerController.getRect().getY() + this.playerController.getRect().getHeight() / 2 > position[1] - Constants.AI_EPSILON && this.playerController.getRect().getY() + this.playerController.getRect().getHeight() / 2 < position[1] + Constants.AI_EPSILON) { return; }

        if (position[1] < this.playerController.getRect().getY()) { this.playerController.moveUp(dt, speedMultiplier); }
        else if (position[1] > this.playerController.getRect().getY()) { this.playerController.moveDown(dt, speedMultiplier); }
    }

    /**
     * Moves AI in impossible mode
     * @param dt change in time (double)
     */
    private void moveImpossible(double dt)
    {
        // Predict where the ball will land with 100 iterations and move paddle there, and add effects with moving the paddle just before the ball hits
        double[] position = predictPosition(1000);

        // If the paddle is around the ball's y position, return
        if (position[0] == -1 && position[1] == -1) { return; }

        // Smooth the movement
        double speedMultiplier = smoothMove(0, 1, position[1], this.playerController.getRect().getY());

        // If the paddle is around the ball's y position, return
        if (this.playerController.getRect().getY() + this.playerController.getRect().getHeight() / 2 > position[1] - Constants.AI_EPSILON && this.playerController.getRect().getY() + this.playerController.getRect().getHeight() / 2 < position[1] + Constants.AI_EPSILON) { return; }

        if (position[1] < this.playerController.getRect().getY()) { this.playerController.moveUp(dt, 2); }
        else if (position[1] > this.playerController.getRect().getY()) { this.playerController.moveDown(dt, 2); }

        // TODO: Add effects with moving the paddle just before the ball hits
    }

    /**
     * Returns the distance between two y positions
     * @param y1 first y position (double)
     * @param y2 second y position (double)
     * @return distance (double)
     */
    private static double distance(double y1, double y2)
    {
        return Math.sqrt(Math.pow(y1, 2) + Math.pow(y2, 2));
    }

    /**
     * Returns the linear interpolation between two values
     * @param a first value (double)
     * @param b second value (double)
     * @param f interpolation factor (double)
     * @return linear interpolation (double)
     */
    private static double lerp(double a, double b, double f)
    {
        return a + f * (b - a);
    }

    /**
     * Smooths the movement by reducing or increasing the speed proportionally to the distance between the paddle and the ball
     * @param a minimum speed (double)
     * @param b maximum speed (double)
     * @param y1 first y position (double)
     * @param y2 second y position (double)
     * @return speed multiplier (double)
     */
    private static double smoothMove(double a, double b, double y1, double y2)
    {
        // Smooth the movement by reducing or increasing the speed proportionally to the distance between the paddle and the ball. The minimum speed is 0.75 and the maximum speed is 1.5. The min is when the paddle is at the same height as the ball
        return lerp(a, b, distance(y1, y2) / (Constants.SCREEN_HEIGHT - Constants.TOOLBAR_HEIGHT - Constants.INSETS_BOTTOM));
    }
}
