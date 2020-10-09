package views;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import views.controllers.LoginController;


public class Login extends Application {
	Parent root;
	double xOffset, yOffset;

	@Override
	public void start(Stage primaryStage) {
		try 
		{
			new LoginController().getlogin().show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}