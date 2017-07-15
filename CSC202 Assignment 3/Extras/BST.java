//Mike Faunda III

public class BST <T extends Comparable<T>>implements BSTInterface <T>
{

   private BSTNode root=null;
   private int numElement=0;
   private BSTNode pointer = null;
   private BSTNode parent = null;
   public LinkedUnbndQueue inqueue = new LinkedUnbndQueue();
   public LinkedUnbndQueue prequeue = new LinkedUnbndQueue();
   public LinkedUnbndQueue postqueue = new LinkedUnbndQueue();
   public enum Order {PREORDER, POSTORDER, INORDER};
   boolean found=false;
	
   @Override
   public void add(T element)
   {
   	// TODO Auto-generated method stub
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
   }

   private BSTNode recAdd(T element, BSTNode<T> root)
   {
   	// TODO Auto-generated method stub
      if (root == null) 
      {
         root = new BSTNode(element);
      } 
      else if ((element).compareTo(root.getElement())<=0)
      {
         root.setLeft(recAdd(element,root.getLeft()));			
      } 
      else if ((element).compareTo(root.getElement())>0) 
      {
         root.setRight(recAdd(element,root.getRight()));
      }
      return root;
   }

   @Override
   public T remove(T element) 
   {
   	// TODO Auto-generated method stub
      if (contains(element)) 
      {
      	//found at leave
         if((pointer.getLeft()==null) && (pointer.getRight()==null)) 
         {
         
         }
      }
      return null;
   }

   @Override
   public boolean contains(T element)
   {
   	// TODO Auto-generated method stub
      recContains(element,this.root);
      return found;
   }
	
   public BSTNode recContains(T element, BSTNode<T> root)
   {
   	
      if (root == null)
      {
         found = false;
      } 
      else if (element.compareTo(root.getElement())<0) 
      {
         parent=root;
         recContains(element,root.getLeft());			
      } 
      else if (element.compareTo(root.getElement())>0) 
      {
         parent=root;
         recContains(element,root.getRight());			
      } 
      else if (element.compareTo(root.getElement())==0)
      {
         pointer = root;
         found = true;
      }
      return root;
   }

   @Override
   public int size() 
   {
   	// TODO Auto-generated method stub
      return 0;
   }

   public LinkedUnbndQueue traversal(Order order) 
   {
      pointer = this.root;
      if (!isEmpty())
      {
         if (order.equals(Order.PREORDER)) 
         {
            System.out.println("PREORDER");
            preorder(pointer);
            return prequeue;
         } 
         else if (order.equals(Order.POSTORDER)) 
         {
            System.out.println("POSTORDER");
            postorder(pointer);
            return postqueue;
         } 
         else if (order.equals(Order.INORDER))
         {
            System.out.println("INORDER");
            inorder(pointer);
            return inqueue;
         }
      }	
      return null;
   }
   
   @Override
   public void preorder(BSTNode root)
   {
   	// TODO Auto-generated method stub
   	//root
      if(root != null)
      {
         prequeue.enqueue(root.getElement());
      //left
         preorder(root.getLeft());
      //right
         preorder(root.getRight());
      }
   }

   @Override
   public void postorder(BSTNode root)
   {
   	// TODO Auto-generated method stub
   	//left, right, root
      if(root != null) 
      {
         postorder(root.getLeft());
         postorder(root.getRight());
         postqueue.enqueue(root.getElement());
      
      }
   }

   @Override
   public void inorder(BSTNode root)
   {
   	// TODO Auto-generated method stub
   	//left, root, right
      if(root != null) 
      {
         inorder(root.getLeft());
         inqueue.enqueue(root.getElement());
         inorder(root.getRight());
      }	
   }

   @Override
   public boolean isEmpty() 
   {
   	// TODO Auto-generated method stub
      if (numElement==0)
      {
         return true;
      }
      return false;
   }

   @Override
   public void reset()
   {
   	// TODO Auto-generated method stub
   	
   }

}
