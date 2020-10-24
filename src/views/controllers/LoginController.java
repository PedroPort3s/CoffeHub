package views.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import control.acesso.ControlAcesso;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {

	private static Stage login;
	
    @FXML
    private JFXTextField txtUsuario;

    @FXML
    private JFXPasswordField txtSenha;
    
    @FXML
    private JFXButton btnSair;
    
    @FXML
    private JFXButton btnAcessar;
    
	public Stage getlogin() {
		if (login == null)
		{
			try {
		    	Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				scene.setFill(Color.TRANSPARENT);
				login = primaryStage;
			}
		  catch(Exception e)
		  	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  System.out.println("asdasdasd");
			  alert.showAndWait();	
		  	}
		}
		return login;
	}

    @FXML
    void btnAcessar_Action(ActionEvent event) throws Exception {
    	
		int retorno = 0 ;	
		int cod = 0;
		try 
		{	
			if (txtUsuario.getText().equals("")) 
			{				
				throw new Exception("Informe o código de acesso");
			}
			else
			{
				cod = Integer.parseInt(txtUsuario.getText());				
			}			
			retorno = new ControlAcesso().CarregarLogin(cod, txtSenha.getText());
			if (retorno == 1)
			{
				try 
				{
					login.hide();
					new HomeController().getHome().show();
				} 			  
		  catch(Exception e)
		  	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();	
		  	}
		}
	} 
		catch (Exception e) 
		{
    		Alert alert = new Alert(AlertType.WARNING);

            alert.setTitle("Atenção");
            alert.setHeaderText(e.getMessage());
            
            alert.showAndWait();
            
		}
	}
    


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
}
