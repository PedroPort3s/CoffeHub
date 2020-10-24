package views.controllers.cliente;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.sun.istack.internal.Nullable;

import control.produto.ControlCategoria;
import control.produto.ControlProduto;
import dao.ClienteDAO;
import entitys.Categoria;
import entitys.Cliente;
import entitys.Produto;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private JFXListView<Cliente> lvClientes;

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

    @FXML
    void lvCliente_MouseClicked(MouseEvent event) {
		try {
			Cliente cliente = lvClientes.getSelectionModel().getSelectedItem();
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
        assert btnPesquisar != null : "fx:id=\"btnPesquisar\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert btnVoltar != null : "fx:id=\"btnVoltar\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert btnCadCliente != null : "fx:id=\"btnCadCliente\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert txtNomePesquisa != null : "fx:id=\"txtNomePesquisa\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert lvClientes != null : "fx:id=\"lvClientes\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";
        assert txtCodPesquisa != null : "fx:id=\"txtCodPesquisa\" was not injected: check your FXML file 'PesquisaCliente.fxml'.";

        listarClientes();
    }
}
