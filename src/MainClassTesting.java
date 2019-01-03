import Model.BDD;
import Model.Colonne;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;

public class MainClassTesting extends Application {

    public void start(Stage s) {
        //initView();
        Scene View = null;
        try{
            View = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("./View/Principale.fxml")), 1000, 1000);
        }catch (Exception a){
            System.out.println(a.getMessage());
        }

        s.setScene(View);
        s.setMaximized(true);
        s.show();
    }

    public static void main(String[] args) throws SQLException {
//        Colonne colonne = Colonne.dbFetchWithID(3);
//        System.out.println("ID : "+ colonne.id+"| Nom : "+ colonne.nom +"| IDTable : "+ colonne.idTableAttr +";");
//        colonne.idTableAttr = 2;
//        colonne.dbModifier();
//        List<Colonne> listColonne = Colonne.dbGetAll();
//        for (Colonne c:listColonne) {
//            //System.out.println("ID : "+ c.id+"| Nom : "+ c.nom +"| IDTable : "+ c.idTableAttr +";");
//        }
//        //System.out.println("executed ");
//
//        Colonne col = new Colonne("Lol", 2);
//        col.dbAjouter();
            launch(args);

    }
}
