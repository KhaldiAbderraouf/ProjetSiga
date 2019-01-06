package Controler;


import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;


public abstract class ShapeDrawer {
    Canvas canvas;
    String coucheName;
    Sig sig;
    int pointSize = 5;

    public ShapeDrawer(String coucheName,Sig sig, Canvas canvas){
        this.coucheName = coucheName;
        this.canvas = canvas;
        this.sig = sig;
    }

    public abstract void draw(MouseEvent e);
    public abstract void reDrawAll();
    public abstract void addToCouche();

//    Returns the closest multiple of pointSize of v
    int approximateCoordinate(double v){
        int n = (int)v;
        if (n%pointSize > pointSize - n%pointSize){
            return n + pointSize - n%pointSize;
        }
        else {
            return n - n%pointSize;
        }
    }


}
