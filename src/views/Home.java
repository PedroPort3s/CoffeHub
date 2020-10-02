package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Home extends Application {
	

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Home.fxml"));
            Scene scene = new Scene(root,815,400);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}