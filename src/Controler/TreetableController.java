package Controler;

import Model.Colonne;
import Model.TableAttr;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import org.controlsfx.control.CheckTreeView;

import java.net.URL;
import java.util.ResourceBundle;

public class TreetableController implements Initializable {
    public Principale2Controller principale2Controller;
    @FXML
    private JFXTreeTableView<TableAttr> treetableview;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXTreeTableColumn<TableAttr , String> name = new JFXTreeTableColumn<>("nomColonne1");
        name.setPrefWidth(150);
        /*name.setCellValueFactory(

        );*/

    }

    public void setPrincipale2Controller(Principale2Controller principale2Controller) {
        this.principale2Controller = principale2Controller;
    }
}
