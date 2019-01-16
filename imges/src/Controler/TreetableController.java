package Controler;

import Model.Colonne;
import Model.TableAttr;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import org.controlsfx.control.CheckTreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class TreetableController implements Initializable {
    public Principale2Controller principale2Controller;
    /*@FXML
    private JFXTreeTableView<Colonne> treetableview;*/
    @FXML
    private TableView<Colonne> mytableview;

    //TableView<Colonne> viewtable ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<Colonne , String> name = new TableColumn<Colonne, String>("name");
        //JFXTreeTableColumn<Colonne , String> name = new JFXTreeTableColumn<>("nomColonne1");
        name.setPrefWidth(150);

        mytableview.getColumns().addAll(name);
        /*name.setCellValueFactory(

        );*/

    }

    public void setPrincipale2Controller(Principale2Controller principale2Controller) {
        this.principale2Controller = principale2Controller;
    }
}
