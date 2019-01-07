package Controler;

import Model.Ligne;
import Model.Point;
import Model.Polygone;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextInputDialog;
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

        int x = approximateCoordinate(e.getX());
        int y = approximateCoordinate(e.getY());

        if(points.isEmpty()){
            System.out.println("Begining path");
            gc.beginPath();
            gc.moveTo(e.getX(), e.getY());
            gc.stroke();

            gc.fillOval(x-pointSize/2, y - pointSize/2, pointSize, pointSize);
            points.add(new Point(x, y));
        }
        else{
            System.out.println("Drawing");
            gc.lineTo(x, y);
            gc.stroke();

            gc.fillOval(x-pointSize/2, y - pointSize/2, pointSize, pointSize);
            points.add(new Point(x, y));

            Point firstPoint = points.get(0);
            if (x == firstPoint.getX() && y == firstPoint.getY()){
                System.out.println("Closing path");

                addToCouche();
                gc.closePath();
                gc.fill();
                points.clear();
            }

        }

    }

    @Override
    public void reDrawAll(){
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        CouchePolygone couche = (CouchePolygone)sig.getCouche(coucheName);
        List<Polygone> listPolys = couche.getPolys();

        for(Polygone p : listPolys){
            Point init = p.getPoint(0);
            gc.beginPath();
            gc.moveTo(init.getX(), init.getY());
            gc.stroke();
            gc.fillOval(init.getX() - pointSize/2, init.getY() - pointSize/2, pointSize, pointSize);

            for (int i = 1; i<p.getlenght(); i++){
                Point current = p.getPoint(i);
                gc.lineTo(current.getX(), current.getY());
                gc.stroke();
                gc.fillOval(current.getX() - pointSize/2, current.getY() - pointSize/2, pointSize, pointSize);
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
}