//Mike Faunda III

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestJunit {
	
   Stack<User> myStack = new Stack();
   Queue<User> myQueue = new Queue();
   OrderedList<User> myOrderedList = new OrderedList();
   IndexedList<User> myIndexedList = new IndexedList();
   User user1 = new User("Mike", "Faunda", "123456789", "04171996", "Male", "MikeFaunda", "mitch.LFDA@mail.com", "0123456789", "As!1", null);
   User user2 = new User("Mike", "Faunda", "123456789", "04171996", "Male", "MikeFaunda", "mitch.LFDA@mail.com", "0123456789", "As!1", null);
   User user3 = new User("Adam", "Savage", "987654321", "12121974", "Male", "AdamSavage", "adam.LFDA@mail.com", "0123456789", "As!1", null);
   User user4 = new User("Sara", "Metley", "456789123", "06191847", "Female", "SaraMetley", "sara.LFDA@mail.com", "0123456789", "As!1", null);
   User[] userArray1 = new User[3];
   User[] userArray2 = new User[3];
  /*
  User 1 and 2 are equal
  Alphabetically: User 3, User 1, User 4
  */

   @Test
   public void testStack()//Push, pop, top, isEmpty, size, toString
   {
      String temp = "";
      temp = temp+"\n"+user1.toString();
      temp = temp+"\n"+user4.toString();
      temp = temp+"\n"+user3.toString();
      userArray1[0] = user1; userArray1[1] = user4; userArray1[2] = user3; 
      userArray2[0] = user3; userArray2[0] = user1; userArray2[0] = user4;
      
      myStack.push(user1);
      myStack.push(user2);
      myStack.push(user3);
      myStack.push(user4);                          //push all Stack: User1, User3, User4 since user 1 and 2 have equal usernames
     
      myStack.pop();
      myStack.pop();
      myStack.push(user4);                         //pop two and push them back in reverse to test popping method
      myStack.push(user3);
      assertEquals(myStack.top(), user3);          //test top method by equaling stack.top() with last pushed user
      myStack.pop();
      assertEquals(myStack.top(), user4);          //pop and do it again for consistency
      
      assertEquals(myStack.isEmpty(), false);      //test to see if filled stack is empty or not, return false
      myStack.pop(); myStack.pop(); myStack.pop(); //empty stack
      assertEquals(myStack.isEmpty(), true);       //test isempty again, return true
      myStack.pop(); myStack.pop(); myStack.pop();
      
      assertEquals(myStack.size(), 0);             //test size when there's nothing in stack
      myStack.setAll(userArray1);                  //send custom array from above into stack to test size again
      assertEquals(myStack.size(), 3);
      
      assertEquals(myStack.toString(), temp);      //compare toString with custom made string that resembles what is created based on the order of the users in the stack
   }
   
   @Test
   public void testQueue()//Enqueue, Dequeue, isEmpty, size, toString
   {
      String temp = "";
      temp = temp+"\n"+user3.toString();
      temp = temp+"\n"+user2.toString();
      userArray1[0] = user1; userArray1[1] = user4; userArray1[2] = user3; 
      userArray2[0] = user3; userArray2[0] = user1; userArray2[0] = user4;
      
      myQueue.enqueue(user1);
      myQueue.enqueue(user2);
      myQueue.enqueue(user3);
      myQueue.enqueue(user4);
      assertEquals(myQueue.front(), user1);
      myQueue.dequeue();
      assertEquals(myQueue.front(), user3);
      
      assertEquals(myQueue.isEmpty(), false);
      myQueue.dequeue(); myQueue.dequeue(); myQueue.dequeue(); myQueue.dequeue();
      assertEquals(myQueue.isEmpty(), true);
      
      assertEquals(myQueue.size(), 0);
      myQueue.enqueue(user3);
      myQueue.enqueue(user2);
      assertEquals(myQueue.size(), 2);
      assertEquals(myQueue.toString(), temp);
   }
   
   @Test
   public void testOrderedList() throws Exception//add, remove, contains, isEmpty, size, get, toString, reset, getNext
   {
      String temp = "";
      temp = temp+"\n"+user3.toString();
      temp = temp+"\n"+user1.toString();
      temp = temp+"\n"+user4.toString();
      userArray1[0] = user1; userArray1[1] = user4; userArray1[2] = user3; 
      userArray2[0] = user3; userArray2[0] = user1; userArray2[0] = user4;
      
      assertEquals(myOrderedList.isEmpty(), true); //test isEmpty
      assertEquals(myOrderedList.size(), 0); //test size
      myOrderedList.add(user1);              //add works
      assertEquals(myOrderedList.size(), 1); //test size
      myOrderedList.add(user2);              //test contains by adding a second of the same user
      myOrderedList.add(user3);
      assertEquals(myOrderedList.size(), 2); //test size
      myOrderedList.add(user4);
      assertEquals(myOrderedList.size(), 3); //test size
      assertEquals(myOrderedList.isEmpty(), false);// test isEmpty
      
      assertEquals(user3, myOrderedList.getNext().get());//check getNext
      assertEquals(user1, myOrderedList.getNext().get());
      assertEquals(user4, myOrderedList.getNext().get());
      myOrderedList.reset();
      assertEquals(user3, myOrderedList.getNext().get());//check reset
      
      assertEquals(myOrderedList.get(user1), user1);//check get method
      assertEquals(myOrderedList.get(user4), user4);
      assertEquals(myOrderedList.get(user3), user3);
      
      myOrderedList.remove(user1);
      myOrderedList.reset();
      assertEquals(user3, myOrderedList.getNext().get());//check removal
      assertEquals(user4, myOrderedList.getNext().get());
      
      myOrderedList.remove(user3);
      myOrderedList.reset();
      assertEquals(user4, myOrderedList.getNext().get());//double check removal
      
      myOrderedList.add(user3);
      myOrderedList.add(user1);
      
      assertEquals(myOrderedList.toString(), temp);//test toString
   }
   
   @Test
   public void testIndexedList()//add, set, remove, IndexOf, contains, isEmpty, size, get, toString, reset, getNext
   {
      String temp = "";
      temp = temp+"\n"+user1.toString();
      temp = temp+"\n"+user4.toString();
      temp = temp+"\n"+user3.toString();
      userArray1[0] = user1; userArray1[1] = user4; userArray1[2] = user3; 
      userArray2[0] = user3; userArray2[0] = user1; userArray2[0] = user4;
      
      myIndexedList.add(user1);
      myIndexedList.add(user2);
      myIndexedList.add(user3);
      myIndexedList.add(user4);
      
      assertEquals(user1, myIndexedList.getNext());//check first add and getNext
      assertEquals(user3, myIndexedList.getNext());
      assertEquals(user4, myIndexedList.getNext());
      myIndexedList.remove(0);
      myIndexedList.remove(0);
      myIndexedList.reset();
      assertEquals(user4, myIndexedList.getNext());//test removal
      
      myIndexedList.add(user4);
      assertEquals(myIndexedList.size(), 1);
      myIndexedList.add(user3);
      assertEquals(myIndexedList.size(), 2); //test adding duplicate and testing size
      
      myIndexedList.add(user1, 4);
      assertEquals(myIndexedList.size(), 2);//test adding to impossible number
      myIndexedList.reset();
      
      myIndexedList.add(user1, 1);
      assertEquals(myIndexedList.size(), 3);
      
      assertEquals(user4, myIndexedList.getNext());
      assertEquals(user1, myIndexedList.getNext());
      assertEquals(user3, myIndexedList.getNext());//test adding to a specific index
      
      assertEquals(user4, myIndexedList.get(0));
      myIndexedList.remove(0);
      myIndexedList.set(user4, 1);
      assertEquals(user1, myIndexedList.get(0));
      assertEquals(user4, myIndexedList.get(1)); //test get method and set method
      
      assertEquals(1, myIndexedList.indexOf(user4));//test indexOf
      
      myIndexedList.remove(0);
      myIndexedList.remove(0);
      myIndexedList.remove(0);
      myIndexedList.remove(0);
      
      myIndexedList.add(user1);
      myIndexedList.add(user2);
      myIndexedList.add(user4);
      myIndexedList.add(user3);
      
      
      assertEquals(myIndexedList.toString(), temp);
      
   }
}