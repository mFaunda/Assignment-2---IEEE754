//Mike Faunda III

public class IndexedList<E> implements list, java.io.Serializable
{
   E[] container = null;
   int position = 0;

   IndexedList()
   {
   
   }
   
   IndexedList(E input)
   {
      container = (E[])(new Object[1]);
      container[0] = input;
   }

   public void add(E input)
   {
      if(container == null)
      {
         container = (E[])(new Object[1]);
         container[0] = input;
      }
      else
      {
         E[] temp = (E[])(new Object[container.length+1]);
         for(int x=0; x<container.length; x++)
         {
            if(container[x].equals(input))
               return;
            temp[x]=container[x];
         }
         temp[container.length]=input;
         container = temp;
      }
     
   }
   
   public boolean add(E input, int indexInput)//returns true if it succeeds. to activate a conditional statement if needed, do if(!*name*.add(*parameter*)){}
   {
      if(container == null)
      {
         container = (E[])(new Object[1]);
         container[0] = input;
         return true;
      }
      else if(indexInput<container.length && indexInput>=0)
      {
         if(!contains(input))
         {
            E[] temp = (E[]) new Object[container.length+1];
            for(int x=0; x<indexInput; x++)
               temp[x]=container[x];
            temp[indexInput]=input;
            for(int x=indexInput; x<container.length; x++)
               temp[x+1]=container[x];
            container=temp;
            return true;
         }
      }
      return false;
   }
   
   public boolean contains(E input)
   {
      for(int x=0; x<container.length; x++)
      {
         if(container[x].equals(input))
            return true;
      }
      return false;
   }
   
   public int indexOf(E input)
   {
      for(int x=0; x<container.length; x++)
      {
         if(container[x].equals(input))
            return x;
      }
      return -1;
   }   
   
   public E[] getAll()
   {
      return container;
   }
   
   public E getNext()
   {
      int temp = position;
      position++;
      return container[temp];
   }
   
   public void reset()
   {
      position = 0;
   }

   public void setAll(E[] input)
   {
      container = input;
   }
   
   public E get(int indexInput)
   {
      return container[indexInput];
   }
   
   public boolean set(E input, int indexInput)//if there are no duplicates, completes and returns true for success
   {
      for(int x=0; x<container.length; x++)
      {
         if(container[x].equals(input))
            return false;
      }
      container[indexInput]=input;
      return true;
   }
   
   public void forceSet(E input, int indexInput)//skips duplicate check
   {
      container[indexInput]=input;
   }

   public void remove(int indexInput)
   {
      if(container == null || container.length==0 || indexInput>=container.length || indexInput<0)
      {}
      else
      {
         E[] temp = (E[]) new Object[container.length-1];
         if(temp.length>0)
         {
            for(int x=0; x<indexInput; x++)
               temp[x] = container[x];
            for(int x=indexInput; x<temp.length; x++)
               temp[x] = container[x+1];
         }
         container=temp;
      }
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