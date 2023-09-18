import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Window extends JFrame implements Runnable
{
    public Graphics2D g2;
    public KL keyListener = new KL();
    public Rect playerOne, ai, ball;
    public PlayerController playerController;
    public AIController aiController;
    public Ball ballController;
    public Text leftScore, rightScore;
    public int leftScoreInt = 0, rightScoreInt = 0;

    public Window()
    {
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setTitle(Constants.SCREEN_TITLE);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);

        Constants.TOOLBAR_HEIGHT = this.getInsets().top;
        Constants.INSETS_BOTTOM = this.getInsets().bottom;

        g2 = (Graphics2D)this.getGraphics();

        playerOne = new Rect(Constants.HZ_PADDING, (Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT) / 2, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Constants.PADDLE_COLOR);
        ai = new Rect(Constants.SCREEN_WIDTH - Constants.HZ_PADDING - Constants.PADDLE_WIDTH, (Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT) / 2, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Constants.PADDLE_COLOR);
        ball = new Rect((Constants.SCREEN_WIDTH - Constants.BALL_RADIUS) / 2, (Constants.SCREEN_HEIGHT - Constants.BALL_RADIUS) / 2, Constants.BALL_RADIUS, Constants.BALL_RADIUS, Constants.BALL_COLOR);

        playerController = new PlayerController(playerOne, keyListener);
        ballController = new Ball(this, ball, playerOne, ai);
        aiController = new AIController(new PlayerController(ai), ball, ballController, Constants.AI_LEVEL);

        leftScore = new Text(leftScoreInt, Constants.TEXT_FONT, Constants.TEXT_COLOR, (float)(Constants.HZ_PADDING + Constants.PADDLE_WIDTH + Constants.TEXT_X_PADDING), (float)(Constants.TOOLBAR_HEIGHT + Constants.TEXT_Y_PADDING));
        rightScore = new Text(rightScoreInt, Constants.TEXT_FONT, Constants.TEXT_COLOR, (float)(Constants.SCREEN_WIDTH - Constants.HZ_PADDING - Constants.PADDLE_WIDTH - Constants.TEXT_X_PADDING - Constants.TEXT_SIZE), (float)(Constants.TOOLBAR_HEIGHT + Constants.TEXT_Y_PADDING));
    }

    public void update(double dt)
    {
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);

        playerController.update(dt);
        aiController.update(dt);
        ballController.update(dt, 0);

        leftScore.setText(leftScoreInt);
        rightScore.setText(rightScoreInt);
    }

    public void draw(Graphics g)
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
            double time = Time.getTime();
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;

            update(deltaTime);

            // Cap the framerate
            try
            {
                Thread.sleep(30);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
