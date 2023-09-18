import java.awt.*;
import java.awt.geom.Rectangle2D;

public class AIController
{
    public PlayerController playerController;
    public Rect ball;
    public Ball ballController;
    public int difficulty; // 0 = easy, 1 = medium, 2 = hard, 3 = impossible
    // In easy mode, the AI will move up and down randomly
    // In medium mode, the AI will predict where the ball will land with 1 iteration and move paddle there
    // In hard mode, the AI will predict where the ball will land with 10 iterations and move paddle there
    // In impossible mode, the AI will predict where the ball will land with 100 iterations and move paddle there, and add effects with moving the paddle just before the ball hits

    public double[] position;

    public AIController(PlayerController playerController, Rect ball, Ball ballController, int difficulty)
    {
        this.playerController = playerController;
        this.ball = ball;
        this.ballController = ballController;
        this.difficulty = difficulty;
        this.position = new double[2];
    }

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

    private double[] predictPosition(int steps)
    {
        double[] position = new double[2];

        // Copy ball
        Ball predictedBall = new Ball(this.ballController);

        for (int i = 0; i < steps; i++) {if (predictedBall.update(0.01, 1) != 0) { break; }}
        position[0] = predictedBall.rect.x;
        position[1] = predictedBall.rect.y;
        return position;
    }

    private void moveEasy(double dt)
    {
        // Move paddle using a sine wave. The center of the sine wave is the center of screen, and the amplitude is the height of the screen
        double amplitude = (float)Constants.SCREEN_HEIGHT;
        double frequency = 0.1;
        double y = amplitude * Math.sin(2 * Math.PI * frequency * Time.getTime()) + (Constants.SCREEN_HEIGHT - Constants.TOOLBAR_HEIGHT - Constants.INSETS_BOTTOM) / 2;
        if (y < this.playerController.rect.y) { this.playerController.moveUp(dt, 0.35); }
        else if (y > this.playerController.rect.y) { this.playerController.moveDown(dt, 0.35); }
    }

    private void moveMedium(double dt)
    {
        // Predict where the ball will land with 1 iteration and move paddle there
        double[] position = predictPosition(1);
        if (position[1] < this.playerController.rect.y) { this.playerController.moveUp(dt, 0.4); }
        else if (position[1] > this.playerController.rect.y) { this.playerController.moveDown(dt, 0.4); }
    }

    private void moveHard(double dt)
    {
        // Predict where the ball will land with 10 iterations and move paddle there
        double[] position = predictPosition(50);

        // Smooth the movement
        double speedMultiplier = smoothMove(0.25, 0.75, position[1], this.playerController.rect.y);

        // If the paddle is arround the ball's y position, return
        if (this.playerController.rect.y + this.playerController.rect.height / 2 > position[1] - Constants.AI_EPSILON && this.playerController.rect.y + this.playerController.rect.height / 2 < position[1] + Constants.AI_EPSILON) { return; }

        if (position[1] < this.playerController.rect.y) { this.playerController.moveUp(dt, speedMultiplier); }
        else if (position[1] > this.playerController.rect.y) { this.playerController.moveDown(dt, speedMultiplier); }
    }

    private void moveImpossible(double dt)
    {
        // Predict where the ball will land with 100 iterations and move paddle there, and add effects with moving the paddle just before the ball hits
        double[] position = predictPosition(1000);

        // Smooth the movement
        double speedMultiplier = smoothMove(0, 1, position[1], this.playerController.rect.y);

        // If the paddle is arround the ball's y position, return
        if (this.playerController.rect.y + this.playerController.rect.height / 2 > position[1] - Constants.AI_EPSILON && this.playerController.rect.y + this.playerController.rect.height / 2 < position[1] + Constants.AI_EPSILON) { return; }

        if (position[1] < this.playerController.rect.y) { this.playerController.moveUp(dt, 2); }
        else if (position[1] > this.playerController.rect.y) { this.playerController.moveDown(dt, 2); }

        // TODO: Add effects with moving the paddle just before the ball hits
    }

    private double distance(double y1, double y2)
    {
        return Math.sqrt(Math.pow(y1, 2) + Math.pow(y2, 2));
    }

    private double lerp(double a, double b, double f)
    {
        return a + f * (b - a);
    }

    private double smoothMove(double a, double b, double y1, double y2)
    {
        // Smooth the movement by reducing or increasing the speed proportionally to the distance between the paddle and the ball. The minimum speed is 0.75 and the maximum speed is 1.5. The min is when the paddle is at the same height as the ball
        return this.lerp(a, b, this.distance(y1, y2) / (Constants.SCREEN_HEIGHT - Constants.TOOLBAR_HEIGHT - Constants.INSETS_BOTTOM));
    }
}
