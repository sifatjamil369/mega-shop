package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author sifat
 */
public class EditController {

    @FXML
    private TextField namei, brandi, pricei;
    @FXML
    private Button addbtn, cancelbtn;
    @FXML
    private ChoiceBox categoryBox;

    ObservableList<String> categoryList = FXCollections.observableArrayList("Select a category", "Mobile", "PC", "Food", "Dress", "Music", "Books");
    public static String id, name, brand, category, price;


    public void add(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        Window owner = addbtn.getScene().getWindow();

        //Validation not done yet
        System.out.println(namei.getText());
        System.out.println(brandi.getText());
        System.out.println(categoryBox.getValue());
        System.out.println(pricei.getText());

        if (namei.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter product name");
            return;
        }
        if (brandi.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter brand name");
            return;
        }
        if (categoryBox.getValue().equals("Select a category")) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a category");
            return;
        }

        if (pricei.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter price");
            return;
        }


        String name = namei.getText();
        String brand = brandi.getText();
        String category = categoryBox.getValue().toString();
        String price = pricei.getText();

        dbc query = new dbc();
        query.editProduct(id, name, brand, category, price);

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Successful!",
                "Edit was successful");

        cancel(event);
    }

    public void cancel(ActionEvent event) throws IOException {
        Parent shop = FXMLLoader.load(getClass().getResource("shopAdmin.fxml"));
        Scene shopScene = new Scene(shop);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Mega Shop");
        window.setScene(shopScene);
        window.show();
    }

    public void initialize(){
        namei.setText(name);
        brandi.setText(brand);
        pricei.setText(price);
        categoryBox.setValue(category);
        categoryBox.setItems(categoryList);
    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
