package Controler;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
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

    public void drawonthis (){
        System.out.println("i entered draw on this");
        principale2Controller.drawOnThis(combo.getValue());
    }





}
