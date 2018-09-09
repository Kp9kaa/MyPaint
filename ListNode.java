import java.io.Serializable;

public class ListNode<T> implements Serializable {
    private T data;
    private ListNode next;

    public ListNode( T nodeData ) {
        this( nodeData, null);
    }

    public ListNode( T nodeData, ListNode nodeNext ) {
        data = nodeData;
        next = nodeNext;
    }

    public T getData() {
        return data;
    }

    public ListNode getNext() {
        return next;
    }


    public void setNext( ListNode newNext ) {
        next = newNext;
    }
}