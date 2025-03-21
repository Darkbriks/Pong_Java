import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KL implements KeyListener
{
    private final boolean[] keyPressed = new boolean[128];

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyPressed(KeyEvent keyEvent) { keyPressed[keyEvent.getKeyCode()] = true; }

    @Override
    public void keyReleased(KeyEvent keyEvent) { keyPressed[keyEvent.getKeyCode()] = false; }

    public boolean isKeyPressed(int keyCode) { return keyPressed[keyCode]; }
}