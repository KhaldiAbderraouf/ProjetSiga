package Controler;

import Model.CouleurInterv;
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
    CouleurInterv c;
    public PointShapeDrawer(String coucheName,Sig sig, Canvas canvas) {
        super(coucheName, sig, canvas);
    }

    @Override
    public void draw(MouseEvent e) {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();

        Symbologie sym = this.sig.get(coucheName).getSym();
        c = sym.getCouleur();
        int r = c.getRGB().get("r");
        int g = c.getRGB().get("g");
        int b = c.getRGB().get("b");

        gc.setFill(Color.rgb(r, g, b));
        int x = (int)Principale2Controller.cleanValue(e.getX());
        int y = (int)Principale2Controller.cleanValue(e.getY());

//        System.out.println("Inserting x = " + x + "| y = " + y);
//        System.out.println("Drawing x = " + e.getX() + " y = " + e.getY());
        currentPoint = new Point(x,y);

        gc.fillOval(x - pointSize/2, y - pointSize/2, this.pointSize, this.pointSize);

        addToCouche();
    }

    @Override
    public void reDrawAll() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // clear all the shapes from canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // redraw all shapes (only ones added to couche)
        CouchePoint couche = (CouchePoint) sig.get(coucheName);
        ArrayList<PointNomer> listPoints = couche.getPoints();


//        gc.setFill(Color.BLUE);
        for(PointNomer p: listPoints){
            double x = (p.getX() - pointSize/2);
            double y = (p.getY() - pointSize/2);

            CouleurInterv c = p.getColor();

            int r = c.getRGB().get("r");
            int g = c.getRGB().get("g");
            int b = c.getRGB().get("b");

            gc.setFill(Color.rgb(r, g, b));
            gc.fillOval(x, y, pointSize, pointSize);
        }
    }

    @Override
    public boolean addToCouche(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Nommage");
        dialog.setContentText("Donnez un nom à la forme :");

        //CouchePoint couche = (CouchePoint) sig.get(coucheName);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
//            System.out.println("PointNomer added to " + couche.getName());
        	ArrayList<Point> ps = new ArrayList<Point>();
        	ps.add(currentPoint);
            sig.addPoint(coucheName,result.get(),ps);
            sig.getPoint(coucheName,result.get()).setColor(c);
            System.out.println("Added point x = "+ currentPoint.getX() + "| y = " + currentPoint.getY());
            return true;
        }
        else {
            reDrawAll();
            return false;
        }
    }

    @Override
    public void cancel() {
        //remove last point from couche
        CouchePoint couche = (CouchePoint)sig.get(coucheName);
        if (!couche.isEmpty()){
            couche.removeLast();
            //redraw couche
            reDrawAll();
        }
    }

    @Override
    public void changeColor(CouleurInterv c, String shapeName) {

        PointNomer p = sig.getPoint(coucheName, shapeName);
        p.setColor(c);
        reDrawAll();

    }
}