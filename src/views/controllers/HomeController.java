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
import javafx.stage.StageStyle;

public class HomeController {

	private static Stage Home;
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
    private Button btnCategorias;
    

	public Stage getHome() {
		if (Home == null)
		{
			try {
		    	Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/Home.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				Home = primaryStage;
			}
		  catch(Exception e)
		  	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();	
		  	}
		}
		return Home;
	}
    
    @FXML
    void btnClientes_Action(ActionEvent event) {
		try {
			Home.hide();
			new PesquisaClienteController().getPesquisaCliente().show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
    }

    @FXML
    void btnCompras_Action(ActionEvent event) {
    	try 
    	{
			Home.hide();
			new PesquisaCompraController().getPesquisaCompra().show();
		}
    	catch (Exception e) 
    	{
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}

    }

    @FXML
    void btnFornecedores_Action(ActionEvent event) {

    }

    @FXML
    void btnFuncionarios_Action(ActionEvent event) {

    }

    @FXML
    void btnProdutos_Action(ActionEvent event) {
		try
		{
	    	Home.hide();
			new PesquisaProdutoController().getPesquisaProduto().show();
		}
		catch(Exception e)
		{
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
		try
		{
	    	Home.hide();
			new PesquisaVendaController().getPesquisaVenda().show();
		}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			alert.showAndWait();	
    	}
    }

    @FXML
    void handleClicks(ActionEvent event) {

    }
    
    @FXML
    void btnCategorias_Action(ActionEvent event) {
    	try {
    	Home.hide();
    	new PesquisaCategoriaController().getPesquisaCategoria().show();
    	}
		catch(Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			alert.showAndWait();	
    	}
    }

}
