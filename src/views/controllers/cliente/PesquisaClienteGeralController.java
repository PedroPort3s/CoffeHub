package views.controllers.cliente;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import dao.ClienteDAO;
import entitys.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import views.controllers.CadVendaController;
import views.controllers.PesquisaVendaController;

public class PesquisaClienteGeralController {

	private static Stage pesquisaCliente;

	public static String cadPesqVenda;

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
	private JFXTextField txtNomePesquisa;

	@FXML
	private JFXListView<Cliente> lvClientes;

	@FXML
	private JFXTextField txtCodPesquisa;

	@FXML
	void btnVoltar_Action(ActionEvent event) {

		if (cadPesqVenda == "CADVENDA") {
			new CadVendaController().getCadVenda().show();
			pesquisaCliente.close();
			pesquisaCliente = null;
		}
		if (cadPesqVenda == "PESQUISAVENDA") {
			new PesquisaVendaController().getPesquisaVenda().show();
			pesquisaCliente.close();
			pesquisaCliente = null;
		}
	}

	public Stage getPesquisaClienteGeral() {
		if (pesquisaCliente == null) {
			try {
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/views/pesquisa/PesquisaClienteGeral.fxml"));
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

	@FXML
	void btnPesquisar_Action(ActionEvent event) {
		listarClientes();
	}

	@FXML
	void lvCliente_MouseClicked(MouseEvent event) {
		try
		{
			
			Cliente cliente = lvClientes.getSelectionModel().getSelectedItem();
			if (cliente != null) {
				if (cadPesqVenda == "CADVENDA") {
					CadVendaController.ClienteEstatico = null;
					CadVendaController.ClienteEstatico = cliente;
					new CadVendaController().getCadVenda().show();
					pesquisaCliente.close();
					pesquisaCliente = null;
				}
				if (cadPesqVenda == "PESQUISAVENDA") {
					PesquisaVendaController.clienteEstatico = null;
					PesquisaVendaController.clienteEstatico = cliente;
					new PesquisaVendaController().getPesquisaVenda().show();
					pesquisaCliente.close();
					pesquisaCliente = null;
				}
			}
			
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	private void listarClientes() {
		List<Cliente> lstClientes;
		lvClientes.getItems().clear();

		try {
			lstClientes = dao.listar();
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
				lstClientes.forEach(p -> lvClientes.getItems().add(p));
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
		assert btnPesquisar != null
				: "fx:id=\"btnPesquisar\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
		assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
		assert txtNomePesquisa != null
				: "fx:id=\"txtNomePesquisa\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
		assert lvClientes != null
				: "fx:id=\"lvClientes\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
		assert txtCodPesquisa != null
				: "fx:id=\"txtCodPesquisa\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";

		listarClientes();
	}
}
