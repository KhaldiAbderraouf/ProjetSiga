package Controler;

import Model.*;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.CheckTreeView;

import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class TreetableController implements Initializable {
    public Principale2Controller principale2Controller;
    public ArrayList<String> nomscol = new ArrayList<>();
    public ArrayList<ArrayList<String>> matric = new ArrayList<>();

    Sig mysig ;
    Couche couche ;
    TableAttr table;
    ArrayList<Colonne> mycolons;
    ObservableList<ObservableList> data;

    @FXML
    private TableView<ObservableList<StringProperty>> mytableview;

    @FXML
    private ComboBox<String> combo_selected;

    public ClassesController classesController;

    public void setClassesController(ClassesController classesController) {
        this.classesController = classesController;
    }

    private Map<String,ArrayList<TableColumn<Shape, String>>> mymapofcols = new TreeMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mytableview.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    System.out.println(mytableview.getSelectionModel().getSelectedItem());
                    System.out.println(mytableview.getSelectionModel().getSelectedItem().size()+" this is the size of the row");
                    System.out.println("this is the index of the row " + mytableview.getSelectionModel().selectedIndexProperty().get());

                    changerow(mytableview.getSelectionModel().selectedIndexProperty().get(),mytableview.getSelectionModel().getSelectedItem());
                   // mytableview.getSelectionModel().getSelectedItem().clear();
                    //mytableview.getSelectionModel().getSelectedItem().
                    /*StringProperty test1 = new SimpleStringProperty("test");
                    StringProperty test2 = new SimpleStringProperty("test2");
                    StringProperty test3 = new SimpleStringProperty("test3");


                    mytableview.getSelectionModel().getSelectedItem().addAll(test1,test2,test3);
                    System.out.println(mytableview.getSelectionModel().getSelectedItem());*/
                }
            }
        });

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
                //System.out.println(elem.getValue());
                combo_selected.getItems().add(elem.getValue());
            }
        }

    }

    public void remplirtable(){

        mytableview.getItems().clear();
        mytableview.getColumns().clear();
        data= FXCollections.observableArrayList();

        //data = FXCollections.observableArrayList();
        mysig =  principale2Controller.getSig();
        couche = mysig.getCouche(combo_selected.getValue());
        table = couche.getTableAt();
        mycolons = table.getTables();
        int oldsize = 0;
        if (mycolons.size() != 0){
            ////////////////////////////////////////////////////////////////////
            System.out.println("colonne existe");
            boolean changedshapes = false;
            ArrayList<String> nameshape = new ArrayList<>();
            if (couche instanceof CoucheLigne){
                List<Ligne> list = ((CoucheLigne) couche).getLignes();
                oldsize = mycolons.get(0).getCol().size();
                if (list.size()!=oldsize){
                    changedshapes = true;
                    couche.removeallfromcolonne("id=name");
                    for (int i=0;i<list.size();i++){
                        nameshape.add(list.get(i).getName());
                    }

                }


            }else if (couche instanceof CouchePoint){
                List<PointNomer> list =  ((CouchePoint) couche).getPoints();
                oldsize = mycolons.get(0).getCol().size();
                if (list.size()!=oldsize){
                    System.out.println("hereeeeeeeee");
                    changedshapes = true;
                    couche.removeallfromcolonne("id=name");
                    for (int i=0;i<list.size();i++){
                        nameshape.add(list.get(i).getName());
                    }
                }

            }else{
                List<Polygone> list = ((CouchePolygone)couche).getPolys();
                oldsize = mycolons.get(0).getCol().size();
                if (list.size()!=oldsize){
                    changedshapes = true;
                    couche.removeallfromcolonne("id=name");
                    for (int i=0;i<list.size();i++){
                        nameshape.add(list.get(i).getName());
                    }
                }
            }
            if (changedshapes){
                addemptytext(oldsize,nameshape.size());
                for (int i=0;i<nameshape.size();i++){
                    table.addtocol("id=name",nameshape.get(i));
                }
            }
            /////////////////////////////////////////////////////////////////

          testtable();
       } else {

            ArrayList<String> nameshape = new ArrayList<>();
            if (couche instanceof CoucheLigne){
                List<Ligne> list = ((CoucheLigne) couche).getLignes();
                for (int i=0;i<list.size();i++){
                    nameshape.add(list.get(i).getName());
                }
            }else if (couche instanceof CouchePoint){
                List<PointNomer> list =  ((CouchePoint) couche).getPoints();
                for (int i=0;i<list.size();i++){
                    nameshape.add(list.get(i).getName());
                }
            }else{
                List<Polygone> list = ((CouchePolygone)couche).getPolys();
                for (int i=0;i<list.size();i++){
                    nameshape.add(list.get(i).getName());
                }
            }
            table.setTaille(nameshape.size());
            table.addColonne("id=name");
            //System.out.println(nameshape);
            for (int i=0;i<nameshape.size();i++){
                table.addtocol("id=name",nameshape.get(i));
            }
            System.out.println(table.getcolnew("id=name").getCol());
            testtable();
       }



        /*for (int i=0 ; i<nomscol.size();i++){
            final int j =i;
            TableColumn col = new TableColumn(nomscol.get(j));
           col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    System.out.println(Integer.MAX_VALUE);
                    System.out.println("hna param " + param.getValue().get(0).toString());
                    return new ReadOnlyObjectWrapper(param.getValue().get(j).toString());
                }
            });
            mytableview.getColumns().addAll(col);
        }


        for (int i = 0;i<matric.size();i++){
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int j =0;j<matric.get(i).size();j++){
                row.add(matric.get(i).get(j));
            }
            data.add(row);
        }

        mytableview.setItems(data);*/


    }

    public void testtable(){

        mytableview.getItems().clear();
        mytableview.getColumns().clear();
        data = FXCollections.observableArrayList();
        mysig =  principale2Controller.getSig();
        couche = mysig.getCouche(combo_selected.getValue());
        table = couche.getTableAt();
        mycolons = table.getTables();

        /*int nbrows = mycolons.get(0).getCol().size();
        ObservableList<String> rowtest = FXCollections.observableArrayList();
        ArrayList<String> mycol;
        for (int nbrow = 0;nbrow<nbrows;nbrow++){
            for (int i =0;i<mycolons.size();i++){
                mycol = mycolons.get(i).getCol();
                //System.out.println(mycol.get(nbrow) + " hahahaha \n");
                rowtest.add(mycol.get(nbrow));
            }
            data.add(rowtest);
        }*/

        ////////////////////////////////////////////////////////////////////
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                // Header line
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            for (int column = 0; column < mycolons.size(); column++) {
                                mytableview.getColumns().add(
                                        createColumn(column, mycolons.get(column).getName()));
                            }
                        }
                    });


                // Data:

               // String dataLine;

                int nbrows = mycolons.get(0).getCol().size();


                ArrayList<String> mycol;
                for (int nbrow = 0;nbrow<nbrows;nbrow++){
                    ArrayList<String> rowtest = new ArrayList<>();
                    for (int i =0;i<mycolons.size();i++){
                        mycol = mycolons.get(i).getCol();
                        //System.out.println(mycol.get(nbrow) + " hahahaha \n");
                        rowtest.add(mycol.get(nbrow));
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // Add additional columns if necessary:
                            for (int columnIndex = mytableview.getColumns().size(); columnIndex < rowtest.size(); columnIndex++) {
                                mytableview.getColumns().add(createColumn(columnIndex, ""));
                            }
                            // Add data to table:
                            ObservableList<StringProperty> data = FXCollections
                                    .observableArrayList();
                            for (String value : rowtest) {
                                data.add(new SimpleStringProperty(value));
                            }
                            mytableview.getItems().add(data);
                        }
                    });
                    //data.add(rowtest);
                }

                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        ///////////////////////////////////////////////////////////////////


    }

    private TableColumn<ObservableList<StringProperty>, String> createColumn(
            final int columnIndex, String columnTitle) {
        TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>();
        String title;
        if (columnTitle == null || columnTitle.trim().length() == 0) {
            title = "Column " + (columnIndex + 1);
        } else {
            title = columnTitle;
        }
        column.setText(title);
        column
                .setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<StringProperty>, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<ObservableList<StringProperty>, String> cellDataFeatures) {
                        ObservableList<StringProperty> values = cellDataFeatures.getValue();
                        if (columnIndex >= values.size()) {
                            return new SimpleStringProperty("");
                        } else {
                            return cellDataFeatures.getValue().get(columnIndex);
                        }
                    }
                });
        column.setCellFactory(TextFieldTableCell.<ObservableList<StringProperty>>forTableColumn());
        return column;
    }

    public void addColonne() {
        Stage addcolonne = new Stage();
       // addcolonne.setResizable(false);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);
        VBox vbox = new VBox();
        Label hint = new Label("Nom de la colonne :");
        TextField name = new TextField();
        name.setPrefWidth(250);
        ArrayList<TextField> myarraylist = new ArrayList<>();
        int nbrows = mycolons.get(0).getCol().size();
        for (int i=0;i<nbrows;i++){
            TextField newfield = new TextField();
            newfield.setText("champ"+(i+1));
            myarraylist.add(newfield);
            vbox.getChildren().add(newfield);
        }

        Button valider = new Button("Valider");
        valider.setOnAction(e -> {
            table.addColonne(name.getText());

            for (int i=0;i<myarraylist.size();i++){
                table.addtocol(name.getText(),myarraylist.get(i).getText());
            }


            /*String colname = name.getText();
            TableColumn<ObservableList<StringProperty>, String> col = new  TableColumn<>(colname);
            //col.setCellFactory(TextFieldTableCell.forTableColumn());
            mytableview.getColumns().add(col);*/

            testtable();
            addcolonne.close();
        });

        grid.add(hint, 0, 0);
        grid.add(name, 1, 0);
        grid.add(vbox,0,1);
        grid.add(valider, 2, 1);

        Principale2Controller.setInitialScene(new Scene(grid, 600, 500));
        addcolonne.setScene(Principale2Controller.getInitialScene());
        addcolonne.show();
    }

    public void changerow(int rownumber,ObservableList<StringProperty> mycurrentrow){
        Stage addcolonne = new Stage();
        // addcolonne.setResizable(false);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);
        HBox hbox = new HBox();
        //Label hint = new Label("Nom de la colonne :");
        //TextField name = new TextField();
        //name.setPrefWidth(250);
        ArrayList<TextField> myarraylist = new ArrayList<>();

        //int nbrows = mycolons.get(0).getCol().size();
        for (int i=1;i< mycolons.size();i++){
            TextField newfield = new TextField();
            newfield.setText(mycurrentrow.get(i).get());
            myarraylist.add(newfield);
            hbox.getChildren().add(newfield);
        }

        Button valider = new Button("Valider");
        valider.setOnAction(e -> {
            //table.addColonne(name.getText());

            for (int i=0;i<myarraylist.size();i++){
                table.changeFromCol(i+1,myarraylist.get(i).getText(),rownumber);
                //table.addtocol(name.getText(),myarraylist.get(i).getText());
            }



            /*String colname = name.getText();
            TableColumn<ObservableList<StringProperty>, String> col = new  TableColumn<>(colname);
            //col.setCellFactory(TextFieldTableCell.forTableColumn());
            mytableview.getColumns().add(col);*/

            testtable();
            addcolonne.close();
        });

       // grid.add(hint, 0, 0);
        //grid.add(name, 1, 0);
        grid.add(hbox,0,1);
        grid.add(valider, 2, 1);

        Principale2Controller.setInitialScene(new Scene(grid, 600, 200));
        addcolonne.setScene(Principale2Controller.getInitialScene());
        addcolonne.show();
    }

    public void addemptytext (int oldsize , int newsize){
        int i=1;
        for (i=1;i<mycolons.size();i++){
            for (int j=0;j<(newsize-oldsize);j++){
                mycolons.get(i).addstring("a remplir");
            }
        }

    }

}
