package Controler;

import Model.CouleurInterv;
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

    ArrayList<Point> points = new ArrayList<>();
    CouleurInterv couleur;

    public LineShapeDrawer(String coucheName,Sig sig, Canvas canvas) {
        super(coucheName, sig, canvas);
    }


    @Override
    public void draw(MouseEvent e) {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
//        gc.setStroke(Color.BLUE);
//        gc.setFill(Color.BLUE);

        if (e.getButton() == MouseButton.PRIMARY){
            int x = (int)Principale2Controller.cleanValue(e.getX());
            int y = (int)Principale2Controller.cleanValue(e.getY());

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

                gc.setFill(Color.rgb(r, g, b));
                gc.setStroke(Color.rgb(r, g, b));

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

            addToCouche();
            gc.closePath();
            points.clear();
        }

    }

    @Override
    public void reDrawAll() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // clear all lines in canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        CoucheLigne couche = (CoucheLigne)sig.get(coucheName);
        List<Ligne> listLigne = couche.getLignes();

        for(Ligne l : listLigne ){
//            printing the first point
            Point init = l.getPoint(0);
            gc.beginPath();
            gc.moveTo(init.getX(), init.getY());
            gc.stroke();
            gc.fillOval(init.getX() - pointSize/2, init.getY() - pointSize/2, pointSize, pointSize);

//            Symbologie sym = couche.getSym();
            CouleurInterv c = l.getColor();
            int r = c.getRGB().get("r");
            int g = c.getRGB().get("g");
            int b = c.getRGB().get("b");

            gc.setFill(Color.rgb(r, g, b));
            gc.setStroke(Color.rgb(r, g, b));
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

        CoucheLigne couche = (CoucheLigne)sig.get(coucheName);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            /*Ligne ligne = new Ligne(result.get(), couleur);
            for(Point p : points){
                ligne.add(p);
            }*/
            sig.addLigne(coucheName,result.get(),points);
            sig.getLigne(coucheName,result.get()).setColor(couleur);
            //sig.addLigne(coucheName, ligne);
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
        CoucheLigne couche = (CoucheLigne)sig.get(coucheName);

        if (points.isEmpty()){
            Ligne derniereLigne = couche.getLast();
            if (derniereLigne != null){
                points = derniereLigne.getPoints();
                couleur = derniereLigne.getColor();
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

    @Override
    public void changeColor(CouleurInterv c, String shapeName) {
        Ligne l = sig.getLigne(coucheName, shapeName);
        l.setColor(c);
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

            gc.setFill(Color.rgb(r, g, b));
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
}

