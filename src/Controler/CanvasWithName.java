package Controler;

import javafx.scene.canvas.Canvas;

public class CanvasWithName extends Canvas {
    private String name;

    public CanvasWithName(Double width ,double height,String name){
        super(width,height);
        this.name = name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
