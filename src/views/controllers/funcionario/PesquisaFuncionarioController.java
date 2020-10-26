package views.controllers.funcionario;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import dao.FuncionarioDAO;
import entitys.Funcionario;
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
import views.controllers.HomeController;

public class PesquisaFuncionarioController {

	private static Stage pesquisaFuncionario;
	
	private FuncionarioDAO dao = new FuncionarioDAO();
	
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

    @FXML
    void lvFuncionario_MouseClicked(MouseEvent event) {
		try {
			Funcionario funcionario = lvFuncionarios.getSelectionModel().getSelectedItem();
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
    
    private void listarFuncionarios() {
    	List<Funcionario> lstFuncionarios;
    	lvFuncionarios.getItems().clear();
    	
		try {
			lstFuncionarios = dao.listar();
			if (lstFuncionarios != null) {
				if (txtCodPesquisa != null && conferirNumero(txtCodPesquisa.getText(), "Insira um número")) {
					List<Funcionario> listaCod = new ArrayList<Funcionario>();
					for (Funcionario f : lstFuncionarios) {
						if ((f.getCod() == Integer.parseInt(txtCodPesquisa.getText()))) {
							listaCod.add(f);
						}
					}
					lstFuncionarios = listaCod;
				}
				if (txtNomePesquisa != null) {
					List<Funcionario> listaNome = new ArrayList<Funcionario>();
					for (Funcionario f : lstFuncionarios) {
						if (f.getNome().matches(".*" + txtNomePesquisa.getText() + ".*")) {
							listaNome.add(f);
						}
					}
					lstFuncionarios = listaNome;
				}
				lstFuncionarios.forEach(f -> lvFuncionarios.getItems().add(f));
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
    }
}
