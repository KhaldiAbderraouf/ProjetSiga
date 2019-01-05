package Controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrincipaleControler implements Initializable {
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXButton map_button;

    @FXML
    private JFXButton classes_button;

    @FXML
    private JFXButton vector_button;

    @FXML
    private JFXButton table_attr_button;

    @FXML
    private JFXButton symb_button;

    @FXML
    private JFXButton api_button;

    @FXML
    private AnchorPane plateau;

    @FXML
    private BorderPane border;

    @FXML
    private AnchorPane sidemenu;

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        try {
            loadPlateau();
        } catch (IOException ex){
            System.out.println("erreur io loadplaeau ");
        }
        drawer.toBack();

        drawer.setOnDrawerClosed((event -> {
            drawer.toBack();
        }));

        drawer.setOnDrawerOpening(event -> {
            drawer.toFront();
            sidemenu.toFront();
        });
    }

    public void drawerHandler(){
        if (drawer.isShown()){
            System.out.println("am shown");
            drawer.close();
        } else {
            drawer.open();
            System.out.println("am closed");
        }
    }

    private void loadPlateau () throws IOException {
        try {
            BorderPane plat = FXMLLoader.load(getClass().getResource("Principale.fxml"));
            border.getChildren().setAll(plat);
        }catch (IOException ex){
            System.out.println("erreur load");
        }
    }
}
