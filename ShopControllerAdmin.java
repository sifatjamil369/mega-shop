package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShopControllerAdmin implements Initializable {

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
    private Button addProd, removeProd, edit, logout, order;

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

    public void addProd(ActionEvent event) throws IOException {
        Parent add = FXMLLoader.load(getClass().getResource("product.fxml"));
        Scene addScene = new Scene(add);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Add a new product");
        window.setScene(addScene);
        window.show();
    }

    public void edit(ActionEvent event) throws IOException {
        shopModel selection = shopTable.getSelectionModel().getSelectedItem();
        EditController.id=selection.getId();
        EditController.name = selection.getName();
        EditController.brand = selection.getBrand();
        EditController.category = selection.getCategory();
        EditController.price = selection.getPrice();

        Parent edit = FXMLLoader.load(getClass().getResource("edit.fxml"));
        Scene editScene = new Scene(edit);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Edit product");
        window.setScene(editScene);
        window.show();
    }

    public void removeProd(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        shopModel selection = shopTable.getSelectionModel().getSelectedItem();
        String prod_id=selection.getId();
        dbc query = new dbc();
        query.removeProd(prod_id);

        Parent refresh = FXMLLoader.load(getClass().getResource("shopAdmin.fxml"));
        Scene refreshScene = new Scene(refresh);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Mega Shop");
        window.setScene(refreshScene);
        window.show();

        infoBox("Product removed!", null, "Removed");

    }

    public void order(ActionEvent event) throws IOException {
        Parent check = FXMLLoader.load(getClass().getResource("check.fxml"));
        Scene checkScene = new Scene(check);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Orders");
        window.setScene(checkScene);
        window.show();
    }

    public void logout(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene loginScene = new Scene(login);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Mega Shop");
        window.setScene(loginScene);
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
