//Mike Faunda III

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;

public class testBST {



    @Test
    public void testTheTree()//Add, Remove, Contains, Size, isEmpty, toString, reset, getNext
    {
        BST<Integer> myBST = new BST();

        myBST.add(5);
        myBST.add(3);
        myBST.add(4);
        myBST.add(2);
        myBST.add(7);
        myBST.add(8);
        myBST.add(9);
        myBST.add(1);
        myBST.add(6); //Test Add (tests contains)
        myBST.remove(3);
        myBST.remove(2);
        myBST.remove(5); //Test Remove
        myBST.reset();//test reset
        myBST.reset();

        System.out.println(myBST.getNext("INORDER"));//test get next and the previous functions as well
        System.out.println(myBST.getNext("INORDER"));
        System.out.println(myBST.getNext("INORDER"));
        System.out.println(myBST.getNext("INORDER"));
        System.out.println(myBST.getNext("INORDER"));
        System.out.println(myBST.getNext("INORDER"));//Should output 1-9 in order without 3,2,5 *spoiler*(it does)

        myBST.reset();
        System.out.println(myBST);
        
        assertEquals(false, myBST.isEmpty());//test is empty
        myBST.remove(1);
        myBST.remove(4);
        myBST.remove(6);
        myBST.remove(7);
        myBST.remove(8);
        myBST.remove(9);
        assertEquals(true, myBST.isEmpty());//test full removal and is empty
    }

    public static void main(String[] args)
    {
        Result result = JUnitCore.runClasses(testBST.class);

        for (Failure failure : result.getFailures())
        {
           System.out.println(failure.toString());
        }

       System.out.println(result.wasSuccessful());
    }

}