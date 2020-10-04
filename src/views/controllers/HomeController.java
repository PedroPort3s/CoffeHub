package views.controllers;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnVendas;

    @FXML
    private Button btnProdutos;

    @FXML
    private Button btnFuncionarios;

    @FXML
    private Button btnFornecedores;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnCompras;

    @FXML
    private Button btnSair;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlMenus;

    @FXML
    private Pane pnlOverview;

    @FXML
    private VBox pnItems;

    @FXML
    void btnClientes_Action(ActionEvent event) {

    }

    @FXML
    void btnCompras_Action(ActionEvent event) {

    }

    @FXML
    void btnFornecedores_Action(ActionEvent event) {

    }

    @FXML
    void btnFuncionarios_Action(ActionEvent event) {

    }

    @FXML
    void btnProdutos_Action(ActionEvent event) {
		try {
    	Stage primaryStage = new Stage();
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/Produto/PesquisaProduto.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Prudutos");
		primaryStage.show();
	} 
    catch(Exception e) {
		Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
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

    @FXML
    void btnVendas_Action(ActionEvent event) {

    }

    @FXML
    void handleClicks(ActionEvent event) {

    }

}
