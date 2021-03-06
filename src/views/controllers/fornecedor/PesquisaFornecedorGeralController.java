package views.controllers.fornecedor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import dao.FornecedorDAO;
import entitys.Fornecedor;
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
import views.controllers.CadCompraController;
import views.controllers.PesquisaCompraController;

public class PesquisaFornecedorGeralController {

	private static Stage pesquisaFornecedor;

	public static String cadPesqCompra;

	private FornecedorDAO dao = new FornecedorDAO();

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
	private JFXListView<Fornecedor> lvFornecedores;

	@FXML
	private JFXTextField txtCodPesquisa;

	@FXML
	void btnVoltar_Action(ActionEvent event) {
		if (cadPesqCompra == "CADCOMPRA") {
			new CadCompraController().getCadCompra().show();
			pesquisaFornecedor.close();
			pesquisaFornecedor = null;
		}
		if (cadPesqCompra == "PESQUISACOMPRA") {
			new PesquisaCompraController().getPesquisaCompra().show();
			pesquisaFornecedor.close();
			pesquisaFornecedor = null;
		}
	}

	public Stage getPesquisaFornecedorGeral() {
		if (pesquisaFornecedor == null) {
			try {
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/views/pesquisa/PesquisaFornecedorGeral.fxml"));
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

	@FXML
	void btnPesquisar_Action(ActionEvent event) {
		listarFornecedores();
	}

	@FXML
	void lvFornecedor_MouseClicked(MouseEvent event) {
		try {
			Fornecedor fornecedor = lvFornecedores.getSelectionModel().getSelectedItem();
			if (fornecedor != null) {
				if (cadPesqCompra == "CADCOMPRA") {
					CadCompraController.FornecedorEstatico = null;
					CadCompraController.FornecedorEstatico = fornecedor;
					new CadCompraController().getCadCompra().show();
					pesquisaFornecedor.close();
					pesquisaFornecedor = null;
				}
				if (cadPesqCompra == "PESQUISACOMPRA") {
					PesquisaCompraController.fornecedorEstatico = null;
					PesquisaCompraController.fornecedorEstatico = fornecedor;
					new PesquisaCompraController().getPesquisaCompra().show();
					pesquisaFornecedor.close();
					pesquisaFornecedor = null;
				}
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Aten??o");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	private void listarFornecedores() {
		List<Fornecedor> lstFornecedor;
		lvFornecedores.getItems().clear();

		try {
			lstFornecedor = dao.listar();
			if (lstFornecedor != null) {
				if (txtCodPesquisa != null && conferirNumero(txtCodPesquisa.getText(), "Insira um n?mero")) {
					List<Fornecedor> listaCod = new ArrayList<Fornecedor>();
					for (Fornecedor forn : lstFornecedor) {
						if ((forn.getCod() == Integer.parseInt(txtCodPesquisa.getText()))) {
							listaCod.add(forn);
						}
					}
					lstFornecedor = listaCod;
				}
				if (txtNomePesquisa != null) {
					List<Fornecedor> listaNome = new ArrayList<Fornecedor>();
					for (Fornecedor forn : lstFornecedor) {
						if (forn.getNome().matches(".*" + txtNomePesquisa.getText() + ".*")) {
							listaNome.add(forn);
						}
					}
					lstFornecedor = listaNome;
				}
				lstFornecedor.forEach(f -> lvFornecedores.getItems().add(f));
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
		assert txtCodPesquisa != null
				: "fx:id=\"txtCodPesquisa\" was not injected: check your FXML file 'PesquisaFornecedor.fxml'.";
		assert txtNomePesquisa != null
				: "fx:id=\"txtNomePesquisa\" was not injected: check your FXML file 'PesquisaFornecedor.fxml'.";
		assert btnPesquisar != null
				: "fx:id=\"btnPesquisar\" was not injected: check your FXML file 'PesquisaFornecedor.fxml'.";
		assert btnVoltar != null
				: "fx:id=\"btnVoltar\" was not injected: check your FXML file 'PesquisaFornecedor.fxml'.";
		assert lvFornecedores != null
				: "fx:id=\"lvFornecedores\" was not injected: check your FXML file 'PesquisaFornecedor.fxml'.";

		listarFornecedores();
	}
}
