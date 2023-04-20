import java.sql.*;
import java.util.*;    // embedded sql query , document preparation

import static java.sql.Types.NULL;

public class Main {
    public static void main(String[] args) throws SQLException{

        Connection con;
        try{
            String url      = "jdbc:mysql://localhost:3306/online_retail_store";
            String uname    = "root";
            String pass     = "root";

            Class.forName("com.mysql.cj.jdbc.Driver");

            con  = DriverManager.getConnection(url, uname, pass);
        }catch(Exception e){
            System.out.println("Encountered Error while connecting : "+e);
            return;
        }

        try{

            while(true) {

                System.out.println("\nWelcome to the Online Shopping Store!!!");
                System.out.println("\n                    MENU                             ");
                System.out.println("\n1) Admin Login");
                System.out.println("2) Seller Login");
                System.out.println("3) Customer Login");
                System.out.println("4) Delivery Partner Login");
                System.out.println("5) Customer Signup");
                System.out.println("6) Check Trending Categories");
                System.out.println("7) Check Trending Products");

                System.out.println("\nSelect one of the options");

                int op;
                Scanner sc = new Scanner(System.in);
                op = sc.nextInt();
                sc.nextLine();

                if (op == 1) {
                    System.out.println("Enter your user id");
                    String usrida = sc.nextLine();                     // (14, 'aruded', 'kncbFp');

                    System.out.println("Enter your password");
                    String pwda = sc.nextLine();

                    String q1 = "Select * FROM Admin WHERE admin_username=? AND admin_password=?";
                    PreparedStatement st1 = con.prepareStatement(q1);

                    st1.setString(1, usrida);
                    st1.setString(2, pwda);
                    int ans = -1;

                    ResultSet rs1 = st1.executeQuery();

                    while (rs1.next()) {
                        if (rs1.getInt(1) != NULL) {
                            ans = 1;
                        }
                    }

                    st1.close();

                    if (ans == 1) {
                        while (true) {
                            System.out.println("\nChoose one of the options");
                            System.out.println("\n1)  Add a Delivery Partner");
                            System.out.println("2)  Remove a Delivery Partner");
                            System.out.println("3)  Add a Seller");
                            System.out.println("4)  Remove a Seller");
                            System.out.println("5)  Remove a Customer");  // embedded sql     TBDDDD
                            System.out.println("6)  View Customer Details");   // done
                            System.out.println("7)  View Delivery Partner Details");   // done
                            System.out.println("8)  [OLAP] View Customers with most money spent");
                            System.out.println("9)  [OLAP] View Delivery Partner with most number of orders delivered");
                            System.out.println("10) Show Customer History");
                            System.out.println("11) Back");

                            System.out.println("\nEnter your option");
                            int opt = sc.nextInt();
                            sc.nextLine();

                            if (opt == 1) {

                                String dpname;
                                String dppass;
                                String dp_firstname;
                                String dp_lastname;
                                String dp_email;
                                String dp_city;
                                String dp_pincode;
                                double dp_mobile;

                                System.out.println("Enter Username   : ");
                                dpname = sc.nextLine();

                                System.out.println("\nEnter Password   : ");
                                dppass = sc.nextLine();

                                System.out.println("\nEnter First Name : ");
                                dp_firstname = sc.nextLine();

                                System.out.println("\nEnter Last Name  : ");
                                dp_lastname = sc.nextLine();

                                System.out.println("\nEnter Mobile No. : ");
                                dp_mobile = sc.nextDouble();

                                System.out.println("\nEnter E mail     : ");
                                dp_email = sc.nextLine();

                                sc.nextLine();

                                System.out.println("\nEnter City       : ");
                                dp_city = sc.nextLine();

                                System.out.println("\nEnter Pin Code   : ");
                                dp_pincode = sc.nextLine();

                                String q11 = "INSERT INTO Delivery_Partner (dp_username,dp_password," +
                                        "dp_firstname,dp_lastname,dp_mobile,dp_email,dp_city," +
                                        "dp_pin_code) VALUES (?,?,?,?,?,?,?,?)";

                                PreparedStatement st11 = con.prepareStatement(q11);

                                st11.setString(1, dpname);
                                st11.setString(2, dppass);
                                st11.setString(3, dp_firstname);
                                st11.setString(4, dp_lastname);
                                st11.setDouble(5, dp_mobile);
                                st11.setString(6, dp_email);
                                st11.setString(7, dp_city);
                                st11.setString(8, dp_pincode);

                                int rs11 = st11.executeUpdate();

                                if (rs11 == 1) {
                                    System.out.println("\nDelivery Partner Added Successfully");
                                } else {
                                    System.out.println("\nEncountered error while adding Delivery Partner");
                                }

                                st11.close();
                            } else if (opt == 2) {

                                while (true) {
                                    System.out.println("\n1. Remove Delivery Partner via ID");
                                    System.out.println("2. Remove Delivery Partner via username");
                                    System.out.println("3. Back");
                                    int opt2;
                                    System.out.println("\nSelect one of the options");
                                    opt2 = sc.nextInt();
                                    sc.nextLine();

                                    if (opt2 == 1) {
                                        String q121 = "DELETE FROM Delivery_Partner WHERE dp_id = ?";
                                        PreparedStatement st121 = con.prepareStatement(q121);

                                        System.out.println("\nEnter ID : ");
                                        int dpid = sc.nextInt();

                                        st121.setInt(1, dpid);

                                        int rs121 = 0;
                                        try {
                                            rs121 = st121.executeUpdate();
                                        }catch(SQLException e){
                                            if (e.getSQLState().equals("23000")) {
                                                System.out.println("You cannot remove Delivery Partner with pending Deliveries!");
                                            }
                                        }
                                        if (rs121 == 1) {
                                            System.out.println("Delivery Partner removed successfully");
                                        } else {
                                            System.out.println("Encountered error while removing delivery partner");
                                        }
                                        st121.close();

                                    } else if (opt2 == 2) {
                                        String q122 = "DELETE FROM Delivery_Partner WHERE dp_username = ?";
                                        PreparedStatement st122 = con.prepareStatement(q122);

                                        System.out.println("\nEnter username : ");
                                        String dpname = sc.nextLine();

                                        st122.setString(1, dpname);

                                        int rs122 = 0;
                                        try {
                                            rs122 = st122.executeUpdate();
                                        }catch(SQLException e) {
                                            if (e.getSQLState().equals("23000")) {
                                                System.out.println("You cannot remove Delivery Partner with pending Deliveries!");
                                            }
                                        }
                                        if (rs122 == 1) {
                                            System.out.println("Delivery Partner removed successfully");
                                        } else {
                                            System.out.println("Encountered error while removing delivery partner");
                                        }
                                        st122.close();

                                    } else if (opt2 == 3) {
                                        break;

                                    } else {
                                        System.out.println("Invalid Choice!");
                                    }
                                }
                            } else if (opt == 3) {

                                String sname;
                                String spass;
                                String s_firstname;
                                String s_lastname;
                                String s_email;
                                String s_city;
                                String s_pincode;
                                double s_mobile;

                                System.out.println("Enter Username   : ");
                                sname = sc.nextLine();

                                System.out.println("\nEnter Password   : ");
                                spass = sc.nextLine();

                                System.out.println("\nEnter First Name : ");
                                s_firstname = sc.nextLine();

                                System.out.println("\nEnter Last Name  : ");
                                s_lastname = sc.nextLine();

                                System.out.println("\nEnter Mobile No. : ");
                                s_mobile = sc.nextDouble();

                                System.out.println("\nEnter E mail     : ");
                                s_email = sc.nextLine();

                                sc.nextLine();

                                System.out.println("\nEnter City       : ");
                                s_city = sc.nextLine();

                                System.out.println("\nEnter Pin Code   : ");
                                s_pincode = sc.nextLine();

                                String q13 = "INSERT INTO Seller (s_username,s_password," +
                                        "s_firstname,s_lastname,s_mobile,s_email,s_city," +
                                        "s_pin_code) VALUES (?,?,?,?,?,?,?,?)";

                                PreparedStatement st13 = con.prepareStatement(q13);

                                st13.setString(1, sname);
                                st13.setString(2, spass);
                                st13.setString(3, s_firstname);
                                st13.setString(4, s_lastname);
                                st13.setDouble(5, s_mobile);
                                st13.setString(6, s_email);
                                st13.setString(7, s_city);
                                st13.setString(8, s_pincode);

                                int rs13 = st13.executeUpdate();

                                if (rs13 == 1) {
                                    System.out.println("\nSeller Added Successfully");
                                } else {
                                    System.out.println("\nEncountered error while adding Seller");
                                }

                                st13.close();
                            } else if (opt == 4 ) {

                                while (true) {
                                    System.out.println("\n1. Remove Seller via ID");
                                    System.out.println("2. Remove Seller via username");
                                    System.out.println("3. Back");
                                    int opt2;
                                    System.out.println("\nSelect one of the options");
                                    opt2 = sc.nextInt();
                                    sc.nextLine();

                                    if (opt2 == 1) {
                                        String q141 = "DELETE FROM Seller WHERE s_id = ?";
                                        PreparedStatement st141 = con.prepareStatement(q141);

                                        System.out.println("\nEnter ID : ");
                                        int sid = sc.nextInt();

                                        st141.setInt(1, sid);

                                        int rs141 = st141.executeUpdate();

                                        if (rs141 == 1) {
                                            System.out.println("Seller removed successfully");
                                        } else {
                                            System.out.println("Encountered error while removing seller");
                                        }
                                        st141.close();

                                    } else if (opt2 == 2) {
                                        String q142 = "DELETE FROM Seller WHERE s_username = ?";
                                        PreparedStatement st142 = con.prepareStatement(q142);

                                        System.out.println("\nEnter username : ");
                                        String sname = sc.nextLine();

                                        st142.setString(1, sname);

                                        int rs142 = st142.executeUpdate();

                                        if (rs142 == 1) {
                                            System.out.println("Seller removed successfully");
                                        } else {
                                            System.out.println("Encountered error while removing seller");
                                        }
                                        st142.close();

                                    } else if (opt2 == 3) {
                                        break;

                                    } else {
                                        System.out.println("Invalid Choice!");
                                    }
                                }

                            } else if (opt == 5) {

                                while(true) {
                                    System.out.println("\n1. Remove Customer via ID");
                                    System.out.println("2. Remove Customer via username");
                                    System.out.println("3. Back");
                                    int opt2;
                                    System.out.println("\nSelect one of the options");
                                    opt2 = sc.nextInt();
                                    sc.nextLine();

                                    if(opt2 == 1){
                                        String q151 = "DELETE FROM Customer WHERE cus_id = ?";
                                        PreparedStatement st151 = con.prepareStatement(q151);

                                        System.out.println("\nEnter ID : ");
                                        int cid = sc.nextInt();

                                        st151.setInt(1,cid);

                                        int rs151 = st151.executeUpdate();

                                        if (rs151 == 1) {
                                            System.out.println("Customer removed successfully");
                                        } else {
                                            System.out.println("Encountered error while removing Customer");
                                        }
                                        st151.close();

                                    }else if (opt2 == 2) {
                                        String q152 = "DELETE FROM Customer WHERE cus_username = ?";
                                        PreparedStatement st152 = con.prepareStatement(q152);

                                        System.out.println("\nEnter username : ");
                                        String cname = sc.nextLine();

                                        st152.setString(1,cname);

                                        int rs152 = st152.executeUpdate();

                                        if (rs152 == 1) {
                                            System.out.println("Customer removed successfully");
                                        } else {
                                            System.out.println("Encountered error while removing customer");
                                        }
                                        st152.close();

                                    }else if (opt2 ==3){
                                        break;

                                    }else{
                                        System.out.println("Invalid Choice!");
                                    }
                                }

                            } else if (opt == 6) {
                                String q16 = "Select * FROM Customer WHERE cus_id=?";
                                PreparedStatement st16 = con.prepareStatement(q16);

                                System.out.println("Enter the customer id");
                                int cid = sc.nextInt();
                                sc.nextLine();

                                st16.setInt(1, cid);

                                ResultSet rs16 = st16.executeQuery();

                                while (rs16.next()) {
                                    System.out.println(String.format("Customer id = %s , Firstname = %s , Lastname = %s , Wallet Balance =%d ",
                                            rs16.getInt(1), rs16.getString(4), rs16.getString(5), rs16.getInt(13)
                                    ));
                                }

                                st16.close();

                            } else if (opt == 7) {
                                String q17 = "Select * Delivery_Partner WHERE dp_id=?";
                                PreparedStatement st17 = con.prepareStatement(q17);

                                System.out.println("Enter the delivery partner id");
                                int dpid = sc.nextInt();
                                sc.nextLine();

                                st17.setInt(1, dpid);

                                ResultSet rs16 = st17.executeQuery();

                                ResultSet rs17 = st17.executeQuery();

                                while (rs17.next()) {
                                    System.out.println(String.format("Customer id = %s , Firstname = %s , Lastname = %s ,  City =%s , Salary =%d ",
                                            rs17.getInt(1), rs17.getString(2), rs17.getString(3), rs17.getString(6), rs17.getInt(4)
                                    ));
                                }

                                st17.close();

                            } else if (opt == 8) {
                                String q18 = "SELECT cus_id, cus_firstname, cus_lastname, " +
                                        "SUM(cp_quantity * cp_price) AS total_spent " +
                                        "FROM Customer " +
                                        "JOIN Cart ON Customer.cus_cart_id = Cart.cart_id " +
                                        "JOIN Cart_Product_List ON Cart.cart_id = Cart_Product_List.cp_cart_id " +
                                        "GROUP BY cus_id " +
                                        "ORDER BY total_spent DESC " +
                                        "LIMIT ?";

                                PreparedStatement st18 = con.prepareStatement(q18);

                                System.out.println("Enter how many customers from the top do you want to see ");
                                int cno = sc.nextInt();
                                sc.nextLine();

                                st18.setInt(1, cno);

                                ResultSet rs18 = st18.executeQuery();

                                while (rs18.next()) {
                                    System.out.println(String.format("Customer id = %d , Firstname = %s , Lastname = %s ,  Total Spent =%d ",
                                            rs18.getInt(1), rs18.getString(2), rs18.getString(3), rs18.getInt(4)
                                    ));
                                }

                                st18.close();

                            } else if (opt == 9) {
                                String q19 = "SELECT dp.dp_id ,dp.dp_firstname, dp.dp_lastname, COUNT(o.order_id) AS num_deliveries " +
                                        "FROM Delivery_Partner dp " +
                                        "JOIN _Order o ON dp.dp_id = o.order_dp_id " +
                                        "GROUP BY dp.dp_id " +
                                        "ORDER BY num_deliveries DESC " +
                                        "LIMIT ? ";

                                PreparedStatement st19 = con.prepareStatement(q19);

                                System.out.println("Enter how many delivery partners from the top do you want to see ");
                                int dpno = sc.nextInt();
                                sc.nextLine();

                                st19.setInt(1, dpno);

                                ResultSet rs19 = st19.executeQuery();

                                while (rs19.next()) {
                                    System.out.println(String.format("Delivery Partner ID = %d , Firstname = %s , Lastname = %s ,                                                                 Total Deliveries Completed =%d ",
                                            rs19.getInt(1), rs19.getString(2), rs19.getString(3), rs19.getInt(4)
                                    ));
                                }

                                st19.close();

                            } else if (opt == 10) {
                                String q110 = "Select * FROM Cust_History ";
                                PreparedStatement st110 = con.prepareStatement(q110);

//                            System.out.println("Enter the customer  id");
//                            int dpid=sc.nextInt();
//                            sc.nextLine();

//                            st110.setInt(1,dpid);

                                ResultSet rs110 = st110.executeQuery();

                                // java.util.Date date;
                                // Timestamp timestamp = resultSet.getTimestamp(i);
                                // if (timestamp != null)
                                //     date = new java.util.Date(timestamp.getTime()));

                                while (rs110.next()) {
//                                System.out.println(String.format("Customer id = %d , Firstname = %s , Lastname = %s ,  Date of Leaving =%s ",
//                                        rs110.getString(1),rs110.getString(2),rs110.getString(3), rs110.getTime(4)
//                                ));
                                    System.out.println("Customer id =" + rs110.getString(1));
                                    System.out.println("Firstname:" + rs110.getString(2));
                                    System.out.println("Lastname:" + rs110.getString(3));
                                    System.out.println("Date of Leaving :" + rs110.getTimestamp(4));
                                    System.out.println(" ");
                                }

                                st110.close();

                            } else if (opt == 11) {
                                break;
                            }
                        }

                    }


                } else if (op == 2) {
                    System.out.println("Enter your user id");     //'dbagwell0', 'T7UbhttD'
                    String usrids = sc.nextLine();
                    System.out.println("Enter your password");
                    String pwds = sc.nextLine();

                    String q2 = "Select * FROM Seller WHERE s_username=? AND s_password=?";
                    PreparedStatement st2 = con.prepareStatement(q2);

                    st2.setString(1, usrids);
                    st2.setString(2, pwds);
                    int ans = -1;

                    ResultSet rs2 = st2.executeQuery();

                    while (rs2.next()) {
                        if (rs2.getInt(1) != NULL) {
                            ans = 1;
                        }
                    }

                    st2.close();

                    if (ans == 1) {

                        while (true) {

                            System.out.println("\nChoose one of the options");
                            System.out.println("\n1) Update Stock"); // done
                            System.out.println("2) Back");

                            System.out.println("\nEnter your option");
                            int opt = sc.nextInt();
                            sc.nextLine();

                            if (opt == 1) {
                                System.out.println("Enter the product id ");
                                int product_id_update = sc.nextInt();
                                sc.nextLine();

                                System.out.println("Enter the amount by which you want to increase the quantity");
                                int stk = sc.nextInt();
                                sc.nextLine();

                                String q21 = "UPDATE Product SET p_stock = p_stock + ? WHERE p_id=?";
                                PreparedStatement st21 = con.prepareStatement(q21);

                                st21.setInt(1, stk);
                                st21.setInt(2, product_id_update);

                                int rs21 = st21.executeUpdate();

                                if (rs21 == 1) {
                                    System.out.println("Stock added succesfully");
                                } else {
                                    System.out.println("Encountered error while adding product");
                                }

                                st21.close();
                            }else if (opt==2){
                                break;
                            }
                        }
                    }

                } else if (op == 3) {
                    System.out.println("Enter your user id");    //'robey17', '6gTZMPZR4'  , 'eciccottoy', 'TizaC3Ze'
                    String usridc = sc.nextLine();

                    System.out.println("Enter your password");
                    String pwdc = sc.nextLine();

                    String q3 = "Select * FROM Customer WHERE cus_username=? AND cus_password=?";
                    PreparedStatement st3 = con.prepareStatement(q3);

                    st3.setString(1, usridc);
                    st3.setString(2, pwdc);
                    int ans = -1;

                    ResultSet rs3 = st3.executeQuery();

                    while (rs3.next()) {
                        if (rs3.getInt(1) != NULL) {
                            ans = 1;
                        }
                    }

                    st3.close();

                    if (ans == 1) {
                        while (true) {
                            System.out.println("\nChoose one of the options");
                            System.out.println("\n1) Check wallet balance");   // embedded sql SELECT cus_wallet FROM Customer WHERE cus_id=?
                            System.out.println("2) Update wallet balance");
                            System.out.println("3) View cart");  // done
                            System.out.println("4) Check Subscription");
                            System.out.println("5) Upgrade Subscription");
                            System.out.println("6) Delete Account");  // embedded sql DELETE FROM Customer WHERE cus_id=? ( using trigger , entries to be added to history table)
                            System.out.println("7) Checkout");
                            System.out.println("8) Back"); //done


                            System.out.println("\nEnter your option");
                            int opt = sc.nextInt();
                            sc.nextLine();

                            if (opt == 1) {
                                String q31 = "Select * FROM Customer WHERE cus_username=?";
                                PreparedStatement st31 = con.prepareStatement(q31);

                                // System.out.println("Enter the customer id");
                                // int cid=sc.nextInt();
                                // sc.nextLine();

                                st31.setString(1, usridc);

                                ResultSet rs31 = st31.executeQuery();

                                while (rs31.next()) {
                                    System.out.println(String.format("Wallet Balance =%d",
                                            rs31.getInt(13)
                                    ));
                                }

                                st31.close();

                            } else if (opt == 3) {
                                String q33 = "SELECT P.p_name,P.p_cost,CP.cp_quantity,CP.cp_price FROM Product as P , Cart_Product_List as CP , Customer as C WHERE P.p_id=CP.cp_pid AND CP.cp_cart_id=C.cus_cart_id AND C.cus_username=?;";
                                PreparedStatement st33 = con.prepareStatement(q33);

                                // System.out.println("Enter the customer id");
                                // int cid=sc.nextInt();
                                // sc.nextLine();

                                st33.setString(1, usridc);

                                ResultSet rs33 = st33.executeQuery();

                                while (rs33.next()) {
                                    System.out.println(String.format("Product name = %s , Product cost = %d , product quantity = %d , total price =%d ",
                                            rs33.getString(1), rs33.getInt(2), rs33.getInt(3), rs33.getInt(4)
                                    ));
                                }

                                st33.close();

                            } else if (opt == 6) {
                                String q36 = "DELETE FROM Customer WHERE cus_username=?";
                                PreparedStatement st36 = con.prepareStatement(q36);

//                            System.out.println("Enter the customer id");
//                            int cid=sc.nextInt();
//                            sc.nextLine();

                                st36.setString(1, usridc);

                                // ResultSet rs15    = st15.executeQuery();

                                int rs36 = st36.executeUpdate();

                                if (rs36 == 1) {
                                    System.out.println("Account deleted  succesfully , Sorry if you have had a bad experience");
                                    st36.close();
                                    break;
                                } else {
                                    System.out.println("Encountered error while deleting your account!!");
                                }

                                st36.close();

                            } else if (opt == 7) {

                                int balance = 0;
                                String q370 = "Select cus_wallet FROM Customer WHERE cus_username= ? ";
                                PreparedStatement st370 = con.prepareStatement(q370);

                                st370.setString(1, usridc);

                                ResultSet rs370 = st370.executeQuery();

                                while (rs370.next()) {
                                    balance=rs370.getInt(1);
                                }

                                st370.close();

                                String q371 = "SELECT cp_pid,cp_quantity FROM Cart_Product_List where cp_cart_id = (SELECT cus_cart_id FROM Customers WHERE cus_username=?)";
                                PreparedStatement st371 = con.prepareStatement(q371);

                                st371.setString(1,usridc);

                                ResultSet rs371 = st371.executeQuery();

                                int ans2 = 0;
                                while( rs371.next() ){
                                    String q372 = "SELECT p_stock FROM Products where p_id = ?";
                                    PreparedStatement st372 = con.prepareStatement(q372);

                                    st372.setInt(1,rs371.getInt(1));
                                    ResultSet rs372 = st372.executeQuery();

                                    if (rs372.getInt(1) < rs371.getInt(2)){
                                        System.out.println(" One or More Product is out of stock! View cart to know more ");
                                        ans2 = 1;
                                        break;
                                    }
                                    st372.close();
                                }
                                st371.close();

                                if(ans2==1){
                                    continue;
                                }

                                String q373 = "SELECT total_price FROM Cart WHERE cart_id = (SELECT cus_cart_id FROM Customers WHERE cus_username=?)";
                                PreparedStatement st373 = con.prepareStatement(q373);

                                st373.setString(1,usridc);

                                ResultSet rs373 = st373.executeQuery();

                                if ( rs373.getInt(1) > balance ){
                                    System.out.println("You are "+ (balance - rs373.getInt(1)) +" INR short!");
                                    continue;
                                }

                        } else if (opt == 8) {
                                break;
                            }
                        }
                    }

                } else if (op == 4) {
                    System.out.println("Enter your user id");    //'fbenziep', 'l4yBKhdUbdg'
                    String usriddp = sc.nextLine();

                    //check with embedded sql
                    System.out.println("Enter your password");
                    String pwddp = sc.nextLine();

                    String q4 = "Select dp_id FROM Delivery_Partner WHERE dp_username=? AND dp_password=?";
                    PreparedStatement st4 = con.prepareStatement(q4);

                    st4.setString(1, usriddp);
                    st4.setString(2, pwddp);
                    int ans = -1;

                    ResultSet rs4 = st4.executeQuery();
//                int dpID;

                    while (rs4.next()) {
                        if (rs4.getInt(1) != NULL) {
                            ans = 1;
                            //dpID=rs.getInt(1);
                        }
                    }

                    st4.close();

                    if (ans == 1) {
                        while (true) {
                            System.out.println("Choose one of the options");
                            System.out.println("\n1) Check Compensation");  // embedded sql
                            System.out.println("2) Check orders to be delivered ");
                            System.out.println("3) Update orders list");
                            System.out.println("4) Back");

                            System.out.println("\nEnter your option");
                            int opt = sc.nextInt();
                            sc.nextLine();

                            if (opt == 1) {
                                String q41 = "Select dp_salary FROM Delivery_Partner WHERE dp_username=?";
                                PreparedStatement st41 = con.prepareStatement(q41);

                                st41.setString(1, usriddp);

                                ResultSet rs41 = st41.executeQuery();

                                while (rs41.next()) {
                                    System.out.println(String.format("Salary = %d",
                                            rs41.getInt(1)
                                    ));
                                }

                                st4.close();

                            } else if (opt == 4) {
                                break;
                            }
                        }
                    }


                } else if (op == 5) {   // customer signup
                    System.out.println("Enter your user id");
                    String usrid_csu = sc.nextLine();
                    System.out.println("Enter your password");


                    String pwd_csu = sc.nextLine();    // check if they exist , then ask for another , else add it to the table
                } else if (op == 6) { // check trending categories

                    String q6 = "SELECT p.p_name, COUNT(DISTINCT cpl.cp_cart_id) as cart_count " +
                            "FROM Product p " +
                            "JOIN Cart_Product_List cpl ON p.p_id = cpl.cp_pid " +
                            "GROUP BY p.p_id " +
                            "ORDER BY cart_count DESC " +
                            "LIMIT 5";

                    PreparedStatement st6 = con.prepareStatement(q6);

                    ResultSet rs6 = st6.executeQuery(q6);

                    while (rs6.next()) {
                        System.out.println(String.format("Product name = %s ",
                                rs6.getString(1)
                        ));
                    }

                    st6.close();

                } else if (op == 7) { // check trending products

                    String q7 = "SELECT Category.cat_name, " +
                            "SUM(cp_quantity) AS total_in_cart " +
                            "FROM Category " +
                            "JOIN Product ON Category.cat_id = Product.p_cat_id " +
                            "JOIN Cart_Product_List ON Product.p_id = Cart_Product_List.cp_pid " +
                            "GROUP BY Category.cat_name " +
                            "ORDER BY total_in_cart DESC " +
                            "LIMIT 5";

                    PreparedStatement st7 = con.prepareStatement(q7);

                    ResultSet rs7 = st7.executeQuery(q7);

                    while (rs7.next()) {
                        System.out.println(String.format("Category name = %s ",
                                rs7.getString(1)
                        ));
                    }

                    st7.close();

                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
        con.close();
    }
}