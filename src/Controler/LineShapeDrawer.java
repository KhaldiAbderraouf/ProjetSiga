package Controler;

import Model.Ligne;
import Model.Point;
import Model.PointNomer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LineShapeDrawer extends ShapeDrawer {

    List<Point> points = new ArrayList<>();

    public LineShapeDrawer(String coucheName,Sig sig, Canvas canvas) {
        super(coucheName, sig, canvas);
    }


    @Override
    public void draw(MouseEvent e) {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLUE);
        gc.setFill(Color.BLUE);

        if (e.getButton() == MouseButton.PRIMARY){
            int x = (int)Principale2Controller.cleanValue(e.getX());
            int y = (int)Principale2Controller.cleanValue(e.getY());

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
            gc.fillOval(x-pointSize/2, y - pointSize/2, pointSize, pointSize);
            points.add(new Point(x, y));
        }
        else{
            System.out.println("closing path");

            if (addToCouche()){

            }
            gc.closePath();
            points.clear();
        }

    }

    @Override
    public void reDrawAll() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // clear all lines in canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        CoucheLigne couche = (CoucheLigne)sig.getCouche(coucheName);
        List<Ligne> listLigne = couche.getLignes();

        for(Ligne l : listLigne ){
//            printing the first point
            Point init = l.getPoint(0);
            gc.beginPath();
            gc.moveTo(init.getX(), init.getY());
            gc.stroke();
            gc.fillOval(init.getX() - pointSize/2, init.getY() - pointSize/2, pointSize, pointSize);

//            printing all the remaining point
            for (int i = 1; i<l.getlenght(); i++){
                Point current = l.getPoint(i);
                gc.lineTo(current.getX(), current.getY());
                gc.stroke();
                gc.fillOval(current.getX() - pointSize/2, current.getY() - pointSize/2, pointSize, pointSize);
            }
//            end of the line
            gc.closePath();
        }

    }

    @Override
    public boolean addToCouche(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Nommage");
        dialog.setContentText("Donnez un nom à la forme :");

        CoucheLigne couche = (CoucheLigne)sig.getCouche(coucheName);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Ligne ligne = new Ligne(result.get());
            for(Point p : points){
                ligne.add(p);
            }
            sig.addLigne(coucheName, ligne);
//            couche.add(ligne);
            System.out.println("Ligne added to " + couche.getName());
            return true;
        }
        else{
            reDrawAll();
            return false;
        }

    }

    @Override
    public void cancel() {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        CoucheLigne couche = (CoucheLigne)sig.getCouche(coucheName);

        if (points.isEmpty()){
            Ligne derniereLigne = couche.getLast();
            if (derniereLigne != null){
                points = derniereLigne.getPoints();
                couche.remove(derniereLigne);
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

