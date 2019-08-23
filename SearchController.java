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

import static sample.LoginController.infoBox;

public class SearchController implements Initializable {

    @FXML
    private TableView<shopModel> shopTable;
    @FXML
    private TableColumn<shopModel,String> idv;
    @FXML
    private TableColumn<shopModel,String> namev;
    @FXML
    private TableColumn<shopModel,String> brandv;
    @FXML
    private TableColumn<shopModel,String> categoryv;
    @FXML
    private TableColumn<shopModel,String> pricev;
    @FXML
    private TextField searchfield;
    @FXML
    private Button viewCart, addToCart, logout, searchbtn;

    ObservableList<shopModel> productList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources){

        dbc db=new dbc();
        try {
            Connection conn = db.getConnection();
            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM `product`");
            while(rs.next()){
                productList.add(new shopModel(rs.getString("product_id"),rs.getString("product_name"),
                        rs.getString("brand"),rs.getString("category"),
                        rs.getString("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        idv.setCellValueFactory(new PropertyValueFactory<>("id"));
        namev.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandv.setCellValueFactory(new PropertyValueFactory<>("brand"));
        categoryv.setCellValueFactory(new PropertyValueFactory<>("category"));
        pricev.setCellValueFactory(new PropertyValueFactory<>("price"));

        shopTable.setItems(productList);
    }

    public void addToCart(ActionEvent event) throws SQLException, ClassNotFoundException {
        shopModel selection = shopTable.getSelectionModel().getSelectedItem();
        String user_id=""+session.id;
        String product_id=selection.getId();
        String name=selection.getName();
        String brand=selection.getBrand();
        String category=selection.getCategory();
        String price=selection.getPrice();
        dbc query = new dbc();
        query.addToCart(user_id,product_id,name,brand,category,price);
    }
    public void viewCart(ActionEvent event) throws IOException {
        Parent cart = FXMLLoader.load(getClass().getResource("cart.fxml"));
        Scene cartScene = new Scene(cart);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Your Cart");
        window.setScene(cartScene);
        window.show();
    }

    public void back(ActionEvent event) throws IOException {
        Parent shop = FXMLLoader.load(getClass().getResource("shop.fxml"));
        Scene shopScene = new Scene(shop);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Mega Shop");
        window.setScene(shopScene);
        window.show();
    }
}
