package sample;

import com.mysql.cj.protocol.Resultset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CartController implements Initializable {

    @FXML
    private TableView<cartModel> cartTable;
    @FXML
    private TableColumn<cartModel,String> idv;
    @FXML
    private TableColumn<cartModel,String> pidv;
    @FXML
    private TableColumn<cartModel,String> namev;
    @FXML
    private TableColumn<cartModel,String> brandv;
    @FXML
    private TableColumn<cartModel,String> categoryv;
    @FXML
    private TableColumn<cartModel,String> pricev;
    @FXML
    private Button checkout, remove;

    ObservableList<cartModel> productList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        dbc db=new dbc();
        try {
            Connection conn = db.getConnection();
            System.out.println("id  "+session.id);
            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM `cart` WHERE `user_id` = "+session.id);
            while(rs.next()){
                productList.add(new cartModel(rs.getString("cart_id"),rs.getString("user_id"),
                        rs.getString("product_id"),rs.getString("name"),rs.getString("brand"),
                        rs.getString("category"), rs.getString("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        idv.setCellValueFactory(new PropertyValueFactory<>("id"));
        pidv.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        namev.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandv.setCellValueFactory(new PropertyValueFactory<>("brand"));
        categoryv.setCellValueFactory(new PropertyValueFactory<>("category"));
        pricev.setCellValueFactory(new PropertyValueFactory<>("price"));

        cartTable.setItems(productList);
    }

    public void checkout(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        cartModel selection = cartTable.getSelectionModel().getSelectedItem();
        String user_id=""+session.id;
        String product_id=selection.getId();
        String product_name=selection.getName();
        String cart_id=selection.getId();

        dbc query = new dbc();
        query.checkout(product_id, user_id);
        query.removeCart(cart_id);

        infoBox(product_name+" successfully checked out!", null, "Checkout");

    }
    public void remove(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        cartModel selection = cartTable.getSelectionModel().getSelectedItem();
        String cart_id=selection.getId();
        String product_name=selection.getName();
        dbc query = new dbc();
        query.removeCart(cart_id);

        //resfresh
        Parent refresh = FXMLLoader.load(getClass().getResource("cart.fxml"));
        Scene refreshScene = new Scene(refresh);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Cart");
        window.setScene(refreshScene);
        window.show();

        infoBox(product_name+" has been removed from cart!", null, "Removed");
    }

    public void logout(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene loginScene = new Scene(login);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Login");
        window.setScene(loginScene);
        window.show();
    }

    public void back(ActionEvent event) throws IOException {
        Parent shop = FXMLLoader.load(getClass().getResource("shop.fxml"));
        Scene shopScene = new Scene(shop);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Login");
        window.setScene(shopScene);
        window.show();
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

}
