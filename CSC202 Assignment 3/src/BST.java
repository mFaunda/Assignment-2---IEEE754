//Mike Faunda III
import edu.nvcc.csc202.BST.Order;

public class BST <T extends Comparable<T>> implements java.io.Serializable
{

   private BSTNode<T> root=null;
   private int numElement=0;
   private BSTNode<T> pointer = null;
   private BSTNode<T> parent = null;
   public Queue<T> inqueue = new Queue();
   public Queue<T> prequeue = new Queue();
   public Queue<T> postqueue = new Queue();
   boolean found=false;
	
   
   public void add(T element)
   {
      BSTNode newNode = new BSTNode(element);
      if (root == null) 
      {
         root = newNode;
      } 
      else 
      {
         recAdd(element,this.root);
      }
      numElement++;
      reset();
   }

   private BSTNode recAdd(T element, BSTNode<T> root)
   {
      if (root == null) 
      {
         root = new BSTNode(element);
      } 
      else if ((element).compareTo(root.get())<=0)
      {
         root.setLeft(recAdd(element,root.getLeft()));
         if(root.getLeft()!=null)
         root.getLeft().setParent(root);
          if(root.getRight()!=null)
          root.getRight().setParent(root);
      } 
      else if ((element).compareTo(root.get())>0)
      {
         root.setRight(recAdd(element,root.getRight()));
          if(root.getRight()!=null)
         root.getRight().setParent(root);
          if(root.getLeft()!=null)
          root.getLeft().setParent(root);
      }
      return root;
   }
    
    
    public boolean remove(T value)
    {
        if (root == null)
            return false;
        else
        {
            if (root.get().equals(value))
            {
                BSTNode auxRoot = new BSTNode(0);
                auxRoot.setLeft(root);
                boolean result = root.remove(value, auxRoot);
                root = auxRoot.getLeft();
                numElement--;
                return result;
            }
            else
                {
                    numElement--;
                return root.remove(value, null);
            }
        }
    }

   
   public boolean contains(T element)
   {
      recContains(element,this.root);
      return found;
   }
	
   public BSTNode recContains(T element, BSTNode<T> root)
   {
   	
      if (root == null)
      {
         found = false;
      } 
      else if (element.compareTo(root.get())<0)
      {
         parent=root;
         recContains(element,root.getLeft());			
      } 
      else if (element.compareTo(root.get())>0)
      {
         parent=root;
         recContains(element,root.getRight());			
      } 
      else if (element.compareTo(root.get())==0)
      {
         pointer = root;
         found = true;

      }
      return root;
   }

   
   public int size() 
   {
      return numElement;
   }

   public Queue traversal(Order order)
   {
      pointer = this.root;
      if (!isEmpty())
      {
         if (order.equals(Order.PREORDER)) 
         {
            System.out.println("PREORDER");
            preOrder(pointer);
            return prequeue;
         } 
         else if (order.equals(Order.POSTORDER)) 
         {
            System.out.println("POSTORDER");
            postOrder(pointer);
            return postqueue;
         } 
         else if (order.equals(Order.INORDER))
         {
            System.out.println("INORDER");
            inOrder(pointer);
            return inqueue;
         }
      }	
      return null;
   }
   
   public void preOrder(BSTNode root)
   {
   	//root
      if(root != null)
      {
         prequeue.enqueue((T)root.get());
      //left
         preOrder(root.getLeft());
      //right
         preOrder(root.getRight());
      }
   }

   
   public void postOrder(BSTNode root)
   {
   	//left, right, root
      if(root != null) 
      {
         postOrder(root.getLeft());
         postOrder(root.getRight());
         postqueue.enqueue((T)root.get());
      }
   }

   
   public void inOrder(BSTNode root)
   {
   	//left, root, right
      if(root != null) 
      {
         inOrder(root.getLeft());
         inqueue.enqueue((T)root.get());
         inOrder(root.getRight());
      }
   }

   
   public boolean isEmpty() 
   {
      if (numElement==0)
      {
         return true;
      }
      return false;
   }

   public String toString()
   {
      return inqueue.toString();
   }

   public BSTNode get(T element)
   {
       if(contains(element))
           return pointer;
       else
           return null;
   }

    public int reset()
// Initializes current position for an iteration through this BST
// in orderType order. Returns current number of nodes in the BST.
    {
            inqueue = new Queue<T>();
            inOrder(root);

            prequeue = new Queue<T>();
            preOrder(root);

            postqueue = new Queue<T>();
            postOrder(root);

        return size();
    }

    public T getNext (String orderType)
// Preconditions: the BST is not empty
//                the BST has been reset for orderType
//                the BST has not been modified since the most recent reset
//                the end of orderType iteration has not been reached
// Returns the element at the current position on this BST and advances
// the value of the current position based on the orderType set by reset.
    {
        if (orderType.equals("INORDER"))
        {
            T temp = (T)inqueue.front();
            inqueue.dequeue();
            return temp;
        }
        else if (orderType.equals("PREORDER"))
        {
            T temp = (T)prequeue.front();
            prequeue.dequeue();
            return temp;
        }
        else if (orderType.equals("POSTORDER"))
        {
            T temp = (T)postqueue.front();
            postqueue.dequeue();
            return temp;
        }
        else return null;
    }



}
