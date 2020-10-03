package views.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class LoginController implements Initializable {


    @FXML
    private JFXTextField txtUsuario;

    @FXML
    private JFXPasswordField txtSenha;

    @FXML
    private JFXButton btnSignIn;
    
    @FXML
    private JFXButton btnSair;

    @FXML
    void btnSair_Action(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	
    	alert.setTitle("Sair");
    	alert.setHeaderText("Deseja realmente sair?");  	
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	 if (result.isPresent() && result.get() == ButtonType.OK) {
    	     System.exit(0);
    	 }  
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void handleSignIn(ActionEvent actionEvent) {
	}

}
