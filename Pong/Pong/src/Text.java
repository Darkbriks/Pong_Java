import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Color;

public class Text
{
    ///////////////////////////// FIELDS /////////////////////////////

    private String text;
    private Font font;
    private Color color;
    private float x, y;

    ///////////////////////////// CONSTRUCTORS /////////////////////////////

    /**
     * Constructor for Text class
     * @param text text to display (String)
     * @param font font of text (Font)
     * @param color color of text (Color)
     * @param x x-coordinate of top left corner of text (float)
     * @param y y-coordinate of top left corner of text (float)
     */
    public Text(String text, Font font, Color color, float x, float y)
    {
        this.text = text;
        this.font = font;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor for Text class
     * @param text text to display (int)
     * @param font font of text (Font)
     * @param color color of text (Color)
     * @param x x-coordinate of top left corner of text (float)
     * @param y y-coordinate of top left corner of text (float)
     */
    public Text(int text, Font font, Color color, float x, float y)
    {
        this.text = Integer.toString(text);
        this.font = font;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor for Text class
     * @param text Text object to copy (Text)
     */
    public Text(Text text)
    {
        this.text = text.text;
        this.font = text.font;
        this.color = text.color;
        this.x = text.x;
        this.y = text.y;
    }

    ///////////////////////////// GETTERS /////////////////////////////

    /**
     * Returns text to display
     * @return text to display (String)
     */
    public String getText() { return this.text; }

    /**
     * Returns font of text
     * @return font of text (Font)
     */
    public Font getFont() { return this.font; }

    /**
     * Returns color of text
     * @return color of text (Color)
     */
    public Color getColor() { return this.color; }

    /**
     * Returns x-coordinate of top left corner of text
     * @return x-coordinate of top left corner of text (float)
     */
    public float getX() { return this.x; }

    /**
     * Returns y-coordinate of top left corner of text
     * @return y-coordinate of top left corner of text (float)
     */
    public float getY() { return this.y; }

    ///////////////////////////// SETTERS /////////////////////////////

    /**
     * Sets text to display
     * @param text text to display (String)
     */
    public void setText(String text) { this.text = text; }

    /**
     * Sets text to display
     * @param text text to display (int)
     */
    public void setText(int text) { this.text = Integer.toString(text); }

    /**
     * Sets font of text
     * @param font font of text (Font)
     */
    public void setFont(Font font) { this.font = font; }

    /**
     * Sets color of text
     * @param color color of text (Color)
     */
    public void setColor(Color color) { this.color = color; }

    /**
     * Sets x-coordinate of top left corner of text
     * @param x x-coordinate of top left corner of text (float)
     */
    public void setX(float x) { this.x = x; }

    /**
     * Sets y-coordinate of top left corner of text
     * @param y y-coordinate of top left corner of text (float)
     */
    public void setY(float y) { this.y = y; }

    ///////////////////////////// METHODS /////////////////////////////

    /**
     * Draws text
     * @param g2 Graphics2D object to draw text (Graphics2D)
     */
    public void draw(Graphics2D g2)
    {
        g2.setColor(this.color);
        g2.setFont(this.font);
        g2.drawString(this.text, this.x, this.y);
    }
}
