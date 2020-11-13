package views.controllers.funcionario;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import dao.FuncionarioDAO;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import utils.GenericTableButton;
import views.controllers.HomeController;

public class PesquisaFuncionarioController {

	private static Stage pesquisaFuncionario;

	private FuncionarioDAO dao = new FuncionarioDAO();

	// TABELA:

	@FXML
	private TableView<Funcionario> tableView;

	@FXML
	private TableColumn<Funcionario, Integer> cCod;

	@FXML
	private TableColumn<Funcionario, String> cDoc;

	@FXML
	private TableColumn<Funcionario, String> cFone;

	@FXML
	private TableColumn<Funcionario, String> cNome;

	@FXML
	private TableColumn<Funcionario, String> cEndereco;

	@FXML
	private TableColumn<Funcionario, String> cEmail;

	@FXML
	private TableColumn<Funcionario, Double> cSalario;

	@FXML
	private TableColumn<Funcionario, LocalDate> cDataContratacao;

	@FXML
	private TableColumn<Funcionario, LocalDate> cDataDemissao;

	@FXML
	private TableColumn<Funcionario, String> cSenha;

	@FXML
	private TableColumn<Funcionario, Integer> cAcesso;

	@FXML
	private TableColumn<Funcionario, Funcionario> cEditar;

	@FXML
	private TableColumn<Funcionario, Funcionario> cExcluir;

	// -------------------------------------------------------------------

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private JFXTextField txtCodPesquisa;

	@FXML
	private JFXTextField txtNomePesquisa;

	@FXML
	private JFXButton btnPesquisar;

	@FXML
	private JFXButton btnCadFuncionario;

	@FXML
	private JFXButton btnVoltar;

	@FXML
	private JFXListView<Funcionario> lvFuncionarios;

