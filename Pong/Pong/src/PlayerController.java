import java.awt.event.KeyEvent;

public class PlayerController
{
    // TODO : Add java doc

    ///////////////////////////// VARIABLES /////////////////////////////
    private final Rect rect;
    private final KL keyListener;

    private boolean speedBoost = false;
    private double speedBoostTime = 0.0;
    private double speedBoostCooldownTime = 0.0;

    ///////////////////////////// CONSTRUCTORS /////////////////////////////

    // Constructor for physical player
    public PlayerController(Rect rect, KL keyListener) { this.rect = rect; this.keyListener = keyListener; }

    // Constructor for AI player
    public PlayerController(Rect rect) { this.rect = rect; keyListener = null; }

    ///////////////////////////// GETTERS /////////////////////////////

    public Rect getRect() { return rect; }

    public KL getKeyListener() { return keyListener; }

    public boolean getSpeedBoost() {return this.speedBoost;}

    ///////////////////////////// METHODS /////////////////////////////

    private void activateSpeedBoost()
    {
        if (!this.speedBoost && this.speedBoostCooldownTime == 0.0)
        {
            this.speedBoost = true;
            this.speedBoostTime = Constants.SPEED_BOOST_INPUT_THRESHOLD;
        }
    }

    public void desactivateSpeedBoost()
    {
        if (this.speedBoost)
        {
            this.speedBoost = false;
            this.speedBoostTime = 0;
            this.speedBoostCooldownTime = 0;
        }
    }

    public void update(double dt)
    {
        if (this.keyListener != null)
        {
            // Move physical player
            if (keyListener.isKeyPressed(KeyEvent.VK_UP)) { moveUp(dt, 1); }
            else if (keyListener.isKeyPressed(KeyEvent.VK_DOWN)) { moveDown(dt, 1); }
            else if (keyListener.isKeyPressed(KeyEvent.VK_RIGHT)) { this.activateSpeedBoost(); }
        }

        // Speed boost
        if (this.speedBoost)
        {
            this.speedBoostTime = this.clamp(this.speedBoostTime - dt, 0, Constants.SPEED_BOOST_INPUT_THRESHOLD);
            if (this.speedBoostTime == 0) {this.speedBoost = false; this.speedBoostCooldownTime = Constants.SPEED_BOOST_COOLDOWN;}
        }
        else if (this.speedBoostCooldownTime > 0.0) {this.speedBoostCooldownTime = this.clamp(this.speedBoostCooldownTime -dt, 0, Constants.SPEED_BOOST_COOLDOWN);}
    }

    public void moveUp(double dt, double speedMultiplier)
    {
        if (this.rect.getY() - Constants.PADDLE_SPEED * dt * speedMultiplier < Constants.TOOLBAR_HEIGHT) { this.rect.setY(Constants.TOOLBAR_HEIGHT); return; }
        //this.rect.y -= Constants.PADDLE_SPEED * dt * speedMultiplier;
        this.rect.setY(this.rect.getY() - (Constants.PADDLE_SPEED * dt * speedMultiplier));
    }

    public void moveDown(double dt, double speedMultiplier)
    {
        if (this.rect.getY() + Constants.PADDLE_SPEED * dt * speedMultiplier + this.rect.getHeight() > Constants.SCREEN_HEIGHT - Constants.INSETS_BOTTOM)
        { this.rect.setY(Constants.SCREEN_HEIGHT - Constants.INSETS_BOTTOM - this.rect.getHeight()); return; }
        //this.rect.y += Constants.PADDLE_SPEED * dt * speedMultiplier;
        this.rect.setY(this.rect.getY() + (Constants.PADDLE_SPEED * dt * speedMultiplier));
    }

    private double clamp(double x, double min, double max) { return (x < min) ? min : Math.min(x, max); }
}
