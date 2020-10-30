package views.controllers;

import java.net.URL;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import control.compra_venda.ControlCompra;
import control.compra_venda.ControlCompraItens;
import control.produto.ControlProduto;
import dao.FornecedorDAO;
import entitys.Compra;
import entitys.Compra_Item;
import entitys.Fornecedor;
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
import views.controllers.fornecedor.PesquisaFornecedorGeralController;

public class CadCompraController implements Initializable {

	private static Stage CadCompra;

	private static Compra compraPrivate;
	
	public static Compra compraCarregada;
	
	public static Produto ProdutoEstatico = new Produto();

	public static Funcionario FuncionarioEstatico = new Funcionario();

	public static Fornecedor FornecedorEstatico = new Fornecedor();

	@FXML
	private JFXTextField txtStatusCompra;

	@FXML
	private JFXTextField txtCodCompra;

	@FXML
	private JFXTextField txtDataCompra;

	@FXML
	private JFXListView<Produto> lvProdutos;

	@FXML
	private JFXTextField txtProduto;

	@FXML
	private JFXButton btnAddProduto;

	@FXML
	private JFXButton btnBuscarProduto;

	@FXML
	private JFXButton btnLimparProduto;

	@FXML
	private JFXTextField txtCodProduto;

	@FXML
	private JFXButton btnBuscarFornecedor;

	@FXML
	private JFXTextField txtFornecedor;

	@FXML
	private JFXButton btnLimparFornecedor;

	@FXML
	private JFXTextField txtCodFornecedor;

	@FXML
	private JFXButton btnVoltar;

	@FXML
	private JFXButton btnLimpar;

	@FXML
	private JFXButton btnFinalizar;
	
    @FXML
    private JFXButton btnEditar;

    @FXML
    private JFXButton btnAlterarQtd;

	@FXML
	private Label lblTotalCompra;

	public Stage getCadCompra() {
		if (CadCompra == null) {
			try {
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/views/compraVenda/CadCompra.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				CadCompra = primaryStage;
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
				alert.showAndWait();
			}
		}
		return CadCompra;
	}

