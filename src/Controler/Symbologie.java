package Controler;

import java.util.ArrayList;
import java.util.List;

import Model.BDD;
import Model.Couleur;

public class Symbologie {
	private List<Couleur> couleurs=new ArrayList<Couleur>();
	private long id =0;
	private String nom;

    public void dbSave(long idCouche) {
        if(id == 0)
            this.dbAjouter(idCouche);
        else
            this.Modifier();
        for (Couleur couleur: couleurs) {
            couleur.dbSave(id);
        }
    }


    private void Modifier() {
        String query = "UPDATE Symbologie SET Nom = ? WHERE ID = ?";
        ArrayList<String> args = new ArrayList<String>();
        args.add(this.nom);
        args.add(String.valueOf(this.id));
        BDD.execute(query, args);
    }

    private void dbAjouter(long idCouche) {
        String query = "INSERT INTO Symbologie VALUES(null, ?, ?)";
        List<String> args = new ArrayList<String>();
        args.add(this.nom);
        args.add(String.valueOf(idCouche));
        id = BDD.execute(query, args);
    }
}
