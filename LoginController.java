package sample;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController {

    @FXML
    private TextField emaili;

    @FXML
    private PasswordField passi;

    @FXML
    private Button loginbtn, registerbtn;

    public void register(ActionEvent event) throws IOException {
        Parent registration = FXMLLoader.load(getClass().getResource("register.fxml"));
        Scene register = new Scene(registration);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setTitle("User registration");
        window.setScene(register);
        window.show();
    }

    public void login(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

        Window owner = loginbtn.getScene().getWindow();

        System.out.println(emaili.getText());
        System.out.println(passi.getText());

        if (emaili.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your email id");
            return;
        }
        if (passi.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter a password");
            return;
        }

        String emailId = emaili.getText();
        String password = passi.getText();

        dbc db = new dbc();
        boolean flag = db.validate(emailId, password);

        if (!flag) {
            infoBox("Please enter correct Email and Password", null, "Failed");
        } else {

            ResultSet rs = db.findId(emailId);
            System.out.println(rs);
            session.id = 3;
            session.name= "Sifat Jamil";
            while (rs.next()){
                session.id = rs.getInt(1);
            }
            System.out.println(session.id +"  "+session.name);

            if (session.id == 1) {
                Parent shop = FXMLLoader.load(getClass().getResource("shopAdmin.fxml"));
                Scene shopScene = new Scene(shop);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("Mega Shop");
                window.setScene(shopScene);
                window.show();
                infoBox("Welcome admin!", null, "Nice");
            } else {
                Parent shop = FXMLLoader.load(getClass().getResource("shop.fxml"));
                Scene shopScene = new Scene(shop);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setTitle("Mega Shop");
                window.setScene(shopScene);
                window.show();
                infoBox("Login Successful!", null, "Nice");
            }
        }
    }



    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
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