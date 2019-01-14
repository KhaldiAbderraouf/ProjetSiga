package Controler;

import Model.Ligne;
import Model.Point;
import Model.Polygone;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PolygoneShapeDrawer extends ShapeDrawer {

    List<Point> points = new ArrayList<>();

    public PolygoneShapeDrawer(String coucheName,Sig sig, Canvas canvas) {
        super(coucheName, sig, canvas);
    }

    @Override
    public void draw(MouseEvent e) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);

        int x = (int)Principale2Controller.cleanValue(e.getX());
        int y = (int)Principale2Controller.cleanValue(e.getY());

        if (e.getButton() == MouseButton.PRIMARY){

            if (points.isEmpty()){
//                System.out.println("begining path");
                gc.beginPath();
                gc.moveTo(x, y);
            }
            else{
//                System.out.println("drawing");
                gc.lineTo(x, y);
            }
            gc.stroke();
            gc.fillOval(x - pointSize/2, y - pointSize/2, pointSize, pointSize);
            points.add(new Point(x, y));
        }
        else {
            Point firstPoint = points.get(0);
            gc.lineTo(firstPoint.getX(), firstPoint.getY());
            gc.stroke();

            points.add(firstPoint);

            addToCouche();
            gc.closePath();
            gc.fill();
            points.clear();
        }

    }

    @Override
    public void reDrawAll(){
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        CouchePolygone couche = (CouchePolygone)sig.getCouche(coucheName);
        List<Polygone> listPolys = couche.getPolys();

        double x, y;


        for(Polygone p : listPolys){
            Point init = p.getPoint(0);
            gc.beginPath();
            x = init.getX();
            y = init.getY();
            gc.moveTo(x, y);
            gc.stroke();
            gc.fillOval(x - pointSize/2, y - pointSize/2, pointSize, pointSize);

            for (int i = 1; i<p.getlenght(); i++){
                Point current = p.getPoint(i);
                x = current.getX();
                y = current.getY();
                gc.lineTo(x, y);
                gc.stroke();
                gc.fillOval(x - pointSize/2, y - pointSize/2, pointSize, pointSize);
            }
//            end of the line
            gc.closePath();
            gc.fill();
        }
    }

    @Override
    public void addToCouche() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Nommage");
        dialog.setContentText("Donnez un nom à la forme :");

        CouchePolygone couche = (CouchePolygone)sig.getCouche(coucheName);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Polygone pol = new Polygone(result.get());
            for(Point p : points){
                pol.add(p);
            }
            sig.addPolygone(coucheName, pol);
//            couche.add(pol);
            System.out.println("Polygone added to " + couche.getName());
        }
        else{
            reDrawAll();
        }
    }

    @Override
    public void cancel() {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        CouchePolygone couche = (CouchePolygone)sig.getCouche(coucheName);

        if (points.isEmpty()){
            Polygone dernierPolygone = couche.getLast();
            if (dernierPolygone != null){
                points = dernierPolygone.getPoints();
                couche.remove(dernierPolygone);
            }
        }
        // currently drawing the line => not saved yet
        if (points.size() > 0)
            points.remove(points.size() - 1);
        // clear and redraw everything => Best algorithme ever XD
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        reDrawAll();
        redrawCurrent();

    }

    private void redrawCurrent(){

        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (! points.isEmpty()){
            Point init = points.get(0);
            gc.beginPath();
            gc.moveTo(init.getX(), init.getY());
            gc.stroke();
            gc.fillOval(init.getX() - pointSize/2, init.getY() - pointSize/2, pointSize, pointSize);

//            printing all the remaining point
            for (int i = 1; i<points.size(); i++){
                Point current = points.get(i);
                gc.lineTo(current.getX(), current.getY());
                gc.stroke();
                gc.fillOval(current.getX() - pointSize/2, current.getY() - pointSize/2, pointSize, pointSize);
            }
        }

    }
}