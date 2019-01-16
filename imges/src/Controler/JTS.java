package Controler;

import Model.Point;

import java.util.ArrayList;

public interface JTS {
    public int intersectsegment(Point A, Point B, Point I, Point P);
    public boolean inclusionPoint(ArrayList<Point> poly, int nbp, Point P);
    public boolean inclusionShape(ArrayList<Point> poly, Point... P);
    public boolean intersection(ArrayList<Point> poly,Point... P);
    public boolean adjacent(ArrayList<Point> poly,ArrayList<Point> tab2);
}
