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
            
//            SELECT Product.p_id, Product.p_descrip, Product.p_cost, Cart_Product_List.cp_price
//            FROM Cart_Product_List
//            LEFT JOIN Product
//            ON Cart_Product_List.cp_pid=Product.p_id
//            WHERE Cart_Product_List.cp_cart_id = (SELECT cus_cart_id
//                                                  FROM Customer
//                                                  WHERE Customer.cus_id=222)
                                                  
            st              = con.prepareStatement("SELECT Product.p_name, Product.p_descrip, Product.p_cost, Cart_Product_List.cp_price " +
                                                   "FROM Cart_Product_List " +
                                                   "LEFT JOIN Product " +
                                                   "ON Cart_Product_List.cp_pid=Product.p_id " +
                                                   "WHERE Cart_Product_List.cp_cart_id = (SELECT cus_cart_id " +
                                                   "                                      FROM Customer " +
                                                   "                                      WHERE Customer.cus_id=?)");
            
            Scanner sc      = new Scanner(System.in);
            int query       = sc.nextInt();
            
            st.setInt(1, query);
            
            ResultSet rs    = st.executeQuery();
            
            while (rs.next()){
                System.out.println(String.format("Product Name = %s \nProduct Description = %s \nProduct Cost = %d \nTotal Price = %d \n\n",
                                    rs.getString("Product.p_name"),
                                    rs.getString("Product.p_descrip"),
                                    rs.getInt("Product.p_cost"),
                                    rs.getInt("Cart_Product_List.cp_price")                 
                ));
            }
            
        }catch(Exception e){
            System.out.println("Encountered Error while preparing statement : "+e);
        }
    }   
}