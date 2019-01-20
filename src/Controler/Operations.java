package Controler;

import java.util.ArrayList;
import java.util.Random;

import Model.Point;

public class Operations implements JTS {

    public int intersectsegment(Point A, Point B, Point I, Point P) {
        Vecteur D = new Vecteur(), E = new Vecteur();
        D.setX(B.getX() - A.getX());
        D.setY(B.getY() - A.getY());
        E.setX(P.getX() - I.getX());
        E.setY(P.getY() - I.getY());
        double denom = D.getX() * E.getY() - D.getY() * E.getX();
        if (denom == 0)
            return -1; // erreur, cas limite
        double t = -(A.getX() * E.getY() - I.getX() * E.getY() - E.getX() * A.getY() + E.getX() * I.getY()) / denom;

        if (t < 0 || t >= 1){
            return 0;
        }
        double u = -(-D.getX() * A.getY() + D.getX() * I.getY() + D.getY() * A.getX() - D.getY() * I.getX()) / denom;

        if (u < 0 || u >= 1){
            return 0;
        }
        return 1;
    }

    public int isInSegment(Point A, Point B, Point I){
        if((B.getY()!=A.getY())&&(I.getY()!=A.getY())){
            int i=I.getX()-A.getX();
            int j=I.getY()-A.getY();
            int a=B.getX()-A.getX();
            int b=B.getY()-A.getY();
            float x,y;
            x=(float)a/b;y= (float)i/j;
            boolean t =(x==y);
            if (t) {
                // M est sur la droite formée par A-B
                //System.out.println(I.getX()+","+I.getY()+";"+B.getX()+","+B.getY()+";"+A.getX()+","+A.getY());
                if((I.getX()<=B.getX() && I.getX()>=A.getX())||(I.getX()<=A.getX() && I.getX()>=B.getX())) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        }
        else{
            if(B.getY()==A.getY()){
                if((I.getY()==A.getY())&&((I.getX()<=B.getX() && I.getX()>=A.getX())||(I.getX()<=A.getX() && I.getX()>=B.getX()))) {
                    return 1;
                } else {
                    return 0;
                }
            }else{
                if((I.getY()!=B.getY())){
                    int i=B.getX()-I.getX();
                    int j=B.getY()-I.getY();
                    int a=B.getX()-A.getX();
                    int b=B.getY()-A.getY();
                    float x,y;
                    x=(float)a/b;y= (float)i/j;
                    boolean t =(x==y);

                    if (t) {
                        // M est sur la droite formée par A-B
                        //System.out.println(I.getX()+","+I.getY()+";"+B.getX()+","+B.getY()+";"+A.getX()+","+A.getY());
                        if((I.getX()<=B.getX() && I.getX()>=A.getX())||(I.getX()<=A.getX() && I.getX()>=B.getX())) {
                            return 1;
                        } else {
                            return 0;
                        }
                    } else {
                        return 0;
                    }
                }else{
                    if((I.getX()<=B.getX() && I.getX()>=A.getX())||(I.getX()<=A.getX() && I.getX()>=B.getX())) {
                        return 1;
                    } else {
                        return 0;
                    }
                }

            }
        }
    }

    public boolean inclusionPointPoly(ArrayList<Point> poly, int nbp, Point P) {
        Random rn = new Random();
        int i;
        Point I = new Point(10000 + rn.nextInt(100), 10000 + rn.nextInt(100));
        // 10000 + un nombre alÃ©atoire entre 0 et 99

        int nbintersections = 0;
        for (i = 0; i < nbp; i++) {
            Point A = poly.get(i);
            Point B;

            B = poly.get((i + 1) % nbp);

            int iseg = intersectsegment(A, B, I, P);
            if (iseg == -1)
                return inclusionPointPoly(poly, nbp, P); // cas limite, on relance la fonction.
            nbintersections += iseg;
        }
        // nbintersections est-il impair ?
        return (nbintersections % 2 == 1);
    }

    public boolean inclusionPointLigne(ArrayList<Point> poly, int nbp, Point P) {
        Random rn = new Random();
        int i;
        //Point I = new Point(10000 + rn.nextInt(100), 10000 + rn.nextInt(100));
        // 10000 + un nombre alÃ©atoire entre 0 et 99

        int nbintersections = 0;
        for (i = 0; i < nbp-1; i++) {
            Point A = poly.get(i);
            Point B;

            B = poly.get((i + 1));

            int iseg = isInSegment(A, B, P);
            if (iseg == -1)
                return inclusionPointLigne(poly, nbp, P); // cas limite, on relance la fonction.
            nbintersections += iseg;
        }
        // nbintersections est-il impair ?
        return (nbintersections >= 1);
    }

    public boolean inclusionShape(ArrayList<Point> poly, ArrayList<Point> P) {
        boolean oui = true;
        for (int i = 0; i < P.size(); i++) {
            if (!(inclusionPointPoly(poly, poly.size(), P.get(i)))) {
                return false;
            }
        }
        return oui;
    }

    public boolean intersection(ArrayList<Point> poly, ArrayList<Point> P) {
        for (int i = 0; i < P.size(); i++) {
            if (inclusionPointPoly(poly, poly.size(), P.get(i))) {
                return true;
            }
        }
        return false;
    }
    public boolean intersectionL(ArrayList<Point> poly, ArrayList<Point> P) {
        for (int i = 0; i < P.size(); i++) {
            if (inclusionPointLigne(poly, poly.size(), P.get(i))) {
                return true;
            }
        }
        return false;
    }
    public int isInSegments(ArrayList<Point> poly, Point p){
        int cpt=0;
        for (int i = 0; i < poly.size(); i++) {
            Point A = poly.get(i);
            Point B;

            B = poly.get((i + 1) % poly.size());
            int iseg = isInSegment(A, B, p);
            if (iseg == 1){

                System.out.println("("+p.getX()+","+p.getY()+");("+A.getX()+","+A.getY()+");("+B.getX()+","+B.getY()+")");
                cpt++;
            }
        }
        return cpt;
    }
    public boolean adjacent(ArrayList<Point> poly, ArrayList<Point> tab2) {
        boolean oui = false;
        int cpt=0;
        for (Point p : tab2) {
            boolean b=(poly.contains(p));
            if(b||(isInSegments(poly,p)>=1)){
                cpt++;
            }
        }
        return (cpt>1);
    }
    public boolean point_en_commun(ArrayList<Point> poly, ArrayList<Point> tab2) {
        boolean oui = false;
        int cpt=0;
        for (Point p : tab2) {
            if((poly.contains(p))|| (isInSegments(poly,p)>0)){
                System.out.println("("+p.getX()+","+p.getY()+")");
                cpt++;
            }
        }
        return (cpt>0);
    }

    public int[][] remplirres(int... l){
        if(l.length==9){
            int[][] res = new int[3][3];
            for(int i=0;i<9;i++){
                res[i/3][i%3]=l[i];
            }
            return res;
        }
        else return null;
    }

    public int[][] topoPolyPoly(ArrayList<Point> poly1,ArrayList<Point> poly2){
        int[][] res = new int[3][3];


        if(intersection(poly1,poly2)){
            res=remplirres(2,1,2,1,0,1,2,1,2);
        }else{
            if(adjacent(poly1,poly2)){
                res=remplirres(-1,-1,2,-1,0,1,2,1,2);
            }else{
                res=remplirres(-1,-1,2,-1,-1,1,2,1,2);
            }
        }

        return res;
    }

    public int[][] topoPolyLigne(ArrayList<Point> poly1,ArrayList<Point> ligne){
        int[][] res = new int[3][3];


        if(intersection(poly1,ligne)){
            res=remplirres(1,0,1,1,0,1,2,1,2);
        }else{
            if(point_en_commun(poly1,ligne)){
                res=remplirres(-1,0,1,-1,0,1,2,1,2);
            }else{
                res=remplirres(-1,-1,1,-1,-1,1,2,1,2);
            }
        }

        return res;
    }

    public int[][] topoPolyPoint(ArrayList<Point> poly1,Point p){
        int[][] res = new int[3][3];

        if(inclusionPointLigne(poly1,poly1.size(),p)){
            res=remplirres(-1,0,-1,-1,0,-1,2,1,2);

        }else{
            if(inclusionPointPoly(poly1,poly1.size(),p)){
                res=remplirres(0,0,-1,0,0,-1,2,1,2);
            }else{
                res=remplirres(-1,-1,0,-1,-1,0,2,1,2);
            }
        }

        return res;
    }

    public int[][] topoLigneLigne(ArrayList<Point> ligne1,ArrayList<Point> ligne2){
        int[][] res = new int[3][3];

        if(adjacent(ligne1,ligne2)){
            res=remplirres(1,1,1,1,1,1,1,1,2);

        }else{
            if(intersectionL(ligne1,ligne2)){
                res=remplirres(0,0,1,0,0,1,1,1,2);
            }else{
                res=remplirres(-1,-1,1,-1,-1,1,1,1,2);
            }
        }

        return res;
    }

    public int[][] topoLignePoint(ArrayList<Point> ligne1,Point p){
        int[][] res = new int[3][3];

        if(inclusionPointLigne(ligne1,ligne1.size(), p)){
            res=remplirres(0,0,-1,0,0,-1,1,1,2);
        }
        else{
            res=remplirres(-1,-1,0,-1,-1,0,1,1,2);
        }

        return res;
    }

    public int[][] topoPointPoint(Point p1,Point p){
        int[][] res = new int[3][3];

        if(p.getX()==p1.getX() && p.getY()==p.getY()){
            res=remplirres(0,0,-1,0,0,-1,-1,-1,2);
        }
        else{
            res=remplirres(-1,-1,0,-1,-1,0,0,0,2);
        }

        return res;
    }

    public int area(ArrayList<Point> poly){
        int A=0;
        int n=poly.size();
        for(int i=0;i<n;i++){
            A+=((poly.get(i).getX()*poly.get((i+1)%n).getY())-(poly.get((i+1)%n).getX()*poly.get(i).getY()));
        }
        if(A<0){A=-A;}
        return (A/2);
    }

    public int perimetre(ArrayList<Point> poly){
        double p=0;
        int n=poly.size();
        for(int i=0;i<n;i++){
            p+=lenght(poly.get(i),poly.get((i+1)%n));
        }
        return ((int)p);
    }

    public int longeur(ArrayList<Point> poly){
        double p=0;
        int n=poly.size()-1;
        for(int i=0;i<n;i++){
            p+=lenght(poly.get(i),poly.get(i+1));
        }
        return ((int)p);
    }

    public double lenght(Point a, Point b){
        return Math.sqrt(Math.pow(a.getX()-b.getX(), 2)+Math.pow(a.getY()-b.getY(), 2));
    }

}

class Vecteur {
    private float x, y;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}