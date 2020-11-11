package views.controllers.cliente;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import utils.Formatacao;
import views.controllers.HomeController;

public class PesquisaClienteController {

	private static Stage pesquisaCliente;

	private ClienteDAO dao = new ClienteDAO();

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

	@FXML
	private TableView<Cliente> tbl;

	@FXML
	private TableColumn<Cliente, Integer> colCod;

	@FXML
	private TableColumn<Cliente, String> colNome;

	@FXML
	private TableColumn<Cliente, String> colEmail;

	@FXML
	private TableColumn<Cliente, String> colDocumento;

	@FXML
	private TableColumn<Cliente, String> colTelefone;

	@FXML
	private TableColumn<Cliente, String> colIdade;
	
	@FXML
    private TableColumn<Cliente, String> colEndereco;

	@FXML
    private TableColumn<Cliente, Void> colEditar;

    @FXML
    private TableColumn<Cliente, Void> colExcluir;
	
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
		tbl.setItems(listaClientes());
	}

	private List<Cliente> filtrarClientes() {
		List<Cliente> lstClientes = dao.listar();
		if (lstClientes != null) {
			if (txtCodPesquisa != null && conferirNumero(txtCodPesquisa.getText(), "Insira um número")) {
				List<Cliente> listaCod = new ArrayList<Cliente>();
				for (Cliente cl : lstClientes) {
					if ((cl.getCod() == Integer.parseInt(txtCodPesquisa.getText()))) {
						listaCod.add(cl);
					}
				}
				lstClientes = listaCod;
			}
			if (txtNomePesquisa != null) {
				List<Cliente> listaNome = new ArrayList<Cliente>();
				for (Cliente cl : lstClientes) {
					if (cl.getNome().matches(".*" + txtNomePesquisa.getText() + ".*")) {
						listaNome.add(cl);
					}
				}
				lstClientes = listaNome;
			}
		}
		return lstClientes;
	}

	private ObservableList<Cliente> listaClientes() {
		try {
			List<Cliente> lstClientes = filtrarClientes();
			return FXCollections.observableArrayList(lstClientes);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
		throw new RuntimeException("Erro ao listar Clientes");
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
	
	public void iniciar() {
		tbl.setItems(FXCollections.observableArrayList(
				new Cliente(LocalDate.now(), 1, "09574303969","984941246", "Vitor", "endereco", "email@.com")
				));
		
		
		inserirTooltip();
	}
	
	static class CellTooltip extends TableCell<Cliente, String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            this.setText(item);
            this.setTooltip(
                    (empty || item==null) ? null : new Tooltip(item));
        }
    }
	
	public void inserirTooltip() {
		colCod.setCellValueFactory(new PropertyValueFactory<>("cod"));
		
		colDocumento.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
						return new ReadOnlyStringWrapper(Formatacao.formatarDocumento(param.getValue().getDocumento()));
					}
				});
		colDocumento.setCellFactory(new Callback<TableColumn<Cliente, String>, TableCell<Cliente, String>>() {
			@Override
			public TableCell<Cliente, String> call(TableColumn<Cliente, String> param) {
				return new CellTooltip();
			}
		});
		
		colEmail.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
						return new ReadOnlyStringWrapper(param.getValue().getEmail());
					}
				});
		colEmail.setCellFactory(new Callback<TableColumn<Cliente, String>, TableCell<Cliente, String>>() {
			@Override
			public TableCell<Cliente, String> call(TableColumn<Cliente, String> param) {
				return new CellTooltip();
			}
		});
		
		colIdade.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
						return new ReadOnlyStringWrapper(param.getValue().getIdade()+"");
					}
				});
		colIdade.setCellFactory(new Callback<TableColumn<Cliente, String>, TableCell<Cliente, String>>() {
			@Override
			public TableCell<Cliente, String> call(TableColumn<Cliente, String> param) {
				return new CellTooltip();
			}
		});
		
		colNome.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
						return new ReadOnlyStringWrapper(param.getValue().getNome());
					}
				});
		colNome.setCellFactory(new Callback<TableColumn<Cliente, String>, TableCell<Cliente, String>>() {
			@Override
			public TableCell<Cliente, String> call(TableColumn<Cliente, String> param) {
				return new CellTooltip();
			}
		});
		
		colTelefone.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
						return new ReadOnlyStringWrapper(Formatacao.formatarTelefone(param.getValue().getTelefone()));
					}
				});
		colTelefone.setCellFactory(new Callback<TableColumn<Cliente, String>, TableCell<Cliente, String>>() {
			@Override
			public TableCell<Cliente, String> call(TableColumn<Cliente, String> param) {
				return new CellTooltip();
			}
		});
		
		colEndereco.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Cliente, String> param) {
						return new ReadOnlyStringWrapper(param.getValue().getEndereco());
					}
				});
		colEndereco.setCellFactory(new Callback<TableColumn<Cliente, String>, TableCell<Cliente, String>>() {
			@Override
			public TableCell<Cliente, String> call(TableColumn<Cliente, String> param) {
				return new CellTooltip();
			}
		});
		
        Callback<TableColumn<Cliente, Void>, TableCell<Cliente, Void>> cellTeste = new Callback<TableColumn<Cliente, Void>, TableCell<Cliente, Void>>() {
            @Override
            public TableCell<Cliente, Void> call(final TableColumn<Cliente, Void> param) {
                final TableCell<Cliente, Void> cell = new TableCell<Cliente, Void>() {

                    private final Button btn = new Button("Editar");{
                        btn.setOnAction((ActionEvent event) -> {
							Cliente cliente = getTableView().getItems().get(getIndex());
							
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
                       
//                        btn.setPrefWidth(280);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colEditar.setCellFactory(cellTeste);	
//        tbl.getColumns().add(colBtn);
	}
	
	@FXML
	void initialize() {
		assert txtCodPesquisa != null : "fx:id=\"txtCodPesquisa\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert txtNomePesquisa != null : "fx:id=\"txtNomePesquisa\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert btnPesquisar != null : "fx:id=\"btnPesquisar\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert btnCadCliente != null : "fx:id=\"btnCadCliente\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert tbl != null : "fx:id=\"tbl\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert colCod != null : "fx:id=\"colCod\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert colNome != null : "fx:id=\"colNome\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert colEmail != null : "fx:id=\"colEmail\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert colDocumento != null : "fx:id=\"colDocumento\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert colTelefone != null : "fx:id=\"colTelefone\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert colIdade != null : "fx:id=\"colIdade\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";

		iniciar();
	}
}
