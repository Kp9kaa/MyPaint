import java.awt.*;

public class MySquare extends MyBoundedShape{
    public MySquare(int x1, int y1, int x2, int y2, Color color, boolean fill)
    {
        super(x1, y1, x2, y2, color, fill);
    }
    public void draw( Graphics g )
    {
        g.setColor( getColor() );
        if (getFill())
            g.fillRect( getUpperLeftX(), getUpperLeftY(), getWidth(), getWidth() );
        else
            g.drawRect( getUpperLeftX(), getUpperLeftY(), getWidth(), getWidth() );
    }
}
