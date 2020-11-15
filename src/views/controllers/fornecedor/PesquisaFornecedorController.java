package views.controllers.fornecedor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import dao.FornecedorDAO;
import entitys.Fornecedor;
import entitys.Funcionario;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import views.controllers.HomeController;
import utils.Formatacao;
import utils.GenericTableButton;

public class PesquisaFornecedorController {

	private static Stage pesquisaFornecedor;
	
	private FornecedorDAO dao = new FornecedorDAO();
	
	
	//TABELA
	@FXML
	private TableView<Fornecedor> tableView;
	
	@FXML
	private TableColumn<Fornecedor, Integer> cCod;
	
    @FXML
    private TableColumn<Fornecedor, String> cNome;

    @FXML
    private TableColumn<Fornecedor, String> cEndereco;

    @FXML
    private TableColumn<Fornecedor, String> cEmail;

    @FXML
    private TableColumn<Fornecedor, String> cFone;

    @FXML
    private TableColumn<Fornecedor, String> cDoc;

    @FXML
    private TableColumn<Fornecedor, String> cDataContratacao; 
    
    @FXML
    private TableColumn<Fornecedor, Fornecedor> cEditar;

    @FXML
    private TableColumn<Fornecedor, Fornecedor> cExcluir; 
//	-----------------------------------------------------------	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton btnPesquisar;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXButton btnCadFornecedor;

    @FXML
    private JFXTextField txtNomePesquisa;

    @FXML
    private JFXTextField txtCodPesquisa;
    
	// ÍCONES SVG (EDITAR e EXCLUIR)
	public static final String PEN_SOLID = "M290.74 93.24l128.02 128.02-277.99 277.99-114.14 12.6C11.35 513.54-1.56 500.62.14 485.34l12.7-114.22 277.9-277.88zm207.2-19.06l-60.11-60.11c-18.75-18.75-49.16-18.75-67.91 0l-56.55 56.55 128.02 128.02 56.55-56.55c18.75-18.76 18.75-49.16 0-67.91z";
	public static final String TRASH_SOLID = "M432 32H312l-9.4-18.7A24 24 0 0 0 281.1 0H166.8a23.72 23.72 0 0 0-21.4 13.3L136 32H16A16 16 0 0 0 0 48v32a16 16 0 0 0 16 16h416a16 16 0 0 0 16-16V48a16 16 0 0 0-16-16zM53.2 467a48 48 0 0 0 47.9 45h245.8a48 48 0 0 0 47.9-45L416 128H32z";  
    
	@FXML
	void btnVoltar_Action(ActionEvent event) {
		try {
	    	fechar();
			new HomeController().getHome().show();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}
	
    public Stage getPesquisaFornecedor() {
		if (pesquisaFornecedor == null) {
			try {
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/views/fornecedor/PesquisaFornecedor.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				pesquisaFornecedor = primaryStage;
			} catch (NullPointerException e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
				alert.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pesquisaFornecedor;
	}
    
    private void fechar() {
    	pesquisaFornecedor.close();
    	pesquisaFornecedor = null;
    }

    @FXML
    void btnCadFornecedor_Action(ActionEvent event) {
    	fechar();
		new CadFornecedorController().getCadFornecedor().show();
    }

    @FXML
    void btnPesquisar_Action(ActionEvent event) {
    	listarFornecedores();
    }


    private void listarFornecedores() {
    	ObservableList<Fornecedor> listForn;
    	
		try {
			listForn = FXCollections.observableArrayList(dao.listar());
			
			cCod.setCellValueFactory(new PropertyValueFactory<Fornecedor, Integer>("cod"));
			cCod.setMaxWidth(35);
			cCod.setMinWidth(35);
			cDoc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Fornecedor, String>, ObservableValue<String>>() {
	            @Override
	            public ObservableValue<String> call(CellDataFeatures<Fornecedor, String> param) {
	                return new ReadOnlyStringWrapper(Formatacao.formatarDocumento(param.getValue().getDocumento()));
	            }
	        });
			cFone.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Fornecedor, String>, ObservableValue<String>>() {
	            @Override
	            public ObservableValue<String> call(CellDataFeatures<Fornecedor, String> param) {
	                return new ReadOnlyStringWrapper(Formatacao.formatarTelefone(param.getValue().getTelefone()));
	            }
	        });
			cNome.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("nome"));
			cEndereco.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("endereco"));
			cEmail.setCellValueFactory(new PropertyValueFactory<Fornecedor, String>("email"));
			cDataContratacao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Fornecedor, String>, ObservableValue<String>>() {
	            @Override
	            public ObservableValue<String> call(CellDataFeatures<Fornecedor, String> param) {
	                return new ReadOnlyStringWrapper(param.getValue().getData_contratoString());
	            }
	        });
			
