package pl.gosia.CaricamentoMassivoMaven;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = FXMLLoader.load(getClass().getResource("CaricamentoMassivoMaven.fxml"));
			Scene scene = new Scene(root,600,600);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public void msgBox(Stage primaryStage){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Wrong text format !!!!!");
		alert.setContentText(" You should write letter from scope [a - n] and you can put 0 !");
		alert.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
