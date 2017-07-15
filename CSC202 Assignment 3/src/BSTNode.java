//Mike Faunda III

public class BSTNode<E extends Comparable<E>> implements Comparable<BSTNode<E>>, java.io.Serializable
{
   BSTNode left;
   BSTNode right;
   BSTNode parent;
   E data;




   BSTNode()
   {
      parent = null;
      left = null;
      right = null;
      data = null;
   }

   BSTNode(E input)
   {
      data = input;
   }

   public BSTNode getParent()
   {
      return parent;
   }

   public BSTNode getLeft()
   {
      return left;
   }
   
   public BSTNode getRight()
   {
      return right;
   }
   
   public void setLeft(BSTNode input)
   {
      left = input;
   }
   
   public void setRight(BSTNode input)
   {
      right = input;
   }
   
   public void setParent(BSTNode input)
   {
      parent = input;
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

   public int compareTo(BSTNode input)
   {
      return input.get().compareTo(data);
   }

   public String toString()
   {
      return data.toString();
   }
   
   public boolean remove(E value, BSTNode par)
   {
      if (value.compareTo(this.data) < 0)
      {
         if (left != null)
            return left.remove(value, this);
         else
            return false;
      }
      else if (value.compareTo(this.data) > 0)
      {
         if (right != null)
            return right.remove(value, this);
         else
            return false;
      }
      else
         {
         if (left != null && right != null)
         {
            this.data = (E)right.minValue();
            right.remove(this.data, this);
         }
         else if (par.left == this)
         {
            par.left = (left != null) ? left : right;
         }
         else if (par.right == this)
         {
            par.right = (left != null) ? left : right;
         }
         return true;
      }
   }
   
   public E minValue() {
      if (left == null)
         return data;
      else
         return (E)left.minValue();
   }
}