package Controler;


import Model.CouleurInterv;
import Model.Shape;
import com.jfoenix.controls.JFXColorPicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SymbologieController implements Initializable {

    private Principale2Controller principale2Controller;

    @FXML public JFXColorPicker colorPicker;
    @FXML public ComboBox combo;
    @FXML public ListView shapeList;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setPrincipale2Controller(Principale2Controller principale2Controller) {
        this.principale2Controller = principale2Controller;
    }

    public void remplirCombo() {

        ArrayList<Couche> couches = principale2Controller.getCouches();

        combo.getItems().clear();
        for (Couche c: couches) {
            combo.getItems().add(c.getName());
        }
        shapeList.getItems().clear();

    }

    public void getShapeList() {
        String coucheName = (String)combo.getValue();

        if (coucheName == null) return;

        Couche couche = principale2Controller.getSig().get(coucheName);

        ArrayList<Shape> shapes = couche.getShapes();

        shapeList.getItems().clear();

        for (Shape s: shapes) {
            HBox hb = new HBox();
            hb.setSpacing(10);
            Label shapeName = new Label(s.getName());
            JFXColorPicker cp = new JFXColorPicker();
            cp.setStyle("-fx-color-label-visible: false ;");
            CouleurInterv couleur = s.getColor();
            int r = couleur.getRGB().get("r");
            int g = couleur.getRGB().get("g");
            int b = couleur.getRGB().get("b");
            cp.setValue(Color.rgb(r, g, b));

            cp.setOnAction(e -> {
                Color color = cp.getValue();
                int red = (int)(color.getRed()*255);
                int green = (int)(color.getGreen()*255);
                int blue = (int)(color.getBlue()*255);
                int  o = (int)(color.getOpacity()*100);
                CouleurInterv c = new CouleurInterv("", red, green, blue, o);

                System.out.println(c.toString());
                ShapeDrawer drawer = Principale2Controller.getDrawers().get(coucheName);
                drawer.changeColor(c, s.getName());
            });

            hb.getChildren().addAll(cp, shapeName);
            shapeList.getItems().add(hb);
        }

    }
}
