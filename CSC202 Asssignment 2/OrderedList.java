//Mike Faunda III

import java.lang.reflect.Array;
import java.util.*;

public class OrderedList<E extends Comparable<E>> implements list, java.io.Serializable
{
   Node<E> first = new Node();
   Node<E> current = null;
   int length = 0;

   OrderedList()
   {
      first = null;
      current = null;
   }
   
   OrderedList(E input)
   {
      first.set(input);
      current = first;
      length = 1;
   }

   public void add(E input) throws Exception
   {
      current = first;
      Node temp = new Node(input);
      if(isEmpty())
      {
         first = new Node(input);
         current = first;
      }
      else
      {
         if(contains(input))
            return;
         else
         {
            current = first;
            for(int x=0; x<length; x++)
            {
               if(current.get().compareTo(input)<0)
               {
                  if(!current.equals(first.get()))
                     current.getLast().setNext(temp);
                  temp.setNext(current);
                  temp.setLast(current.getLast());
                  current.setLast(temp);
                  if(current.equals(first.get()))
                     first = temp;
               }
               else
               {
                  if(x+1==length)
                  {
                     temp.setNext(current.getNext());
                     temp.setLast(current);
                     current.setNext(temp);
                  }
               }
               current = current.getNext();
            }
         }
      }
      current = first;
      length++;
   }

   public void remove(E input) throws Exception
   {
      if(isEmpty()){}
      else if(contains(input))
      {
         current = first;
         for(int x=0; x<length; x++)
         {
            if(current.get().equals(input))
            {
               if(current.get().equals(first.get()))
               {
                  first.getNext().setLast(first.getLast());
                  first = first.getNext();
               }
               else
               {
                  current.getNext().setLast(current.getLast());
                  current.getLast().setNext(current.getNext());
               }
            }
            current = current.getNext();
         }
         current = first;
         length--;
      }
   }

   public Object get(E input) throws Exception
   {
      if(this.contains(input))
         return (Object)input;
      return null;
   }
   
   public boolean contains(E input) throws Exception
   {
      Node tmp = current;
      current = first;
      for(int x=0; x<length; x++)
      {
         if(current.equals(input))
            return true;
         current = current.getNext();
      }
      current = tmp;
      return false;
   }
   
   public boolean isEmpty()
   {
      if(first==null)
         return true;
      return false;
   }
   
   public int size()
   {
      return length;
   }
   
   public String toString()
   {
      current = first;
      String temp = "";
      if(!isEmpty())
         for(int x=0; x<length; x++)
         {
            temp = temp+"\n"+current.toString();
            current = current.getNext();
         }
      return temp;
   }
   
   public void reset()
   {
      current = first;
   }
   
   public Node getNext()
   {
      Node<E> temp = current;
      current = current.getNext();
      return temp;
   }
   

}