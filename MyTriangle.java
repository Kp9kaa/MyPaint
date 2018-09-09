import java.awt.*;

public class MyTriangle extends MyBoundedShape{
    public MyTriangle(int x1, int y1, int x2, int y2, Color color, boolean fill)
    {
        super(x1, y1, x2, y2, color, fill);
    }
    public void draw( Graphics g )
    {
        g.setColor(getColor());
        int[] xCoordinates = new int[3]  ;
        int[] yCoordinates = new int[3]    ;
        xCoordinates[0] =getUpperLeftX();
        xCoordinates[1] =getUpperLeftX()+getWidth();
        xCoordinates[2]=getUpperLeftX()+getWidth()/2;
        if (getY1() < getY2()) {
            yCoordinates[0] = getY2();
            yCoordinates[1] =getY2();
            yCoordinates[2] = getY1();
        } else {
            yCoordinates[0] = getY1();
            yCoordinates[1] = getY1();
            yCoordinates[2] =getUpperLeftY();
        }

        g.drawPolyline(xCoordinates, yCoordinates,3);
        g.drawLine(xCoordinates[0],yCoordinates[0],xCoordinates[2],yCoordinates[2]);

    }
}
