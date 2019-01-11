package Controler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrincipaleControler implements Initializable {
    @FXML private JFXDrawer drawerTable;
    @FXML private JFXDrawer drawer;
    @FXML private AnchorPane plateau;
    @FXML private AnchorPane sidemenu;
    @FXML private JFXButton map_button;
    @FXML private JFXButton classes_button;
    @FXML private JFXButton vector_button;
    @FXML private JFXButton table_attr_button;
    @FXML private JFXButton symb_button;
    @FXML private JFXButton api_button;
    @FXML private ScrollPane classecontainer;
    private AnchorPane drawerMap;
    public Principale2Controller prin2;
    public importController importc;
    public ClassesController classesController;
    private AnchorPane classesTree;
    private AnchorPane Vectorhand;
    private AnchorPane drawerattr;
    public VectorController vectorController;
    public TreetableController treetableController;

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        try {

            loadPlateau();
            loadImageimporter();
            loadclasseimporter();
            loadVectorHandler();
            loadTableAttr();
            //drawerTable.toBack();

           // drawerMap = FXMLLoader.load(getClass().getResource("./importcarte.fxml"));
        } catch (IOException ex){
            ex.printStackTrace();
            System.out.println("erreur io loadplaeau ");
        }

        map_button.addEventHandler(MouseEvent.MOUSE_PRESSED,(event -> {
            if (drawer.isHidden()){
                drawer.setSidePane(drawerMap);
            }
        }));

        vector_button.addEventHandler(MouseEvent.MOUSE_PRESSED,(event -> {
            if (drawer.isHidden()){
                drawer.setSidePane(Vectorhand);
            }
        }));

        classes_button.addEventHandler(MouseEvent.MOUSE_PRESSED,(event -> {
            if (drawer.isHidden()){
                drawer.setSidePane(classesTree);
            }
        }));

        table_attr_button.addEventHandler(MouseEvent.MOUSE_PRESSED,(event -> {
            if (drawerTable.isHidden()){
                drawerTable.setSidePane(drawerattr);
            }
        }));

        drawer.toBack();

        drawer.setOnDrawerClosed((event -> {
            drawer.toBack();
        }));

        drawer.setOnDrawerOpening(event -> {
            drawer.toFront();
            sidemenu.toFront();
        });

        //drawerTable.toBack();
        drawerTable.setOnDrawerClosed((event -> {
            drawerTable.toBack();
        }));

        drawerTable.setOnDrawerOpening(event -> {
            drawerTable.toFront();
            sidemenu.toFront();
        });
    }

    public void drawerHandler(){
        if (drawer.isShown()){
//            System.out.println("am shown");
            drawer.close();
        } else {
            drawer.open();
//            System.out.println("am closed");
        }
    }
    public void DrawerTableHandler(){
        drawerTable.toFront();
        if (drawerTable.isShown()){
//            System.out.println("am shown");
            drawerTable.close();
        } else {
            drawerTable.open();
//            System.out.println("am closed");
        }
    }

    private void loadPlateau () throws IOException {
        try {
            FXMLLoader loadr = new FXMLLoader(getClass().getResource("Principale.fxml"));
            AnchorPane plat = loadr.load();
            prin2 = loadr.getController();
            plateau.getChildren().setAll(plat);
        }catch (IOException ex){
            System.out.println("erreur load");
        }
    }

    public void loadImageimporter() throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("importcarte.fxml"));
            drawerMap =  loader.load();
            importc = loader.getController();
            importc.setPrincipale2Controller(prin2);
        } catch (IOException ex){
            System.out.println("erreur loading image importer");
            ex.printStackTrace();
        }

    }

    public void loadclasseimporter() throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("classes.fxml"));
            classesTree =  loader.load();
            classesController = loader.getController();
            classesController.setPrincipale2Controller(prin2);
        } catch (IOException ex){
            System.out.println("erreur loading class");
            ex.printStackTrace();
        }

    }

    public void loadVectorHandler() throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vector.fxml"));
            Vectorhand =  loader.load();
            vectorController = loader.getController();
            vectorController.setPrincipale2Controller(prin2);
            vectorController.setClassesController(classesController);

        } catch (IOException ex){
            System.out.println("erreur loading vector");
            ex.printStackTrace();
        }

    }

    public void loadTableAttr() throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("drawerTable.fxml"));
            drawerattr = loader.load();
            treetableController = loader.getController();
            treetableController.setPrincipale2Controller(prin2);
           // treetableController.setClassesController(classesController);

        } catch (IOException ex){
            System.out.println("erreur loading tableattr");
            ex.printStackTrace();
        }

    }
}
