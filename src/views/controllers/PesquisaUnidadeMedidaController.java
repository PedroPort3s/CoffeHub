package views.controllers;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import entitys.UnidadeMedida;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PesquisaUnidadeMedidaController implements Initializable {

	private static Stage PesquisaUnidadeMedida;

	@FXML
	private JFXTextField txtDescricao;

	@FXML
	private JFXButton btnPesquisar;

	@FXML
	private JFXButton btnVoltar;

	@FXML
	private JFXButton btnCadUnidadeMedida;

	@FXML
	private JFXListView<UnidadeMedida> lvUnidades;

	public Stage getPesquisaUnidadeMedida() {
		if (PesquisaUnidadeMedida == null) {
			try {
				Stage primaryStage = new Stage();
				AnchorPane root = (AnchorPane) FXMLLoader
						.load(getClass().getResource("/views/unidadeMedida/PesquisaUnidadeMedida.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				PesquisaUnidadeMedida = primaryStage;
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
				alert.showAndWait();
			}
		}
		return PesquisaUnidadeMedida;
	}

	@FXML
	void btnCadUnidadeMedida_Action(ActionEvent event) {
		PesquisaUnidadeMedida = null;
		PesquisaUnidadeMedida.close();
		new CadUnidadeMedidaController().getCadUnidadeMedida().show();
	}

	@FXML
	void btnPesquisar_Action(ActionEvent event) {
		Listar();
	}

	@FXML
	void btnVoltar_Action(ActionEvent event) {
		PesquisaUnidadeMedida = null;
		PesquisaUnidadeMedida.close();
		new HomeController().getHome().show();

	}

	private void Listar() {
		try {

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

	@FXML
	void lvCategorias_MouseClicked(MouseEvent event) {
		try {
			UnidadeMedida unidade = lvUnidades.getSelectionModel().getSelectedItem();

			if (unidade != null) {
				CadUnidadeMedidaController.UnidadeEstatica = unidade;
				new CadVendaController().getCadVenda().show();
				PesquisaUnidadeMedida.close();
				PesquisaUnidadeMedida = null;
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


		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

}
