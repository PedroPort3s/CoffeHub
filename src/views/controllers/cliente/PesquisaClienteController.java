package views.controllers.cliente;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import dao.ClienteDAO;
import entitys.Cliente;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import utils.Formatacao;
import utils.GenericTableButton;
import views.controllers.HomeController;

public class PesquisaClienteController {

	private static Stage pesquisaCliente;

	private ClienteDAO dao = new ClienteDAO();

//	TABELA
	@FXML
	private TableView<Cliente> tableView;

	@FXML
	private TableColumn<Cliente, Integer> cCod;
	
	@FXML
	private TableColumn<Cliente, String> cDoc;

	@FXML
	private TableColumn<Cliente, String> cNome;

	@FXML
	private TableColumn<Cliente, String> cEndereco;

	@FXML
	private TableColumn<Cliente, String> cEmail;

	@FXML
	private TableColumn<Cliente, String> cFone;

	@FXML
	private TableColumn<Cliente, String> cDataNascimento;

	@FXML
	private TableColumn<Cliente, Cliente> cEditar;

	@FXML
	private TableColumn<Cliente, Cliente> cExcluir;

	// --------------------------------------

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXButton btnPesquisar;

	@FXML
	private JFXButton btnVoltar;

	@FXML
	private JFXButton btnCadCliente;

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

	public Stage getPesquisaCliente() {
		if (pesquisaCliente == null) {
			try {
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/views/cliente/PesquisaCliente.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				pesquisaCliente = primaryStage;
			} catch (NullPointerException e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
				alert.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pesquisaCliente;
	}

	private void fechar() {
		pesquisaCliente.close();
		pesquisaCliente = null;
	}

	@FXML
	void btnCadCliente_Action(ActionEvent event) {
		fechar();
		new CadClienteController().getCadCliente().show();
	}

	@FXML
	void btnPesquisar_Action(ActionEvent event) {
		listarClientes();
	}

	private void listarClientes() {
		ObservableList<Cliente> listCli;

		try {
			listCli = FXCollections.observableArrayList(dao.listar());
			
			cCod.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("cod"));
			cCod.setMaxWidth(35);
			cCod.setMinWidth(35);
			cDoc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
	            @Override
	            public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
	                return new ReadOnlyStringWrapper(Formatacao.formatarDocumento(param.getValue().getDocumento()));
	            }
	        });
			cFone.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
	            @Override
	            public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
	                return new ReadOnlyStringWrapper(Formatacao.formatarTelefone(param.getValue().getTelefone()));
	            }
	        });			
			cNome.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
			cEndereco.setCellValueFactory(new PropertyValueFactory<Cliente, String>("endereco"));
			cEmail.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
			cDataNascimento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
	            @Override
	            public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
	                return new ReadOnlyStringWrapper(param.getValue().getData_nascimentoString());
	            }
	        });	
			tableView.setItems(listCli);
			
			GenericTableButton.initButtons(cEditar, 15, PEN_SOLID, "svg-gray",
					(Cliente cliente, ActionEvent event) -> {

						try {
							if (cliente != null) {
								CadClienteController.clienteStatic = cliente;
								fechar();
								new CadClienteController().getCadCliente().show();
							}
						} catch (Exception e) {
							e.printStackTrace();
							Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
							alert.showAndWait();
						}
					});

			GenericTableButton.initButtons(cExcluir, 15, TRASH_SOLID, "svg-red",
					(Cliente cliente, ActionEvent event) -> {
						try {
							Alert alert = new Alert(AlertType.CONFIRMATION);

							alert.setTitle("Excluir Cliente");
							alert.setHeaderText(
									" Caso o cliente seja excluído seus dados serão perdidos permanentemente! \n Se deseja editar o cliente vá para guia de edição.");
							Optional<ButtonType> result = alert.showAndWait();
							if (result.isPresent() && result.get() == ButtonType.OK) {
								if (cliente != null) {

									dao.deletar(cliente.getCod());

									ObservableList<Cliente> listCliUp;
									listCliUp = FXCollections.observableArrayList(dao.listar());
									tableView.setItems(listCliUp);
									cliente = null;
								}
							}
						} catch (Exception e) {
							Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
							alert.setHeaderText("Atenção");
							alert.showAndWait();
						}

					});
			
			if (listCli != null) {
				if (txtCodPesquisa != null && conferirNumero(txtCodPesquisa.getText(), "Insira um número")) {
					List<Cliente> listaCod = new ArrayList<Cliente>();
					for (Cliente cl : listCli) {
						if ((cl.getCod() == Integer.parseInt(txtCodPesquisa.getText()))) {
							listaCod.add(cl);
							tableView.setItems(listCli);
						}
					}
					listCli = FXCollections.observableArrayList(listaCod);
				}
				if (txtNomePesquisa != null) {
					List<Cliente> listaNome = new ArrayList<Cliente>();
					for (Cliente cl : listCli) {
						if (cl.getNome().matches(".*" + txtNomePesquisa.getText() + ".*")) {
							listaNome.add(cl);							
						}
					}
					listCli = FXCollections.observableArrayList(listaNome);
				}
				tableView.setItems(listCli);
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

	private Boolean conferirNumero(String texto, String msg) {
		try {
			if (!texto.equals("")) {
				Integer.parseInt(texto);
			} else if (texto.equals("")) {
				return false;
			}
		} catch (Exception e) {
			throw new NumberFormatException(msg);
		}
		return true;
	}

	@FXML
	void initialize() {
		assert btnPesquisar != null : "fx:id=\"btnPesquisar\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
		assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
		assert btnCadCliente != null : "fx:id=\"btnCadCliente\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
		assert txtNomePesquisa != null : "fx:id=\"txtNomePesquisa\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
		assert txtCodPesquisa != null : "fx:id=\"txtCodPesquisa\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";

		listarClientes();
	}
}
