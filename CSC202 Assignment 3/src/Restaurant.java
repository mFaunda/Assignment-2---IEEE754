//Mike Faunda III

import java.awt.image.BufferedImage;
import java.io.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.*;
import java.util.Scanner;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.lang.Math;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;

public class Restaurant implements Comparable<Restaurant>
{

   String name;
   String address;
   String lat;
   String lon;
   String phone;
   String picture;
   
   Restaurant()
   {
      name = null;
      address = null;
      lat = null;
      lon = null;
      phone = null;
      picture = null;
   }

   Restaurant(String n, String a, String la, String lo, String ph, String pic)
   {
      name = n;
      address = a;
      lat = la;
      lon = lo;
      phone = ph;
      picture = pic;
   }

   public void setName(String n)
    {
        name = n;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setAddress(String a)
    {
        address = a;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setLat(String c)
    {
    lat = c;
    }
    
    public String getLat()
    {
        return lat;
    }
    
    public void setLon(String c)
    {
        lon = c;
    }
    
    public String getLon()
    {
        return lon;
    }
    
    public void setPhone(String p)
    {
        phone = p;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPicture(String p)
    {
        picture = p;
    }
    
    public String getPicture()
    {
        return picture;
    }
    
    public int compareTo(Restaurant res)
    {
        if(Double.parseDouble(res.getLat())<Double.parseDouble(lat))
        {
            return 1;
        }
        else if(Double.parseDouble(res.getLat())>Double.parseDouble(lat))
        {
            return -1;
        }
        else
            return 0;
    }
   public String toString()
   {
       return name+"\n"+
               address+"\n"+
               lat+"\n"+
               lon+"\n"+
               phone+"\n"+
               picture;
   }
   
}