package views.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import entitys.Cliente;
import entitys.Funcionario;
import entitys.Produto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CadVendaController implements Initializable{

	private static Stage CadVenda;
	
	public static Produto ProdutoEstatico = new Produto();
	
	public static Cliente ClienteEstatico = new Cliente();
	
	public static Funcionario FuncionarioEstatico = new Funcionario();
	
    @FXML
    private JFXButton btnGravar;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXButton btnLimpar;

    @FXML
    private JFXTextField txtCliente;

    @FXML
    private JFXTextField txtFuncionario;

    @FXML
    private JFXTextField txtDataOrigem;

    @FXML
    private JFXTextField txtDataConfirmacao;

    @FXML
    private JFXTextField txtCodVenda;

    @FXML
    private JFXListView<Produto> lvProdutos;

    @FXML
    private JFXTextField txtProduto;

    @FXML
    private Label lblValorTotal;

    @FXML
    private JFXButton btnAddProduto;

    @FXML
    private JFXButton btnFinalizar;
    
    @FXML
    private JFXButton btnBuscarProduto;

    @FXML
    private JFXButton btnLimparProduto;
    
    @FXML
    private JFXTextField txtCodProduto;
    
	public Stage getCadVenda() {
		if (CadVenda == null)
		{
			try {
		    	Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/compraVenda/CadVenda.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				CadVenda = primaryStage;
			}
		  catch(Exception e)
		  	{
			  Alert alert = new Alert(AlertType.ERROR,e.getMessage(),ButtonType.OK);
			  alert.showAndWait();	
		  	}
		}
		return CadVenda;
	}
	
	void LimparProduto() {
		txtCodProduto.setText("");
		txtProduto.setText("");
		ProdutoEstatico = null;
	}

    @FXML
    void btnAddProduto_Action(ActionEvent event) {
    	try 
    	{
			/* lvProdutos.getSelectionModel().select(ProdutoEstatico); */
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
    void btnFinalizar_Action(ActionEvent event) {

    }

    @FXML
    void btnGravar_Action(ActionEvent event) {

    }

    @FXML
    void btnLimpar_Action(ActionEvent event) {
    	LimparProduto();
    }

    @FXML
    void btnVoltar_Action(ActionEvent event) {
    	CadVenda.close();
    	CadVenda = null;
    	new PesquisaVendaController().getPesquisaVenda().show();
    }

    @FXML
    void lvProdutos_MouseClicked(MouseEvent event) {

    }

    @FXML
    void txtCliente_MouseClicked(MouseEvent event) {

    }

    @FXML
    void txtCodVenda_MouseClicked(MouseEvent event) {

    }

    @FXML
    void txtFuncionario_MouseClicked(MouseEvent event) {

    }
    
    
    @FXML
    void btnLimparProduto_Action(ActionEvent event) {
    	LimparProduto();
    }


    @FXML
    void btnBuscarProduto_Action(ActionEvent event) {
    	CadVenda.close();
    	CadVenda = null;
    	PesquisaProdutoGeralController.compraVenda = "VENDA";
    	new PesquisaProdutoGeralController().getPesquisaProdutoGeral().show();
    }
    
    void CarregarProduto(Produto produto) throws Exception{
    	try
    	{    	
    	if (produto != null)
    	{
			txtCodProduto.setText(produto.getCod() + "");
			txtProduto.setText(produto.getDescricao());
		}
    	else 
    	{
			throw new Exception("Não foi possivel carregar o produto selecionado");
		}
    }
    	
    	catch(Exception e)
    	{
    		Alert alert = new Alert(AlertType.WARNING);

            alert.setTitle("Atenção");
            alert.setHeaderText(e.getMessage());
            
            alert.showAndWait();
    	}
    }
    
    void CarregarCliente() {
    	
    }
    
    void CarregarFuncionario() {
    	
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		  try
		  { 
			  if(ProdutoEstatico != null && ProdutoEstatico.getCod() > 0) 
			  {
				  this.CarregarProduto(ProdutoEstatico);
			  }
			  
		  } 
		  catch(Exception e)
		  {
			  Alert alert = new Alert(AlertType.WARNING);
		  
			  alert.setTitle("Atenção"); alert.setHeaderText(e.getMessage());
			  
			  alert.showAndWait(); 
		   }
		 }
	}
