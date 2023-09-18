import java.awt.*;

public class Constants
{
    // Screen
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final String SCREEN_TITLE = "Pong";

    // Paddle
    public static final double PADDLE_WIDTH = 10;
    public static final double PADDLE_HEIGHT = 100;
    public static final Color PADDLE_COLOR = Color.WHITE;
    public static final double PADDLE_SPEED = 200;

    // Ball
    public static final double BALL_RADIUS = 10;
    public static final Color BALL_COLOR = Color.WHITE;
    public static final double BALL_SPEED_INCREASE_MULTIPLIER = 1.5;
    public static final double BALL_SPEED_DECREASE_MULTIPLIER = 0.5;
    public static final int EPSILON = 5;

    // Padding
    public static final double HZ_PADDING = 20;

    // Toolbar
    public static double TOOLBAR_HEIGHT;
    public static double INSETS_BOTTOM;

    // AI
    public static final int AI_EPSILON = 40;

    // Text
    public static final int TEXT_SIZE = 20;
    public static final Color TEXT_COLOR = Color.WHITE;
    public static final double TEXT_X_PADDING = 20;
    public static final double TEXT_Y_PADDING = 30;
    public static final Font TEXT_FONT = new Font("Times New Roman", Font.PLAIN, TEXT_SIZE);
}
