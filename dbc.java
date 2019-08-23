
package sample;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.protocol.Resultset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;

/**
 *
 * @author sifat
 */
public class dbc {
    private static final String register_query = "INSERT INTO `user` (`user_id`, `user_name`, `email`, `password`) VALUES (?,?,?,?)";
    private static final String login_query = "SELECT * FROM `user` WHERE email = ? and password = ?";
    private static final String addProd_query = "INSERT INTO `product` (`product_id`, `product_name`, `brand`, `category`, `price`) VALUES (?,?,?,?,?)";
    private static final String edit_query = "UPDATE `product` SET `product_name` = ? , `brand` = ? , `category` = ? , `price` = ? WHERE `product_id`= ?";
    private static final String addToCart_query = "INSERT INTO `cart` (`cart_id`, `user_id`, `product_id`, `name` , `brand` , `category`, `price`) VALUES (?,?,?,?,?,?,?)";
    private static final String checkout_query = "INSERT INTO `delivery` (`delivery_id`, `product_id`, `user_id`, `admin_approval`) VALUES (?,?,?,?)";
    private static final String removeCart_query = "DELETE FROM `cart` WHERE `cart_id` = ?";
    private static final String removeItem_query = "DELETE FROM `delivery` WHERE `delivery_id` = ?";
    private static final String removeProd_query = "DELETE FROM `product` WHERE `product_id` = ?";
    private static final String approval_query = "UPDATE `delivery` SET `admin_approval` = 'yes' WHERE `delivery_id`= ?";
    private static final String searchId_query = "SELECT `user_id` FROM `user` WHERE `email` = ?";
    private static final String validation_query = "SELECT COUNT(*) FROM `user` WHERE `email`=?";


    Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/megashopx?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
        return conn;
    }

    //Registration

    public int insertRecord(String name, String email, String pass) throws SQLException, ClassNotFoundException {
        try{
            PreparedStatement st = getConnection().prepareStatement(register_query);
            st.setString(1, null);
            st.setString(2, name);
            st.setString(3, email);
            st.setString(4, pass);
            System.out.println(st);
            st.executeUpdate();
            return 0;
        }catch (Exception e){
            return 1;
        }
    }

    //Login
    public boolean validate(String emailId, String password) throws SQLException, ClassNotFoundException {
        PreparedStatement st = getConnection().prepareStatement(login_query);
        st.setString(1, emailId);
        st.setString(2, password);

        System.out.println(st);

        ResultSet resultSet = st.executeQuery();
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    //finding id for an email
    public ResultSet findId(String email) throws SQLException, ClassNotFoundException {
        PreparedStatement st = getConnection().prepareStatement(searchId_query);
        st.setString(1, email);
        System.out.println(st);
        ResultSet rs = st.executeQuery();
        System.out.println(rs);
        return rs;
    }

    //addProduct
    public void addProduct(String name, String brand, String category, String price) throws SQLException, ClassNotFoundException {
        PreparedStatement st = getConnection().prepareStatement(addProd_query);
        st.setString(1, null);
        st.setString(2, name);
        st.setString(3, brand);
        st.setString(4, category);
        st.setString(5, price);

        System.out.println(st);
        st.executeUpdate();
    }

    //editProduct
    public void editProduct(String id, String name, String brand, String category, String price) throws SQLException, ClassNotFoundException {
        PreparedStatement st = getConnection().prepareStatement(edit_query);
        st.setString(1, name);
        st.setString(2, brand);
        st.setString(3, category);
        st.setString(4, price);
        st.setString(5, id);

        System.out.println(st);
        st.executeUpdate();
    }

    //addToCart
    public void addToCart(String user_id,String product_id,String name,String brand,String category,String price) throws SQLException, ClassNotFoundException {
        PreparedStatement st = getConnection().prepareStatement(addToCart_query);

        st.setString(1, null);
        st.setString(2, user_id);
        st.setString(3, product_id);
        st.setString(4, name);
        st.setString(5, brand);
        st.setString(6, category);
        st.setString(7, price);

        System.out.println(st);
        st.executeUpdate();
    }

    //send for admin approval
    public void checkout(String product_id, String user_id) throws SQLException, ClassNotFoundException {
        PreparedStatement st = getConnection().prepareStatement(checkout_query);

        st.setString(1, null);
        st.setString(2, product_id);
        st.setString(3, user_id);
        st.setString(4, "no");

        System.out.println(st);
        st.executeUpdate();
    }

    //removeItem from cart
    public void removeCart(String cart_id) throws SQLException, ClassNotFoundException {
        PreparedStatement st = getConnection().prepareStatement(removeCart_query);

        st.setString(1, cart_id);
        System.out.println(st);
        st.executeUpdate();
    }

    //removeItem from delivery
    public void removeItem(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement st = getConnection().prepareStatement(removeItem_query);

        st.setString(1, id);
        System.out.println(st);
        st.executeUpdate();
    }

    //remove product from shop
    public void removeProd(String id) throws SQLException, ClassNotFoundException {
            PreparedStatement st = getConnection().prepareStatement(removeProd_query);

            st.setString(1, id);
            System.out.println(st);
            st.executeUpdate();
        }


    //admin approval
    public void approve(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement st = getConnection().prepareStatement(approval_query);
        System.out.println(st);
        st.setString(1, id);
        System.out.println(st);
        st.executeUpdate();
    }
}