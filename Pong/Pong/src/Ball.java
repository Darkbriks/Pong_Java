public class Ball
{
    public Window window;
    public Rect rect;
    public Rect leftPaddle, rightPaddle;

    public double vx = -100, vy = 75;

    public Ball(Window window, Rect rect, Rect leftPaddle, Rect rightPaddle)
    {
        this.window = window;
        this.rect = rect;
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
        resetBall();
    }

    // Copy constructor
    public Ball(Ball ball)
    {
        this.window = ball.window;
        this.rect = new Rect(ball.rect.x, ball.rect.y, ball.rect.width, ball.rect.height, ball.rect.color);
        this.leftPaddle = new Rect(ball.leftPaddle.x, ball.leftPaddle.y, ball.leftPaddle.width, ball.leftPaddle.height, ball.leftPaddle.color);
        this.rightPaddle = new Rect(ball.rightPaddle.x, ball.rightPaddle.y, ball.rightPaddle.width, ball.rightPaddle.height, ball.rightPaddle.color);
        this.vx = ball.vx;
        this.vy = ball.vy;
    }

    private void resetBall()
    {
        this.rect.x = (Constants.SCREEN_WIDTH - Constants.BALL_RADIUS) / 2;
        this.rect.y = (Constants.SCREEN_HEIGHT - Constants.BALL_RADIUS) / 2;
        this.vx = (Math.random() < 0.5) ? -200 : 200;
        this.vy = (Math.random() < 0.5) ? -35 : 35;
    }

    public int update(double dt, int simulation)
    {
        // Check bounce on players' paddles
        if (vx < 0)
        {
            // If the ball is within the left paddle's x range and y range, then bounce
            if (this.rect.x <= this.leftPaddle.x + this.leftPaddle.width && this.rect.x >= this.leftPaddle.x && this.rect.y >= this.leftPaddle.y && this.rect.y <= this.leftPaddle.y + this.leftPaddle.height)
            {
                vx *= -1;
                vy = (vy < Constants.EPSILON && vy > -Constants.EPSILON) ? (this.leftPaddle.prevY == this.leftPaddle.y) ? vy : (this.leftPaddle.prevY < this.leftPaddle.y) ? 7.5 : -7.5 : vy;
                vy *= (this.leftPaddle.prevY == this.leftPaddle.y) ? 1 : (this.leftPaddle.prevY < this.leftPaddle.y) ? (vy > 0) ? Constants.BALL_SPEED_INCREASE_MULTIPLIER : Constants.BALL_SPEED_DECREASE_MULTIPLIER : (vy < 0) ? Constants.BALL_SPEED_INCREASE_MULTIPLIER : Constants.BALL_SPEED_DECREASE_MULTIPLIER;
            }
            // Else if the ball is outside the left paddle's x range and y range, player loses
            else if (this.rect.x <= this.leftPaddle.x)
            {
                resetBall();
                if (simulation == 0) { window.rightScoreInt += 1; }
            }
        }
        else
        {
            // If the ball is within the right paddle's x range and y range, then bounce
            if (this.rect.x + this.rect.width >= this.rightPaddle.x && this.rect.x + this.rect.width <= this.rightPaddle.x + this.rightPaddle.width && this.rect.y >= this.rightPaddle.y && this.rect.y <= this.rightPaddle.y + this.rightPaddle.height)            {
                vx *= -1;
                vy = (vy < Constants.EPSILON && vy > -Constants.EPSILON) ? (this.rightPaddle.prevY == this.rightPaddle.y) ? vy : (this.rightPaddle.prevY < this.rightPaddle.y) ? 7.5 : -7.5 : vy;
                vy *= (this.rightPaddle.prevY == this.rightPaddle.y) ? 1 : (this.rightPaddle.prevY < this.rightPaddle.y) ? (vy > 0) ? Constants.BALL_SPEED_INCREASE_MULTIPLIER : Constants.BALL_SPEED_DECREASE_MULTIPLIER : (vy < 0) ? Constants.BALL_SPEED_INCREASE_MULTIPLIER : Constants.BALL_SPEED_DECREASE_MULTIPLIER;
            }
            // Else if the ball is outside the right paddle's x range and y range, AI loses
            else if (this.rect.x + this.rect.width >= this.rightPaddle.x + this.rightPaddle.width)
            {
                resetBall();
                if (simulation == 0) { window.leftScoreInt += 1; }
            }

            // Return 1 if the ball is in the right paddle's x range (it's for the AI to know when to stop computing)
            if (this.rect.x + this.rect.width >= this.rightPaddle.x && this.rect.x + this.rect.width <= this.rightPaddle.x + this.rightPaddle.width && simulation == 1) { return 1; }
        }

        // Check bounce on top and bottom walls. If the ball is within the top or bottom walls' y range, teleport to the edge of the screen and bounce
        if (this.rect.y <= Constants.TOOLBAR_HEIGHT || this.rect.y + this.rect.height >= Constants.SCREEN_HEIGHT - Constants.INSETS_BOTTOM) { this.rect.y = (this.rect.y <= Constants.TOOLBAR_HEIGHT) ? Constants.TOOLBAR_HEIGHT : Constants.SCREEN_HEIGHT - Constants.INSETS_BOTTOM - this.rect.height; vy *= -1; }

        this.rect.x += vx * dt;
        this.rect.y += vy * dt;

        return 0;
    }
}
