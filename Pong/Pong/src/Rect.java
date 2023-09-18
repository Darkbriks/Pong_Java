import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class Rect
{
    public double x, y, prevX, prevY, width, height;
    public final Color color;

    public Rect(double x, double y, double width, double height,  Color color)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void draw(Graphics2D g2)
    {
        g2.setColor(color);
        //g2.fillRect(this.x, this.y, this.width, this.height);
        g2.fill(new Rectangle2D.Double(this.x, this.y, this.width, this.height));
    }
}
