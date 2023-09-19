public class Ball
{
    ///////////////////////////// VARIABLES /////////////////////////////

    private final Window window;
    private final Rect leftPaddle, rightPaddle, rect;
    private double vx, vy;

    ///////////////////////////// CONSTRUCTORS /////////////////////////////

    /**
     * Constructor for Ball class
     * @param window Window object (Window)
     * @param leftPaddle left paddle (Rect)
     * @param rightPaddle right paddle (Rect)
     * @param rect Rect object to represent ball (Rect)
     */
    public Ball(Window window, Rect leftPaddle, Rect rightPaddle, Rect rect)
    {
        this.window = window;
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
        this.rect = rect;
        this.vx = 0;
        this.vy = 0;
        resetBall();
    }

    /**
     * Copy constructor for Ball class
     * @param ball Ball object to copy (Ball)
     */
    public Ball(Ball ball)
    {
        this.window = ball.window;
        this.leftPaddle = ball.leftPaddle;
        this.rightPaddle = ball.rightPaddle;
        this.rect = ball.rect;
        this.vx = ball.vx;
        this.vy = ball.vy;
    }

    ///////////////////////////// GETTERS /////////////////////////////

    /**
     * Returns rect object representing ball
     * @return rect object representing ball (Rect)
     */
    public Rect getRect() { return this.rect; }

    /**
     * Returns left paddle
     * @return left paddle (Rect)
     */
    public Rect getLeftPaddle() { return this.leftPaddle; }

    /**
     * Returns right paddle
     * @return right paddle (Rect)
     */
    public Rect getRightPaddle() { return this.rightPaddle; }

    /**
     * Returns x-velocity of ball
     * @return x-velocity of ball (double)
     */
    public double getVX() { return this.vx; }

    /**
     * Returns y-velocity of ball
     * @return y-velocity of ball (double)
     */
    public double getVY() { return this.vy; }

    ///////////////////////////// SETTERS /////////////////////////////

    /**
     * Sets x-velocity of ball
     * @param vx x-velocity of ball (double)
     */
    public void setVX(double vx) { this.vx = vx; }

    /**
     * Sets y-velocity of ball
     * @param vy y-velocity of ball (double)
     */
    public void setVY(double vy) { this.vy = vy; }

    ///////////////////////////// METHODS /////////////////////////////

    /**
     * Resets ball to center of screen and assigns random velocity
     */
    private void resetBall()
    {
        this.rect.setX((Constants.SCREEN_WIDTH - Constants.BALL_RADIUS) / 2);
        this.rect.setY((Constants.SCREEN_HEIGHT - Constants.BALL_RADIUS) / 2);
        this.vx = (Math.random() < 0.5) ? -200 : 200;
        this.vy = (Math.random() < 0.5) ? -35 : 35;
    }

    /**
     * Updates ball position and checks for bounce on paddles and walls
     * @param dt time since last frame (double)
     */
    public void update(double dt)
    {
        // Check bounce on players' paddles
        if (vx < 0)
        {
            // If the ball is within the left paddle's x range and y range, then bounce
            if (this.rect.getX() <= this.leftPaddle.getX() + this.leftPaddle.getWidth() && this.rect.getX() >= this.leftPaddle.getX() && this.rect.getY() >= this.leftPaddle.getY() && this.rect.getY() <= this.leftPaddle.getY() + this.leftPaddle.getHeight())
            {
                bounceOnPaddle();
            }
            // Else if the ball is outside the left paddle's x range and y range, player loses
            else if (this.rect.getX() <= this.leftPaddle.getX()) { resetBall(); window.incrementRightScoreInt(); }
        }
        else
        {
            // If the ball is within the right paddle's x range and y range, then bounce
            if (this.rect.getX() + this.rect.getWidth() >= this.rightPaddle.getX() && this.rect.getX() + this.rect.getWidth() <= this.rightPaddle.getX() + this.rightPaddle.getWidth() && this.rect.getY() >= this.rightPaddle.getY() && this.rect.getY() <= this.rightPaddle.getY() + this.rightPaddle.getHeight())
            {
                bounceOnPaddle();
            }
            // Else if the ball is outside the right paddle's x range and y range, AI loses
            else if (this.rect.getX() + this.rect.getWidth() >= this.rightPaddle.getX() + this.rightPaddle.getWidth()) { resetBall(); window.incrementLeftScoreInt(); }
        }

        // Check bounce on top and bottom walls. If the ball is within the top or bottom walls' y range, teleport to the edge of the screen and bounce
        bounceOnWall();

        this.rect.setX(this.rect.getX() + vx * dt);
        this.rect.setY(this.rect.getY() + vy * dt);
    }

    /**
     * Updates ball position and checks for bounce on paddles and walls
     * @param dt time since last frame (double)
     * @return 0 if not major event
     *        1 if ball is in right paddle's x range
     *        2 if ball is in right paddle's x range and y range
     *        3 if ball moves into left paddle's x range
     */
    public int simulateUpdate(double dt)
    {
        // TODO : Check why the ai comportement is confusing
        // Check bounce on players' paddles
        if (vx < 0)
        {
            return 3;
        }
        else
        {
            // If the ball is within the right paddle's x range and y range, then bounce
            if (this.rect.getX() + this.rect.getWidth() >= this.rightPaddle.getX() && this.rect.getX() + this.rect.getWidth() <= this.rightPaddle.getX() + this.rightPaddle.getWidth() && this.rect.getY() >= this.rightPaddle.getY() && this.rect.getY() <= this.rightPaddle.getY() + this.rightPaddle.getHeight())
            {
                return 2;
            }
            // Else if the ball is outside the right paddle's x range and y range, AI loses
            else if (this.rect.getX() + this.rect.getWidth() >= this.rightPaddle.getX() + this.rightPaddle.getWidth()) { return 1; }
        }

        // Check bounce on top and bottom walls. If the ball is within the top or bottom walls' y range, teleport to the edge of the screen and bounce
        bounceOnWall();

        //this.rect.setX(this.rect.getX() + vx * dt);
        //this.rect.setY(this.rect.getY() + vy * dt);

        return 0;
    }

    private void bounceOnPaddle()
    {
        vx *= -1;
        vy = (vy < Constants.EPSILON && vy > -Constants.EPSILON) ? (this.leftPaddle.getPrevY() == this.leftPaddle.getY()) ? vy : (this.leftPaddle.getPrevY()  < this.leftPaddle.getY()) ? 7.5 : -7.5 : vy;
        vy *= (this.leftPaddle.getPrevY()  == this.leftPaddle.getY()) ? 1 : (this.leftPaddle.getPrevY()  < this.leftPaddle.getY()) ? (vy > 0) ? Constants.BALL_SPEED_INCREASE_MULTIPLIER : Constants.BALL_SPEED_DECREASE_MULTIPLIER : (vy < 0) ? Constants.BALL_SPEED_INCREASE_MULTIPLIER : Constants.BALL_SPEED_DECREASE_MULTIPLIER;
    }

    private void bounceOnWall()
    {
        if (this.rect.getY() <= Constants.TOOLBAR_HEIGHT || this.rect.getY() + this.rect.getHeight() >= Constants.SCREEN_HEIGHT - Constants.INSETS_BOTTOM)
        {
            this.rect.setY((this.rect.getY() <= Constants.TOOLBAR_HEIGHT) ? Constants.TOOLBAR_HEIGHT : Constants.SCREEN_HEIGHT - Constants.INSETS_BOTTOM - this.rect.getHeight());
            vy *= -1;
        }
    }
}
