
import java.sql.*;
import java.util.*;

public class EmbeddedSQL{

    public static void main(String[] args) {

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
            PreparedStatement st;
            st              = con.prepareStatement("SELECT P.p_name,P.p_cost,CP.cp_quantity,CP.cp_price FROM Product as P , Cart_Product_List as CP , Customer as C WHERE P.p_id=CP.cp_pid AND CP.cp_cart_id=C.cus_cart_id AND C.cus_id=?;");

            Scanner sc      = new Scanner(System.in);
            int query       = sc.nextInt();
            sc.nextLine();

            st.setInt(1, query);

            ResultSet rs    = st.executeQuery();

            while (rs.next()){
                System.out.println(String.format("Product name = %s , Product cost = %d , product quantity = %d , total price =%d ",
                        rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getInt(4)


                ));
                //System.out.println(String.format("Product name = %s , Product cost=%d , Cp quantity= "));
            }


//            while(rs.next()) {
//                System.out.println(rs.getString(1));
//                System.out.println(rs.getInt(2));
//                System.out.println(rs.getInt(3));
//                System.out.println(rs.getInt(4));
//            }
            st.close();
            con.close();

        }catch(Exception e){
            System.out.println("Encountered Error while preparing statement : "+e);
        }
    }
}