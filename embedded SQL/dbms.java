//package dbms;
//
//import java.sql.*;
//import java.util.*;
//import sqlj.runtime.*;
//import sqlj.runtime.ref.*;
//import java.io.*;
//import oracle.sqlj.runtime.*;
//
//public class dbms{
//    
//    public static void main(String[] args){
//        
//        Connection con;
//        
//        try{     
//            String url      = "jdbc:mysql://localhost:3306/Online_Retail_Store";
//            String uname    = "root";
//            String pass     = "root";
//
//            con             = DriverManager.getConnection(url, uname, pass);
//        }catch(Exception e){
//            System.out.println("Encountered Error while connecting : "+e);
//            return;
//        }
//        
//    }
//}


package dbms;

import java.sql.*;
import java.util.*;

public class dbms{

    public static void main(String[] args) {

        Connection con;
        try{     
            String url      = "jdbc:mysql://localhost:3306/Online_Retail_Store";
            String uname    = "root";
            String pass     = "root";

//          Class.forName("com.mysql.cj.jdbc.Driver");

            con  = DriverManager.getConnection(url, uname, pass);
        }catch(Exception e){
            System.out.println("Encountered Error while connecting : "+e);
            return;
        }
        
        try{
            PreparedStatement st;
            st              = con.prepareStatement("SELECT * FROM Cart_Product_List WHERE cp_cart_id=?");
            
            Scanner sc      = new Scanner(System.in);
            int query       = sc.nextInt();
            
            st.setInt(1, query);
            
            ResultSet rs    = st.executeQuery();
            
            while (rs.next()){
                System.out.println(String.format("Product ID = %d , Product Quantity = %d , Total Price = %d",
                                    rs.getInt("cp_pid"),
                                    rs.getInt("cp_quantity"),
                                    rs.getInt("cp_price")
                                    
                ));
            }
            
        }catch(Exception e){
            System.out.println("Encountered Error while preparing statement : "+e);
        }
    }   
}