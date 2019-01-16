package Controler;

import Model.Point;

import java.util.ArrayList;

public interface JTS {
    public int intersectsegment(Point A, Point B, Point I, Point P);
    public boolean inclusionPointPoly(ArrayList<Point> poly, int nbp, Point P);
    public boolean inclusionPointLigne(ArrayList<Point> poly, int nbp, Point P);
    public boolean inclusionShape(ArrayList<Point> poly, ArrayList<Point> P);
    public boolean intersection(ArrayList<Point> poly,ArrayList<Point> P);
    public boolean adjacent(ArrayList<Point> poly,ArrayList<Point> tab2);
	public int isInSegment(Point a, Point b, Point i);
	public int[][] topoPolyPoly(ArrayList<Point> a, ArrayList<Point> b);
	public int[][] topoPolyLigne(ArrayList<Point> a, ArrayList<Point> b);
	public int[][] topoPolyPoint(ArrayList<Point> a,Point b);
	public int[][] topoLigneLigne(ArrayList<Point> ligne1,ArrayList<Point> ligne2);
	public int[][] topoLignePoint(ArrayList<Point> ligne1,Point p);
	public int[][] topoPointPoint(Point p1,Point p);
}
