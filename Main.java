import java.sql.*;
import java.util.Scanner;

public class Main {
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
            System.out.println("3) Delivery Partner Login");
            System.out.println("4) Customer Login");
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
                String usrida=sc.nextLine();
                sc.nextLine();
                //check with embedded sql
                System.out.println("Enter your password");
                String pwda=sc.nextLine();


                System.out.println("Choose one of the options");
                System.out.println("1) Add a Delivery Partner");  // embedded sql
                System.out.println("2) Remove a Delivery Partner");
                System.out.println("3) Add a Seller");
                System.out.println("4) Remove a Seller");  // embedded sql
                System.out.println("5) Remove a Customer");  // embedded sql
                System.out.println("5) View Customer Details");  // embedded sql
                System.out.println("View Delivery Partner Details");   // embedded sql
            } else if (op==2) {
                System.out.println("Enter your user id");
                String usrids=sc.nextLine();
                sc.nextLine();
                //check with embedded sql
                System.out.println("Enter your password");
                String pwds=sc.nextLine();


                System.out.println("Choose one of the options");
                System.out.println("1)Update Stock"); // embedded sql



            } else if (op==3) {
                System.out.println("Enter your user id");
                String usridd=sc.nextLine();
                sc.nextLine();
                //check with embedded sql
                System.out.println("Enter your password");
                String pwdd=sc.nextLine();

                System.out.println("Choose one of the options");
                System.out.println("1)Check wallet balance");   // embedded sql
                System.out.println("2)Update wallet balance");
                System.out.println("3)View cart");  // embedded sql
                System.out.println("4)Check Subscription");
                System.out.println("5)Upgrade Subscription");
                System.out.println("6)Delete Account");  // embedded sql

            } else if (op==4) {
                System.out.println("Enter your user id");
                String usridcc=sc.nextLine();
                sc.nextLine();
                //check with embedded sql
                System.out.println("Enter your password");
                String pwdc=sc.nextLine();
            } else if (op==5){   // customer signup
                System.out.println("Enter your user id");
                String usrid_csu=sc.nextLine();
                System.out.println("Enter your password");
                String pwd_csu=sc.nextLine();    // check if they exist , then ask for another , else add it to the table
            } else if (op==6){ // check trending categories

            } else if (op==7){ // check trending products

            }
        } catch (Exception e){
            System.out.println(e);
        }



    }
}
