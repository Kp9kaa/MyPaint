import java.io.Serializable;

public class Serial implements Serializable {
    private LinkedList<MyShape> myShapes;

    Serial(LinkedList<MyShape> list){
        myShapes = list;
    }


    public LinkedList<MyShape> getLinked(){
        return myShapes;
    }

}
