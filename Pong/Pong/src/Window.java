import javax.swing.JFrame;
import java.awt.*;

public class Window extends JFrame implements Runnable
{
    // TODO : javadoc
    ///////////////////////////// VARIABLES /////////////////////////////
    private final Graphics2D g2;
    private final KL keyListener = new KL();
    private final Rect playerOne, ai, ball;
    private final PlayerController playerController;
    private final AIController aiController;
    private final Ball ballController;
    private final Text leftScore, rightScore;
    private int leftScoreInt = 0, rightScoreInt = 0;

    ///////////////////////////// CONSTRUCTORS /////////////////////////////

    public Window()
    {
        // Set attributes
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setTitle(Constants.SCREEN_TITLE);
        this.setResizable(Constants.SCREEN_RESIZABLE);
        this.setVisible(Constants.SCREEN_VISIBLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);

        // Set constants
        Constants.TOOLBAR_HEIGHT = this.getInsets().top;
        Constants.INSETS_BOTTOM = this.getInsets().bottom;

        // Create graphics
        g2 = (Graphics2D)this.getGraphics();

        // Create rectangle for ball and players
        playerOne = new Rect(Constants.HZ_PADDING, (Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT) / 2, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Constants.PADDLE_COLOR);
        ai = new Rect(Constants.SCREEN_WIDTH - Constants.HZ_PADDING - Constants.PADDLE_WIDTH, (Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT) / 2, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Constants.PADDLE_COLOR);
        ball = new Rect((Constants.SCREEN_WIDTH - Constants.BALL_RADIUS) / 2, (Constants.SCREEN_HEIGHT - Constants.BALL_RADIUS) / 2, Constants.BALL_RADIUS, Constants.BALL_RADIUS, Constants.BALL_COLOR);

        // Create controller for ball and players
        ballController = new Ball(this, playerOne, ai, ball);
        playerController = new PlayerController(playerOne, keyListener);
        //ballController = new Ball(this, ball, playerOne, ai, playerController);
        aiController = new AIController(new PlayerController(ai), ball, ballController, Constants.AI_LEVEL);

        // Init score
        leftScore = new Text(leftScoreInt, Constants.TEXT_FONT, Constants.TEXT_COLOR, (float)(Constants.HZ_PADDING + Constants.PADDLE_WIDTH + Constants.TEXT_X_PADDING), (float)(Constants.TOOLBAR_HEIGHT + Constants.TEXT_Y_PADDING));
        rightScore = new Text(rightScoreInt, Constants.TEXT_FONT, Constants.TEXT_COLOR, (float)(Constants.SCREEN_WIDTH - Constants.HZ_PADDING - Constants.PADDLE_WIDTH - Constants.TEXT_X_PADDING - Constants.TEXT_SIZE), (float)(Constants.TOOLBAR_HEIGHT + Constants.TEXT_Y_PADDING));
    }

    ///////////////////////////// GETTERS /////////////////////////////

    public Graphics2D getG2() { return g2; }

    public KL getKeyListener() { return keyListener; }

    public Rect getPlayerOne() { return playerOne; }

    public Rect getAi() { return ai; }

    public Rect getBall() { return ball; }

    public AIController getAiController() { return aiController; }

    public Ball getBallController() { return ballController; }

    public Text getLeftScore() { return leftScore; }

    public Text getRightScore() { return rightScore; }

    public int getLeftScoreInt() { return leftScoreInt; }

    public int getRightScoreInt() { return rightScoreInt; }

    ///////////////////////////// SETTERS /////////////////////////////

    public void setLeftScoreInt(int leftScoreInt) { this.leftScoreInt = leftScoreInt; }

    public void setRightScoreInt(int rightScoreInt) { this.rightScoreInt = rightScoreInt; }

    public void  incrementLeftScoreInt() {this.leftScoreInt++;}

    public void incrementRightScoreInt() {this.rightScoreInt++;}

    ///////////////////////////// METHODS /////////////////////////////

    public void update(double dt)
    {
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);

        playerController.update(dt);
        aiController.update(dt);
        ballController.update(dt);

        leftScore.setText(leftScoreInt);
        rightScore.setText(rightScoreInt);
    }

    private void draw(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.black);
        g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        playerOne.draw(g2);
        ai.draw(g2);
        ball.draw(g2);

        leftScore.draw(g2);
        rightScore.draw(g2);
    }

    public void run()
    {
        double lastFrameTime = 0.0;
        while (true)
        {
            // Compute time
            double time = Time.getTimeSeconds();
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;

            update(deltaTime);
        }
    }
}
