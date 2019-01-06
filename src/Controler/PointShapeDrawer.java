package Controler;

import Model.Point;
import Model.PointNomer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Optional;

public class PointShapeDrawer extends ShapeDrawer {

    private Point currentPoint;

    public PointShapeDrawer(String coucheName,Sig sig, Canvas canvas) {
        super(coucheName, sig, canvas);
    }

    @Override
    public void draw(MouseEvent e) {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        int x = approximateCoordinate(e.getX());
        int y = approximateCoordinate(e.getY());

        currentPoint = new Point(x, y);

        gc.fillOval(x - pointSize/2, y - pointSize/2, this.pointSize, this.pointSize);

        addToCouche();
    }

    @Override
    public void reDrawAll() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // clear all the shapes from canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // redraw all shapes (only ones added to couche)
        CouchePoint couche = (CouchePoint) sig.getCouche(coucheName);
        ArrayList<PointNomer> listPoints = couche.getPoints();

        gc.setFill(Color.BLUE);
        for(PointNomer p: listPoints){
            gc.fillOval(p.getX() - pointSize/2, p.getY() - pointSize/2, pointSize, pointSize);
        }
    }

    @Override
    public void addToCouche(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Nommage");
        dialog.setContentText("Donnez un nom à la forme :");

        CouchePoint couche = (CouchePoint) sig.getCouche(coucheName);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("PointNomer added to " + couche.getName());
            sig.addPoint(coucheName, new PointNomer(currentPoint, result.get()));
//            couche.add(new PointNomer(currentPoint, result.get()));
        }
        else {
           reDrawAll();
        }

    }
}
