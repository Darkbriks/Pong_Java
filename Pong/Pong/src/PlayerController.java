import java.awt.event.KeyEvent;

public class PlayerController
{
    // TODO : Add java doc

    ///////////////////////////// VARIABLES /////////////////////////////
    private final Rect rect;
    private final KL keyListener;

    /*private boolean speedBoost = false;
    private double speedBoostTime = 0.0;
    private double speedBoostDisableTime = 0.0;*/

    ///////////////////////////// CONSTRUCTORS /////////////////////////////

    // Constructor for physical player
    public PlayerController(Rect rect, KL keyListener) { this.rect = rect; this.keyListener = keyListener; }

    // Constructor for AI player
    public PlayerController(Rect rect) { this.rect = rect; keyListener = null; }

    ///////////////////////////// GETTERS /////////////////////////////

    public Rect getRect() { return rect; }

    public KL getKeyListener() { return keyListener; }

    ///////////////////////////// METHODS /////////////////////////////

    /*public boolean getSpeedBoost()
    {
        return this.speedBoost;
    }

    private void activateSpeedBoost()
    {
        if (!this.speedBoost && this.speedBoostDisableTime < 0.0)
        {
            this.speedBoost = true;
            this.speedBoostTime = Constants.SPEED_BOOST_DURATION;
        }
    }*/

    public void update(double dt)
    {
        // Update previous position
        //this.rect.prevX = this.rect.x;
        //this.rect.prevY = this.rect.y;

        if (this.keyListener != null)
        {
            // Move physical player
            if (keyListener.isKeyPressed(KeyEvent.VK_UP)) { moveUp(dt, 1); }
            else if (keyListener.isKeyPressed(KeyEvent.VK_DOWN)) { moveDown(dt, 1); }
        }

        /*// Speed boost
        if(this.speedBoost)
        {
            this.speedBoostTime -= dt;
            if (this.speedBoostTime <= 0) {this.speedBoost = false; this.speedBoostDisableTime = Constants.SPEED_BOOST_DISABLE_TIME;}
        }
        else if (this.speedBoostDisableTime > 0.0) {this.speedBoostDisableTime -= dt;}*/
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
}
