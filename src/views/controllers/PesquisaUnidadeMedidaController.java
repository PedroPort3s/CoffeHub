package views.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import control.produto.ControlUnidadeMedida;
import entitys.UnidadeMedida;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
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
		PesquisaUnidadeMedida.close();
		PesquisaUnidadeMedida = null;
		new CadUnidadeMedidaController().getCadUnidadeMedida().show();
	}

	@FXML
	void btnPesquisar_Action(ActionEvent event) {
		Listar();
	}

	@FXML
	void btnVoltar_Action(ActionEvent event) {
		PesquisaUnidadeMedida.close();
		PesquisaUnidadeMedida = null;
		new HomeController().getHome().show();

	}

	private void Listar() {
		List<UnidadeMedida> lstUnidades = null;
		lvUnidades.getItems().clear();
		try {
			lstUnidades = new ControlUnidadeMedida().Listar(txtDescricao.getText());
			
			if (lstUnidades != null) {
				lstUnidades.forEach(u -> lvUnidades.getItems().add(u));
			}

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);

			alert.setTitle("Atenção");
			alert.setHeaderText(e.getMessage());

			alert.showAndWait();
		}
	}

    @FXML
    void lvUnidades_MouseClicked(MouseEvent event) {
		try {
			UnidadeMedida unidade = lvUnidades.getSelectionModel().getSelectedItem();

			if (unidade != null) {
				CadUnidadeMedidaController.UnidadeEstatica = unidade;
				new CadUnidadeMedidaController().getCadUnidadeMedida().show();
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
			Listar();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK);
			alert.showAndWait();
		}
	}

}
