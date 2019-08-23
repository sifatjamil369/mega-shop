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
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.LoginController.infoBox;

public class ShopController implements Initializable {

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
    private TextField searchField;
    @FXML
    private ChoiceBox priceSearch, categorySearch;
    @FXML
    private Button viewCart, addToCart, logout, searchbtn, backbtn;

    ObservableList<shopModel> productList = FXCollections.observableArrayList();
    ObservableList<String> categoryList = FXCollections.observableArrayList("Category", "Mobile", "PC", "Food", "Dress", "Music", "Books");
    ObservableList<String> priceList = FXCollections.observableArrayList("Price range", "0~1000", "1000~3000", "3000~10000", "10000~500000");

    @Override
    public void initialize(URL location, ResourceBundle resources){

        priceSearch.setValue("Price range");
        priceSearch.setItems(priceList);
        categorySearch.setValue("Category");
        categorySearch.setItems(categoryList);

        dbc db=new dbc();
        try {
            Connection conn = db.getConnection();
            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM `product`");
            while(rs.next()){
                productList.add(new shopModel(rs.getString("product_id"),rs.getString("product_name"),
                        rs.getString("brand"),rs.getString("category"),
                        rs.getString("price")));
            }
        } catch (SQLException | ClassNotFoundException e) {
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

        infoBox("Product added to cart!", null, "Success");
    }

    public void viewCart(ActionEvent event) throws IOException {
        Parent shop = FXMLLoader.load(getClass().getResource("cart.fxml"));
        Scene shopScene = new Scene(shop);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Mega Shop");
        window.setScene(shopScene);
        window.show();
    }

    public void logout(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene loginScene = new Scene(login);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Login !");
        window.setScene(loginScene);
        window.show();
    }

    public  void  search(){
        Window owner = searchbtn.getScene().getWindow();
        String searchName="", searchCategory="", searchPrice="", query="x";

        if ( searchField.getText().isEmpty() && priceSearch.getValue().equals("Price range") && categorySearch.getValue().equals("Category")){
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Form Error!",
                    "Please enter a valid search criteria");
        }else{
            if (!searchField.getText().isEmpty()) {
                searchName = "`product_name` LIKE '%"+searchField.getText()+"%'";
                if (!categorySearch.getValue().equals("Category")) searchName+=" AND ";
            }
            if (!categorySearch.getValue().equals("Category")) {
                searchCategory = "`category` = '" +categorySearch.getValue().toString()+"'";
                if (!priceSearch.getValue().equals("Price range")) searchCategory+=" AND ";
            }
            if (!priceSearch.getValue().equals("Price range")){
                String [] split = priceSearch.getValue().toString().split("~");
                searchPrice = "`price` >= '" +split[0]+"' " + "AND `price` <= '" +split[1]+"'";
            }
            query = "SELECT * FROM `product` WHERE "+ searchName + searchCategory + searchPrice ;
            //SELECT * FROM `product` WHERE `product_name` = 'Mouse' and `category` = 'PC' and `price` > '0' and `price` < '10000'
        }
        if (!query.equals("x")){

            shopTable.getItems().clear();

            System.out.println(query);
            dbc db=new dbc();
            try {
                Connection conn = db.getConnection();
                ResultSet rs=conn.createStatement().executeQuery(query);
                while(rs.next()){
                    productList.add(new shopModel(rs.getString("product_id"),rs.getString("product_name"),
                            rs.getString("brand"),rs.getString("category"),
                            rs.getString("price")));
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            idv.setCellValueFactory(new PropertyValueFactory<>("id"));
            namev.setCellValueFactory(new PropertyValueFactory<>("name"));
            brandv.setCellValueFactory(new PropertyValueFactory<>("brand"));
            categoryv.setCellValueFactory(new PropertyValueFactory<>("category"));
            pricev.setCellValueFactory(new PropertyValueFactory<>("price"));

            shopTable.setItems(productList);
        }
    }

    public void back(ActionEvent event) throws IOException {
        Parent shop = FXMLLoader.load(getClass().getResource("shop.fxml"));
        Scene shopScene = new Scene(shop);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Mega Shop");
        window.setScene(shopScene);
        window.show();
    }


    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

}
