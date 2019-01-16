package Controler;

import Model.Ligne;
import Model.Point;
import Model.PointNomer;
import Model.Polygone;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class VectorController implements Initializable {

    @FXML
    private ComboBox<String> combo;


    public Principale2Controller principale2Controller;
    public ClassesController classesController;

    public void setClassesController(ClassesController classesController) {
        this.classesController = classesController;
    }

    public void setPrincipale2Controller(Principale2Controller principale2Controller) {
        this.principale2Controller = principale2Controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //remplirCombo();

    }

    public void remplirCombo (){

        combo.getItems().clear();
        List<TreeItem<String>> mylist = classesController.getSelectedClasses();
        if (mylist!=null){
            for (Iterator<TreeItem<String>> i = mylist.iterator();i.hasNext();){
                TreeItem<String> elem = i.next();
                //elem.getValue();
                combo.getItems().add(elem.getValue());
            }
        }

    }
    public void cancel(){
        // PDC ! What's that ??
        Map<String, ShapeDrawer> drawers = principale2Controller.getDrawers();
        String targetCouche = combo.getValue();

        //// pas de couche -> pas de drawer -> NullPointerException
        if(targetCouche != null  && drawers.containsKey(targetCouche)){
            drawers.get(combo.getValue()).cancel();
        }

    }

    public void drawonthis (){
//        System.out.println("i entered draw on this");
        principale2Controller.drawOnThis(combo.getValue());
    }

    public void supprimercouche(){

        if (combo.getValue() != null){

            Stage createCoucheStage = new Stage();
            createCoucheStage.setResizable(false);

            GridPane grid = new GridPane();
            grid.setHgap(20);
            grid.setVgap(20);
            grid.setAlignment(Pos.CENTER);

            Label hint = new Label("Nom du forme associé a la couche: "+combo.getValue());
            //TextField name = new TextField();
            // name.setEditable(false);
            //name.setPrefWidth(250);

            ChoiceBox selectType = new ChoiceBox();
            selectType.setPrefWidth(75);


            Button valider = new Button("Valider");
            valider.setOnAction(e -> {
                principale2Controller.DeleteShape(combo.getValue(),(String) selectType.getValue());
                createCoucheStage.close();
            });

            grid.add(hint, 0, 0);
            //grid.add(name, 1, 0);
            grid.add(selectType, 2, 0);
            grid.add(valider, 2, 1);


            Principale2Controller.setInitialScene(new Scene(grid, 600, 100));
            createCoucheStage.setScene(Principale2Controller.getInitialScene());
            createCoucheStage.show();


            Couche couch =  principale2Controller.getSig().getCouche( combo.getValue());
            if (couch instanceof CoucheLigne){
                List<Ligne> list = ((CoucheLigne) couch).getLignes();
                for (int i=0;i<list.size();i++){
                    selectType.getItems().add(list.get(i).getName());
                }
            }else if (couch instanceof CouchePoint){
                List<PointNomer> list =  ((CouchePoint) couch).getPoints();
                for (int i=0;i<list.size();i++){
                    selectType.getItems().add(list.get(i).getName());
                }
            }else{
                List<Polygone> list = ((CouchePolygone)couch).getPolys();
                for (int i=0;i<list.size();i++){
                    selectType.getItems().add(list.get(i).getName());
                }
            }
        }


    }





}
