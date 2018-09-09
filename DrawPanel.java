import java.awt.Color;
import java.awt.Graphics;
import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.io.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class DrawPanel extends JPanel
{
    private LinkedList<MyShape> myShapes;
    private LinkedList<MyShape> clearedShapes;
    private DefaultListModel<String> defaultListModel;

    private int currentShapeType;
    private MyShape currentShapeObject;
    private Color currentShapeColor;
    private boolean currentShapeFilled;
    private boolean currentShapeChange;
    private boolean block;
    
    JLabel statusLabel;

    public DrawPanel(JLabel statusLabel, DefaultListModel<String> dlm){


        defaultListModel = dlm;
        myShapes = new LinkedList<MyShape>();
        clearedShapes = new LinkedList<MyShape>();
        

        currentShapeType=0;
        currentShapeObject=null;
        currentShapeColor=Color.BLACK;
        currentShapeFilled=false;
        currentShapeChange=false;
        
        this.statusLabel = statusLabel;
        
        setLayout(new BorderLayout());
        setBackground( Color.WHITE );
        add( statusLabel, BorderLayout.SOUTH );
        

        MouseHandler handler = new MouseHandler();                                    
        addMouseListener( handler );
        addMouseMotionListener( handler ); 
    }
    

    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        

        ArrayList<MyShape> shapeArray=myShapes.getArray();
        for ( int counter=shapeArray.size()-1; counter>=0; counter-- )
           shapeArray.get(counter).draw(g);

        if (currentShapeObject!=null)
            currentShapeObject.draw(g);

    }


    public void setCurrentShapeType(int type)
    {
        currentShapeType=type;
    }


    public void setCurrentShapeColor(Color color)
    {
        currentShapeColor=color;
    }

    public void setCurrentShapeFilled(boolean filled)
    {
        currentShapeFilled=filled;
    }
    
    public void setCurrentShapeChange(boolean change){currentShapeChange=change;}

    public void clearLastShape()
    {
        if (! myShapes.isEmpty())
        {
            clearedShapes.addFront(myShapes.removeFront());
            repaint();
        }
        setList();
    }

    public void redoLastShape()
    {
        if (! clearedShapes.isEmpty())
        {
            myShapes.addFront(clearedShapes.removeFront());
            repaint();
        }
        setList();
    }



    public void clearDrawing()
    {
        myShapes.makeEmpty();
        clearedShapes.makeEmpty();
        repaint();
        setList();
    }
    public void saveFile(){
        JFileChooser jFileChooser = new JFileChooser();
        int ret = jFileChooser.showDialog(null, "Сохранить файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(jFileChooser.getSelectedFile() + ".bin"))) {
                Serial ts = new Serial(myShapes);
                oos.writeObject(ts);
            } catch (Exception ex) {

                System.out.println(ex.getMessage());
            }
        }
    }

    public void downloadFile(){
        JFileChooser jFileChooser = new JFileChooser();
        int ret = jFileChooser.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(jFileChooser.getSelectedFile()))) {
                Serial p = (Serial) ois.readObject();
                myShapes = p.getLinked();
                repaint();
                setList();
            } catch (Exception ex) {

                System.out.println(ex.getMessage());
            }
        }
    }
    public void searchInList(int x, int y){
        ArrayList<MyShape> arrayList = myShapes.getArray();
        int x1,x2,y1,y2, i =0;
        while(i!=arrayList.size()&i>=0){
            x1=arrayList.get(i).getX1();
            x2=arrayList.get(i).getX2();
            y1=arrayList.get(i).getY1();
            y2=arrayList.get(i).getY2();
            if((x<=x1&x>=x2)|(x>=x1&x<=x2))
                if((y<=y1&y>=y2)|(y>=y1&y<=y2)){
                    movedChooseObject(i);
                    repaint();
                    i=-5;

                }
            i++;

        }
    }
    public void movedChooseObject(int i){
        int count;
        for(count=0;count<i;count++){
            clearedShapes.addFront(myShapes.removeFront());
        }
        MyShape temp=myShapes.removeFront();
        for(i=0;i<count;i++){
            myShapes.addFront(clearedShapes.removeFront());
        }
        myShapes.addFront(temp);
        repaint();
    }


    public void setList(){
        defaultListModel.removeAllElements();
        ArrayList<MyShape> temp = myShapes.getArray();
        for (int i = 0; i < temp.size(); i++){
            defaultListModel.addElement((i + 1) + " " + temp.get(i).getClass().getName());
        }
    }

    public void changeColor(int idx){
        ArrayList<MyShape> temp = myShapes.getArray();
        temp.get(idx).setColor(currentShapeColor);
        myShapes.setArray(temp);
        repaint();
    }

    private class MouseHandler extends MouseAdapter 
    {

        public void mousePressed( MouseEvent event)
        {
            if(block)currentShapeColor= Color.BLACK;
            if(!currentShapeChange){switch (currentShapeType)
                {
                    case 0:
                        currentShapeObject= new MyLine( event.getX(), event.getY(),
                                                       event.getX(), event.getY(), currentShapeColor);
                        break;
                    case 1:
                        currentShapeObject= new MyRectangle( event.getX(), event.getY(),
                                                            event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
                        break;
                    case 2:
                        currentShapeObject= new MyOval( event.getX(), event.getY(),
                                                       event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
                        break;

                    case 3:
                        currentShapeObject= new MySquare( event.getX(), event.getY(),
                                event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
                        break;
                    case 4:
                        currentShapeObject= new MyCircle( event.getX(), event.getY(),
                                event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
                        break;
                    case 5:
                        currentShapeObject= new MyTriangle( event.getX(), event.getY(),
                                event.getX(), event.getY(), currentShapeColor, currentShapeFilled);
                        break;

                }
            }
            else {
                searchInList(event.getX(),event.getY());
            }
            setList();
        }
        

        public void mouseReleased( MouseEvent event )
        {

            if(!currentShapeChange) {
                currentShapeObject.setX2(event.getX());
                currentShapeObject.setY2(event.getY());

                myShapes.addFront(currentShapeObject);
                setList();
                currentShapeObject = null;
                repaint();
            }
            
        }
        public void mouseMoved( MouseEvent event )
        {
            statusLabel.setText(String.format("Mouse Coordinates X: %d Y: %d",event.getX(),event.getY()));
        }
        

        public void mouseDragged( MouseEvent event )
        {

            if(!currentShapeChange) {
                currentShapeObject.setX2(event.getX());
                currentShapeObject.setY2(event.getY());
            }

            statusLabel.setText(String.format("Mouse Coordinates X: %d Y: %d",event.getX(),event.getY()));
            
            repaint();
            
        }



        
    }// end MouseHandler
    
} // end class DrawPanel