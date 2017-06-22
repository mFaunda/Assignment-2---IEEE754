//Mike Faunda III
import java.io.*;

public class Person implements java.io.Serializable
{

   String firstName;
   String lastName;
   String ssn;
   String dob;
   String gender;


   public void Person()
   {
      firstName = "John";
      lastName = "Doe";
      ssn = "123456789";
      dob = "01/01/1995";
      gender = "Male";
   }

   public void Person(String fn, String ln, String ss, String db, String gn)
   {
      firstName = fn;
      lastName = ln;
      ssn = ss;
      dob = db;
      gender = gn;
   }

   public String getFullName()
   {
      return firstName +" "+ lastName;
   }

   public String getFirstName()
   {
      return firstName;
   }

   public String getLastName()
   {
      return lastName;
   }

   public String getSSN()
   {
      return ssn;
   }

   public String getGender()
   {
      return gender;
   }

   public void setName(String fn)
   {
      firstName = fn;
   }

   public void setName(String fn, String ln)
   {
      firstName = fn;
      lastName = ln;
   }

   public void setSSN(String ss)
   {
      ssn = ss;
   }

   public void setGender(String gn)
   {
      gender = gn;
   }

}