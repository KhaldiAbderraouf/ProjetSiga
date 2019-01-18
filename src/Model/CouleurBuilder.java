package Model;

public class CouleurBuilder {
    int nombreCouleur;

    public CouleurBuilder(){
        nombreCouleur = 1;
    }

    public Couleur creerCouleurIntervale(){
        Couleur couleur = null;
        int r = getRandomDegreeColor();
        int g = getRandomDegreeColor();
        int b = getRandomDegreeColor();
        couleur = new CouleurInterv("couleur "+nombreCouleur,r,g,b);
        nombreCouleur ++;
        return  couleur;
    }

    private int getRandomDegreeColor(){
        return (int)(Math.random() *255) % 255;
    }


}