//Mike Faunda III

public class Queue<E> implements list, java.io.Serializable
{
   E[] container = null;

   Queue()
   {
   
   }
   
   Queue(E input)
   {
      container = (E[])(new Object[1]);
      container[0] = input;
   }

   public void enqueue(E input)
   {
      if(container == null)
      {
         container = (E[])(new Object[1]);
         container[0] = input;
      }
      else
      {
         boolean dontstop = true;
         E[] temp = (E[])(new Object[container.length+1]);
         for(int x=0; x<container.length; x++)
         {
            if(input.equals(container[x]))
            {
               dontstop = false;
            }
            temp[x]=container[x];
         }
         if(dontstop)
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

   public void dequeue()
   {
      if(container == null || container.length==0)
      {}
      else
      {
         E[] temp = (E[])(new Object[container.length-1]);
         for(int x=0; x<temp.length; x++)
         {
            temp[x]=container[x+1];
         }
         container=temp;   
      }
   }

   public E front()
   {
      return container[0];
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