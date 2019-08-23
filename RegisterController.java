package sample;

import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author sifat
 */
public class RegisterController {
    
    @FXML
    private TextField namei, emaili;
    @FXML
    private PasswordField passi, confirmPassi;
    @FXML
    private Button registerbtn, backbtn;
    
    public void register(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        Window owner = registerbtn.getScene().getWindow();
        
        //Validation not done yet
        System.out.println(namei.getText());
        System.out.println(emaili.getText());
        System.out.println(passi.getText());
        System.out.println(confirmPassi.getText());


        if (namei.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }
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

        if (!passi.getText().equals(confirmPassi.getText())){
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Password mismatch");
            return;
        }
        
        String name = namei.getText();
        String email = emaili.getText();
        String pass = passi.getText();

        dbc query = new dbc();
        int x = query.insertRecord(name, email, pass);
        if (x==0) {
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
                    "Welcome " + namei.getText());

            //redirecting
            Parent login = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene loginScene = new Scene(login);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Login");
            window.setScene(loginScene);
            window.show();

        }else {
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Failed!",
                    "Account already exists!!! ");
        }

    }

    public void back(ActionEvent event) throws IOException {
        Parent login = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene loginScene = new Scene(login);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Login");
        window.setScene(loginScene);
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
    
}
