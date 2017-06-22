//Mike Faunda III

public class Stack<E> implements list, java.io.Serializable
{

   E[] container = null;

   Stack()
   {
   
   }
   
   Stack(E input)
   {
      container = (E[])(new Object[1]);
      container[0] = input;
   }

   public void push(E input)
   {
      if(container == null)
      {
         container = (E[])(new Object[1]);
         container[0] = input;
      }
      else
      {
         boolean skip = false;
         E[] temp = (E[])(new Object[container.length+1]);
         for(int x=0; x<container.length; x++)
         {
            if(container[x].equals((input)))
            {
               skip = true;
               break;
            }
            temp[x]=container[x];
         }
         if(!skip)
         {
            temp[container.length]=input;
            container = temp;
         }
      }
     
   }
   
   public E[] getAll()
   {
      return container;
   }

   public void setAll(E[] input)
   {
      container = input;
   }

   public void pop()
   {
      if(container == null || container.length==0)
      {}
      else
      {
         E[] temp = (E[])(new Object[container.length-1]);
         for(int x=0; x<temp.length; x++)
         {
            temp[x]=container[x];
         }
         container=temp;   
      }
   }

   public E top()
   {
      return container[container.length-1];
   }
   
   public boolean isEmpty()
   {
      if(container == null || container.length==0)
      {
         return true;
      }
      else
      {
         return false;
      }
   }
   
   public int size()
   {
      if(container == null)
         return 0;
      else
         return container.length;
   }
   
   public String toString()
   {
      String temp = "";
      for(int x=0; x<container.length; x++)
      {
         temp = temp+"\n"+container[x].toString();
      }
      return temp;  
   }

}