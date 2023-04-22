import java.sql.*;
import java.util.*;

import static java.sql.Types.NULL;

public class Main2 {
    public static void main(String[] args) throws SQLException{

        Connection con;
        try{
            String url      = "jdbc:mysql://localhost:3306/demodb";
            String uname    = "root";
            String pass     = "anjaneya@E-35";

            Class.forName("com.mysql.cj.jdbc.Driver");

            con  = DriverManager.getConnection(url, uname, pass);
        }catch(Exception e){
            System.out.println("Encountered Error while connecting : "+e);
            return;
        }

        try{

            System.out.println("Welcome to the Online Shopping Store!!!");
            System.out.println("                    MENU                             ");
            System.out.println("1) Admin Login");
            System.out.println("2) Seller Login");
            System.out.println("3) Customer Login");
            System.out.println("4) Delivery Partner Login");
            System.out.println("5) Customer Signup");
            System.out.println("6) Check Trending Categories");
            System.out.println("7) Check Trending Products");

            System.out.println("Select one of the options");

            int op;
            Scanner sc=new Scanner(System.in);
            op=sc.nextInt();
            sc.nextLine();

            if(op==1){
                System.out.println("Enter your user id");
                String usrida=sc.nextLine();                     // (14, 'aruded', 'kncbFp');

                System.out.println("Enter your password");
                String pwda=sc.nextLine();

                String q="Select * FROM Admin WHERE admin_username=? AND admin_password=?";
                PreparedStatement st = con.prepareStatement(q);

                st.setString(1,usrida);
                st.setString(2,pwda);
                int ans=-1;

                ResultSet rs    = st.executeQuery();

                while (rs.next()){
                    if(rs.getInt(1)!=NULL){
                        ans=1;
                    }
                }

                if(ans==1){
                    while(true){
                        System.out.println("Choose one of the options");
                        System.out.println("1)  Add a Delivery Partner");
                        System.out.println("2)  Remove a Delivery Partner");
                        System.out.println("3)  Add a Seller");
                        System.out.println("4)  Remove a Seller");
                        System.out.println("5)  Remove a Customer");  // embedded sql     TBDDDD
                        System.out.println("6)  View Customer Details");   // done
                        System.out.println("7)  View Delivery Partner Details");   // done
                        System.out.println("8)  [OLAP] View Customers with most money spent");
                        System.out.println("9)  [OLAP] View Delivery Partner with most number of orders delivered");
                        System.out.println("10) Show Customer History");
                        System.out.println("11) Exit");

                        System.out.println("Enter your option");
                        int opt=sc.nextInt();
                        sc.nextLine();

                        if(opt==5){
                            String q15="DELETE FROM Customer WHERE cus_id=?";
                            PreparedStatement st15 = con.prepareStatement(q15);

                            System.out.println("Enter the customer id");
                            int cid=sc.nextInt();
                            sc.nextLine();

                            st15.setInt(1,cid);

                            // ResultSet rs15    = st15.executeQuery();

                            int rs15 = st15.executeUpdate();

                            if (rs15==1){
                                System.out.println("Customer removed  succesfully");
                            }else{
                                System.out.println("Encountered error while  removing customer");
                            }

                            // while (rs15.next()){
                            //     System.out.println(String.format("Customer id = %s , Firstname = %s , Lastname = %s , Wallet Balance =%d ",
                            //             rs15.getInt(1),rs15.getString(4),rs15.getString(5),rs15.getInt(13)
                            //     ));
                            // }
                        }
                        
                        else if(opt==6){
                            String q16="Select * FROM Customer WHERE cus_id=?";
                            PreparedStatement st16 = con.prepareStatement(q16);

                            System.out.println("Enter the customer id");
                            int cid=sc.nextInt();
                            sc.nextLine();

                            st16.setInt(1,cid);

                            ResultSet rs16    = st16.executeQuery();

                            while (rs16.next()){
                                System.out.println(String.format("Customer id = %s , Firstname = %s , Lastname = %s , Wallet Balance =%d ",
                                        rs16.getInt(1),rs16.getString(4),rs16.getString(5),rs16.getInt(13)
                                ));
                            }

                        }
                        else if(opt==7){
                            String q17="Select * Delivery_Partner WHERE dp_id=?";
                            PreparedStatement st17 = con.prepareStatement(q);

                            System.out.println("Enter the delivery partner id");
                            int dpid=sc.nextInt();
                            sc.nextLine();

                            st17.setInt(1,dpid);

                            ResultSet rs16    = st17.executeQuery();

                            ResultSet rs17    = st17.executeQuery();

                            while (rs17.next()){
                                System.out.println(String.format("Customer id = %s , Firstname = %s , Lastname = %s ,  City =%s , Salary =%d ",
                                        rs17.getInt(1),rs17.getString(2),rs17.getString(3),rs17.getString(6), rs17.getInt(4)
                                ));
                            }
                        }
                        else if (opt==8){
                            String q18="SELECT cus_id, cus_firstname, cus_lastname, " +
                                       "SUM(cp_quantity * cp_price) AS total_spent " +
                                       "FROM Customer " +
                                       "JOIN Cart ON Customer.cus_cart_id = Cart.cart_id " +
                                       "JOIN Cart_Product_List ON Cart.cart_id = Cart_Product_List.cp_cart_id "+
                                       "GROUP BY cus_id " +
                                       "ORDER BY total_spent DESC "+
                                       "LIMIT ?"; 

                            PreparedStatement s18= con.prepareStatement(q18);
                            
                            System.out.println("Enter how many customers from the top do you want to see ");
                            int cno=sc.nextInt();
                            sc.nextLine();

                            s18.setInt(1,cno);
                            
                            ResultSet rs18 = s18.executeQuery();

                            while (rs18.next()){
                                System.out.println(String.format("Customer id = %d , Firstname = %s , Lastname = %s ,  Total Spent =%d ",
                                        rs18.getInt(1),rs18.getString(2),rs18.getString(3),rs18.getInt(4)
                                ));
                            }
                        }
                        else if (opt==9){
                            String q19="SELECT dp.dp_id ,dp.dp_firstname, dp.dp_lastname, COUNT(o.order_id) AS num_deliveries "+
                                       "FROM Delivery_Partner dp "+
                                       "JOIN _Order o ON dp.dp_id = o.order_dp_id "+
                                       "GROUP BY dp.dp_id "+
                                       "ORDER BY num_deliveries DESC "+
                                       "LIMIT ? ";

                            PreparedStatement s19= con.prepareStatement(q19);
                            
                            System.out.println("Enter how many delivery partners from the top do you want to see ");
                            int dpno=sc.nextInt();
                            sc.nextLine();

                            s19.setInt(1,dpno);
                            
                            ResultSet rs19 = s19.executeQuery();

                            while (rs19.next()){
                                System.out.println(String.format("Delivery Partner ID = %d , Firstname = %s , Lastname = %s ,                                                                 Total Deliveries Completed =%d ",
                                        rs19.getInt(1),rs19.getString(2),rs19.getString(3),rs19.getInt(4)
                                ));
                            }
                        }
                        else if(opt==10){
                            String q110"Select * Delivery_Partner WHERE dp_id=?";
                            PreparedStatement st110 = con.prepareStatement(q110);

                            System.out.println("Enter the delivery partner id");
                            int dpid=sc.nextInt();
                            sc.nextLine();

                            st110.setInt(1,dpid);

                            ResultSet rs110    = st110.executeQuery();

                            // java.util.Date date;
                            // Timestamp timestamp = resultSet.getTimestamp(i);
                            // if (timestamp != null)
                            //     date = new java.util.Date(timestamp.getTime()));

                            while (rs110.next()){
                                System.out.println(String.format("Customer id = %d , Firstname = %s , Lastname = %s ,  Date of Leaving =%s ",
                                        rs110.getString(1),rs110.getString(2),rs110.getString(3), rs110.getTimestamp(4)
                                ));
                            }
                        }
                        else if (opt==11){
                            break;
                        }
                    }

                }




            } else if (op==2) {
                System.out.println("Enter your user id");     //'dbagwell0', 'T7UbhttD'
                String usrids=sc.nextLine();
                System.out.println("Enter your password");
                String pwds=sc.nextLine();

                String q="Select * FROM Seller WHERE s_username=? AND s_password=?";
                PreparedStatement st = con.prepareStatement(q);

                st.setString(1,usrids);
                st.setString(2,pwds);
                int ans=-1;

                ResultSet rs    = st.executeQuery();

                while (rs.next()){
                    if(rs.getInt(1)!=NULL){
                        ans=1;
                    }
                }

                if(ans==1){

                    System.out.println("Choose one of the options");
                    System.out.println("1)Update Stock"); // done

                    System.out.println("Enter your option");
                    int opt=sc.nextInt();
                    sc.nextLine();

                    if(opt==1){
                        System.out.println("Enter the product id ");
                        int product_id_update=sc.nextInt();
                        sc.nextLine();

                        System.out.println("Enter the amount by which you want to increase the quantity");
                        int stk=sc.nextInt();
                        sc.nextLine();

                        String q21="UPDATE Product SET p_stock = p_stock + ? WHERE p_id=?";
                        PreparedStatement st21 = con.prepareStatement(q21);

                        st21.setInt(1,stk);
                        st21.setInt(2,product_id_update);

                        int rs21 = st21.executeUpdate();

                        if (rs21==1){
                            System.out.println("Stock added succesfully");
                        }else{
                            System.out.println("Encountered error while adding product");
                        }


                    }
                }


            } else if (op==3) {
                System.out.println("Enter your user id");    //'robey17', '6gTZMPZR4'
                String usridc=sc.nextLine();

                System.out.println("Enter your password");
                String pwdc=sc.nextLine();

                String q="Select * FROM Customer WHERE cus_username=? AND cus_password=?";
                PreparedStatement st = con.prepareStatement(q);

                st.setString(1,usridc);
                st.setString(2,pwdc);
                int ans=-1;

                ResultSet rs    = st.executeQuery();

                while (rs.next()){
                    if(rs.getInt(1)!=NULL){
                        ans=1;
                    }
                }

                if(ans==1){
                    while(true){
                        System.out.println("Choose one of the options");
                        System.out.println("1)Check wallet balance");   // embedded sql SELECT cus_wallet FROM Customer WHERE cus_id=?
                        System.out.println("2)Update wallet balance");  //TBD 
                        System.out.println("3)View cart");  // done 
                        System.out.println("4)Check Subscription");    // TBD
                        System.out.println("5)Upgrade Subscription");   // TBD
                        System.out.println("6)Delete Account");  // embedded sql DELETE FROM Customer WHERE cus_id=? ( using trigger , entries to be added to history table)
                        System.out.println("7)Exit"); //done




                        System.out.println("Enter your option");
                        int option=sc.nextInt();
                        sc.nextLine();

                        if(option==1){
                            String q31="Select * FROM Customer WHERE cus_username=?";
                            PreparedStatement st31 = con.prepareStatement(q31);

                            st31.setString(1,usridc);

                            ResultSet rs31    = st31.executeQuery();

                            while (rs31.next()){
                                System.out.println(String.format("Wallet Balance =%d",
                                        rs31.getInt(13)
                                ));
                            }
                        }else if(option==2){  // check wallet balance
                            System.out.println("Enter the amount by which you want to increase your balance:")
                            int incr_bal=0;
                            incr_bal=sc.nextInt();
                            sc.nextLine();
                            String q32 = "UPDATE Customer SET cus_walleet=cus_wallet+incr_bal WHERE cus_username=?";
                            PreparedStatement st32 = prepareStatement(q32);

                            st32.setInt(1,usridc);

                            ResultSet rs32 = st32.executeUpdate();

                            if(rs32 == 1){

                            }
                            else{
                                System.out.println("ERROR encountered in updating the balance");
                            }
                        }
                        
                        else if(option==3){
                            String q33="SELECT P.p_name,P.p_cost,CP.cp_quantity,CP.cp_price FROM Product as P , Cart_Product_List as CP , Customer as C WHERE P.p_id=CP.cp_pid AND CP.cp_cart_id=C.cus_cart_id AND C.cus_username=?;";
                            PreparedStatement st33 = con.prepareStatement(q33);

                            st33.setString(1,usridc);

                            ResultSet rs33   = st33.executeQuery();

                            while (rs33.next()){
                                System.out.println(String.format("Product name = %s , Product cost = %d , product quantity = %d , total price =%d ",
                                        rs33.getString(1),rs33.getInt(2),rs33.getInt(3),rs33.getInt(4)
                                ));
                            }
                        }else if(option==4){

                        }else if(option == 5){  // upgrade subscription

                        }
                        
                        else if(option==6){
                            String q26="DELETE FROM Customer WHERE cus_id=?";
                            PreparedStatement st26 = con.prepareStatement(q26);

                            System.out.println("Enter the customer id");
                            int cid=sc.nextInt();
                            sc.nextLine();

                            st26.setInt(1,cid);

                            // ResultSet rs15    = st15.executeQuery();

                            int rs26 = st26.executeUpdate();

                            if (rs26==1){
                                System.out.println("Account deleted  succesfully , Sorry if you have had a bad experience");
                            }else{
                                System.out.println("Encountered error while deleting your account!!");
                            }
                        }
                        else if(option ==7){
                            break;
                        }
                    }
                }

            } else if (op==4) {
                System.out.println("Enter your user id");    //'fbenziep', 'l4yBKhdUbdg' , use this one ->'nrenfortht', 'dLnoaLn6l'
                String usriddp=sc.nextLine();

                //check with embedded sql
                System.out.println("Enter your password");
                String pwddp=sc.nextLine();

                String q="Select dp_id FROM Delivery_Partner WHERE dp_username=? AND dp_password=?";
                PreparedStatement st = con.prepareStatement(q);

                st.setString(1,usriddp);
                st.setString(2,pwddp);
                int ans=-1;

                ResultSet rs    = st.executeQuery();
//                int dpID;

                while (rs.next()){
                    if(rs.getInt(1)!=NULL){
                        ans=1;
                        //dpID=rs.getInt(1);
                    }
                }

                if(ans==1){
                    while(true){
                        System.out.println("Choose one of the options");
                        System.out.println("1) Check Compensation");  // embedded sql
                        System.out.println("2) Check orders to be delivered ");
                        System.out.println("3) Update orders list");
                        System.out.println("4) Exit");

                        System.out.println("Enter your option");
                        int opt=sc.nextInt();
                        sc.nextLine();

                        if (opt==1){
                            String q41="Select dp_salary FROM Delivery_Partner WHERE dp_username=?";
                            PreparedStatement st41 = con.prepareStatement(q41);

                            st41.setString(1,usriddp);

                            ResultSet rs41 = st41.executeQuery();

                            while (rs41.next()){
                                System.out.println(String.format("Salary = %d",
                                        rs41.getInt(4)
                                ));
                            }
                        }
                        else if(opt==2){  // orders to be delivered



                                int dp_id=0; // to be found out , since required in the second embedded query

                                String q042="Select * FROM Delivery_Partner WHERE dp_username=?";
                                PreparedStatement st042 = con.prepareStatement(q042);

                                st042.setString(1,usriddp);

                                ResultSet rs042 = st042.executeQuery();

                                while (rs042.next()){
                                    dp_id=rs042.getInt(1);
                                }
                                

                                String q42="Select * FROM _Order WHERE order_dp_id=? and order_delivery_status='in-transit'";
                                PreparedStatement st42 = con.prepareStatement(q42);

                                st42.setInt(1,dp_id);

                                ResultSet rs42 = st42.executeQuery();
                                

                                while (rs42.next()){

                                    System.out.println("Order id :" + rs42.getInt(1));
                                    System.out.println("Customer id :" + rs42.getInt(3));
                                    System.out.println("Order date:" + rs42.getTimestamp(5));
                                    System.out.println("Firstname :" + rs42.getString(7));
                                    System.out.println("Lastname :" + rs42.getString(8));
                                    System.out.println("Mobile Number :" + rs42.getLong(9));
                                    System.out.println("Street :" + rs42.getString(11));
                                    System.out.println("City :" + rs42.getString(12));
                                    System.out.println("Pin Code :" + rs42.getString(13));
                                    System.out.println(" ");


                                }

                        }
                        else if(opt==3){
                            int oid=0;

                            System.out.println("Enter the order_id whose status you want to update ");      //
                            oid=sc.nextInt();
                            sc.nextLine();

                            String q43="UPDATE _Order SET order_delivery_status='delivered' WHERE order_id = ? ";

                            PreparedStatement st43=con.prepareStatement(q43);

                            st43.setInt(1,oid);

                            int rs43 = st43.executeUpdate();

                            if(rs43 == 1){
                                System.out.println("Delivery updated successfully !!");
                            }
                            else{
                                System.out.println("ERROR Encountered while updating delivery");
                            }

                        }
                        
                        else if(opt==4){
                            break;
                        }
                    }
                }


            } else if (op==5){   // customer signup
                System.out.println("Enter your user id");
                String usrid_csu=sc.nextLine();
                System.out.println("Enter your password");



                String pwd_csu=sc.nextLine();    // check if they exist , then ask for another , else add it to the table
            } else if (op==6){ // check trending categories

                String q6="SELECT p.p_name, COUNT(DISTINCT cpl.cp_cart_id) as cart_count "+
                          "FROM Product p "+
                          "JOIN Cart_Product_List cpl ON p.p_id = cpl.cp_pid "+
                          "GROUP BY p.p_id "+
                          "ORDER BY cart_count DESC " +
                          "LIMIT 5";

                PreparedStatement st6 = con.prepareStatement(q6);

                ResultSet rs6 = st6.executeQuery(q6);

                while (rs6.next()){
                    System.out.println(String.format("Product name = %s ",
                    rs6.getString(1)
                    ));
                }

            } else if (op==7){ // check trending products

                String q7="SELECT Category.cat_name, "+
                          "SUM(cp_quantity) AS total_in_cart "+
                          "FROM Category "+
                          "JOIN Product ON Category.cat_id = Product.p_cat_id "+
                          "JOIN Cart_Product_List ON Product.p_id = Cart_Product_List.cp_pid "+
                          "GROUP BY Category.cat_name "+
                          "ORDER BY total_in_cart DESC "+
                          "LIMIT 5";

                PreparedStatement st7 = con.prepareStatement(q7);

                ResultSet rs7 = st7.executeQuery(q7);

                while (rs7.next()){
                    System.out.println(String.format("Category name = %s ",
                    rs7.getString(1)
                    ));
                }

            }

//            st.close();
//            con.close();

        } catch (Exception e){
            System.out.println(e);
        }

    }
}

