package Controler;


import javafx.scene.canvas.Canvas;

public abstract class Drawer {
    Canvas canvas;
    Couche couche;

    public Drawer(Couche couche, Canvas canvas){
        this.couche = couche;
        this.canvas = canvas;
    }

    public abstract void draw(double x, double y);

}
