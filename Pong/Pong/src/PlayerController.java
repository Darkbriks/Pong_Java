import java.awt.event.KeyEvent;

public class PlayerController
{
    public Rect rect;
    public KL keyListener;

    // Constructor for physical player
    public PlayerController(Rect rect, KL keyListener) { this.rect = rect; this.keyListener = keyListener; }

    // Constructor for AI player
    public PlayerController(Rect rect) { this.rect = rect; keyListener = null; }

    public void update(double dt)
    {
        // Update previous position
        this.rect.prevX = this.rect.x;
        this.rect.prevY = this.rect.y;

        if (this.keyListener != null)
        {
            // Move physical player
            if (keyListener.isKeyPressed(KeyEvent.VK_UP)) { moveUp(dt, 1); }
            else if (keyListener.isKeyPressed(KeyEvent.VK_DOWN)) { moveDown(dt, 1); }
        }
    }

    public void moveUp(double dt, double speedMultiplier)
    {
        if (this.rect.y - Constants.PADDLE_SPEED * dt * speedMultiplier < Constants.TOOLBAR_HEIGHT) { this.rect.y = Constants.TOOLBAR_HEIGHT; return; }
        this.rect.y -= Constants.PADDLE_SPEED * dt * speedMultiplier;
    }

    public void moveDown(double dt, double speedMultiplier)
    {
        if (this.rect.y + Constants.PADDLE_SPEED * dt * speedMultiplier + this.rect.height > Constants.SCREEN_HEIGHT - Constants.INSETS_BOTTOM) { this.rect.y = Constants.SCREEN_HEIGHT - Constants.INSETS_BOTTOM - this.rect.height; return; }
        this.rect.y += Constants.PADDLE_SPEED * dt * speedMultiplier;
    }
}
