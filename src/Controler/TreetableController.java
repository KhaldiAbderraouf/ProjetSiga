package Controler;

import Model.Colonne;
import Model.TableAttr;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import org.controlsfx.control.CheckTreeView;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class TreetableController implements Initializable {
    public Principale2Controller principale2Controller;
    /*
     * @FXML private JFXTreeTableView<Colonne> treetableview;
     */
    @FXML
    private TableView<Colonne> mytableview;

    @FXML
    private ComboBox<String> combo_selected;

    public ClassesController classesController;

    public void setClassesController(ClassesController classesController) {
        this.classesController = classesController;
    }

    // TableView<Colonne> viewtable ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Colonne, String> name = new TableColumn<Colonne, String>("name");
        // JFXTreeTableColumn<Colonne , String> name = new
        // JFXTreeTableColumn<>("nomColonne1");
        name.setPrefWidth(150);

        mytableview.getColumns().addAll(name);
        /*
         * name.setCellValueFactory(
         * 
         * );
         */

    }

    public void setPrincipale2Controller(Principale2Controller principale2Controller) {
        this.principale2Controller = principale2Controller;
    }

    public void remplirCombo() {

        combo_selected.getItems().clear();
        List<TreeItem<String>> mylist = classesController.getSelectedClasses();
        if (mylist != null) {
            for (Iterator<TreeItem<String>> i = mylist.iterator(); i.hasNext();) {
                TreeItem<String> elem = i.next();
                // elem.getValue();
                combo_selected.getItems().add(elem.getValue());
            }
        }

    }
}
