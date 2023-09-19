import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class Rect
{
    ///////////////////////////// FIELDS /////////////////////////////

    private double x, y, prevX, prevY, width, height;
    private Color color;

    ///////////////////////////// CONSTRUCTORS /////////////////////////////

    //TODO : javadoc
    public Rect()
    {
        this.x = 0;
        this.y = 0;
        this.prevX = 0;
        this.prevY = 0;
        this.width = 0;
        this.height = 0;
        this.color = Color.WHITE;
    }

    /**
     * Constructor for Rect class
     * @param x x-coordinate of top left corner of rectangle (double)
     * @param y y-coordinate of top left corner of rectangle (double)
     * @param width width of rectangle (double)
     * @param height height of rectangle (double)
     * @param color color of rectangle (Color)
     */
    public Rect(double x, double y, double width, double height, Color color)
    {
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * Copy constructor for Rect class
     * @param rect Rect object to copy (Rect)
     */
    public Rect(Rect rect)
    {
        this.x = rect.x;
        this.y = rect.y;
        this.prevX = rect.prevX;
        this.prevY = rect.prevY;
        this.width = rect.width;
        this.height = rect.height;
        this.color = rect.color;
    }

    ///////////////////////////// GETTERS /////////////////////////////

    /**
     * Returns x-coordinate of top left corner of rectangle
     * @return x-coordinate of top left corner of rectangle (double)
     */
    public double getX() { return this.x; }

    /**
     * Returns y-coordinate of top left corner of rectangle
     * @return y-coordinate of top left corner of rectangle (double)
     */
    public double getY() { return this.y; }

    /**
     * Returns previous x-coordinate of top left corner of rectangle
     * @return previous x-coordinate of top left corner of rectangle (double)
     */
    public double getPrevX() { return this.prevX; }

    /**
     * Returns previous y-coordinate of top left corner of rectangle
     * @return previous y-coordinate of top left corner of rectangle (double)
     */
    public double getPrevY() { return this.prevY; }

    /**
     * Returns width of rectangle
     * @return width of rectangle (double)
     */
    public double getWidth() { return this.width; }

    /**
     * Returns height of rectangle
     * @return height of rectangle (double)
     */

    public double getHeight() { return this.height; }

    /**
     * Returns color of rectangle
     * @return color of rectangle (Color)
     */
    public Color getColor() { return this.color; }

    ///////////////////////////// SETTERS /////////////////////////////

    /**
     * Sets x-coordinate of top left corner of rectangle and updates previous x-coordinate
     * X must be greater than or equal to 0
     * @param x x-coordinate of top left corner of rectangle (double)
     */
    public void setX(double x) { if (x >= 0) { this.prevX = this.x; this.x = x; } }

    /**
     * Sets y-coordinate of top left corner of rectangle and updates previous y-coordinate
     * Y must be greater than or equal to 0
     * @param y y-coordinate of top left corner of rectangle (double)
     */
    public void setY(double y) { if (y >= 0) { this.prevY = this.y; this.y = y; } }

    /**
     * Sets width of rectangle
     * Width must be greater than or equal to 0
     * @param width width of rectangle (double)
     */
    public void setWidth(double width) { if (width >= 0) { this.width = width; } }

    /**
     * Sets height of rectangle
     * Height must be greater than or equal to 0
     * @param height height of rectangle (double)
     */
    public void setHeight(double height) { if (height >= 0) { this.height = height; } }

    /**
     * Sets color of rectangle
     * @param color color of rectangle (Color)
     */
    public void setColor(Color color) { this.color = color; }

    ///////////////////////////// METHODS /////////////////////////////

    /**
     * Draws rectangle
     * @param g2 Graphics2D object
     */
    public void draw(Graphics2D g2)
    {
        g2.setColor(this.color);
        g2.fill(new Rectangle2D.Double(this.x, this.y, this.width, this.height));
    }
}
