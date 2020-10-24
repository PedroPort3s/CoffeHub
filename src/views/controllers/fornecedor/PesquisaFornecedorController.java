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
import views.controllers.HomeController;

public class PesquisaFornecedorController {

	private static Stage pesquisaFornecedor;
	
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
    private JFXButton btnCadFornecedor;

    @FXML
    private JFXTextField txtNomePesquisa;

    @FXML
    private JFXListView<Fornecedor> lvFornecedores;

    @FXML
    private JFXTextField txtCodPesquisa;
    
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

    @FXML
    void lvFornecedor_MouseClicked(MouseEvent event) {
		try {
			Fornecedor fornecedor = lvFornecedores.getSelectionModel().getSelectedItem();
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
    }

    private void listarFornecedores() {
    	List<Fornecedor> lstFornecedor;
    	lvFornecedores.getItems().clear();
    	
		try {
			lstFornecedor = dao.listar();
			if (lstFornecedor != null) {
				if (txtCodPesquisa != null && conferirNumero(txtCodPesquisa.getText(), "Insira um número")) {
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
        assert lvFornecedores != null : "fx:id=\"lvFornecedores\" was not injected: check your FXML file 'PesquisaFornecedor.fxml'.";

        listarFornecedores();
    }
}
