package proyecto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("interfaz.fxml"));
        Scene scene = new Scene(root, 656, 455);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("imagenes/logo.png")));
        primaryStage.setTitle("Proyecto Hibernate");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("estilos.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
