import java.awt.*;

public class MyCircle extends MyBoundedShape{
    public MyCircle(int x1, int y1, int x2, int y2, Color color, boolean fill)
    {
        super(x1, y1, x2, y2, color, fill);
    }
    public void draw( Graphics g )
    {
        g.setColor( getColor() );
        if (getFill())
            g.fillOval( getUpperLeftX(), getUpperLeftY(), getWidth(), getWidth() );
        else
            g.drawOval( getUpperLeftX(), getUpperLeftY(), getWidth(), getWidth() );
    }

}