			tableView.setItems(listForn);
			
			GenericTableButton.initButtons(cEditar, 15, PEN_SOLID, "svg-gray",
					(Fornecedor fornecedor, ActionEvent event) -> {

						try {
							if (fornecedor != null) {
								CadFornecedorController.fornecedorStatic = fornecedor;
								fechar();
								new CadFornecedorController().getCadFornecedor().show();
							}
						} catch (Exception e) {
							e.printStackTrace();
							Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
							alert.showAndWait();
						}
					});

			GenericTableButton.initButtons(cExcluir, 15, TRASH_SOLID, "svg-red",
					(Fornecedor fornecedor, ActionEvent event) -> {
						try {
							Alert alert = new Alert(AlertType.CONFIRMATION);

							alert.setTitle("Excluir Funcionario");
							alert.setHeaderText(
									" Caso o fornecedor seja excluído seus dados serão perdidos permanentemente! \n Se deseja editar o fornecedor vá para guia de edição.");
							Optional<ButtonType> result = alert.showAndWait();
							if (result.isPresent() && result.get() == ButtonType.OK) {
								if (fornecedor != null) {

									dao.deletar(fornecedor.getCod());

									ObservableList<Fornecedor> listFornUp;
									listFornUp = FXCollections.observableArrayList(dao.listar());
									tableView.setItems(listFornUp);
									fornecedor = null;
								}
							}
						} catch (Exception e) {
							Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
							alert.setHeaderText("Atenção");
							alert.showAndWait();
						}

					});
			
			if (listForn != null) {
				if (txtCodPesquisa != null && conferirNumero(txtCodPesquisa.getText(), "Insira um número")) {
					List<Fornecedor> listaCod = new ArrayList<Fornecedor>();
					for (Fornecedor forn : listForn) {
						if ((forn.getCod() == Integer.parseInt(txtCodPesquisa.getText()))) {
							listaCod.add(forn);
							tableView.setItems(listForn);
						}
					}
					listForn = FXCollections.observableArrayList(listaCod);
				}
				if (txtNomePesquisa != null) {
					List<Fornecedor> listaNome = new ArrayList<Fornecedor>();
					for (Fornecedor forn : listForn) {
						if (forn.getNome().matches(".*" + txtNomePesquisa.getText() + ".*")) {
							listaNome.add(forn);
						}
					}
					listForn = FXCollections.observableArrayList(listaNome);
				}
				tableView.setItems(listForn);
			}
			
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}
    
    private Boolean conferirNumero(String texto, String msg) {
    	try {
    		if(!texto.equals("")) {
    			Integer.parseInt(texto);
    		} else if(texto.equals("")) {
    			return false;
    		}
		} catch (Exception e) {
			throw new NumberFormatException(msg);
		}
    	return true;
    }
    
    @FXML
    void initialize() {
    	assert txtCodPesquisa != null : "fx:id=\"txtCodPesquisa\" was not injected: check your FXML file 'PesquisaFornecedor.fxml'.";
        assert txtNomePesquisa != null : "fx:id=\"txtNomePesquisa\" was not injected: check your FXML file 'PesquisaFornecedor.fxml'.";
        assert btnPesquisar != null : "fx:id=\"btnPesquisar\" was not injected: check your FXML file 'PesquisaFornecedor.fxml'.";
        assert btnCadFornecedor != null : "fx:id=\"btnCadFornecedor\" was not injected: check your FXML file 'PesquisaFornecedor.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'PesquisaFornecedor.fxml'.";

        listarFornecedores();
    }
}
