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

public class CheckController implements Initializable {

    @FXML
    private TableView<checkModel> checkTable;
    @FXML
    private TableColumn<checkModel,String> idv;
    @FXML
    private TableColumn<checkModel,String> pidv;
    @FXML
    private TableColumn<checkModel,String> uidv;
    @FXML
    private TableColumn<checkModel,String> approvalv;

    @FXML
    private Button approve, remove;

    ObservableList<checkModel> itemList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources){

        dbc db=new dbc();
        try {
            Connection conn = db.getConnection();
            ResultSet rs=conn.createStatement().executeQuery("SELECT * FROM `delivery`");
            while(rs.next()){
                itemList.add(new checkModel(rs.getString("delivery_id"),rs.getString("product_id"),
                        rs.getString("user_id"),rs.getString("admin_approval")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        idv.setCellValueFactory(new PropertyValueFactory<>("id"));
        pidv.setCellValueFactory(new PropertyValueFactory<>("product_id"));
        uidv.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        approvalv.setCellValueFactory(new PropertyValueFactory<>("approval"));

        checkTable.setItems(itemList);
    }

    public void approve(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        checkModel selection = checkTable.getSelectionModel().getSelectedItem();
        String check_id=selection.getId();
        dbc query = new dbc();
        query.approve(check_id);

        //refresh
        Parent check = FXMLLoader.load(getClass().getResource("check.fxml"));
        Scene checkScene = new Scene(check);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Orders");
        window.setScene(checkScene);
        window.show();

        infoBox("Order has been approved!", null, "Approved");
    }

    public void remove(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        checkModel selection = checkTable.getSelectionModel().getSelectedItem();
        String check_id=selection.getId();
        dbc query = new dbc();
        query.removeItem(check_id);

        //refresh
        Parent check = FXMLLoader.load(getClass().getResource("check.fxml"));
        Scene checkScene = new Scene(check);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Orders");
        window.setScene(checkScene);
        window.show();

        infoBox("Order has been declined!", null, "Declined");
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
        Parent shop = FXMLLoader.load(getClass().getResource("shopAdmin.fxml"));
        Scene shopScene = new Scene(shop);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Mega Shop");
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
