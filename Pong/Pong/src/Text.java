import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Color;

public class Text
{
    public String text;
    public Font font;
    public Color color;
    public float x, y;

    public Text(String text, Font font, Color color, float x, float y)
    {
        this.text = text;
        this.font = font;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public Text(int text, Font font, Color color, float x, float y)
    {
        this.text = Integer.toString(text);
        this.font = font;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public void setText(int text) { this.text = Integer.toString(text); }

    public void draw(Graphics2D g2)
    {
        g2.setColor(this.color);
        g2.setFont(this.font);
        g2.drawString(this.text, this.x, this.y);
    }
}