	// ÍCONES SVG (EDITAR e EXCLUIR)
	public static final String PEN_SOLID = "M290.74 93.24l128.02 128.02-277.99 277.99-114.14 12.6C11.35 513.54-1.56 500.62.14 485.34l12.7-114.22 277.9-277.88zm207.2-19.06l-60.11-60.11c-18.75-18.75-49.16-18.75-67.91 0l-56.55 56.55 128.02 128.02 56.55-56.55c18.75-18.76 18.75-49.16 0-67.91z";
	public static final String TRASH_SOLID = "M432 32H312l-9.4-18.7A24 24 0 0 0 281.1 0H166.8a23.72 23.72 0 0 0-21.4 13.3L136 32H16A16 16 0 0 0 0 48v32a16 16 0 0 0 16 16h416a16 16 0 0 0 16-16V48a16 16 0 0 0-16-16zM53.2 467a48 48 0 0 0 47.9 45h245.8a48 48 0 0 0 47.9-45L416 128H32z";

	
	static class CellTooltip extends TableCell<Funcionario, String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            this.setText(item);
            this.setTooltip(
                    (empty || item==null) ? null : new Tooltip(item));
        }
    }
	
	private void inserirToolTip() {
		cEndereco.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Funcionario, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(CellDataFeatures<Funcionario, String> param) {
                        return new ReadOnlyStringWrapper(param.getValue().getEndereco());
                    }
                });
        cEndereco.setCellFactory(new Callback<TableColumn<Funcionario, String>, TableCell<Funcionario, String>>() {
            @Override
            public TableCell<Funcionario, String> call(TableColumn<Funcionario, String> param) {
                return new CellTooltip();
            }
        });
        
	}
	
	
	private void listarFuncionarios() {
		ObservableList<Funcionario> listFunc;

		try {

			listFunc = FXCollections.observableArrayList(dao.listar());

			cCod.setCellValueFactory(new PropertyValueFactory<Funcionario, Integer>("cod"));
			
			cDoc.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("documento"));
			cFone.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("telefone"));
			cNome.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("nome"));
			cEndereco.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("endereco"));
			cEmail.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("email"));
			cSalario.setCellValueFactory(new PropertyValueFactory<Funcionario, Double>("salario"));
			cDataContratacao.setCellValueFactory(new PropertyValueFactory<Funcionario, LocalDate>("data_contratacao"));
			cDataDemissao.setCellValueFactory(new PropertyValueFactory<Funcionario, LocalDate>("data_demissao"));
			cSenha.setCellValueFactory(new PropertyValueFactory<Funcionario, String>("senha_funcionario"));
			cAcesso.setCellValueFactory(new PropertyValueFactory<Funcionario, Integer>("cod_acesso"));

			tableView.setItems(listFunc);

			GenericTableButton.initButtons(cEditar, 15, PEN_SOLID, "svg-gray",
					(Funcionario funcionario, ActionEvent event) -> {
												
						try {
							if (funcionario != null) {
								CadFuncionarioController.funcionarioStatic = funcionario;
								fechar();
								new CadFuncionarioController().getCadFuncionario().show();
							}
						} catch (Exception e) {
							e.printStackTrace();
							Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
							alert.showAndWait();
						}
					});

			GenericTableButton.initButtons(cExcluir, 15, TRASH_SOLID, "svg-red",
					(Funcionario funcionario, ActionEvent event) -> {
						try {
							Alert alert = new Alert(AlertType.CONFIRMATION);

							alert.setTitle("Excluir Funcionario");
							alert.setHeaderText(
									" Caso o funcionário seja excluído seus dados serão perdidos permanentemente! \n Se deseja editar ou demitir funcionário vá para guia de edição.");
							Optional<ButtonType> result = alert.showAndWait();
							if (result.isPresent() && result.get() == ButtonType.OK) {
								if (funcionario != null) {

//									CadFuncionarioController.funcionarioStatic = funcionario;
									dao.deletar(funcionario.getCod());

									ObservableList<Funcionario> listFuncUp;
									listFuncUp = FXCollections.observableArrayList(dao.listar());
									tableView.setItems(listFuncUp);
									funcionario = null;
								}
							}
						} catch (Exception e) {
							Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
							alert.setHeaderText("Atenção");
							alert.showAndWait();
						}

					});

			if (listFunc != null) {
				if (txtCodPesquisa != null && conferirNumero(txtCodPesquisa.getText(), "Insira um número")) {
					List<Funcionario> listaCod = new ArrayList<Funcionario>();
					for (Funcionario f : listFunc) {
						if ((f.getCod() == Integer.parseInt(txtCodPesquisa.getText()))) {
							listaCod.add(f);
							tableView.setItems(listFunc);
						}
					}
					listFunc = FXCollections.observableArrayList(listaCod);
				}
				if (txtNomePesquisa != null) {
					List<Funcionario> listaNome = new ArrayList<Funcionario>();
					for (Funcionario f : listFunc) {
						if (f.getNome().matches(".*" + txtNomePesquisa.getText() + ".*")) {
							listaNome.add(f);
						}
					}
					listFunc = FXCollections.observableArrayList(listaNome);
				}
				listFunc.forEach(f -> lvFuncionarios.getItems().add(f));
				tableView.setItems(listFunc);
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

	public Stage getPesquisaFuncionario() {
		if (pesquisaFuncionario == null) {
			try {
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/views/funcionario/PesquisaFuncionario.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				pesquisaFuncionario = primaryStage;
			} catch (NullPointerException e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
				alert.showAndWait();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pesquisaFuncionario;
	}

	private void fechar() {
		pesquisaFuncionario.close();
		pesquisaFuncionario = null;
	}

	@FXML
	void btnCadFuncionario_Action(ActionEvent event) {
		fechar();
		new CadFuncionarioController().getCadFuncionario().show();
	}

	@FXML
	void btnPesquisar_Action(ActionEvent event) {
		listarFuncionarios();
	}

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

	@FXML
	void initialize() {
		assert txtCodPesquisa != null : "fx:id=\"txtCodPesquisa\" was not injected: check your FXML file 'PesquisaFuncionario.fxml'.";
		assert txtNomePesquisa != null : "fx:id=\"txtNomePesquisa\" was not injected: check your FXML file 'PesquisaFuncionario.fxml'.";
		assert btnPesquisar != null : "fx:id=\"btnPesquisar\" was not injected: check your FXML file 'PesquisaFuncionario.fxml'.";
		assert btnCadFuncionario != null : "fx:id=\"btnCadFuncionario\" was not injected: check your FXML file 'PesquisaFuncionario.fxml'.";
		assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'PesquisaFuncionario.fxml'.";
		assert lvFuncionarios != null : "fx:id=\"lvFuncionarios\" was not injected: check your FXML file 'PesquisaFuncionario.fxml'.";

		listarFuncionarios();
		inserirToolTip();
	}

	// DEPRECATED (NOT USED)
	@FXML
	void lvFuncionario_MouseClicked(MouseEvent event) {
	}
	// *

}
