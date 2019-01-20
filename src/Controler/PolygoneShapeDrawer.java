package Controler;

import Model.CouleurInterv;
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

    ArrayList<Point> points = new ArrayList<>();
    CouleurInterv couleur;

    public PolygoneShapeDrawer(String coucheName,Sig sig, Canvas canvas) {
        super(coucheName, sig, canvas);
    }

    @Override
    public void draw(MouseEvent e) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
//        gc.setStroke(Color.BLUE);

        int x = (int)Principale2Controller.cleanValue(e.getX());
        int y = (int)Principale2Controller.cleanValue(e.getY());

        if (e.getButton() == MouseButton.PRIMARY){

            if (points.isEmpty()){
//                System.out.println("begining path");
                gc.beginPath();
                gc.moveTo(x, y);

                // set the color
                Symbologie sym = sig.get(coucheName).getSym();
                couleur = sym.getCouleur();
                int r = couleur.getRGB().get("r");
                int g = couleur.getRGB().get("g");
                int b = couleur.getRGB().get("b");
                int o = couleur.getRGBA().get("o");

                gc.setFill(Color.rgb(r, g, b, ((double)o)/100));
                gc.setStroke(Color.rgb(r, g, b));
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

            //points.add(firstPoint);

            if (addToCouche()){
                gc.fill();
            }
            gc.closePath();
            points.clear();
        }

    }

    @Override
    public void reDrawAll(){
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        CouchePolygone couche = (CouchePolygone)sig.get(coucheName);
        List<Polygone> listPolys = couche.getPolys();

        double x, y;


        for(Polygone p : listPolys){
            Point init = p.getPoint(0);
            gc.beginPath();
            x = init.getX();
            y = init.getY();
            gc.moveTo(x, y);

            CouleurInterv c = p.getColor();
            int r = c.getRGB().get("r");
            int g = c.getRGB().get("g");
            int b = c.getRGB().get("b");
            int o = c.getRGBA().get("o");
            gc.setFill(Color.rgb(r, g, b, ((double)o)/100));
            gc.setStroke(Color.rgb(r, g, b));

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
            gc.lineTo(init.getX(), init.getY());
            gc.stroke();
            gc.closePath();
            gc.fill();
        }
    }

    @Override
    public boolean addToCouche() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Nommage");
        dialog.setContentText("Donnez un nom à la forme :");

        CouchePolygone couche = (CouchePolygone)sig.get(coucheName);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            /*Polygone pol = new Polygone(result.get(), couleur);
            for(Point p : points){
                pol.add(p);
            }
            sig.addPolygone(coucheName, pol);*/
            sig.addPolygone(coucheName,result.get(),points);
            sig.getPolygone(coucheName,result.get()).setColor(couleur);
//            couche.add(pol);
            System.out.println("Polygone added to " + couche.getName());
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
        CouchePolygone couche = (CouchePolygone)sig.get(coucheName);

        if (points.isEmpty()){
            Polygone dernierPolygone = couche.getLast();
            if (dernierPolygone != null){
                points = dernierPolygone.getPoints();
                couleur = dernierPolygone.getColor();
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

            int r = couleur.getRGB().get("r");
            int g = couleur.getRGB().get("g");
            int b = couleur.getRGB().get("b");
            int o = couleur.getRGBA().get("o");

            gc.setFill(Color.rgb(r, g, b, ((double)o)/100));
            gc.setStroke(Color.rgb(r, g, b));

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

    @Override
    public void changeColor(CouleurInterv c, String shapeName){

        Polygone polygone = sig.getPolygone(coucheName, shapeName);
        polygone.setColor(c);
        reDrawAll();
        redrawCurrent();
    }
}