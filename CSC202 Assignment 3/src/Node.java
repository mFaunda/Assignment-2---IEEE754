//Mike Faunda III

public class Node<E extends Comparable<E>> implements Comparable<Node<E>>, java.io.Serializable
{
    Node next;
    Node last;
    E data;




    Node()
    {
        next = null;
        last = null;
        data = null;
    }

    Node(E input)
    {
        data = input;
    }

    public Node getNext()
    {
        return next;
    }

    public Node getLast()
    {
        return last;
    }

    public void setNext(Node input)
    {
        next = input;
    }

    public void setLast(Node input)
    {
        last = input;
    }

    public E get()
    {
        return data;
    }

    public void set(E input)
    {
        data = input;
    }

    public boolean equals(E input)
    {
        if(input.equals(data))
            return true;
        return false;
    }

    public int compareTo(Node input)
    {
        return input.get().compareTo(data);
    }

    public String toString()
    {
        return data.toString();
    }
}