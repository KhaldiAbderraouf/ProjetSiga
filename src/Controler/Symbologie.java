package Controler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.BDD;
import Model.Couleur;
import Model.CouleurBuilder;
import Model.CouleurInterv;

public class Symbologie {

	private List<Couleur> couleurs=new ArrayList<Couleur>();
	private long id =0;
	private String nom;
	private CouleurBuilder couleurBuilder = new CouleurBuilder();
	private int defaultNbColors = 50;
	private int nbColors = 0;
	private int currentColor = 0;

	public Symbologie(String nom, List<Couleur> couleurs){
		this.nom = nom;
		this.couleurs = couleurs;
		for (int i = 0; i < defaultNbColors; i++){
			ajouterCouleur();
		}
	}

	public Symbologie(){ }

	public void ajouterCouleur(){
		this.couleurs.add(couleurBuilder.creerCouleurIntervale());
		nbColors++;
	}
	public Couleur getCouleur(int i){
		if (couleurs.size() > i)
			return (CouleurInterv)couleurs.get(i);
		return null;
	}

	public CouleurInterv getCouleur(){
		if (currentColor < nbColors)
			return (CouleurInterv)couleurs.get(currentColor++);

		ajouterCouleur();
		return getCouleur();
	}

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

	public static Symbologie dbFetchWithId(long id) {
		String query = "SELECT * FROM Symbologie WHERE ID = ?";
		List<String> args = new ArrayList<String>();
		args.add(String.valueOf(id));
		ResultSet rs = BDD.fetch(query, args);
		Symbologie symbologie = null;
		try {
			if(rs.next()){
				List<Couleur> couleurs = CouleurInterv.dbFetchWithIdSym(id);
				symbologie = new Symbologie(rs.getString("Nom"), couleurs);
				symbologie.id = rs.getLong("ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return symbologie;
	}


}