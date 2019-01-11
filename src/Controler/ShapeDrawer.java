package Controler;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

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
    public abstract void cancel();

//  Returns the closest multiple to pointSize of v
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
