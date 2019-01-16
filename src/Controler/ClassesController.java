package Controler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckTreeView;
//import sun.reflect.generics.tree.Tree;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class ClassesController implements Initializable {
    public Principale2Controller principale2Controller;
    private TreeView treeView;
    @FXML
    private CheckTreeView<String> treecontainer;
    CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>("CLASSES");

    public void setPrincipale2Controller(Principale2Controller principale2Controller) {
        this.principale2Controller = principale2Controller;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*
         * CheckBoxTreeItem<String> class1 = new CheckBoxTreeItem<>("class1");
         * CheckBoxTreeItem<String> class2 = new CheckBoxTreeItem<>("class2");
         * CheckBoxTreeItem<String> var1 = new CheckBoxTreeItem<>("var1");
         * CheckBoxTreeItem<String> var2 = new CheckBoxTreeItem<>("var2");
         * CheckBoxTreeItem<String> var3 = new CheckBoxTreeItem<>("var3");
         * CheckBoxTreeItem<String> var4 = new CheckBoxTreeItem<>("var4");
         * CheckBoxTreeItem<String> var5 = new CheckBoxTreeItem<>("var5");
         * 
         * class1.getChildren().addAll(var1,var2,var3);
         * class2.getChildren().addAll(var4,var5);
         * 
         * root.getChildren().addAll(class1,class2); root.setExpanded(true);
         */

        treecontainer.setRoot(root);

    }

    public void addToList(String type, String couchename) {
        principale2Controller.createCouche(type, couchename);
    }

    public void createCouche() {
        ////// Added ///////////////////////////////////////////////
        Sig sig = principale2Controller.getSig();
        if (sig == null)
            return; // pas de sig => pas de couche !!!!!
        ////////////////////////////////////////////////////////////

        Stage createCoucheStage = new Stage();
        createCoucheStage.setResizable(false);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        Label hint = new Label("Nom de la couche :");
        TextField name = new TextField();
        name.setPrefWidth(250);

        ChoiceBox selectType = new ChoiceBox(FXCollections.observableArrayList("Point", "Ligne", "Polygone"));
        selectType.setValue("Point");

        Button valider = new Button("Valider");
        valider.setOnAction(e -> {

            String coucheName = name.getText();

            ///////// Added ////////////////////////////////////////////
            if (coucheName.equals(""))
                return; // nom de couche ne doit pas etre vide !
            ////////////////////////////////////////////////////////////
            String type = (String) selectType.getValue();
            addToList(type, coucheName);
            CheckBoxTreeItem<String> coucheToAdd = new CheckBoxTreeItem<>(coucheName);
            coucheToAdd.selectedProperty().addListener((event) -> {
                if (coucheToAdd.selectedProperty().getValue()) {
                    principale2Controller.canVisibility(true, coucheName);
                } else {
                    principale2Controller.canVisibility(false, coucheName);
                }
            });
            root.getChildren().add(coucheToAdd);
            createCoucheStage.close();

        });

        grid.add(hint, 0, 0);
        grid.add(name, 1, 0);
        grid.add(selectType, 2, 0);
        grid.add(valider, 2, 1);

        Principale2Controller.setInitialScene(new Scene(grid, 600, 100));
        createCoucheStage.setScene(Principale2Controller.getInitialScene());
        createCoucheStage.show();
    }

    public List<TreeItem<String>> getSelectedClasses() {

        // return treecontainer.getCheckModel().getCheckedItems();

        List<TreeItem<String>> mylist = root.getChildren();
        List<TreeItem<String>> toreturn = new ArrayList<TreeItem<String>>();

        // CheckBoxTreeItem item = (CheckBoxTreeItem<String>) root.getChildren().get(1);
        int number = root.getChildren().size();
        for (Iterator<TreeItem<String>> i = mylist.iterator(); i.hasNext();) {
            CheckBoxTreeItem item = (CheckBoxTreeItem<String>) i.next();
            if (item.isSelected())
                toreturn.add(item);
        }
        // item.isSelected();
        return toreturn;

        /*
         * for (Iterator<CheckBoxTreeItem<String>> i = list.iterator();i.hasNext();) {
         * TreeItem<String> item = i.next(); if (item.) }
         */
    }
}
