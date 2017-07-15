//Mike Faunda III
import java.io.*;

public class User extends Person
{
   static String defaultUsername = "Username0";
   String username;
   String email;
   String phoneNumber;
   String password;
   File profilePhoto;
   String[] savedUser = new String[10];
   
   
   User()
   {
      firstName = "John";
      lastName = "Doe";
      ssn = "123456789";
      dob = "01/01/1995";
      gender = "Male";
      username = defaultUsername;
      defaultUsername = defaultUsername + "0";
      email = "123John@mail.com";
      phoneNumber = "17037037003";
      password = "password";
      profilePhoto = null;
   }

   User(String fn, String ln, String ss, String db, String gn, String un, String em, String pn, String pw, File pp)
   {
      firstName = fn;
      lastName = ln;
      ssn = ss;
      dob = db;
      gender = gn;
      username = un;
      email = em;
      phoneNumber = pn;
      password = pw;
      profilePhoto = pp;
   }
   
   public void saveUser()
   {
      savedUser[0] = firstName;
      savedUser[1] = lastName;
      savedUser[2] = ssn;
      savedUser[3] = dob;
      savedUser[4] = gender;
      savedUser[5] = username;
      savedUser[6] = email;
      savedUser[7] = phoneNumber;
      savedUser[8] = password;
   }
   
   public String[] getUserInfo()
   {
      return savedUser;
   }
   
   public String getUsername()
   {
      return username;
   }
   
   public String getEmail()
   {
      return email;
   }
   
   public String getPhone()
   {
      return phoneNumber;
   }
   
   public String getPassword()
   {
      return password;
   }
   
   public File getPicture()
   {
      return profilePhoto;
   }
   
   public void setEmail(String em)
   {
      email = em;
   }
   
   public void setPhone(String pn)
   {
      phoneNumber = pn;
   }
   
   public void setPassword(String pw)
   {
      password = pw;
   }
   
   public void setPicture(File pp)
   {
      profilePhoto = pp;
   }
   
}