	@FXML
	void btnAddProduto_Action(ActionEvent event) throws Exception {
		try {
			if (!txtCodProduto.getText().equals("")) {
				Produto produtoCarregado = new ControlProduto().Carregar(Integer.parseInt(txtCodProduto.getText()));

				if (produtoCarregado != null) {
					Compra_Item item = new Compra_Item();
					item.setProduto(produtoCarregado);

					item.setQtd_item(1);
					item.setValor_unitario(produtoCarregado.getValor_un());

					if (compraPrivate == null || compraPrivate.getCod() == 0) {
						Date date = new Date();
						compraPrivate = new Compra();
						compraPrivate.setData_origem(date);

						Fornecedor fornecedorCompra = new FornecedorDAO().buscarId(Integer.parseInt(txtCodFornecedor.getText()));						
						compraPrivate.setFornecedor(fornecedorCompra);

						Funcionario funcionario = new Funcionario();
						funcionario.setCod(1);
						/* funcionario.setData_contratacao(date); */
						funcionario.setDocumento("123456789101");
						funcionario.setEmail("a@a.com");
						funcionario.setEndereco("FUNC");
						funcionario.setNome("Guina");
						funcionario.setTelefone("44444444444");
						compraPrivate.setFuncionario(funcionario);

						compraPrivate.setStatus("A");
						
						compraPrivate.setCod(new ControlCompra().Inserir(compraPrivate));

						RecarregarCompra(compraPrivate);
					}
					else {
						RecarregarCompra(compraPrivate);
					}

					if (new ControlCompraItens().AdicionarItem(compraPrivate, item) != 1) 
						throw new Exception("Não foi possivel inserir o item na compra");
					else {
						txtCodProduto.setText("");
						txtProduto.setText("");
						RecarregarCompra(compraPrivate);
					}					
				}
			}
		}
		catch (Exception e) {			
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

	@FXML
	void btnBuscarFornecedor_Action(ActionEvent event) {
		CadCompra.close();
		CadCompra = null;
		new PesquisaFornecedorGeralController().getPesquisaFornecedorGeral().show();
	}

	@FXML
	void btnBuscarProduto_Action(ActionEvent event) {
		CadCompra.close();
		CadCompra = null;
		PesquisaProdutoGeralController.compraVenda = "COMPRA";
		new PesquisaProdutoGeralController().getPesquisaProdutoGeral().show();
	}
	
	void Finalizar() {
		try 
		{
	    	Alert ConfirmaRemover = new Alert(AlertType.CONFIRMATION);
	    	
	    	ConfirmaRemover.setTitle("Finalizar");
	    	ConfirmaRemover.setHeaderText("Deseja realmente finalizar esta venda?");  	
	    	
	    	Optional<ButtonType> result = ConfirmaRemover.showAndWait();
	    	 if (result.isPresent() && result.get() == ButtonType.OK) {
			if (txtCodCompra.getText().equals("") == false) {
				Compra compraFinalizar = new ControlCompra().Carregar(Integer.parseInt(txtCodCompra.getText()));
				if (compraFinalizar != null) {
					if(new ControlCompra().Finalizar(compraFinalizar) == 1) {
						 	Limpar();
							Alert alert = new Alert(AlertType.INFORMATION);

							alert.setTitle("Atenção");
							alert.setHeaderText("Compra Finalizada com sucesso");

							alert.showAndWait();
					}
					else {
						throw new Exception("Não foi possivel finalizar a compra");
					}
				}
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
	void btnFinalizar_Action(ActionEvent event) {
		try 
		{
			this.Finalizar();
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
	void btnLimparFornecedor_Action(ActionEvent event) {
		txtCodFornecedor.setText("");
		txtFornecedor.setText("");
		FornecedorEstatico = null;
	}

	@FXML
	void btnLimparProduto_Action(ActionEvent event) {
		txtCodProduto.setText("");
		txtProduto.setText("");
		ProdutoEstatico = null;
	}

	void Limpar() {
		txtCodProduto.setText("");
		txtProduto.setText("");
		ProdutoEstatico = null;

		txtCodCompra.setText("");

		txtCodFornecedor.setText("");
		txtFornecedor.setText("");
		FornecedorEstatico = null;

		txtDataCompra.setText("");

		lblTotalCompra.setVisible(false);
		lblTotalCompra.setText("");
		
		btnEditar.setVisible(false);
		btnAlterarQtd.setVisible(false);
		
		compraCarregada = null;
		
		lvProdutos.getItems().clear();
		
		btnAddProduto.setVisible(true);
		btnBuscarFornecedor.setVisible(true);
		btnLimparFornecedor.setVisible(true);
		btnBuscarProduto.setVisible(true);
		btnLimparProduto.setVisible(true);
	}

	@FXML
	void btnLimpar_Action(ActionEvent event) {
		Limpar();
	}

	@FXML
	void btnVoltar_Action(ActionEvent event) {
		Limpar();
		CadCompra.close();
		CadCompra = null;
		compraPrivate = null;
		FornecedorEstatico = null;
		new PesquisaCompraController().getPesquisaCompra().show();
	}


    @FXML
    void btnEditar_Action(ActionEvent event) {
    	try 
    	{
    		if(compraCarregada != null) {
    			EditarCompra(compraCarregada);
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
    
    void EditarCompra(Compra compra) {
    	try
    	{
			if(new ControlCompra().Editar(compra) == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);

				alert.setTitle("Atenção");
				alert.setHeaderText("Compra editada com sucesso");

				alert.showAndWait();
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

    void RemoverItem() {
    	try 
    	{
			
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
    void btnAlterarQtd_Action(ActionEvent event) {
    	try 
    	{
			
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
	void lvProdutos_MouseClicked(MouseEvent event) {
		try 
		{
	    	Alert ConfirmaRemover = new Alert(AlertType.CONFIRMATION);
	    	
	    	ConfirmaRemover.setTitle("Remover Item");
	    	ConfirmaRemover.setHeaderText("Deseja realmente remover o item selecionado?");  	
	    	
	    	Optional<ButtonType> result = ConfirmaRemover.showAndWait();
	    	 if (result.isPresent() && result.get() == ButtonType.OK) {
	    		 Compra compra = new ControlCompra().Carregar(Integer.parseInt(txtCodCompra.getText()));
	    		 
	    		 List<Compra_Item> itens = compra.getItens();	    		 
	    		 Compra_Item ci = itens.get(lvProdutos.getSelectionModel().getSelectedIndex());
	    		 
	    		 if (compra != null && ci != null) {
					if(new ControlCompraItens().RemoverItem(compra, ci) == 1) {
						this.RecarregarCompra(compra);
					}
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
	void txtFornecedor_MouseClicked(MouseEvent event) {

	}

	void CarregarProduto(Produto produto) throws Exception {
		try {
			if (produto != null) {
				txtCodProduto.setText(produto.getCod() + "");
				txtProduto.setText(produto.getDescricao());
			} else {
				throw new Exception("Não foi possivel carregar o produto selecionado");
			}
		}

		catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	void CarregarFornecedor(Fornecedor fornecedor) throws Exception{
		try
		{
			if(fornecedor != null) {
				txtCodFornecedor.setText(fornecedor.getCod() + "");
				txtFornecedor.setText(fornecedor.getNome());
			}
			else {
				throw new Exception("Não foi possivel carregar o fornecedor selecionado");
			}
		}

		catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}
	
	void CarregarCompra(Compra compra) throws Exception{
		try
		{
			if(compra != null) {
				
				compraPrivate = compra;
				
				txtCodCompra.setText(compra.getCod() + "");
				txtStatusCompra.setText(compra.getStatus());
				txtDataCompra.setText(compra.getData_origem() + "");
				
				txtCodFornecedor.setText(compra.getFornecedor().getCod() + "");
				txtFornecedor.setText(compra.getFornecedor().getNome());
				
				lblTotalCompra.setText("Total: R$ 0.00");				

				// Lista de produto para o list view
				lvProdutos.getItems().clear();
				if(compra.getItens() != null && compra.getItens().size() > 0)
				{
					compra.getItens().forEach(p -> lvProdutos.getItems().add(p.getProduto()));
					lblTotalCompra.setText("Total: R$" + compra.TotalCompra());
				}
				if(compra.getStatus().equals("F")) {
					btnAddProduto.setVisible(false);
					btnBuscarFornecedor.setVisible(false);
					btnLimparFornecedor.setVisible(false);
					btnBuscarProduto.setVisible(false);
					btnLimparProduto.setVisible(false);
					btnEditar.setVisible(false);
					btnAlterarQtd.setVisible(false);
				}
				else {
				btnEditar.setVisible(true);
				btnAlterarQtd.setVisible(true);
				}
			}
			else {
				throw new Exception("Não foi possivel carregar a compra selecionada");
			}
		}

		catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}
	
	void RecarregarCompra(Compra compra) {
		try {
			if (compra.getCod() > 0) {
				compra = new ControlCompra().Carregar(compra.getCod());
				
				if (compra == null) {
					throw new Exception("Erro ao recarregar compra");
				}
				
				compraPrivate = compra;
				
				txtCodCompra.setText(compra.getCod() + "");
				txtDataCompra.setText(compra.getData_origem() + "");				
				
				txtCodFornecedor.setText(compra.getFornecedor().getCod() + "");
				txtFornecedor.setText(compra.getFornecedor().getNome());
				
				txtStatusCompra.setText(compra.getStatus());
				lblTotalCompra.setVisible(true);
				lblTotalCompra.setText("Total: R$ 0.00");
				

				// Lista de produto para o list view
				lvProdutos.getItems().clear();
				if(compra.getItens() != null && compra.getItens().size() > 0)
				{
					compra.getItens().forEach(p -> lvProdutos.getItems().add(p.getProduto()));
					lblTotalCompra.setText("Total: R$" + compra.TotalCompra());
				}
				
				if(compra.getStatus().equals("F")) {
					btnAddProduto.setVisible(false);
					btnBuscarFornecedor.setVisible(false);
					btnLimparFornecedor.setVisible(false);
					btnBuscarProduto.setVisible(false);
					btnLimparProduto.setVisible(false);
					btnEditar.setVisible(false);
					btnAlterarQtd.setVisible(false);
				}
				
				else {
				btnEditar.setVisible(true);
				btnAlterarQtd.setVisible(true);
				}
				
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		try {
			if (compraPrivate != null) {
				RecarregarCompra(compraPrivate);
			}
			
			if (ProdutoEstatico != null && ProdutoEstatico.getCod() > 0) {
				this.CarregarProduto(ProdutoEstatico);
			}
			if(FornecedorEstatico != null) {
				this.CarregarFornecedor(FornecedorEstatico);
			}
			if(compraCarregada != null) {
				this.CarregarCompra(compraCarregada);
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

}
