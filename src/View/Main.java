package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
//        System.out.println("hahahah");
        Parent root = FXMLLoader.load(getClass().getResource("../Controler/mainpage.fxml"));
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 1280, 850));
//        primaryStage.setFullScreen(true);
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        //primaryStage.setMaxWidth();
//        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
