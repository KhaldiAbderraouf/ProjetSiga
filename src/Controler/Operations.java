package Controler;

import java.util.ArrayList;
import java.util.Random;

import Model.Point;

/**
 * Created by ACER E1 on 28/12/2018.
 */
public class Operations implements JTS {

    public int intersectsegment(Point A,Point B,Point I,Point P){
        Vecteur D= new Vecteur(),E= new Vecteur();
        D.setX( B.getX() - A.getX());
        D.setY(B.getY() - A.getY());
        E.setX( P.getX() - I.getX());
        E.setY(P.getY() - I.getY());
        double denom = D.getX()*E.getY() - D.getY()*E.getX();
        if (denom==0)
            return -1;   // erreur, cas limite
        double t = - (A.getX()*E.getY()-I.getX()*E.getY()-E.getX()*A.getY()+E.getX()*I.getY()) / denom;
        if (t<0 || t>=1)
            return 0;
        double u = - (-D.getX()*A.getY()+D.getX()*I.getY()+D.getY()*A.getX()-D.getY()*I.getX()) / denom;
        if (u<0 || u>=1)
            return 0;
        return 1;
    }

    public boolean inclusionPoint(ArrayList<Point> poly,int nbp,Point P){
        Random rn= new Random();
        int i;
        Point I= new Point(10000 + rn.nextInt(100),10000 + rn.nextInt(100));
        // 10000 + un nombre aléatoire entre 0 et 99

        int nbintersections = 0;
        for(i=0;i<nbp;i++)
        {
            Point A = poly.get(i);
            Point B;

            B = poly.get((i+1)%nbp);

            int iseg = intersectsegment(A,B,I,P);
            if (iseg == -1)
                return inclusionPoint(poly,nbp,P);  // cas limite, on relance la fonction.
            nbintersections+=iseg;
        }
        // nbintersections est-il impair ?
        return (nbintersections%2==1);
    }

    public boolean inclusionShape(ArrayList<Point> poly, Point... P){
        boolean oui=true;
        for(int i=0;i<P.length;i++){
            if(!(inclusionPoint(poly,poly.size(),P[i]))){return false;}
        }
        return  oui;
    }

    public boolean intersection(ArrayList<Point> poly,Point... P){
        for(int i=0;i<P.length;i++){
            if(inclusionPoint(poly,poly.size(),P[i])){return true;}
        }
        return false;
    }

    public boolean adjacent(ArrayList<Point> poly,ArrayList<Point> tab2){
        boolean oui=false;
        for (Point p:poly) {
            oui=tab2.contains(p);
        }
        return oui;
    }



}

class Vecteur {
    private float x,y;
    public float getX(){return x;}
    public float getY(){return y;}
    public void setX(int x){this.x= x;}
    public void setY(int y){this.y= y;}
}
